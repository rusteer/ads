package com.ads.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsStatEntity;
import com.ads.repository.AdsStatRepository;
import com.ads.repository.mybatis.AdsStatRepositoryMyBatis;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AdsStatService extends AbstractService<AdsStatEntity> {
    @Autowired
    private AdsStatRepository repository;
    @Autowired
    private AdsStatRepositoryMyBatis myBatisRepository;
    @Override
    protected AdsStatRepository getRepository() {
        return repository;
    }
    public List<AdsStatEntity> getDateStat(String groupName, String fromDate, String toDate, long advertiserId, long adsId, long cpId, int channelId, long appId) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(fromDate)) params.put("fromDate", fromDate);
        if (StringUtils.isNotBlank(toDate)) params.put("toDate", toDate);
        if (advertiserId > 0) params.put("advertiserId", advertiserId);
        if (adsId > 0) params.put("adsId", adsId);
        if (cpId > 0) params.put("cpId", cpId);
        if (channelId > 0) params.put("channelId", channelId);
        if (appId > 0) params.put("appId", appId);
        List<AdsStatEntity> list = null;
        if ("date".equals(groupName)) {
            list = myBatisRepository.getDateStat(params);;
        } else if ("app".equals(groupName)) {
            list = myBatisRepository.getAppStat(params);;
        } else if ("ads".equals(groupName)) {
            list = myBatisRepository.getAdsStat(params);;
        } else if ("channel".equals(groupName)) {
            list = myBatisRepository.getChannelStat(params);;
        }
        return list;
    }
}
