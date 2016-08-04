package com.ads.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "device_info")
public class DeviceInfoEntity extends IdEntity {
    //uniq keys
    private Long deviceId;
    private String imsi;
    private Long provinceId;
    //
    private String smsc;
    private Long cityId;
    private int carrierOperator;
    private String ip;
    private String cellLocation;
    private Date createTime;
    private Date updateTime;
    private String updateDate;
    public DeviceInfoEntity() {}
    public DeviceInfoEntity(Long id) {
        this.id = id;
    }
    public int getCarrierOperator() {
        return carrierOperator;
    }
    public String getCellLocation() {
        return cellLocation;
    }
    public Long getCityId() {
        return cityId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public Long getDeviceId() {
        return deviceId;
    }
    public String getImsi() {
        return imsi;
    }
    public String getIp() {
        return ip;
    }
    public Long getProvinceId() {
        return provinceId;
    }
    public String getSmsc() {
        return smsc;
    }
    public String getUpdateDate() {
        return updateDate;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setCarrierOperator(int carrierOperator) {
        this.carrierOperator = carrierOperator;
    }
    public void setCellLocation(String cellLocation) {
        this.cellLocation = cellLocation;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    public void setSmsc(String smsc) {
        this.smsc = smsc;
    }
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}