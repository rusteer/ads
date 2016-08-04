package com.rot.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rot.entity.AdsDeviceStatEntity;
import com.rot.repository.AdsDeviceStatRepository;

@Component
@Transactional(readOnly = true)
public class AdsDeviceStatService extends AbstractService<AdsDeviceStatEntity> {
    @Autowired
    private AdsDeviceStatRepository dao;
    @Override
    protected AdsDeviceStatRepository getRepository() {
        return dao;
    }
    public AdsDeviceStatEntity findByAdsIdAndDeviceId(Long adsId, Long deviceId) {
        return dao.findByAdsIdAndDeviceId(adsId,deviceId);
    }
    public List<AdsDeviceStatEntity> getDeviceStat(Long deviceId) {
        return dao.findByDeviceId(deviceId);
    }
    
    
    public List<AdsDeviceStatEntity> getDeviceStatByLastDate(Long deviceId,String statDate) {
        return dao.findByDeviceIdAndLastReportDate(deviceId,statDate);
    }
}
