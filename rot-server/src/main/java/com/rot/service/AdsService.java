package com.rot.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.rot.entity.AdsEntity;
import com.rot.repository.AdsRepository;
import com.rot.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AdsService extends AbstractService<AdsEntity> {
    @Autowired
    private AdsRepository dao;
    @Override
    protected AdsRepository getRepository() {
        return dao;
    }
    public List<AdsEntity> getRetentionList() {
        return this.dao.getRetentionList(true);
    }
    
    public List<AdsEntity> getInstallList() {
        return this.dao.getInstallList(true);
    }
    
    public List<AdsEntity> getActivateList() {
        return this.dao.getActivateList(true);
    }
    
    public AdsEntity findByPackageName(String packageName) {
       
        return dao.findByPackageName(packageName);
    }
}
