package com.rot.repository.mybatis;
import java.util.List;
import java.util.Map;
import com.rot.entity.AdsRetentionStatEntity;
import com.rot.repository.framework.MyBatisRepository;

@MyBatisRepository
public interface AdsRetentionStatRepositoryMyBatis {
    List<AdsRetentionStatEntity> getStat(Map<String, Object> parameters);
}
