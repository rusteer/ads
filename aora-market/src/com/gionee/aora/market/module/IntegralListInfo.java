// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

// Referenced classes of package com.gionee.aora.market.module:
//            AppInfo

public class IntegralListInfo
    implements Serializable
{

    public IntegralListInfo()
    {
        installState = 0;
    }

    public IntegralListInfo(AppInfo appinfo, int i)
    {
        installState = 0;
        appInfo = appinfo;
        installState = i;
    }

    public AppInfo getAppInfo()
    {
        return appInfo;
    }

    public int getInstallState()
    {
        return installState;
    }

    public void setAppInfo(AppInfo appinfo)
    {
        appInfo = appinfo;
    }

    public void setInstallState(int i)
    {
        installState = i;
    }

    private static final long serialVersionUID = 0x6680933d4f08d304L;
    private AppInfo appInfo;
    private int installState;
}
