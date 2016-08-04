// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;


public class LenjoyUserInfo
{

    public LenjoyUserInfo()
    {
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public String getImei()
    {
        return imei;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setImei(String s)
    {
        imei = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setSurname(String s)
    {
        surname = s;
    }

    private String iconUrl;
    private String imei;
    private String name;
    private String surname;
}
