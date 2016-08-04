package com.ads.repository;
import com.ads.entity.AdsPopEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdsPopRepository extends MyJpaRepository<AdsPopEntity> {
    AdsPopEntity findByAdsId(Long adsId);
}
