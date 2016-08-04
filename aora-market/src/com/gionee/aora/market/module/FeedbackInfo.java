// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class FeedbackInfo
    implements Serializable
{

    public FeedbackInfo()
    {
    }

    public FeedbackInfo(String s, int i)
    {
        msg = s;
        type = i;
    }

    public String getMsg()
    {
        return msg;
    }

    public int getType()
    {
        return type;
    }

    public void setMsg(String s)
    {
        msg = s;
    }

    public void setType(int i)
    {
        type = i;
    }

    private static final long serialVersionUID = 0xd2fd40f01f9a275L;
    public String msg;
    public int type;
}
