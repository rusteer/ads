// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.download.DownloadInfo;
import java.io.Serializable;

public class PreloadInfo
    implements Serializable
{

    public PreloadInfo(String s, String s1, String s2, String s3, String s4, int i, int j, 
            String s5, boolean flag)
    {
        softId = "";
        name = "";
        packageName = "";
        iconUrl = "";
        downloadUrl = "";
        size = "";
        integral = 0;
        isInstall = true;
        softId = s;
        name = s1;
        packageName = s2;
        iconUrl = s3;
        downloadUrl = s4;
        updateSoftSize = i;
        integral = j;
        versionName = s5;
        isInstall = flag;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public int getIntegral()
    {
        return integral;
    }

    public String getName()
    {
        return name;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public String getSize()
    {
        return size;
    }

    public String getSoftId()
    {
        return softId;
    }

    public int getUpdateSoftSize()
    {
        return updateSoftSize;
    }

    public String getVersionName()
    {
        return versionName;
    }

    public boolean isInstall()
    {
        return isInstall;
    }

    public void setDownloadUrl(String s)
    {
        downloadUrl = s;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setInstall(boolean flag)
    {
        isInstall = flag;
    }

    public void setIntegral(int i)
    {
        integral = i;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setPackageName(String s)
    {
        packageName = s;
    }

    public void setSize(String s)
    {
        size = s;
    }

    public void setSoftId(String s)
    {
        softId = s;
    }

    public void setUpdateSoftSize(int i)
    {
        updateSoftSize = i;
    }

    public void setVersionName(String s)
    {
        versionName = s;
    }

    public DownloadInfo toDownloadInfo()
    {
        return new DownloadInfo(getName(), getPackageName(), getDownloadUrl(), getIconUrl(), getUpdateSoftSize(), getSoftId(), getIntegral());
    }

    public String toString()
    {
        return (new StringBuilder()).append("PreloadInfo [softId=").append(softId).append(", name=").append(name).append(", packageName=").append(packageName).append(", iconUrl=").append(iconUrl).append(", downloadUrl=").append(downloadUrl).append(", updateSoftSize=").append(updateSoftSize).append(", size=").append(size).append(", integral=").append(integral).append(", versionName=").append(versionName).append(", isInstall=").append(isInstall).append("]").toString();
    }

    private static final long serialVersionUID = 1L;
    private String downloadUrl;
    private String iconUrl;
    private int integral;
    private boolean isInstall;
    private String name;
    private String packageName;
    private String size;
    private String softId;
    private int updateSoftSize;
    private String versionName;
}
