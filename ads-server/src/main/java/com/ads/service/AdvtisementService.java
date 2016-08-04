package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsEntity;
import com.ads.repository.AdsRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AdvtisementService extends AbstractService<AdsEntity> {
    private AdsRepository dao;
   
    @Override
    protected AdsRepository getRepository() {
        return dao;
    }
    @Autowired
    public void setDao(AdsRepository dao) {
        this.dao = dao;
    }
}
