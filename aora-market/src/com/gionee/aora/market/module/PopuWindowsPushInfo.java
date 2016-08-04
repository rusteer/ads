// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;
import java.util.ArrayList;

// Referenced classes of package com.gionee.aora.market.module:
//            EvaluatInfo

public class PopuWindowsPushInfo
    implements Serializable
{

    public PopuWindowsPushInfo()
    {
        bannerType = 0;
        bannerIconUrl = "";
        bannerContent = "";
        apps = new ArrayList();
    }

    public ArrayList getApps()
    {
        return apps;
    }

    public String getBannerContent()
    {
        return bannerContent;
    }

    public String getBannerIconUrl()
    {
        return bannerIconUrl;
    }

    public int getBannerType()
    {
        return bannerType;
    }

    public EvaluatInfo getEvaluatInfo()
    {
        return evaluatInfo;
    }

    public void setApps(ArrayList arraylist)
    {
        apps = arraylist;
    }

    public void setBannerContent(String s)
    {
        bannerContent = s;
    }

    public void setBannerIconUrl(String s)
    {
        bannerIconUrl = s;
    }

    public void setBannerType(int i)
    {
        bannerType = i;
    }

    public void setEvaluatInfo(EvaluatInfo evaluatinfo)
    {
        evaluatInfo = evaluatinfo;
    }

    private static final long serialVersionUID = 0x86c0597c7ef981f6L;
    private ArrayList apps;
    private String bannerContent;
    private String bannerIconUrl;
    private int bannerType;
    private EvaluatInfo evaluatInfo;
}
