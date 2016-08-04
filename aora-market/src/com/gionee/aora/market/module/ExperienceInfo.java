// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.download.DownloadInfo;

// Referenced classes of package com.gionee.aora.market.module:
//            AppInfo

public class ExperienceInfo extends AppInfo
{

    public ExperienceInfo()
    {
    }

    public String getExAppId()
    {
        return exAppId;
    }

    public String getExDownloadCount()
    {
        return exDownloadCount;
    }

    public String getExDownloadUrl()
    {
        return exDownloadUrl;
    }

    public String getExIcon()
    {
        return exIcon;
    }

    public int getExId()
    {
        return exId;
    }

    public String getExImage()
    {
        return exImage;
    }

    public String getExIntro()
    {
        return exIntro;
    }

    public String getExName()
    {
        return exName;
    }

    public String getExPackageName()
    {
        return exPackageName;
    }

    public long getExSize()
    {
        return exSize;
    }

    public String getExSkipUrl()
    {
        return exSkipUrl;
    }

    public String getExTitle()
    {
        return exTitle;
    }

    public String getExVersionName()
    {
        return exVersionName;
    }

    public int getExiIntegral()
    {
        return exiIntegral;
    }

    public int getImageHeight()
    {
        return imageHeight;
    }

    public void setExAppId(String s)
    {
        exAppId = s;
    }

    public void setExDownloadCount(String s)
    {
        exDownloadCount = s;
    }

    public void setExDownloadUrl(String s)
    {
        exDownloadUrl = s;
    }

    public void setExIcon(String s)
    {
        exIcon = s;
    }

    public void setExId(int i)
    {
        exId = i;
    }

    public void setExImage(String s)
    {
        exImage = s;
    }

    public void setExIntro(String s)
    {
        exIntro = s;
    }

    public void setExName(String s)
    {
        exName = s;
    }

    public void setExPackageName(String s)
    {
        exPackageName = s;
    }

    public void setExSize(long l)
    {
        exSize = l;
    }

    public void setExSkipUrl(String s)
    {
        exSkipUrl = s;
    }

    public void setExTitle(String s)
    {
        exTitle = s;
    }

    public void setExVersionName(String s)
    {
        exVersionName = s;
    }

    public void setExiIntegral(int i)
    {
        exiIntegral = i;
    }

    public void setImageHeight(int i)
    {
        imageHeight = i;
    }

    public AppInfo toAppInfo()
    {
        AppInfo appinfo = new AppInfo(getExAppId(), getExIcon(), getExName(), getExPackageName(), getExDownloadUrl(), exSize, getExVersionName(), getExiIntegral(), getExDownloadCount());
        appinfo.setvId(getExId());
        return appinfo;
    }

    public DownloadInfo toDownloadInfo()
    {
        return new DownloadInfo(getExName(), getExPackageName(), getExDownloadUrl(), getExIcon(), exSize, getExAppId(), getExiIntegral());
    }

    private static final long serialVersionUID = 0xed55a0883f7aad76L;
    private String exAppId;
    private String exDownloadCount;
    private String exDownloadUrl;
    private String exIcon;
    private int exId;
    private String exImage;
    private String exIntro;
    private String exName;
    private String exPackageName;
    private long exSize;
    private String exSkipUrl;
    private String exTitle;
    private String exVersionName;
    private int exiIntegral;
    private int imageHeight;
}
