// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class RecommendAdInfo
    implements Serializable
{

    public RecommendAdInfo()
    {
    }

    public Object getContent()
    {
        return content;
    }

    public String getDescription()
    {
        return description;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getType()
    {
        return type;
    }

    public String getUrl()
    {
        return url;
    }

    public void setContent(Object obj)
    {
        content = obj;
    }

    public void setDescription(String s)
    {
        description = s;
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setType(int i)
    {
        type = i;
    }

    public void setUrl(String s)
    {
        url = s;
    }

    private static final long serialVersionUID = 0x99ffdc35fe8e663cL;
    private Object content;
    private String description;
    private String id;
    private String name;
    private int type;
    private String url;
}
