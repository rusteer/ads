package com.ads.service;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import workspace.bean.form.AbstractForm;
import com.ads.entity.AppEntity;
import com.ads.entity.AppInstanceEntity;
import com.ads.entity.CpChannelEntity;
import com.ads.entity.DeviceEntity;

@Component
public class InfoService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    @Autowired
    DeviceService deviceService;
    @Autowired
    AppService appService;
    @Autowired
    AppInstanceService appInstanceService;
    @Autowired
    DeviceInfoService deviceInfoService;
    @Autowired
    CpChannelService cpChannelService;
    public AppInstanceEntity loadInstance(AbstractForm form) {
        AppInstanceEntity instance = null;
        long instanceId = form.appInstanceId;
        if (instanceId > 0) {
            instance = appInstanceService.get(instanceId);
            if (instance == null) { throw new RuntimeException("can't get appInsant from id" + instanceId); }
            instance.setDevice(deviceService.get(instance.getDeviceId()));
            //if (!isUniqKeysMatch(instance, form)) throw new RuntimeException("instance unique keys changed in client side");
        } else {
            long deviceId = form.deviceId;
            DeviceEntity device = null;
            if (deviceId > 0) {
                device = deviceService.get(deviceId);
                if (device == null) throw new RuntimeException("can't get Device from id:" + deviceId);
            } else if (form.device != null) {
                DeviceEntity temp = new DeviceEntity();
                temp.setAndroidId(form.device.androidId);
                temp.setBrand(form.device.brand);
                temp.setCreateTime(new Date());
                temp.setImei(form.device.imei);
                temp.setMacAddress(form.device.macAddress);
                temp.setManufacturer(form.device.manufacturer);
                temp.setModel(form.device.model);
                temp.setSdkVersion(form.device.sdkVersion);
                temp.setSerial(form.device.serial);
                device = deviceService.loadOrSave(temp);
            }
            if (device == null) { throw new RuntimeException("no device info can be found or provided"); }
            if (form.appId == 0) throw new RuntimeException("no appId provided");
            if (form.versionCode == 0) throw new RuntimeException("no appId provided");
            if (StringUtils.isBlank(form.packageName)) throw new RuntimeException("no packageName provided");
            {
                AppInstanceEntity temp = new AppInstanceEntity();
                temp.setAppId(form.appId);
                temp.setChannelId(form.channelId);
                temp.setCreateTime(new Date());
                temp.setDeviceId(device.getId());
                temp.setPackageName(StringUtils.substring(form.packageName, 0, 100));
                temp.setVersionCode(form.versionCode);
                instance = appInstanceService.loadOrSave(temp);
                AppEntity app = appService.get(form.appId);
                if (app != null) {
                    CpChannelEntity cpChannel = cpChannelService.findByUnique(app.getCpId(), form.channelId);
                    if (cpChannel == null) {
                        cpChannel = new CpChannelEntity();
                        cpChannel.setChannelId(form.channelId);
                        cpChannel.setCpId(app.getCpId());
                        cpChannelService.save(cpChannel);
                    }
                }
                //
            }
            instance.setDevice(device);
        }
        return instance;
    }
    private boolean isUniqKeysMatch(AppInstanceEntity instance, AbstractForm form) {
        return instance.getAppId() == form.appId //
                && instance.getDeviceId() == form.deviceId//
                && instance.getPackageName().equals(form.packageName) //
                && instance.getVersionCode() == form.versionCode//
                && instance.getChannelId() == form.channelId;
    }
}
