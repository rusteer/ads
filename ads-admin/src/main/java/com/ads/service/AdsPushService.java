package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsPushEntity;
import com.ads.repository.AdsPushRepository;
import com.ads.service.framework.AbstractService;

@Service
@Transactional(readOnly = true)
public class AdsPushService extends AbstractService<AdsPushEntity> {
    @Autowired
    private AdsPushRepository dao;
    @Override
    protected AdsPushRepository getRepository() {
        return dao;
    }
    public AdsPushEntity findByAdsId(Long advtisementId) {
        return dao.findByAdsId(advtisementId);
    }
    @Transactional(readOnly = false)
    public void saveOrUpdate(AdsPushEntity entity) {
        AdsPushEntity dbData = this.findByAdsId(entity.getAdsId());
        if (dbData != null) {
            entity.setId(dbData.getId());
        }
        this.save(entity);
    }
}
