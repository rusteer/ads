// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import android.content.Context;
import android.content.pm.*;
import com.aora.base.util.DLog;

public class UpdateInfo
{

    public UpdateInfo()
    {
        packageName = "";
        appName = "";
        versionName = "";
        versionCode = 0;
        desc = "";
        url = "";
        iType = 48;
        size = 0L;
        erroInfo = "";
    }

    public static UpdateInfo getDefaultInstance(Context context)
    {
        UpdateInfo updateinfo = new UpdateInfo();
        updateinfo.packageName = context.getPackageName();
        PackageManager packagemanager = context.getPackageManager();
        PackageInfo packageinfo;
        try
        {
            packageinfo = packagemanager.getPackageInfo(context.getPackageName(), 0);
        }
        catch(Exception exception)
        {
            DLog.e(TAG, "getDefaultInstance", exception);
            return updateinfo;
        }
        if(packageinfo != null)
        {
        updateinfo.appName = packageinfo.applicationInfo.loadLabel(packagemanager).toString();
        updateinfo.versionCode = packageinfo.versionCode;
        updateinfo.versionName = packageinfo.versionName;
        }
        return updateinfo;
    }

    static String TAG = "UpdateInfo";
    public String appName;
    public String desc;
    public String erroInfo;
    public int iType;
    public String packageName;
    public long size;
    public String url;
    public int versionCode;
    public String versionName;

}
