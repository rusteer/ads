package com.rot.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rot.entity.AdsRetentionStatEntity;
import com.rot.entity.AdsSummaryStatEntity;
import com.rot.repository.mybatis.AdsRetentionStatRepositoryMyBatis;
import com.rot.repository.mybatis.AdsSummaryStatRepositoryMyBatis;

@Component
@Transactional(readOnly = true)
public class AdsStatService {
    @Autowired
    AdsRetentionStatRepositoryMyBatis retentionDao;
    @Autowired
    AdsSummaryStatRepositoryMyBatis summaryDao;
    private Map<String, Object> composeParams(String fromDate, String toDate, long adsId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(fromDate)) map.put("fromDate", fromDate);
        if (StringUtils.isNotBlank(toDate)) map.put("toDate", toDate);
        if (adsId > 0) map.put("adsId", adsId);
        return map;
    }
    public List<AdsRetentionStatEntity> getRetention(String fromDate, String toDate, long adsId) {
        return retentionDao.getStat(this.composeParams(fromDate, toDate, adsId));
    }
    public List<AdsSummaryStatEntity> getSummary(String fromDate, String toDate, long adsId) {
        return summaryDao.getStat(this.composeParams(fromDate, toDate, adsId));
    }
}
