// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class PermissionInfo
    implements Serializable
{

    public PermissionInfo()
    {
    }

    public String getPermissionDescrible()
    {
        return PermissionDescrible;
    }

    public String getPermissionTitle()
    {
        return PermissionTitle;
    }

    public void setPermissionDescrible(String s)
    {
        PermissionDescrible = s;
    }

    public void setPermissionTitle(String s)
    {
        PermissionTitle = s;
    }

    public String toString()
    {
        return (new StringBuilder()).append("PermissionInfo [PermissionTitle=").append(PermissionTitle).append(", PermissionDescrible=").append(PermissionDescrible).append("]").toString();
    }

    private static final long serialVersionUID = 1L;
    private String PermissionDescrible;
    private String PermissionTitle;
}
