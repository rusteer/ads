package com.ads.service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AppStatEntity;
import com.ads.repository.AppStatRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AppStatService extends AbstractService<AppStatEntity> {
    @Autowired
    private AppStatRepository dao;
    @Override
    protected AppStatRepository getRepository() {
        return dao;
    }
    @Transactional(readOnly = false)
    public void increaseActivateCount(Long appId, int channelId) {
        String statDate = FormatUtil.format(new Date());
        AppStatEntity stat = this.dao.findByAppIdAndChannelIdAndStatDate(appId, channelId, statDate);
        if (stat == null) {
            stat = new AppStatEntity();
            stat.setAppId(appId);
            stat.setChannelId(channelId);
            stat.setStatDate(statDate);
        }
        stat.setActivateCount(stat.getActivateCount() + 1);
        this.save(stat);
    }
}
