package com.ads.entity;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import com.ads.entity.framework.IdEntity;

@MappedSuperclass
public class AbstractAdsConfigEntity extends IdEntity {
    private Long adsId;
    private int startHour;
    private int endHour;
    private int interval;
    private int maxCount;
    private Date createTime;
    private boolean enabled;
    public AbstractAdsConfigEntity() {}
    public AbstractAdsConfigEntity(Long id) {
        this.id = id;
    }
    public int getStartHour() {
        return startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }
    public int getEndHour() {
        return endHour;
    }
    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
    @Column(name = "config_interval")
    public int getInterval() {
        return interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }
    public int getMaxCount() {
        return maxCount;
    }
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getAdsId() {
        return adsId;
    }
    public void setAdsId(Long adsId) {
        this.adsId = adsId;
    }
}