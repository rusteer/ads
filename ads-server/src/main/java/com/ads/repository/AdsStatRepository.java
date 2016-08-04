package com.ads.repository;
import com.ads.entity.AdsStatEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdsStatRepository extends MyJpaRepository<AdsStatEntity> {
    AdsStatEntity findByAdsIdAndAppIdAndChannelIdAndStatDate(long adsId, Long appId, int channelId, String statDate);
}
