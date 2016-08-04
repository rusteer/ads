package com.ads.entity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "ads_shortcut")
public class AdsShortcutEntity extends AbstractAdsConfigEntity {
    public AdsShortcutEntity() {}
    public AdsShortcutEntity(Long id) {
        this.id = id;
    }
}