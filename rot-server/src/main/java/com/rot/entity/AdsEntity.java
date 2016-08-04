package com.rot.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rot.entity.framework.IdEntity;

@Entity
@Table(name = "ads")
public class AdsEntity extends IdEntity {
    public static final int ACTIVATE_BY_ACTIVITY=1;
    public static final int ACTIVATE_BY_SERVICE=2;
    
    private String name;
    private String packageName;
    private String downloadUrl;
    private boolean enableStat;
    private boolean enableInstall;
    private boolean enableActivate;
    private Date createTime;
    private int activateMethod;// invoke service=1, invoke activity=2;
    private String activateComponent;
    public AdsEntity() {}
    public AdsEntity(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public String getPackageName() {
        return packageName;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public boolean isEnableStat() {
        return enableStat;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public void setEnableStat(boolean enableStat) {
        this.enableStat = enableStat;
    }
    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public boolean isEnableInstall() {
        return enableInstall;
    }
    public void setEnableInstall(boolean enableInstall) {
        this.enableInstall = enableInstall;
    }
    public int getActivateMethod() {
        return activateMethod;
    }
    public String getActivateComponent() {
        return activateComponent;
    }
    public void setActivateMethod(int activateMethod) {
        this.activateMethod = activateMethod;
    }
    public void setActivateComponent(String activateComponent) {
        this.activateComponent = activateComponent;
    }
    public boolean isEnableActivate() {
        return enableActivate;
    }
    public void setEnableActivate(boolean enableActivate) {
        this.enableActivate = enableActivate;
    }
}