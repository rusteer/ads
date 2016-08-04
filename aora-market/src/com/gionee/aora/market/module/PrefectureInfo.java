// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.util.ArrayList;
import java.util.List;

public class PrefectureInfo
{

    public PrefectureInfo()
    {
        appInfos = new ArrayList();
    }

    public PrefectureInfo(String s, int i, String s1, int j, String s2, String s3, boolean flag)
    {
        appInfos = new ArrayList();
        iconUrl = s;
        id = i;
        name = s1;
        browseCount = j;
        skipUrl = s2;
        time = s3;
        isFinish = flag;
    }

    public List getAppInfos()
    {
        return appInfos;
    }

    public int getBrowseCount()
    {
        return browseCount;
    }

    public String getDescrible()
    {
        return describle;
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

    public String getTime()
    {
        return time;
    }

    public boolean isFinish()
    {
        return isFinish;
    }

    public void setAppInfos(List list)
    {
        appInfos = list;
    }

    public void setBrowseCount(int i)
    {
        browseCount = i;
    }

    public void setDescrible(String s)
    {
        describle = s;
    }

    public void setFinish(boolean flag)
    {
        isFinish = flag;
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

    public void setTime(String s)
    {
        time = s;
    }

    private List appInfos;
    private int browseCount;
    private String describle;
    private String iconUrl;
    private int id;
    private boolean isFinish;
    private String name;
    private String skipUrl;
    private String time;
}
