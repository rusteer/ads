package com.rot.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rot.entity.framework.IdEntity;
 
@Entity
@Table(name = "ads_summary_stat")
public class AdsSummaryStatEntity extends IdEntity {
    private Long adsId;
    private String statDate;
    private int activateCount;
    private int retentionCount;
    public AdsSummaryStatEntity() {}
    public AdsSummaryStatEntity(Long id) {
        this.id = id;
    }
    public Long getAdsId() {
        return adsId;
    }
    public String getStatDate() {
        return statDate;
    }
    
    public void setAdsId(Long adsId) {
        this.adsId = adsId;
    }
    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }
   
    public int getActivateCount() {
        return activateCount;
    }
    public void setActivateCount(int activateCount) {
        this.activateCount = activateCount;
    }
    public int getRetentionCount() {
        return retentionCount;
    }
    public void setRetentionCount(int retentionCount) {
        this.retentionCount = retentionCount;
    }
}