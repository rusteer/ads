package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "app_stat")
public class AppStatEntity extends IdEntity {
    private Long appId;
    private int channelId;
    private String statDate;
    private int activateCount;
    public AppStatEntity() {}
    public AppStatEntity(Long id) {
        this.id = id;
    }
    public String getStatDate() {
        return statDate;
    }
    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }
    public int getChannelId() {
        return channelId;
    }
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
    public int getActivateCount() {
        return activateCount;
    }
    public void setActivateCount(int activateCount) {
        this.activateCount = activateCount;
    }
    public Long getAppId() {
        return appId;
    }
    public void setAppId(Long appId) {
        this.appId = appId;
    }
}
