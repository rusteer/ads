// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

// Referenced classes of package com.gionee.aora.market.module:
//            AppInfo

public class GiftInfo
    implements Serializable
{

    public GiftInfo()
    {
        getGiftResultCode = -1;
    }

    public String getAppDownloadUrl()
    {
        return appDownloadUrl;
    }

    public String getAppIconUrl()
    {
        return appIconUrl;
    }

    public int getAppId()
    {
        return appId;
    }

    public long getAppLongSize()
    {
        return appLongSize;
    }

    public String getAppName()
    {
        return appName;
    }

    public String getAppPackageName()
    {
        return appPackageName;
    }

    public String getAppSize()
    {
        return appSize;
    }

    public String getAppVersionName()
    {
        return appVersionName;
    }

    public String getCode()
    {
        return code;
    }

    public String getEndTime()
    {
        return endTime;
    }

    public String getFailedMsg()
    {
        return failedMsg;
    }

    public int getGetGiftResultCode()
    {
        return getGiftResultCode;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getSkipUrl()
    {
        return skipUrl;
    }

    public String getSurplusCount()
    {
        return surplusCount;
    }

    public boolean isGotGift()
    {
        return isGotGift;
    }

    public void setAppDownloadUrl(String s)
    {
        appDownloadUrl = s;
    }

    public void setAppIconUrl(String s)
    {
        appIconUrl = s;
    }

    public void setAppId(int i)
    {
        appId = i;
    }

    public void setAppLongSize(long l)
    {
        appLongSize = l;
    }

    public void setAppName(String s)
    {
        appName = s;
    }

    public void setAppPackageName(String s)
    {
        appPackageName = s;
    }

    public void setAppSize(String s)
    {
        appSize = s;
    }

    public void setAppVersionName(String s)
    {
        appVersionName = s;
    }

    public void setCode(String s)
    {
        code = s;
    }

    public void setEndTime(String s)
    {
        endTime = s;
    }

    public void setFailedMsg(String s)
    {
        failedMsg = s;
    }

    public void setGetGiftResultCode(int i)
    {
        getGiftResultCode = i;
    }

    public void setGotGift(boolean flag)
    {
        isGotGift = flag;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setId(int i)
    {
        id = i;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setSkipUrl(String s)
    {
        skipUrl = s;
    }

    public void setSurplusCount(String s)
    {
        surplusCount = s;
    }

    public AppInfo toAppInfo()
    {
        AppInfo appinfo = new AppInfo();
        appinfo.setSoftId((new StringBuilder()).append(getAppId()).append("").toString());
        appinfo.setName(getAppName());
        appinfo.setIconUrl(getAppIconUrl());
        appinfo.setSize(getAppSize());
        appinfo.setPackageName(getAppPackageName());
        appinfo.setDownloadUrl(getAppDownloadUrl());
        appinfo.setUpdateSoftSize((int)getAppLongSize());
        appinfo.setUpdateVersionName(getAppVersionName());
        appinfo.setvId(getId());
        return appinfo;
    }

    private static final long serialVersionUID = 0x6c231d5efc17a684L;
    private String appDownloadUrl;
    private String appIconUrl;
    private int appId;
    private long appLongSize;
    private String appName;
    private String appPackageName;
    private String appSize;
    private String appVersionName;
    private String code;
    private String endTime;
    private String failedMsg;
    private int getGiftResultCode;
    private String iconUrl;
    private int id;
    private boolean isGotGift;
    private String name;
    private String skipUrl;
    private String surplusCount;
}
