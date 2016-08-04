package com.ads.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsEntity;
import com.ads.repository.AdsRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AdsService extends AbstractService<AdsEntity> {
    @Autowired
    private AdsRepository dao;
    @Override
    protected AdsRepository getRepository() {
        return dao;
    }
    public List<AdsEntity> getAllEnabled() {
        return this.dao.findByEnabled(true);
    }
}
