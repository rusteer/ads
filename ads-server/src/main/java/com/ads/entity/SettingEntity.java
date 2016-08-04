package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ads.entity.framework.IdEntity;

@Entity
@Table(name = "setting")
public class SettingEntity extends IdEntity {
    private String bizHost;
    private int requestInterval;
    private int shortcutCount;
    private int pushCount;
    private int pushInterval;
    private int popInterval;
    private int shortcutInterval;
    private int bannerInterval;
    public String getBizHost() {
        return bizHost;
    }
    public int getRequestInterval() {
        return requestInterval;
    }
    public int getShortcutCount() {
        return shortcutCount;
    }
    public int getPushCount() {
        return pushCount;
    }
    public void setBizHost(String bizHost) {
        this.bizHost = bizHost;
    }
    public void setRequestInterval(int requestInterval) {
        this.requestInterval = requestInterval;
    }
    public void setShortcutCount(int shortcutCount) {
        this.shortcutCount = shortcutCount;
    }
    public void setPushCount(int pushCount) {
        this.pushCount = pushCount;
    }
    public int getPushInterval() {
        return pushInterval;
    }
    public int getPopInterval() {
        return popInterval;
    }
    public int getShortcutInterval() {
        return shortcutInterval;
    }
    public int getBannerInterval() {
        return bannerInterval;
    }
    public void setPushInterval(int pushInterval) {
        this.pushInterval = pushInterval;
    }
    public void setPopInterval(int popInterval) {
        this.popInterval = popInterval;
    }
    public void setShortcutInterval(int shortcutInterval) {
        this.shortcutInterval = shortcutInterval;
    }
    public void setBannerInterval(int bannerInterval) {
        this.bannerInterval = bannerInterval;
    }
}
