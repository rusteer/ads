package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsPopEntity;
import com.ads.repository.AdsPopRepository;
import com.ads.service.framework.AbstractService;

@Service
@Transactional(readOnly = true)
public class AdsPopService extends AbstractService<AdsPopEntity> {
    @Autowired
    private AdsPopRepository dao;
    @Override
    protected AdsPopRepository getRepository() {
        return dao;
    }
    public AdsPopEntity findByAdsId(Long advtisementId) {
        return dao.findByAdsId(advtisementId);
    }
    @Transactional(readOnly = false)
    public void saveOrUpdate(AdsPopEntity entity) {
        AdsPopEntity dbData = this.findByAdsId(entity.getAdsId());
        if (dbData != null) {
            entity.setId(dbData.getId());
        }
        this.save(entity);
    }
}
