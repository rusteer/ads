package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AppInstanceEntity;
import com.ads.repository.AppInstanceRepository;
import com.ads.repository.framework.MyJpaRepository;

@Component
@Transactional(readOnly = true)
public class AppInstanceService extends AbstractService<AppInstanceEntity> {
    @Autowired
    private AppInstanceRepository dao;
    @Autowired
    private AppStatService appStatService;
    @Transactional(readOnly = false)
    public AppInstanceEntity loadOrSave(AppInstanceEntity temp) {
        Long appId = temp.getAppId();
        Long deviceId = temp.getDeviceId();
        String packageName = temp.getPackageName();
        int versionCode = temp.getVersionCode();
        AppInstanceEntity dbData = dao.getByUnique(appId, deviceId, packageName, versionCode, temp.getChannelId());
        if (dbData == null) {
            dbData = dao.save(temp);
            appStatService.increaseActivateCount(dbData.getAppId(), dbData.getChannelId());
        }
        return dbData;
    }
    @Override
    protected MyJpaRepository<AppInstanceEntity> getRepository() {
        return dao;
    }
}
