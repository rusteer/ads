package com.ads.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsEntity;
import com.ads.repository.AdvtisementRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AdsService extends AbstractService<AdsEntity> {
    @Autowired
    private AdvtisementRepository repository;
    @Override
    protected AdvtisementRepository getRepository() {
        return repository;
    }
    public List<AdsEntity> findByAdvertiserId(long advertiserId) {
        return repository.findByAdvertiserId(advertiserId);
    }
}
