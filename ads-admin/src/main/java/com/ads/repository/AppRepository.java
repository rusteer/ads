package com.ads.repository;
import java.util.List;
import com.ads.entity.AppEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface AppRepository extends MyJpaRepository<AppEntity> {
    List<AppEntity> findByCpId(long cpId);
}
