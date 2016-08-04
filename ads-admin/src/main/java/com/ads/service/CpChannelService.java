package com.ads.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.CpChannelEntity;
import com.ads.repository.CpChannelRepository;
import com.ads.service.framework.AbstractService;

@Component
@Transactional(readOnly = true)
public class CpChannelService extends AbstractService<CpChannelEntity> {
    @Autowired
    private CpChannelRepository dao;
    @Override
    protected CpChannelRepository getRepository() {
        return dao;
    }
    public List<CpChannelEntity> findByCpId(long cpId) {
        return dao.findByCpId(cpId);
    }
}
