package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.DeviceEntity;
import com.ads.repository.DeviceRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class DeviceService extends AbstractService<DeviceEntity> {
    private DeviceRepository dao;
    @Override
    protected DeviceRepository getRepository() {
        return dao;
    }
    @Autowired
    public void setDao(DeviceRepository dao) {
        this.dao = dao;
    }
}
