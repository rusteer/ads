package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdvertiserEntity;
import com.ads.repository.AdvertiserRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AdvertiserService extends AbstractService<AdvertiserEntity> {
    private AdvertiserRepository dao;
    @Override
    protected AdvertiserRepository getRepository() {
        return dao;
    }
    @Autowired
    public void setDao(AdvertiserRepository dao) {
        this.dao = dao;
    }
}
