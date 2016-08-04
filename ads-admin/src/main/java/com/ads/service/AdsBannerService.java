package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsBannerEntity;
import com.ads.repository.AdsBannerRepository;
import com.ads.service.framework.AbstractService;

@Service
@Transactional(readOnly = true)
public class AdsBannerService extends AbstractService<AdsBannerEntity> {
    @Autowired
    private AdsBannerRepository dao;
    @Override
    protected AdsBannerRepository getRepository() {
        return dao;
    }
    public AdsBannerEntity findByAdsId(Long adsId) {
        return dao.findByAdsId(adsId);
    }
    @Transactional(readOnly = false)
    public void saveOrUpdate(AdsBannerEntity entity) {
        AdsBannerEntity dbData = this.findByAdsId(entity.getAdsId());
        if (dbData != null) {
            entity.setId(dbData.getId());
        }
        this.save(entity);
    }
}
