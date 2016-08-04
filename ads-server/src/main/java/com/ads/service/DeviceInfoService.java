package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.DeviceInfoEntity;
import com.ads.repository.DeviceInfoRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class DeviceInfoService extends AbstractService<DeviceInfoEntity> {
    private DeviceInfoRepository dao;
    @Override
    protected DeviceInfoRepository getRepository() {
        return dao;
    }
    @Autowired
    public void setDao(DeviceInfoRepository dao) {
        this.dao = dao;
    }
}
