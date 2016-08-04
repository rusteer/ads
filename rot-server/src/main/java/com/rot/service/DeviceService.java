package com.rot.service;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rot.bean.Device;
import com.rot.bean.Request;
import com.rot.entity.DeviceEntity;
import com.rot.repository.DeviceRepository;
import com.rot.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class DeviceService extends AbstractService<DeviceEntity> {
    @Autowired
    private DeviceRepository dao;
    @Override
    protected DeviceRepository getRepository() {
        return dao;
    }
    private DeviceEntity loadOrSave(DeviceEntity entity) {
        String imei = entity.getImei();
        DeviceEntity dbData = null;
        if (StringUtils.isNotBlank(imei)) {
            dbData = dao.findByImei(imei);
        }
        if (dbData == null) {
            dbData = dao.save(entity);
        }
        return dbData;
    }
    private DeviceEntity convert(Device device) {
        DeviceEntity entity = new DeviceEntity();
        entity.setBrand(device.brand);
        entity.setImei(device.imei);
        entity.setManufacturer(device.manufacturer);
        entity.setModel(device.model);
        entity.setDeviceRooted(device.isRooted);
        entity.setSdkVersion(device.sdkVersion);
        entity.setSystemApp(device.isSystemApp);
        entity.setCreateDate(DateUtils.format(new Date()));
        entity.setCreateTime(new Date());
        return entity;
    }
    @Transactional(readOnly = false)
    public DeviceEntity loadDevice(Request request) {
        if (request.deviceId > 0) {//
            return this.get(request.deviceId);
        } else if (request.device != null) {//
            return loadOrSave(convert(request.device));
        }
        return null;
    }
}
