package com.rot.repository;
import com.rot.entity.DeviceEntity;
import com.rot.repository.framework.MyJpaRepository;

public interface DeviceRepository extends MyJpaRepository<DeviceEntity> {
    DeviceEntity findByImei(String imei);
}
