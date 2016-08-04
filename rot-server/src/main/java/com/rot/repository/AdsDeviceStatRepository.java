package com.rot.repository;
import java.util.List;
import com.rot.entity.AdsDeviceStatEntity;
import com.rot.repository.framework.MyJpaRepository;

public interface AdsDeviceStatRepository extends MyJpaRepository<AdsDeviceStatEntity> {

    AdsDeviceStatEntity findByAdsIdAndDeviceId(Long adsId, Long deviceId);
    List<AdsDeviceStatEntity> findByDeviceId(Long deviceId);
    List<AdsDeviceStatEntity> findByDeviceIdAndLastReportDate(Long deviceId, String statDate);
}
