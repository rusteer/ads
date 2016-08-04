// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class CategoryInfo
    implements Serializable
{

    public CategoryInfo()
    {
    }

    public String getSortIcon()
    {
        return sortIcon;
    }

    public String getSortId()
    {
        return sortId;
    }

    public String getSortName()
    {
        return sortName;
    }

    public void setSortIcon(String s)
    {
        sortIcon = s;
    }

    public void setSortId(String s)
    {
        sortId = s;
    }

    public void setSortName(String s)
    {
        sortName = s;
    }

    private static final long serialVersionUID = 1L;
    private String sortIcon;
    private String sortId;
    private String sortName;
}
