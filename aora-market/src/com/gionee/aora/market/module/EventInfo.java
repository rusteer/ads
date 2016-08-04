// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

// Referenced classes of package com.gionee.aora.market.module:
//            AppInfo

public class EventInfo
    implements Serializable
{

    public EventInfo()
    {
        eventId = "";
        eventIcon = "";
        eventUrl = "";
        eventFlag = false;
    }

    public String getAppDownloadCount()
    {
        return appDownloadCount;
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

    public boolean getEventFlag()
    {
        return eventFlag;
    }

    public String getEventIcon()
    {
        return eventIcon;
    }

    public String getEventId()
    {
        return eventId;
    }

    public String getEventUrl()
    {
        return eventUrl;
    }

    public void setAppDownloadCount(String s)
    {
        appDownloadCount = s;
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

    public void setEventFlag(boolean flag)
    {
        eventFlag = flag;
    }

    public void setEventIcon(String s)
    {
        eventIcon = s;
    }

    public void setEventId(String s)
    {
        eventId = s;
    }

    public void setEventUrl(String s)
    {
        eventUrl = s;
    }

    public AppInfo toAppInfo()
    {
        AppInfo appinfo = new AppInfo();
        appinfo.setSoftId((new StringBuilder()).append(getAppId()).append("").toString());
        appinfo.setName(getAppName());
        appinfo.setIconUrl(getAppIconUrl());
        appinfo.setSize(getAppSize());
        appinfo.setPackageName(getAppPackageName());
        appinfo.setDownload_region(getAppDownloadCount());
        appinfo.setDownloadUrl(getAppDownloadUrl());
        appinfo.setUpdateSoftSize((int)getAppLongSize());
        appinfo.setvId(Integer.parseInt(getEventId()));
        appinfo.setUpdateVersionName(getAppVersionName());
        return appinfo;
    }

    public String toString()
    {
        return (new StringBuilder()).append("EventInfo [eventId=").append(eventId).append(", eventIcon=").append(eventIcon).append(", eventUrl=").append(eventUrl).append(", eventFlag=").append(eventFlag).append("]").toString();
    }

    private static final long serialVersionUID = 1L;
    private String appDownloadCount;
    private String appDownloadUrl;
    private String appIconUrl;
    private int appId;
    private long appLongSize;
    private String appName;
    private String appPackageName;
    private String appSize;
    private String appVersionName;
    private boolean eventFlag;
    private String eventIcon;
    private String eventId;
    private String eventUrl;
}
