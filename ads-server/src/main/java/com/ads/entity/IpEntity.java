package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "ip")
public class IpEntity extends IdEntity {
    private String ip;
    private Long cityId;
    public Long getCityId() {
        return cityId;
    }
    public String getIp() {
        return ip;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
}
