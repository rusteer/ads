// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.download.DownloadInfo;
import java.io.Serializable;

public class MasterInfo
    implements Serializable
{

    public MasterInfo()
    {
        masterId = "";
        masterName = "";
        masterIcon = "";
        masterIntro = "";
        appId = "";
        appName = "";
        packageName = "";
        appIcon = "";
        appSize = 0L;
        appDownloadCount = "";
        downloadUrl = "";
        appIntegral = 0;
    }

    public String getAppDownloadCount()
    {
        return appDownloadCount;
    }

    public String getAppIcon()
    {
        return appIcon;
    }

    public String getAppId()
    {
        return appId;
    }

    public int getAppIntegral()
    {
        return appIntegral;
    }

    public String getAppName()
    {
        return appName;
    }

    public long getAppSize()
    {
        return appSize;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public String getMasterIcon()
    {
        return masterIcon;
    }

    public String getMasterId()
    {
        return masterId;
    }

    public String getMasterIntro()
    {
        return masterIntro;
    }

    public String getMasterName()
    {
        return masterName;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setAppDownloadCount(String s)
    {
        appDownloadCount = s;
    }

    public void setAppIcon(String s)
    {
        appIcon = s;
    }

    public void setAppId(String s)
    {
        appId = s;
    }

    public void setAppIntegral(int i)
    {
        appIntegral = i;
    }

    public void setAppName(String s)
    {
        appName = s;
    }

    public void setAppSize(long l)
    {
        appSize = l;
    }

    public void setDownloadUrl(String s)
    {
        downloadUrl = s;
    }

    public void setMasterIcon(String s)
    {
        masterIcon = s;
    }

    public void setMasterId(String s)
    {
        masterId = s;
    }

    public void setMasterIntro(String s)
    {
        masterIntro = s;
    }

    public void setMasterName(String s)
    {
        masterName = s;
    }

    public void setPackageName(String s)
    {
        packageName = s;
    }

    public DownloadInfo toDownloadInfo()
    {
        return new DownloadInfo(getAppName(), getPackageName(), getDownloadUrl(), getAppIcon(), getAppSize(), getAppId(), getAppIntegral());
    }

    public String toString()
    {
        return (new StringBuilder()).append("MasterInfo [masterId=").append(masterId).append(", masterIcon=").append(masterIcon).append(", masterIntro=").append(masterIntro).append(", appId=").append(appId).append(", appName=").append(appName).append(", packageName=").append(packageName).append(", appIcon=").append(appIcon).append(", appSize=").append(appSize).append(", downloadUrl=").append(downloadUrl).append(", appIntegral=").append(appIntegral).append("]").toString();
    }

    private static final long serialVersionUID = 1L;
    private String appDownloadCount;
    private String appIcon;
    private String appId;
    private int appIntegral;
    private String appName;
    private long appSize;
    private String downloadUrl;
    private String masterIcon;
    private String masterId;
    private String masterIntro;
    private String masterName;
    private String packageName;
}
