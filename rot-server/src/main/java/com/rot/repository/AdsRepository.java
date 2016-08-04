package com.rot.repository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.rot.entity.AdsEntity;
import com.rot.repository.framework.MyJpaRepository;

public interface AdsRepository extends MyJpaRepository<AdsEntity> {
    AdsEntity findByPackageName(String packageName);
    @Query("  from AdsEntity a where a.enableStat=?1")
    List<AdsEntity> getRetentionList(boolean enable);
    
    @Query("  from AdsEntity a where a.enableInstall=?1")
    List<AdsEntity> getInstallList(boolean enable);
    
    @Query("  from AdsEntity a where a.enableActivate=?1")
    List<AdsEntity> getActivateList(boolean enable);
}
