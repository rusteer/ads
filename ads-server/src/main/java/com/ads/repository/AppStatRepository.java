package com.ads.repository;
import com.ads.entity.AppStatEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AppStatRepository extends MyJpaRepository<AppStatEntity> {

    AppStatEntity findByAppIdAndChannelIdAndStatDate(Long appId, int channelId, String statDate);
     
}
