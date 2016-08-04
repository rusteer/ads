package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.SettingEntity;
import com.ads.repository.SettingRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class SettingService extends AbstractService<SettingEntity> {
    @Autowired
    private SettingRepository dao;
    public SettingEntity get() {
        return dao.findOne(1L);
    }
    @Override
    protected SettingRepository getRepository() {
        return dao;
    }
}
