package com.ads.repository;
import com.ads.entity.AdsShortcutEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdsShortcutRepository extends MyJpaRepository<AdsShortcutEntity> {
    AdsShortcutEntity findByAdsId(Long adsId);

    AdsShortcutEntity findByAdsIdAndEnabled(Long adsId, boolean enabled);
}
