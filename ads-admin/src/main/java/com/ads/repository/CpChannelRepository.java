package com.ads.repository;
import java.util.List;
import com.ads.entity.CpChannelEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface CpChannelRepository extends MyJpaRepository<CpChannelEntity> {

    List<CpChannelEntity> findByCpId(long cpId);
    
}
