package com.rot.service;
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
    private AdsRepository repository;
    @Override
    protected AdsRepository getRepository() {
        return repository;
    }
}
