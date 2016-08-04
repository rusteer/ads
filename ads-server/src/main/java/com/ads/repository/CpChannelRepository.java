package com.ads.repository;
import com.ads.entity.CpChannelEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface CpChannelRepository extends MyJpaRepository<CpChannelEntity> {
    CpChannelEntity findByCpIdAndChannelId(Long cpId, int channelId);
}
