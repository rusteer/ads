package com.ads.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

/**
 * 广告主
 * @author Administrator
 *
 */
@Entity
@Table(name = "advertiser")
public class AdvertiserEntity extends IdEntity {
    //unique fields
    private String name;
    private Date createTime;
    public AdvertiserEntity() {}
    public AdvertiserEntity(Long id) {
        this.id = id;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public String getName() {
        return name;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public void setName(String name) {
        this.name = name;
    }
}