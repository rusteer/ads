package com.ads.controller;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import workspace.bean.AbstractAdsProperty;
import workspace.bean.Ads;
import workspace.bean.AdsBanner;
import workspace.bean.AdsPop;
import workspace.bean.AdsPush;
import workspace.bean.AdsShortcut;
import workspace.bean.Config;
import workspace.bean.Response;
import workspace.bean.form.AdsForm;
import workspace.bean.form.Methods;
import workspace.bean.form.ReportForm;
import workspace.bean.framework.Json;
import com.ads.entity.AbstractAdsConfigEntity;
import com.ads.entity.AdsBannerEntity;
import com.ads.entity.AdsEntity;
import com.ads.entity.AdsPopEntity;
import com.ads.entity.AdsPushEntity;
import com.ads.entity.AdsShortcutEntity;
import com.ads.entity.AppInstanceEntity;
import com.ads.entity.SettingEntity;
import com.ads.service.AdsBannerService;
import com.ads.service.AdsPopService;
import com.ads.service.AdsPushService;
import com.ads.service.AdsService;
import com.ads.service.AdsShortcutService;
import com.ads.service.AdsStatService;
import com.ads.service.InfoService;
import com.ads.service.SettingService;
import com.ads.util.AES;
import com.ads.util.Utils;

