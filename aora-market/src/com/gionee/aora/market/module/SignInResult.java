// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.gionee.aora.integral.module.UserInfo;
import java.io.Serializable;

public class SignInResult
    implements Serializable
{

    public SignInResult()
    {
        resultcode = -1;
        msg = "";
        isAutoLogin = 0;
        caidanMsg = "";
        caidanCoin = 0;
    }

    public int getCaidanCoin()
    {
        return caidanCoin;
    }

    public String getCaidanMsg()
    {
        return caidanMsg;
    }

    public UserInfo getInfo()
    {
        return info;
    }

    public int getIsAutoLogin()
    {
        return isAutoLogin;
    }

    public String getMsg()
    {
        return msg;
    }

    public int getResultcode()
    {
        return resultcode;
    }

    public void setCaidanCoin(int i)
    {
        caidanCoin = i;
    }

    public void setCaidanMsg(String s)
    {
        caidanMsg = s;
    }

    public void setInfo(UserInfo userinfo)
    {
        info = userinfo;
    }

    public void setIsAutoLogin(int i)
    {
        isAutoLogin = i;
    }

    public void setMsg(String s)
    {
        msg = s;
    }

    public void setResultcode(int i)
    {
        resultcode = i;
    }

    private static final long serialVersionUID = 0x198ca0fa93009897L;
    private int caidanCoin;
    private String caidanMsg;
    private UserInfo info;
    private int isAutoLogin;
    private String msg;
    private int resultcode;
}
