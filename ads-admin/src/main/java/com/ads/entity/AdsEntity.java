package com.ads.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.ads.entity.framework.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 广告
 * @author Administrator
 *
 */
@Entity
@Table(name = "ads")
public class AdsEntity extends IdEntity {
    private Long advertiserId;
    private String name;
    private String alias;
    private String packageName;
    private String description;
    private boolean installable;
    private String url;
    private String screenUrl;
    private String iconUrl;
    private Date createTime;
    private boolean enabled;
    private int price;
    
    private AdsPopEntity pop;
    private AdsPushEntity push;
    private AdsShortcutEntity shortcut;
    private AdsBannerEntity banner;
    
    public AdsEntity() {}
    public AdsEntity(Long id) {
        this.id = id;
    }
    public Long getAdvertiserId() {
        return advertiserId;
    }
    public void setAdvertiserId(Long advertiserId) {
        this.advertiserId = advertiserId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isInstallable() {
        return installable;
    }
    public void setInstallable(boolean installable) {
        this.installable = installable;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getScreenUrl() {
        return screenUrl;
    }
    public void setScreenUrl(String screenUrl) {
        this.screenUrl = screenUrl;
    }
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @Transient
    @JsonIgnore
    public AdsPopEntity getPop() {
        return pop;
    }
    @Transient
    @JsonIgnore
    public AdsPushEntity getPush() {
        return push;
    }
    @Transient
    @JsonIgnore
    public AdsShortcutEntity getShortcut() {
        return shortcut;
    }
    @Transient
    @JsonIgnore
    public AdsBannerEntity getBanner() {
        return banner;
    }
    public void setPop(AdsPopEntity pop) {
        this.pop = pop;
    }
    public void setPush(AdsPushEntity push) {
        this.push = push;
    }
    public void setShortcut(AdsShortcutEntity shortcut) {
        this.shortcut = shortcut;
    }
    public void setBanner(AdsBannerEntity banner) {
        this.banner = banner;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}