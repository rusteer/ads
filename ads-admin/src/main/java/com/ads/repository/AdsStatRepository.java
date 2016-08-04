package com.ads.repository;
import com.ads.entity.AdsStatEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdsStatRepository extends MyJpaRepository<AdsStatEntity> {
    AdsStatEntity findByAdsId(Long adsId);
}
