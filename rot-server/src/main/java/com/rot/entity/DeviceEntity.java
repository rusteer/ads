package com.rot.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rot.entity.framework.IdEntity;

@Entity
@Table(name = "device")
public class DeviceEntity extends IdEntity {
    //unique fields
    private String imei;
    private String model;
    private int sdkVersion;
    private String brand;
    private String manufacturer;
    private boolean deviceRooted;
    private boolean systemApp;
    //
    private Date createTime;
    private String createDate;
    public DeviceEntity() {}
    public DeviceEntity(Long id) {
        this.id = id;
    }
    public String getImei() {
        return imei;
    }
    public String getModel() {
        return model;
    }
    public int getSdkVersion() {
        return sdkVersion;
    }
    public String getBrand() {
        return brand;
    }
    public String getManufacturer() {
        return manufacturer;
    }
   
    public boolean isSystemApp() {
        return systemApp;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setSdkVersion(int sdkVersion) {
        this.sdkVersion = sdkVersion;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public void setSystemApp(boolean systemApp) {
        this.systemApp = systemApp;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * @return the deviceRooted
     */
    public boolean isDeviceRooted() {
        return deviceRooted;
    }
    /**
     * @param deviceRooted the deviceRooted to set
     */
    public void setDeviceRooted(boolean deviceRooted) {
        this.deviceRooted = deviceRooted;
    }
    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}