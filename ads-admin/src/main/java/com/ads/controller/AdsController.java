package com.ads.controller;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ads.entity.AbstractAdsConfigEntity;
import com.ads.entity.AdsBannerEntity;
import com.ads.entity.AdsEntity;
import com.ads.entity.AdsPopEntity;
import com.ads.entity.AdsPushEntity;
import com.ads.entity.AdsShortcutEntity;
import com.ads.service.AdsBannerService;
import com.ads.service.AdsPopService;
import com.ads.service.AdsPushService;
import com.ads.service.AdsService;
import com.ads.service.AdsShortcutService;
import com.ads.service.AdvertiserService;

@Controller
@RequestMapping("/" + AdsController.cmpName)
public class AdsController extends AbstractController {
    public static final String cmpName = "ads";
    @Autowired
    AdsService service;
    @Autowired
    AdvertiserService advertiserService;
    @Autowired
    AdsBannerService bannerService;
    @Autowired
    AdsPopService popService;
    @Autowired
    AdsPushService pushService;
    @Autowired
    AdsShortcutService shortcutService;
    private void composeConfig(HttpServletRequest request, AdsEntity entity, AbstractAdsConfigEntity config, String module) {
        config.setAdsId(entity.getId());
        config.setEnabled("1".equals(request.getParameter(module + "_enabled")));
        config.setStartHour(WebUtil.getIntParameter(request, module + "_startHour"));
        config.setEndHour(WebUtil.getIntParameter(request, module + "_endHour"));
        config.setInterval(WebUtil.getIntParameter(request, module + "_interval"));
        config.setMaxCount(WebUtil.getIntParameter(request, module + "_maxCount"));
    }
    private AdsEntity composeEntity(HttpServletRequest request) {
        AdsEntity entity = new AdsEntity();
        entity.setId(WebUtil.getLongParameter(request, "id"));
        entity.setName(request.getParameter("name"));
        entity.setAlias(request.getParameter("alias"));
        entity.setAdvertiserId(WebUtil.getLongParameter(request, "advertiserId"));
        entity.setPackageName(request.getParameter("packageName"));
        entity.setDescription(request.getParameter("description"));
        entity.setInstallable("1".equals(request.getParameter("installable")));
        entity.setUrl(request.getParameter("url"));
        entity.setScreenUrl(request.getParameter("screenUrl"));
        entity.setIconUrl(request.getParameter("iconUrl"));
        entity.setEnabled("1".equals(request.getParameter("enabled")));
        entity.setPrice(WebUtil.getIntParameter(request, "price"));
        if (entity.getId() == 0) {
            entity.setCreateTime(new Date());
        }
        return entity;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String form(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        long advertiserId = WebUtil.getLongParameter(request, "advertiserId");
        AdsEntity entity = id > 0 ? service.get(id) : new AdsEntity();
        if (entity.getAdvertiserId() == null) {
            entity.setAdvertiserId(advertiserId);
        }
        loadData(request, entity);
        return render(request, "form");
    }
    @Override
    protected String getCmpName() {
        return cmpName;
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("advertiserList", advertiserService.getAll());
        long advertiserId = WebUtil.getLongParameter(request, "advertiserId");
        List<AdsEntity> list = advertiserId>0?service.findByAdvertiserId(advertiserId):service.getAll();
        Collections.sort(list, new Comparator<AdsEntity>() {
            @Override
            public int compare(AdsEntity o1, AdsEntity o2) {
                int result = Boolean.valueOf(o2.isEnabled()).compareTo(o1.isEnabled());
                if (result == 0) {
                    result = o1.getAdvertiserId().compareTo(o2.getAdvertiserId());
                }
                return result;
            }
        });
        for (AdsEntity ads : list) {
            ads.setPop(this.popService.findByAdsId(ads.getId()));
            ads.setPush(this.pushService.findByAdsId(ads.getId()));
            ads.setShortcut(this.shortcutService.findByAdsId(ads.getId()));
            ads.setBanner(this.bannerService.findByAdsId(ads.getId()));
        }
        request.setAttribute("list", list);
        return render(request, "list");
    }
    public void loadData(HttpServletRequest request, AdsEntity entity) {
        request.setAttribute("entity", entity);
        request.setAttribute("advertiserList", advertiserService.getAll());
        loadBanner(request, entity);
        loadPop(request, entity);
        loadPush(request, entity);
        loadShortcut(request, entity);
    }
    private void loadBanner(HttpServletRequest request, AdsEntity entity) {
        AdsBannerEntity config = null;
        if (entity.getId() != null && entity.getId() > 0) {
            config = this.bannerService.findByAdsId(entity.getId());
        }
        if (config == null) {
            config = new AdsBannerEntity();
            config.setAdsId(entity.getId());
        }
        request.setAttribute("banner", config);
    }
    private void loadPop(HttpServletRequest request, AdsEntity entity) {
        AdsPopEntity config = null;
        if (entity.getId() != null && entity.getId() > 0) {
            config = this.popService.findByAdsId(entity.getId());
        }
        if (config == null) {
            config = new AdsPopEntity();
            config.setAdsId(entity.getId());
        }
        request.setAttribute("pop", config);
    }
    private void loadPush(HttpServletRequest request, AdsEntity entity) {
        AdsPushEntity config = null;
        if (entity.getId() != null && entity.getId() > 0) {
            config = this.pushService.findByAdsId(entity.getId());
        }
        if (config == null) {
            config = new AdsPushEntity();
            config.setAdsId(entity.getId());
        }
        request.setAttribute("push", config);
    }
    private void loadShortcut(HttpServletRequest request, AdsEntity entity) {
        AdsShortcutEntity config = null;
        if (entity.getId() != null && entity.getId() > 0) {
            config = this.shortcutService.findByAdsId(entity.getId());
        }
        if (config == null) {
            config = new AdsShortcutEntity();
            config.setAdsId(entity.getId());
        }
        request.setAttribute("shortcut", config);
    }
    private void saveBanner(HttpServletRequest request, AdsEntity entity) {
        AdsBannerEntity config = new AdsBannerEntity();
        String module = "banner";
        config.setAutoExit("1".equals(request.getParameter(module + "_autoExit")));
        config.setBlackPackages(request.getParameter(module + "_blackPackages"));
        config.setWhitePackages(request.getParameter(module + "_whitePackages"));
        composeConfig(request, entity, config, module);
        this.bannerService.saveOrUpdate(config);
    }
    private void savePop(HttpServletRequest request, AdsEntity entity) {
        AdsPopEntity config = new AdsPopEntity();
        String module = "pop";
        config.setCancelDelaySeconds(WebUtil.getIntParameter(request, module + "_cancelDelaySeconds"));
        config.setBlackPackages(request.getParameter(module + "_blackPackages"));
        config.setWhitePackages(request.getParameter(module + "_whitePackages"));
        composeConfig(request, entity, config, module);
        this.popService.saveOrUpdate(config);
    }
    private void savePush(HttpServletRequest request, AdsEntity entity) {
        AdsPushEntity config = new AdsPushEntity();
        String module = "push";
        config.setCancelable("1".equals(request.getParameter(module + "_cancelable")));
        config.setEnableSound("1".equals(request.getParameter(module + "_enableSound")));
        config.setEnableVibrate("1".equals(request.getParameter(module + "_enableVibrate")));
        composeConfig(request, entity, config, module);
        this.pushService.saveOrUpdate(config);
    }
    private void saveShortcut(HttpServletRequest request, AdsEntity entity) {
        AdsShortcutEntity config = new AdsShortcutEntity();
        String module = "shortcut";
        composeConfig(request, entity, config, module);
        this.shortcutService.saveOrUpdate(config);
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        AdsEntity entity = composeEntity(request);
        boolean saveSuccess = false;
        try {
            logger.info("Saving entity:" + mapper.writeValueAsString(entity));
            entity = service.save(entity);
            saveBanner(request, entity);
            savePop(request, entity);
            savePush(request, entity);
            saveShortcut(request, entity);
            saveSuccess = true;
        } catch (Throwable e) {
            logger.error("Failed to save entity", e);
            request.setAttribute("errorMessage", e.getMessage());
        }
        if (saveSuccess) return redirect(request, "/" + cmpName + "/" + entity.getId() + "?saveSuccess=true");
        request.setAttribute("saveSuccess", saveSuccess);
        loadData(request, entity);
        return render(request, "form");
    }
}
