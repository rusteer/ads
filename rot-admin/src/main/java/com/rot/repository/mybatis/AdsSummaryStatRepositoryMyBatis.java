package com.rot.repository.mybatis;
import java.util.List;
import java.util.Map;
import com.rot.entity.AdsSummaryStatEntity;
import com.rot.repository.framework.MyBatisRepository;

@MyBatisRepository
public interface AdsSummaryStatRepositoryMyBatis {
    List<AdsSummaryStatEntity> getStat(Map<String, Object> parameters);
}
