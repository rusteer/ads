// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.control.SoftWareUpdateManager;
import com.gionee.aora.market.gui.ebook.InitEbookActivity;
import java.io.File;

public class ReDownloadDialog extends MarketFloatAcitivityBase
{

    public ReDownloadDialog()
    {
        manager = null;
        info = null;
        msg = "";
        isBackup = false;
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[2];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                finish();
            }

            
        }
;
        aonclicklistener[1] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(info.getPackageName().equals("com.gionee.aora.ebook"))
                {
                    manager.deleteDownload(info);
                    (new Handler()).postDelayed(new Runnable() {

                        public void run()
                        {
                            Intent intent = new Intent(ReDownloadDialog.this, InitEbookActivity.class);
                            startActivity(intent);
                        }

                        
                    }
, 500L);
                } else
                {
                    File file = new File(info.getPath());
                    if(file.exists())
                        file.delete();
                    if(isBackup)
                    {
                        info.setPath(info.getPath().replace("updateApk", "downloadApk"));
                        SoftWareUpdateManager.getInstance().deleteDownloadInfo(info.getPackageName());
                        manager.addDownload(info);
                    } else
                    {
                        manager.reDownloadLostedFinishedTask(info);
                    }
                    sendBroadcast(new Intent("com.gionee.aora.market.action.ACTION_SOFTWARE_REDOWNLOAD"));
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
            "\u53D6\u6D88", "\u91CD\u65B0\u4E0B\u8F7D"
        });
    }

    public String getDialogTitle()
    {
        return null;
    }

    public String getMessage()
    {
        return msg;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        manager = DownloadManager.shareInstance();
        info = (DownloadInfo)getIntent().getSerializableExtra("DOWNLOADINFO");
        msg = getIntent().getStringExtra("MESSAGE");
        messageTv.setText(msg);
        isBackup = getIntent().getBooleanExtra("IS_BACKUP", false);
    }

    private DownloadInfo info;
    private boolean isBackup;
    private DownloadManager manager;
    private String msg;



}
