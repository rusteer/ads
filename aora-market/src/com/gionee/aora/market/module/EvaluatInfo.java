// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

// Referenced classes of package com.gionee.aora.market.module:
//            AppInfo

public class EvaluatInfo
    implements Serializable
{

    public EvaluatInfo()
    {
        appInfo = new AppInfo();
    }

    public String getAppDescrible()
    {
        return appDescrible;
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

    public AppInfo getAppInfo()
    {
        return appInfo;
    }

    public int getAppIntegral()
    {
        return appIntegral;
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

    public String getAuthor()
    {
        return author;
    }

    public String getBrowseCount()
    {
        return browseCount;
    }

    public String getDate()
    {
        return date;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public int getId()
    {
        return Id;
    }

    public String getName()
    {
        return name;
    }

    public String getSkipUrl()
    {
        return skipUrl;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public boolean isEnd()
    {
        return isEnd;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setAppDescrible(String s)
    {
        appDescrible = s;
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

    public void setAppInfo(AppInfo appinfo)
    {
        appInfo = appinfo;
    }

    public void setAppIntegral(int i)
    {
        appIntegral = i;
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

    public void setAuthor(String s)
    {
        author = s;
    }

    public void setBrowseCount(String s)
    {
        browseCount = s;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setEnd(boolean flag1)
    {
        isEnd = flag1;
    }

    public void setFlag(boolean flag1)
    {
        flag = flag1;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setId(int i)
    {
        Id = i;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setSkipUrl(String s)
    {
        skipUrl = s;
    }

    public void setStartDate(String s)
    {
        startDate = s;
    }

    public AppInfo toAppInfo()
    {
        appInfo = new AppInfo();
        appInfo.setSoftId((new StringBuilder()).append(getAppId()).append("").toString());
        appInfo.setName(getAppName());
        appInfo.setIconUrl(getAppIconUrl());
        appInfo.setSize(getAppSize());
        appInfo.setPackageName(getAppPackageName());
        appInfo.setDownload_region(getAppDownloadCount());
        appInfo.setDownloadUrl(getAppDownloadUrl());
        appInfo.setUpdateSoftSize((int)getAppLongSize());
        appInfo.setvId(getId());
        appInfo.setUpdateVersionName(getAppVersionName());
        appInfo.setDescribe(getAppDescrible());
        appInfo.setIntegral(getAppIntegral());
        return appInfo;
    }

    public String toString()
    {
        return (new StringBuilder()).append("EvaluatInfo [iconUrl=").append(iconUrl).append(", skipUrl=").append(skipUrl).append(", Id=").append(Id).append(", name=").append(name).append(", author=").append(author).append(", date=").append(date).append(", browseCount=").append(browseCount).append(", startDate=").append(startDate).append(", isEnd=").append(isEnd).append(", flag=").append(flag).append(", appId=").append(appId).append(", appName=").append(appName).append(", appIconUrl=").append(appIconUrl).append(", appSize=").append(appSize).append(", appPackageName=").append(appPackageName).append(", appDownloadCount=").append(appDownloadCount).append(", appDownloadUrl=").append(appDownloadUrl).append(", appLongSize=").append(appLongSize).append(", appVersionName=").append(appVersionName).append("]").toString();
    }

    private static final long serialVersionUID = 0x18079bd4e01f8e31L;
    private int Id;
    private String appDescrible;
    private String appDownloadCount;
    private String appDownloadUrl;
    private String appIconUrl;
    private int appId;
    private AppInfo appInfo;
    private int appIntegral;
    private long appLongSize;
    private String appName;
    private String appPackageName;
    private String appSize;
    private String appVersionName;
    private String author;
    private String browseCount;
    private String date;
    private boolean flag;
    private String iconUrl;
    private boolean isEnd;
    private String name;
    private String skipUrl;
    private String startDate;
}
