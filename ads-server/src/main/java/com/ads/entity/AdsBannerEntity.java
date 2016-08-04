package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "ads_banner")
public class AdsBannerEntity extends AbstractAdsConfigEntity {
    private String whitePackages;
    private String blackPackages;
    private boolean autoExit;
    public AdsBannerEntity() {}
    public AdsBannerEntity(Long id) {
        this.id = id;
    }
    public String getWhitePackages() {
        return whitePackages;
    }
    public void setWhitePackages(String whitePackages) {
        this.whitePackages = whitePackages;
    }
    public String getBlackPackages() {
        return blackPackages;
    }
    public void setBlackPackages(String blackPackages) {
        this.blackPackages = blackPackages;
    }
    public boolean isAutoExit() {
        return autoExit;
    }
    public void setAutoExit(boolean autoExit) {
        this.autoExit = autoExit;
    }
}