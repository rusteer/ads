package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "cp_channel")
public class CpChannelEntity extends IdEntity {
    private Long cpId;
    private int channelId;
    public CpChannelEntity() {}
    public CpChannelEntity(Long id) {
        this.id = id;
    }
    public int getChannelId() {
        return channelId;
    }
    public Long getCpId() {
        return cpId;
    }
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }
}