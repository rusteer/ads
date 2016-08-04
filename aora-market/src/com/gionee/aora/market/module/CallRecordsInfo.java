// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import android.net.Uri;
import java.io.Serializable;

public class CallRecordsInfo
    implements Serializable
{

    public CallRecordsInfo()
    {
    }

    public String getCallee()
    {
        return callee;
    }

    public String getCallname()
    {
        return callname;
    }

    public String getPhone()
    {
        return phone;
    }

    public Uri getPhotouri()
    {
        return photouri;
    }

    public String getSale()
    {
        return sale;
    }

    public String getTime()
    {
        return time;
    }

    public void setCallee(String s)
    {
        callee = s;
    }

    public void setCallname(String s)
    {
        callname = s;
    }

    public void setPhone(String s)
    {
        phone = s;
    }

    public void setPhotouri(Uri uri)
    {
        photouri = uri;
    }

    public void setSale(String s)
    {
        sale = s;
    }

    public void setTime(String s)
    {
        time = s;
    }

    private static final long serialVersionUID = 1L;
    private String callee;
    private String callname;
    private String phone;
    private Uri photouri;
    private String sale;
    private String time;
}
