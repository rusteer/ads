package com.ads.service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.DeviceEntity;
import com.ads.repository.DeviceRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class DeviceService extends AbstractService<DeviceEntity> {
    @Autowired
    private DeviceRepository dao;
    @Override
    protected DeviceRepository getRepository() {
        return dao;
    }
    @Transactional(readOnly = false)
    public DeviceEntity loadOrSave(DeviceEntity entity) {
        String imei = entity.getImei();
        String androidId = entity.getAndroidId();
        String serial = entity.getSerial();
        String macAddress = entity.getMacAddress();
        DeviceEntity dbData = null;
        if (StringUtils.isNotBlank(imei)) {
            dbData = dao.getByImei(imei);
        }
        if (dbData == null && StringUtils.isNotBlank(androidId)) {
            dbData = dao.getByAndroid(androidId);
        }
        if (dbData == null && StringUtils.isNotBlank(macAddress)) {
            dbData = dao.getByMacAddress(macAddress);
        }
        if (dbData == null && StringUtils.isNotBlank(serial)) {
            dbData = dao.getByMacSerial(serial);
        }
        if (dbData != null) {
            boolean needUpdate = false;
            if (StringUtils.isNotBlank(imei) && StringUtils.isBlank(dbData.getImei())) {
                dbData.setImei(imei);
                needUpdate = true;
            }
            if (StringUtils.isNotBlank(androidId) && StringUtils.isBlank(dbData.getAndroidId())) {
                dbData.setAndroidId(androidId);
                needUpdate = true;
            }
            if (StringUtils.isNotBlank(serial) && StringUtils.isBlank(dbData.getSerial())) {
                dbData.setSerial(serial);
                needUpdate = true;
            }
            if (StringUtils.isNotBlank(macAddress) && StringUtils.isBlank(dbData.getMacAddress())) {
                dbData.setMacAddress(macAddress);
                needUpdate = true;
            }
            if (needUpdate) {
                dbData = save(dbData);
            }
        }
        if (dbData == null) {
            dbData = save(entity);
        }
        return dbData;
    }
}
