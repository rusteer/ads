// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class ConvenInfo
    implements Serializable
{

    public ConvenInfo()
    {
        convenCheck = false;
    }

    public boolean getConvenCheck()
    {
        return convenCheck;
    }

    public String getConvenIcon()
    {
        return convenIcon;
    }

    public String getConvenIconBig()
    {
        return convenIconBig;
    }

    public String getConvenId()
    {
        return convenId;
    }

    public String getConvenIntro()
    {
        return convenIntro;
    }

    public String getConvenName()
    {
        return convenName;
    }

    public void setConvenCheck(boolean flag)
    {
        convenCheck = flag;
    }

    public void setConvenIcon(String s)
    {
        convenIcon = s;
    }

    public void setConvenIconBig(String s)
    {
        convenIconBig = s;
    }

    public void setConvenId(String s)
    {
        convenId = s;
    }

    public void setConvenIntro(String s)
    {
        convenIntro = s;
    }

    public void setConvenName(String s)
    {
        convenName = s;
    }

    public String toString()
    {
        return (new StringBuilder()).append("ConvenInfo [convenId=").append(convenId).append(", convenName=").append(convenName).append(", convenIcon=").append(convenIcon).append(", convenIconBig=").append(convenIconBig).append(", convenIntro=").append(convenIntro).append("]").toString();
    }

    private static final long serialVersionUID = 1L;
    private boolean convenCheck;
    private String convenIcon;
    private String convenIconBig;
    private String convenId;
    private String convenIntro;
    private String convenName;
}
