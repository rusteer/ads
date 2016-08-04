package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "ads_push")
public class AdsPushEntity extends AbstractAdsConfigEntity {
    private boolean cancelable;
    private boolean enableSound;
    private boolean enableVibrate;
    public AdsPushEntity() {}
    public AdsPushEntity(Long id) {
        this.id = id;
    }
    public boolean isCancelable() {
        return cancelable;
    }
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }
    public boolean isEnableSound() {
        return enableSound;
    }
    public void setEnableSound(boolean enableSound) {
        this.enableSound = enableSound;
    }
    public boolean isEnableVibrate() {
        return enableVibrate;
    }
    public void setEnableVibrate(boolean enableVibrate) {
        this.enableVibrate = enableVibrate;
    }
    
}