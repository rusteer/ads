// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.download.DownloadInfo;

public class BannerInfo
{

    public BannerInfo()
    {
        softId = "";
        softName = "";
        packageName = "";
        iconUrl = "";
        imageUrl = "";
        downloadUrl = "";
        openTxt = "";
        integral = 0;
        openingTxt = "";
        isShow = false;
    }

    public int getDownloadSize()
    {
        return downloadSize;
    }

    public String getDownloadUrl()
    {
        return downloadUrl;
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public int getIntegral()
    {
        return integral;
    }

    public String getOpenTxt()
    {
        return openTxt;
    }

    public String getOpeningTxt()
    {
        return openingTxt;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public String getSoftId()
    {
        return softId;
    }

    public String getSoftName()
    {
        return softName;
    }

    public boolean isShow()
    {
        return isShow;
    }

    public void setDownloadSize(int i)
    {
        downloadSize = i;
    }

    public void setDownloadUrl(String s)
    {
        downloadUrl = s;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setImageUrl(String s)
    {
        imageUrl = s;
    }

    public void setIntegral(int i)
    {
        integral = i;
    }

    public void setOpenTxt(String s)
    {
        openTxt = s;
    }

    public void setOpeningTxt(String s)
    {
        openingTxt = s;
    }

    public void setPackageName(String s)
    {
        packageName = s;
    }

    public void setShow(boolean flag)
    {
        isShow = flag;
    }

    public void setSoftId(String s)
    {
        softId = s;
    }

    public void setSoftName(String s)
    {
        softName = s;
    }

    public DownloadInfo toDownloadInfo()
    {
        return new DownloadInfo(getSoftName(), getPackageName(), getDownloadUrl(), getIconUrl(), getDownloadSize(), getSoftId(), getIntegral());
    }

    public String toString()
    {
        return (new StringBuilder()).append("BannerInfo [softId=").append(softId).append(", softName=").append(softName).append(", packageName=").append(packageName).append(", iconUrl=").append(iconUrl).append(", downloadUrl=").append(downloadUrl).append(", downloadSize=").append(downloadSize).append(", openTxt=").append(openTxt).append(", integral=").append(integral).append(", openingTxt=").append(openingTxt).append(", isShow=").append(isShow).append("]").toString();
    }

    private int downloadSize;
    private String downloadUrl;
    private String iconUrl;
    private String imageUrl;
    private int integral;
    private boolean isShow;
    private String openTxt;
    private String openingTxt;
    private String packageName;
    private String softId;
    private String softName;
}
