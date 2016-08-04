package com.rot.repository.mybatis;
import java.util.List;
import java.util.Map;
import com.rot.entity.ActivateCount;
import com.rot.repository.framework.MyBatisRepository;

@MyBatisRepository
public interface DeviceRepositoryMyBatis {
    List<ActivateCount> getStat(Map<String, Object> params);
}
