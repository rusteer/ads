package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "ads_pop")
public class AdsPopEntity extends AbstractAdsConfigEntity {
    private String whitePackages;
    private String blackPackages;
    private int cancelDelaySeconds;
    public AdsPopEntity() {}
    public AdsPopEntity(Long id) {
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
    public int getCancelDelaySeconds() {
        return cancelDelaySeconds;
    }
    public void setCancelDelaySeconds(int cancelDelaySeconds) {
        this.cancelDelaySeconds = cancelDelaySeconds;
    }
}