package com.ads.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "app")
public class AppEntity extends IdEntity {
    //unique fields
    private String name;
    private String description;
    private Long cpId;
    private Date createTime;
    private boolean enabled;
    public AppEntity() {}
    public AppEntity(Long id) {
        this.id = id;
    }
    public Long getCpId() {
        return cpId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public void setCpId(Long cpId) {
        this.cpId = cpId;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}