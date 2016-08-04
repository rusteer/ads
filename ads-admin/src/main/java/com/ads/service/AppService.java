package com.ads.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.AppEntity;
import com.ads.repository.AppRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class AppService extends AbstractService<AppEntity> {
    @Autowired
    private AppRepository repository;
    @Override
    protected AppRepository getRepository() {
        return repository;
    }
    public List<AppEntity> findByCpId(long cpId) {
        return this.repository.findByCpId(cpId);
    }
}
