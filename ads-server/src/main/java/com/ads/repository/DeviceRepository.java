package com.ads.repository;
import org.springframework.data.jpa.repository.Query;
import com.ads.entity.DeviceEntity;
import com.ads.repository.framework.MyJpaRepository;

public interface DeviceRepository extends MyJpaRepository<DeviceEntity> {
    //
    @Query("  from DeviceEntity a where a.androidId=?1")
    DeviceEntity getByAndroid(String androidId);
    //
    @Query("  from DeviceEntity a where a.imei=?1")
    DeviceEntity getByImei(String imei);
    //
    @Query("  from DeviceEntity a where a.macAddress=?1")
    DeviceEntity getByMacAddress(String macAddress);
    //
    @Query("  from DeviceEntity a where a.serial=?1")
    DeviceEntity getByMacSerial(String serial);
}
