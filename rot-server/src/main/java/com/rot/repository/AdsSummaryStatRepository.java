package com.rot.repository;
import com.rot.entity.AdsSummaryStatEntity;
import com.rot.repository.framework.MyJpaRepository;

public interface AdsSummaryStatRepository extends MyJpaRepository<AdsSummaryStatEntity> {
    AdsSummaryStatEntity findByAdsIdAndStatDate(Long id, String today);
}
