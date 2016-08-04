// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;
import java.util.ArrayList;

public class MixtrueInfo
    implements Serializable
{

    public MixtrueInfo()
    {
    }

    public String getMixContent()
    {
        return mixContent;
    }

    public String getMixId()
    {
        return mixId;
    }

    public ArrayList getMixInfos()
    {
        return mixInfos;
    }

    public int getMixStart()
    {
        return mixStart;
    }

    public String getMixSuper()
    {
        return mixSuper;
    }

    public String getMixThum()
    {
        return mixThum;
    }

    public String getMixTitle()
    {
        return mixTitle;
    }

    public int getMixType()
    {
        return mixType;
    }

    public void setMixContent(String s)
    {
        mixContent = s;
    }

    public void setMixId(String s)
    {
        mixId = s;
    }

    public void setMixInfos(ArrayList arraylist)
    {
        mixInfos = arraylist;
    }

    public void setMixStart(int i)
    {
        mixStart = i;
    }

    public void setMixSuper(String s)
    {
        mixSuper = s;
    }

    public void setMixThum(String s)
    {
        mixThum = s;
    }

    public void setMixTitle(String s)
    {
        mixTitle = s;
    }

    public void setMixType(int i)
    {
        mixType = i;
    }

    public String toString()
    {
        return (new StringBuilder()).append("MixtrueInfo [mixType=").append(mixType).append(", mixTitle=").append(mixTitle).append(", mixContent=").append(mixContent).append(", mixThum=").append(mixThum).append(", mixInfos=").append(mixInfos).append("]").toString();
    }

    private static final long serialVersionUID = 1L;
    private String mixContent;
    private String mixId;
    private ArrayList mixInfos;
    private int mixStart;
    private String mixSuper;
    private String mixThum;
    private String mixTitle;
    private int mixType;
}
