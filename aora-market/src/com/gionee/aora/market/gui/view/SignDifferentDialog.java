// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.module.AppInfo;

public class SignDifferentDialog extends MarketFloatAcitivityBase
{

    public SignDifferentDialog()
    {
        downloadManager = null;
        softwareManager = null;
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[2];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                softwareManager.changeUpdateToIgnore(appInfo.getPackageName());
                finish();
            }

             
        }
;
        aonclicklistener[1] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                com.gionee.aora.download.DownloadInfo downloadinfo = appInfo.toDownloadInfo();
                if(downloadManager.addDownload(downloadinfo))
                    softwareManager.uninstallApk(appInfo.getPackageName());
                finish();
            }

            
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u5FFD\u7565\u66F4\u65B0", "\u7EE7\u7EED\u66F4\u65B0"
        });
    }

    public String getDialogTitle()
    {
        return null;
    }

    public String getMessage()
    {
        return null;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        softwareManager = SoftwareManager.getInstace();
        appInfo = (AppInfo)getIntent().getSerializableExtra("APPINFO");
        downloadManager = DownloadManager.shareInstance();
        messageTv.setText((new StringBuilder()).append("\u4F60\u539F\u6709\u7684\u3010").append(appInfo.getName()).append("\u3011\u4E0E\u65B0\u7248\u672C\u4E0D\u517C\u5BB9\uFF0C\u9700\u5378\u8F7D\u540E\u91CD\u65B0\u5B89\u88C5\uFF0C\u662F\u5426\u7EE7\u7EED\u66F4\u65B0\uFF1F").toString());
    }

    private AppInfo appInfo;
    private DownloadManager downloadManager;
    private SoftwareManager softwareManager;



}
