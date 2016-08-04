package com.ads.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "cp")
public class CpEntity extends IdEntity {
    //unique fields
    private String name;
    private String loginName;
    private String loginPassword;
    private int deviceDailyLimit;
    private Date createTime;
    private int discountRate;
    public CpEntity() {}
    public CpEntity(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public int getDeviceDailyLimit() {
        return deviceDailyLimit;
    }
    public String getLoginName() {
        return loginName;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public String getName() {
        return name;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public void setDeviceDailyLimit(int deviceDailyLimit) {
        this.deviceDailyLimit = deviceDailyLimit;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDiscountRate() {
        return discountRate;
    }
    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }
}