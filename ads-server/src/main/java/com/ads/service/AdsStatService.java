package com.ads.service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AdsEntity;
import com.ads.entity.AdsStatEntity;
import com.ads.entity.AppInstanceEntity;
import com.ads.repository.AdsStatRepository;
import com.ads.repository.AppStatRepository;
import com.ads.util.Constants;

@Component
@Transactional(readOnly = true)
public class AdsStatService {
    @Autowired
    private AdsStatRepository adsStatRepository;
    @Autowired
    private AppStatRepository appStatRepository;
    @Autowired
    private AdsService adsService;
    private AdsStatEntity updateStat(AdsStatEntity stat, AdsEntity ads, int reportType, int module) {
        switch (reportType) {
            case Constants.REPORT_TYPE_SHOW:
                switch (module) {
                    case Constants.MODULE_BANNER:
                        stat.setBannerCount(stat.getBannerCount() + 1);
                        break;
                    case Constants.MODULE_POP:
                        stat.setPopCount(stat.getPopCount() + 1);
                        break;
                    case Constants.MODULE_PUSH:
                        stat.setPushCount(stat.getPushCount() + 1);
                        break;
                    case Constants.MODULE_SHORTCUT:
                        stat.setShortcutCount(stat.getShortcutCount() + 1);
                        break;
                }
                break;
            case Constants.REPORT_TYPE_INSTALL:
                stat.setInstallCount(stat.getInstallCount() + 1);
                stat.setEarning(stat.getEarning() + ads.getPrice());
                break;
            case Constants.REPORT_TYPE_OPEN:
                break;
            case Constants.REPORT_TYPE_UNINSTALL:
                break;
        }
        stat.setUpdateTime(new Date());
        return stat;
    }
    @Transactional(readOnly = false)
    public void saveStat(AppInstanceEntity ai, long adsId, int reportType, int module) {
        if (ai != null) {
            AdsEntity ads = this.adsService.get(adsId);
            if (ads != null) {
                {
                    String statDate = FormatUtil.format(new Date());
                    AdsStatEntity stat = adsStatRepository.findByAdsIdAndAppIdAndChannelIdAndStatDate(adsId, ai.getAppId(), ai.getChannelId(), statDate);
                    if (stat == null) {
                        stat = new AdsStatEntity();
                        stat.setAdsId(adsId);
                        stat.setAppId(ai.getAppId());
                        stat.setChannelId(ai.getChannelId());
                        stat.setStatDate(statDate);
                    }
                    adsStatRepository.save(updateStat(stat, ads, reportType, module));
                }
            }
        }
    }
}
