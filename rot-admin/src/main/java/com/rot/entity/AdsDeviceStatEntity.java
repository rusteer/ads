package com.rot.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rot.entity.framework.IdEntity;

/**
 * 广告
 * @author Administrator
 *
 */
@Entity
@Table(name = "ads_device_stat")
public class AdsDeviceStatEntity extends IdEntity {
    private Long adsId;
    private Long deviceId;
    private String activateDate;
    private String lastReportDate;
    public AdsDeviceStatEntity() {}
    public AdsDeviceStatEntity(Long id) {
        this.id = id;
    }
    public Long getAdsId() {
        return adsId;
    }
    public Long getDeviceId() {
        return deviceId;
    }
    public String getActivateDate() {
        return activateDate;
    }
    public String getLastReportDate() {
        return lastReportDate;
    }
    public void setAdsId(Long adsId) {
        this.adsId = adsId;
    }
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
    public void setActivateDate(String activateDate) {
        this.activateDate = activateDate;
    }
    public void setLastReportDate(String lastReportDate) {
        this.lastReportDate = lastReportDate;
    }
}