package com.ads.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ads.entity.CpChannelEntity;
import com.ads.repository.CpChannelRepository;
import com.ads.repository.framework.MyJpaRepository;

@Component
@Transactional(readOnly = true)
public class CpChannelService extends AbstractService<CpChannelEntity> {
    @Autowired
    private CpChannelRepository dao;
    public CpChannelEntity findByUnique(Long cpId, int channelId) {
        return dao.findByCpIdAndChannelId(cpId, channelId);
    }
    @Override
    protected MyJpaRepository<CpChannelEntity> getRepository() {
        return dao;
    }
}
