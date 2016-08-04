package com.ads.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "ads_stat")
public class AdsStatEntity extends IdEntity {
    private Long adsId;
    private Long appId;
    public int channelId;
    private String statDate;
    //
    private int pushCount;
    private int popCount;
    private int bannerCount;
    private int shortcutCount;
    private int installCount;
    private int earning;
    //
    private Date updateTime;
    public AdsStatEntity() {}
    public Long getAdsId() {
        return adsId;
    }
    public void setAdsId(Long adsId) {
        this.adsId = adsId;
    }
    
    public Long getAppId() {
        return appId;
    }
    public int getChannelId() {
        return channelId;
    }
    public String getStatDate() {
        return statDate;
    }
    public int getPushCount() {
        return pushCount;
    }
    public int getPopCount() {
        return popCount;
    }
    public int getBannerCount() {
        return bannerCount;
    }
    public int getShortcutCount() {
        return shortcutCount;
    }
    public int getInstallCount() {
        return installCount;
    }
    public int getEarning() {
        return earning;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setAppId(Long appId) {
        this.appId = appId;
    }
    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }
    public void setStatDate(String statDate) {
        this.statDate = statDate;
    }
    public void setPushCount(int pushCount) {
        this.pushCount = pushCount;
    }
    public void setPopCount(int popCount) {
        this.popCount = popCount;
    }
    public void setBannerCount(int bannerCount) {
        this.bannerCount = bannerCount;
    }
    public void setShortcutCount(int shortcutCount) {
        this.shortcutCount = shortcutCount;
    }
    public void setInstallCount(int installCount) {
        this.installCount = installCount;
    }
    public void setEarning(int earning) {
        this.earning = earning;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}
