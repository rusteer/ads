package com.ads.repository.mybatis;
import java.util.List;
import java.util.Map;
import com.ads.entity.AdsStatEntity;
import com.ads.repository.framework.MyBatisRepository;

@MyBatisRepository
public interface AdsStatRepositoryMyBatis {
    List<AdsStatEntity> getDateStat(Map<String, Object> parameters);

    List<AdsStatEntity> getAppStat(Map<String, Object> params);

    List<AdsStatEntity> getAdsStat(Map<String, Object> params);

    List<AdsStatEntity> getChannelStat(Map<String, Object> params);
}
