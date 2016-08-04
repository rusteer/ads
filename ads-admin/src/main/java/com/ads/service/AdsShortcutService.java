package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsShortcutEntity;
import com.ads.repository.AdsShortcutRepository;
import com.ads.service.framework.AbstractService;

@Service
@Transactional(readOnly = true)
public class AdsShortcutService extends AbstractService<AdsShortcutEntity> {
    @Autowired
    private AdsShortcutRepository dao;
    @Override
    protected AdsShortcutRepository getRepository() {
        return dao;
    }
    public AdsShortcutEntity findByAdsId(Long advtisementId) {
        return dao.findByAdsId(advtisementId);
    }
    @Transactional(readOnly = false)
    public void saveOrUpdate(AdsShortcutEntity entity) {
        AdsShortcutEntity dbData = this.findByAdsId(entity.getAdsId());
        if (dbData != null) {
            entity.setId(dbData.getId());
        }
        this.save(entity);
    }
}
