package com.ads.repository;
import com.ads.entity.AdsPushEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdsPushRepository extends MyJpaRepository<AdsPushEntity> {
    AdsPushEntity findByAdsId(Long adsId);
}
