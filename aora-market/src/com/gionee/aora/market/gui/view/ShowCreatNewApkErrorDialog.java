// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import java.io.File;

public class ShowCreatNewApkErrorDialog extends MarketFloatAcitivityBase
{

    public ShowCreatNewApkErrorDialog()
    {
        info = null;
        manager = null;
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[2];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                manager.deleteDownload(info);
                finish();
            }

            
        }
;
        aonclicklistener[1] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                DownloadInfo downloadinfo = manager.queryDownload(info.getPackageName());
                (new File(info.getPath())).delete();
                if(downloadinfo != null)
                {
                    downloadinfo.setUrl(info.getRelApkUrl());
                    downloadinfo.setSize(info.getRelApkSize());
                    manager.reDownloadLostedFinishedTask(downloadinfo);
                }
                finish();
            }

            
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u53D6\u6D88", "\u666E\u901A\u4E0B\u8F7D"
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
        manager = DownloadManager.shareInstance();
        info = (DownloadInfo)getIntent().getSerializableExtra("DOWNLOADINFO");
        getIntent().getStringExtra("MESSAGE");
        messageTv.setText("\u589E\u91CF\u5347\u7EA7\u5931\u8D25\uFF0C\u662F\u5426\u4F7F\u7528\u666E\u901A\u4E0B\u8F7D\uFF1F");
    }

    private DownloadInfo info;
    private DownloadManager manager;


}
