// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.aora.base.datacollect.BaseCollectManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.control.UpdateManager;

public class UpdateDialogActivity extends MarketFloatAcitivityBase
{

    public UpdateDialogActivity()
    {
        appName = "";
        pkgName = "";
        url = "";
        size = 0L;
        type = 0;
        desc = "";
        message = "";
        title = "";
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[2];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
            }

            
        }
;
        aonclicklistener[1] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                BaseCollectManager.startUpload();
                finish();
                um.startDownload(appName, pkgName, url, size, type);
            }

            
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        String as[] = new String[2];
        as[0] = getString(biz.AR.string.not_update_now);
        as[1] = getString(biz.AR.string.update_now);
        return as;
    }

    public String getDialogTitle()
    {
        return title;
    }

    public String getMessage()
    {
        return desc;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        um = UpdateManager.getInstance();
        Intent intent = getIntent();
        appName = intent.getStringExtra("appName");
        pkgName = intent.getStringExtra("pkgName");
        url = intent.getStringExtra("apkurl");
        size = intent.getLongExtra("size", 0L);
        type = intent.getIntExtra("itype", 48);
        desc = intent.getStringExtra("desc");
        String s = getString(biz.AR.string.found_new_version);
        Object aobj[] = new Object[2];
        aobj[0] = appName;
        aobj[1] = intent.getStringExtra("vn");
        title = String.format(s, aobj);
    }

    public static final String KEY_APP_NAME = "appName";
    public static final String KEY_PACKAGE_NAME = "pkgName";
    public static final String KEY_SIZE = "size";
    public static final String KEY_TYPE = "itype";
    public static final String KEY_URL = "apkurl";
    String appName;
    String desc;
    String message;
    String pkgName;
    long size;
    String title;
    Button tv1;
    Button tv2;
    Button tv3;
    int type;
    UpdateManager um;
    String url;
}
