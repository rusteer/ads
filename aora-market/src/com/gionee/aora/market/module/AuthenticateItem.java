// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class AuthenticateItem
    implements Serializable
{

    public AuthenticateItem()
    {
        iconUrl = "";
        name = "";
    }

    public String getIconUrl()
    {
        return iconUrl;
    }

    public String getName()
    {
        return name;
    }

    public void setIconUrl(String s)
    {
        iconUrl = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    private static final long serialVersionUID = 0x72136714ef055c86L;
    private String iconUrl;
    private String name;
}