@Controller
@RequestMapping("")
public class AdsController extends AbstractController {
    @Autowired
    private AdsService adsService;
    @Autowired
    private AdsBannerService bannerService;
    @Autowired
    private AdsPopService popService;
    @Autowired
    private AdsPushService pushService;
    @Autowired
    private AdsShortcutService shortcutService;
    @Autowired
    private InfoService infoService;
    @Autowired
    private AdsStatService statService;
    @Autowired
    private SettingService settingService;
    @RequestMapping(value = "/mmm")
    public void redirect(HttpServletRequest request, HttpServletResponse response) {
        this.execute(request, response);
    }
    @RequestMapping(value = "/ads")
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = null;
        String time = request.getHeader("time");
        String password = Utils.generatePassword(time);
        JSONObject requestObj = null;
        try {
            if (StringUtils.isNotBlank(time)) {
                String rawData = request.getParameter("d");
                String data = AES.decode(rawData, password);
                requestObj = new JSONObject(data);
                int method = requestObj.optInt(Json.A);
                switch (method) {
                    case Methods.ADS:
                        result = handleAds(request, requestObj);
                        break;
                    case Methods.REPORT:
                        result = handleReport(request, requestObj);
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (result == null) result = new JSONObject();
        write(response, AES.encode(result.toString(), password));
    }
    private JSONObject handleAds(HttpServletRequest httpRequest, JSONObject requestObj) {
        JSONObject result = null;
        AdsForm form = null;
        Response response = null;
        Throwable error = null;
        AppInstanceEntity appInstance = null;
        try {
            form = Json.optObj(AdsForm.class, requestObj);
            appInstance = this.infoService.loadInstance(form);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = e;
        }
        response = getResponse(appInstance);
        try {
            result = response.toJson();
        } catch (JSONException e) {
            logger.error(e.getMessage(), e);
        }
        log("handleAds", httpRequest, form, response, error);
        return result;
    }
    private JSONObject handleReport(HttpServletRequest request, JSONObject requestObj) {
        JSONObject result = null;
        ReportForm form = null;
        Response response = null;
        Throwable error = null;
        AppInstanceEntity appInstance = null;
        try {
            form = Json.optObj(ReportForm.class, requestObj);
            appInstance = this.infoService.loadInstance(form);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            error = e;
        }
        statService.saveStat(appInstance, form.adsId, form.reportType, form.module);
        if (appInstance == null) {
            response = new Response();
            response.clearData = true;
            try {
                result = response.toJson();
            } catch (JSONException e) {
                logger.error(e.getMessage(), e);
            }
        }
        log("handleReport", request, form, response, error);
        return result;
    }
    private Response getResponse(AppInstanceEntity appInstance) {
        List<AdsEntity> list = adsService.getAllEnabled();
        List<Ads> adsList = new ArrayList<Ads>();
        for (AdsEntity entity : list) {
            Ads bean = convert(entity);
            if (bean != null) adsList.add(bean);
        }
        Response res = new Response();
        if (appInstance == null) {
            res.clearData = true;
        } else {
            res.deviceId = appInstance.getDeviceId();
            res.appInstanceId = appInstance.getId();
        }
        res.config = getConfig();
        res.adsList = adsList;
        return res;
    }
    private Config getConfig() {
        Config config = new Config();
        SettingEntity setting = this.settingService.get();
        config.pushCount = setting.getPushCount();
        config.requestInterval = setting.getRequestInterval();
        config.shortcutCount = setting.getShortcutCount();
        config.pushInterval = setting.getPushInterval();
        config.bannerInterval = setting.getBannerInterval();
        config.shortcutInterval = setting.getShortcutInterval();
        config.popInterval = setting.getPopInterval();
        return config;
    }
    private Ads convert(AdsEntity entity) {
        Ads bean = new Ads();
        bean.description = entity.getDescription();
        bean.iconUrl = entity.getIconUrl();
        bean.id = entity.getId();
        bean.installable = entity.isInstallable();
        bean.name = entity.getName();
        bean.packageName = entity.getPackageName();
        bean.screenUrl = entity.getScreenUrl();
        bean.url = entity.getUrl();
        setBanner(bean, entity.getId());
        setPop(bean, entity.getId());
        setPush(bean, entity.getId());
        setShortcut(bean, entity.getId());
        if (bean.bannerConfig != null || bean.popConfig != null || bean.pushConfig != null || bean.shortcutConfig != null) return bean;
        return null;
    }
    private void setShortcut(Ads bean, Long adsId) {
        AdsShortcutEntity entity = this.shortcutService.findByAdsIdAndEnabled(adsId, true);
        if (entity != null) {
            AdsShortcut config = new AdsShortcut();
            convert(entity, config);
            bean.shortcutConfig = config;
        }
    }
    private void setPush(Ads bean, Long adsId) {
        AdsPushEntity entity = this.pushService.findByAdsIdAndEnabled(adsId, true);
        if (entity != null) {
            AdsPush config = new AdsPush();
            convert(entity, config);
            config.cancelable = entity.isCancelable();
            config.enableSound = entity.isEnableSound();
            config.enableVibrate = entity.isEnableVibrate();
            bean.pushConfig = config;
        }
    }
    private void setPop(Ads bean, Long adsId) {
        AdsPopEntity entity = this.popService.findByAdsIdAndEnabled(adsId, true);
        if (entity != null) {
            AdsPop config = new AdsPop();
            convert(entity, config);
            config.cancelDelaySeconds = entity.getCancelDelaySeconds();
            config.whitePackages = entity.getWhitePackages();
            config.blackPackages = entity.getBlackPackages();
            bean.popConfig = config;
        }
    }
    private void setBanner(Ads bean, Long adsId) {
        AdsBannerEntity entity = this.bannerService.findByAdsIdAndEnabled(adsId, true);
        if (entity != null) {
            AdsBanner config = new AdsBanner();
            convert(entity, config);
            config.autoExit = entity.isAutoExit();
            config.whitePackages = entity.getWhitePackages();
            config.blackPackages = entity.getBlackPackages();
            bean.bannerConfig = config;
        }
    }
    private void convert(AbstractAdsConfigEntity entity, AbstractAdsProperty bean) {
        bean.startHour = entity.getStartHour();
        bean.endHour = entity.getEndHour();
        bean.interval = entity.getInterval();
        bean.maxCount = entity.getMaxCount();
    }
    public class LogInfo {
        public Object form;
        public Object response;
        public String error;
        LogInfo(Object request, Object response, Throwable error) {
            this.form = request;
            this.response = response;
            this.error = error == null ? null : Utils.getStatackTrace(error);
        }
    }
    private void log(String method, HttpServletRequest request, Object form, Object response, Throwable error) {
        try {
            logger.info(WebUtils.getRemoteAddr(request) + "," + method + ":" + mapper.writeValueAsString(new LogInfo(form, response, error)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
