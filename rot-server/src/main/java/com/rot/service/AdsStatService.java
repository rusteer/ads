package com.rot.service;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rot.entity.AdsDeviceStatEntity;
import com.rot.entity.AdsEntity;
import com.rot.entity.AdsRetentionStatEntity;
import com.rot.entity.AdsSummaryStatEntity;
import com.rot.entity.DeviceEntity;
import com.rot.repository.AdsRetentionStatRepository;
import com.rot.repository.AdsSummaryStatRepository;

@Component
@Transactional(readOnly = true)
public class AdsStatService {
    @Autowired
    private AdsSummaryStatRepository summaryRepository;
    @Autowired
    private AdsRetentionStatRepository retentionRepository;
    @Autowired
    private AdsDeviceStatService adsDeviceStatService;
    @Autowired
    private AdsService adsService;
    @Transactional(readOnly = false)
    public void saveStat(DeviceEntity device, String packageNames) {
        if (StringUtils.isNotBlank(packageNames)) {
            for (String packageName : packageNames.split(",")) {
                if (StringUtils.isNotBlank(packageName)) {
                    AdsEntity ads = this.adsService.findByPackageName(packageName.trim());
                    if (ads != null) {
                        String today = DateUtils.format(new Date());
                        boolean needSave = false;
                        boolean needIncrease = false;
                        AdsDeviceStatEntity stat = this.adsDeviceStatService.findByAdsIdAndDeviceId(ads.getId(), device.getId());
                        if (stat == null) {
                            stat = new AdsDeviceStatEntity();
                            stat.setAdsId(ads.getId());
                            stat.setDeviceId(device.getId());
                            stat.setActivateDate(today);
                            needSave = true;
                        }
                        if (!today.equals(stat.getLastReportDate())) {
                            stat.setLastReportDate(today);
                            needSave = true;
                            needIncrease = true;
                        }
                        if (needSave) {
                            this.adsDeviceStatService.save(stat);
                        }
                        if (needIncrease) {
                            increaseSummary(ads, stat.getActivateDate(), today);
                            increaseRetention(ads, stat.getActivateDate(), today);
                        }
                    }
                }
            }
        }
    }
    private void increaseSummary(AdsEntity ads, String activateDate, String statDate) {
        synchronized ("increaseSummary" + ads.getId()) {
            AdsSummaryStatEntity stat = this.summaryRepository.findByAdsIdAndStatDate(ads.getId(), statDate);
            if (stat == null) {
                stat = new AdsSummaryStatEntity();
                stat.setAdsId(ads.getId());
                stat.setStatDate(statDate);
            }
            if (activateDate.equals(statDate)) {
                stat.setActivateCount(stat.getActivateCount() + 1);
            }
            stat.setRetentionCount(stat.getRetentionCount() + 1);
            this.summaryRepository.save(stat);
        }
    }
    private void increaseRetention(AdsEntity ads, String activateDate, String statDate) {
        synchronized ("updateRetention" + ads.getId()) {
            AdsRetentionStatEntity stat = this.retentionRepository.findByAdsIdAndActivateDate(ads.getId(), activateDate);
            if (stat == null) {
                stat = new AdsRetentionStatEntity();
                stat.setAdsId(ads.getId());
                stat.setActivateDate(activateDate);
            }
            Date d1 = DateUtils.parse(activateDate);
            Date d2 = DateUtils.parse(statDate);
            long duration = (d2.getTime() - d1.getTime()) / 1000 / 3600 / 24;
            if (duration == 0) stat.setActivateCount(stat.getActivateCount() + 1);
            else if (duration == 1) stat.setDay1Count(stat.getDay1Count() + 1);
            else if (duration == 2) stat.setDay2Count(stat.getDay2Count() + 1);
            else if (duration == 3) stat.setDay3Count(stat.getDay3Count() + 1);
            else if (duration == 4) stat.setDay4Count(stat.getDay4Count() + 1);
            else if (duration == 5) stat.setDay5Count(stat.getDay5Count() + 1);
            else if (duration == 6) stat.setDay6Count(stat.getDay6Count() + 1);
            else if (duration == 7) stat.setDay7Count(stat.getDay7Count() + 1);
            else if (duration == 14) stat.setDay14Count(stat.getDay14Count() + 1);
            else if (duration == 30) stat.setDay30Count(stat.getDay30Count() + 1);
            else if (duration == 60) stat.setDay60Count(stat.getDay60Count() + 1);
            this.retentionRepository.save(stat);
        }
    }
}
