// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class LoginInfo
    implements Serializable
{

    public LoginInfo()
    {
    }

    public LoginInfo(String s, String s1, String s2, String s3, String s4)
    {
        url = s;
        tagId = s1;
        tag = s2;
        session = s3;
        tagLogin = s4;
    }

    private static final long serialVersionUID = 0x472d2a8093a3cc98L;
    public int push;
    public String session;
    public String tag;
    public String tagId;
    public String tagLogin;
    public String url;
}
