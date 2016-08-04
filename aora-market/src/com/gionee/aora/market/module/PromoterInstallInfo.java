// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.download.DownloadInfo;
import java.io.Serializable;

public class PromoterInstallInfo
    implements Serializable
{

    public PromoterInstallInfo(String s, String s1, String s2, String s3, String s4, long l, 
            int i, boolean flag, boolean flag1)
    {
        softId = "";
        name = "";
        packageName = "";
        iconUrl = "";
        downloadUrl = "";
        size = "";
        integral = 0;
        isInstall = true;
        isShowCheck = true;
        softId = s;
        name = s1;
        packageName = s2;
        iconUrl = s3;
        downloadUrl = s4;
        updateSoftSize = l;
        integral = i;
        isInstall = flag;
        isShowCheck = flag1;
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

    public long getUpdateSoftSize()
    {
        return updateSoftSize;
    }

    public boolean isInstall()
    {
        return isInstall;
    }

    public boolean isShowCheck()
    {
        return isShowCheck;
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

    public void setShowCheck(boolean flag)
    {
        isShowCheck = flag;
    }

    public void setSize(String s)
    {
        size = s;
    }

    public void setSoftId(String s)
    {
        softId = s;
    }

    public void setUpdateSoftSize(long l)
    {
        updateSoftSize = l;
    }

    public DownloadInfo toDownloadInfo()
    {
        return new DownloadInfo(getName(), getPackageName(), getDownloadUrl(), getIconUrl(), getUpdateSoftSize(), getSoftId(), getIntegral());
    }

    public String toString()
    {
        return (new StringBuilder()).append("PreloadInfo [softId=").append(softId).append(", name=").append(name).append(", packageName=").append(packageName).append(", iconUrl=").append(iconUrl).append(", downloadUrl=").append(downloadUrl).append(", size=").append(size).append(", integral=").append(integral).append(", isInstall=").append(isInstall).append(",isShowCheck=").append(isShowCheck).append("]").toString();
    }

    private static final long serialVersionUID = 0x7693d1baad6869f8L;
    private String downloadUrl;
    private String iconUrl;
    private int integral;
    private boolean isInstall;
    private boolean isShowCheck;
    private String name;
    private String packageName;
    private String size;
    private String softId;
    private long updateSoftSize;
}
