package com.rot.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.rot.entity.framework.IdEntity;

@Entity
@Table(name = "setting")
public class SettingEntity extends IdEntity {
    private int requestInterval;
    private String hostName;
    private String sdPath;
    private String blackPackageNames;
    private boolean stopBlack;
    private boolean stopBlackReport;
    private boolean backgroundOnly;
    private boolean uninstallAfterActivate;
    private int installCount;
    public String getSdPath() {
        return sdPath;
    }
    public String getBlackPackageNames() {
        return blackPackageNames;
    }
    public boolean isStopBlack() {
        return stopBlack;
    }
    public boolean isStopBlackReport() {
        return stopBlackReport;
    }
    public boolean isBackgroundOnly() {
        return backgroundOnly;
    }
   
    public void setSdPath(String sdPath) {
        this.sdPath = sdPath;
    }
    public void setBlackPackageNames(String blackPackageNames) {
        this.blackPackageNames = blackPackageNames;
    }
    public void setStopBlack(boolean stopBlack) {
        this.stopBlack = stopBlack;
    }
    public void setStopBlackReport(boolean stopBlackReport) {
        this.stopBlackReport = stopBlackReport;
    }
    public void setBackgroundOnly(boolean backgroundOnly) {
        this.backgroundOnly = backgroundOnly;
    }
    public int getRequestInterval() {
        return requestInterval;
    }
    public void setRequestInterval(int requestInterval) {
        this.requestInterval = requestInterval;
    }
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }
    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public boolean isUninstallAfterActivate() {
        return uninstallAfterActivate;
    }
    public void setUninstallAfterActivate(boolean uninstallAfterActivate) {
        this.uninstallAfterActivate = uninstallAfterActivate;
    }
    /**
     * @return the installCount
     */
    public int getInstallCount() {
        return installCount;
    }
    /**
     * @param installCount the installCount to set
     */
    public void setInstallCount(int installCount) {
        this.installCount = installCount;
    }
}
