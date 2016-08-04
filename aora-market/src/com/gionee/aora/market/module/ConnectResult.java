// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;


public class ConnectResult
{

    public ConnectResult()
    {
        resultCode = 0;
        msg = "";
    }

    public String getMsg()
    {
        return msg;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public void setMsg(String s)
    {
        msg = s;
    }

    public void setResultCode(int i)
    {
        resultCode = i;
    }

    private String msg;
    private int resultCode;
}
