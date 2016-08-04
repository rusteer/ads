package com.ads.repository;
import java.util.List;
import com.ads.entity.AdsEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AdvtisementRepository extends MyJpaRepository<AdsEntity> {
    List<AdsEntity> findByAdvertiserId(long advertiserId);
}
