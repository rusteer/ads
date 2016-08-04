package com.rot.repository;
import com.rot.entity.AdsRetentionStatEntity;
import com.rot.repository.framework.MyJpaRepository;

public interface AdsRetentionStatRepository extends MyJpaRepository<AdsRetentionStatEntity> {

    AdsRetentionStatEntity findByAdsIdAndActivateDate(Long id, String statDate);
}
