package com.rot.entity.framework;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdEntity {
    protected final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected final static String TIME_ZONE = "GMT+08:00";
    protected Long id;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
