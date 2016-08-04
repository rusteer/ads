// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;


public class SearchRankInfo
{

    public SearchRankInfo()
    {
        softName = "";
        downloadState = "";
        sn = "";
        softPackageName = "";
        softId = "";
        softDownloadUrl = "";
    }

    public String getDownloadState()
    {
        return downloadState;
    }

    public String getSn()
    {
        return sn;
    }

    public String getSoftDownloadUrl()
    {
        return softDownloadUrl;
    }

    public String getSoftId()
    {
        return softId;
    }

    public String getSoftName()
    {
        return softName;
    }

    public String getSoftPackageName()
    {
        return softPackageName;
    }

    public void setDownloadState(String s)
    {
        downloadState = s;
    }

    public void setSn(String s)
    {
        sn = s;
    }

    public void setSoftDownloadUrl(String s)
    {
        softDownloadUrl = s;
    }

    public void setSoftId(String s)
    {
        softId = s;
    }

    public void setSoftName(String s)
    {
        softName = s;
    }

    public void setSoftPackageName(String s)
    {
        softPackageName = s;
    }

    private String downloadState;
    private String sn;
    private String softDownloadUrl;
    private String softId;
    private String softName;
    private String softPackageName;
}
