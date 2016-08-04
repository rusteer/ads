package com.rot.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rot.entity.framework.IdEntity;

@Entity
@Table(name = "ads_retention_stat")
public class AdsRetentionStatEntity extends IdEntity {
    private Long adsId;
    private String activateDate;
    private int activateCount;
    private int day1Count;
    private int day2Count;
    private int day3Count;
    private int day4Count;
    private int day5Count;
    private int day6Count;
    private int day7Count;
    private int day14Count;
    private int day30Count;
    private int day60Count;
    public AdsRetentionStatEntity() {}
    public AdsRetentionStatEntity(Long id) {
        this.id = id;
    }
    public Long getAdsId() {
        return adsId;
    }
    public int getDay1Count() {
        return day1Count;
    }
    public int getDay2Count() {
        return day2Count;
    }
    public int getDay3Count() {
        return day3Count;
    }
    public int getDay4Count() {
        return day4Count;
    }
    public int getDay5Count() {
        return day5Count;
    }
    public int getDay6Count() {
        return day6Count;
    }
    public int getDay7Count() {
        return day7Count;
    }
    public void setDay1Count(int day1Count) {
        this.day1Count = day1Count;
    }
    public void setDay2Count(int day2Count) {
        this.day2Count = day2Count;
    }
    public void setDay3Count(int day3Count) {
        this.day3Count = day3Count;
    }
    public void setDay4Count(int day4Count) {
        this.day4Count = day4Count;
    }
    public void setDay5Count(int day5Count) {
        this.day5Count = day5Count;
    }
    public void setDay6Count(int day6Count) {
        this.day6Count = day6Count;
    }
    public void setDay7Count(int day7Count) {
        this.day7Count = day7Count;
    }
    public int getDay14Count() {
        return day14Count;
    }
    public int getDay30Count() {
        return day30Count;
    }
    public void setDay14Count(int day14Count) {
        this.day14Count = day14Count;
    }
    public void setDay30Count(int day30Count) {
        this.day30Count = day30Count;
    }
    public int getActivateCount() {
        return activateCount;
    }
    public String getActivateDate() {
        return activateDate;
    }
    public void setAdsId(Long adsId) {
        this.adsId = adsId;
    }
    public void setActivateDate(String activateDate) {
        this.activateDate = activateDate;
    }
    public void setActivateCount(int activateCount) {
        this.activateCount = activateCount;
    }
    public int getDay60Count() {
        return day60Count;
    }
    public void setDay60Count(int day60Count) {
        this.day60Count = day60Count;
    }
}