package com.ads.repository;
import com.ads.entity.AdsBannerEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdsBannerRepository extends MyJpaRepository<AdsBannerEntity> {
    AdsBannerEntity findByAdsId(Long adsId);
    AdsBannerEntity findByAdsIdAndEnabled(Long adsId, boolean enabled);
}
