package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AppEntity;
import com.ads.repository.AppRepository;
import com.ads.repository.framework.MyJpaRepository;

@Component
@Transactional(readOnly = true)
public class AppService extends AbstractService<AppEntity> {
    @Autowired
    AppRepository dao;
    @Override
    protected MyJpaRepository<AppEntity> getRepository() {
        return dao;
    }
}
