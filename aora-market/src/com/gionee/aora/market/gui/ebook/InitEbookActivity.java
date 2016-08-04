// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.ebook;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.*;
import android.widget.Toast;
import com.gionee.aora.download.*;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.module.UpdateInfo;
import com.gionee.aora.market.net.MarketUpdateNet;

public class InitEbookActivity extends Activity
{

    public InitEbookActivity()
    {
        isDestrory = false;
        isInitdialogNeedFinish = true;
        isErrorNeedFinish = true;
    }

    private void init()
    {
        if(!SoftwareManager.getInstace().checkSoftwareInstalled("com.gionee.aora.ebook", -1))
        {
            DownloadInfo downloadinfo = downloadManager.queryDownload("com.gionee.aora.ebook");
            if(downloadinfo != null)
            {
                if(downloadinfo.getState() == 3 && !Constants.canAutoInstall)
                {
                    SoftwareManager.getInstace().installApp(downloadManager, downloadManager.queryDownload("com.gionee.aora.ebook"));
                    finish();
                    return;
                }
                if(downloadinfo.getState() == 1)
                {
                    finish();
                    return;
                }
                Toast.makeText(this, "\u4E66\u57CE\u6B63\u5728\u521D\u59CB\u5316...", 0).show();
                if(!downloadManager.addDownload(downloadinfo))
                    handler.sendEmptyMessage(1);
                finish();
                return;
            } else
            {
                Toast.makeText(this, "\u4E66\u57CE\u6B63\u5728\u521D\u59CB\u5316...", 0).show();
                startInitEbook();
                finish();
                return;
            }
        } else
        {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.gionee.aora.ebook", "com.gionee.aora.ebook.ebook.GoMarketBookshelfActivity"));
            intent.addFlags(0x10000000);
            intent.putExtra("IS_FROME_MARKET", true);
            startActivity(intent);
            finish();
            return;
        }
    }

    private void initDownloadListener()
    {
        listener = new DownloadListener() {

            public void onProgress(int i, DownloadInfo downloadinfo)
            {
            }

            public void onStateChange(int i, DownloadInfo downloadinfo)
            {
                while(downloadinfo.getPackageName() != "com.gionee.aora.ebook" || i != 4) 
                    return;
                handler.sendEmptyMessage(1);
            }

            public void onTaskCountChanged(int i, DownloadInfo downloadinfo)
            {
            }

            
        }
;
        downloadManager = DownloadManager.shareInstance();
        downloadManager.registerDownloadListener(listener);
    }

    private void initHander()
    {
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                super.handleMessage(message);
                switch(message.what)
                {
                default:
                    return;

                case 1: // '\001'
                    isInitdialogNeedFinish = false;
                    isErrorNeedFinish = true;
                    Toast.makeText(InitEbookActivity.this, "\u4E66\u57CE\u521D\u59CB\u5316\u5931\u8D25", 0).show();
                    return;

                case 0: // '\0'
                    isInitdialogNeedFinish = true;
                    isErrorNeedFinish = false;
                    startInitEbook();
                    return;
                }
            }

            
        }
;
    }

    private void startInitEbook()
    {
        (new AsyncTask<Void,UpdateInfo,UpdateInfo>() {

            protected   UpdateInfo doInBackground(Void avoid[])
            {
                UpdateInfo updateinfo = new UpdateInfo();
                updateinfo.packageName = "com.gionee.aora.ebook";
                return MarketUpdateNet.checkUpdate(InitEbookActivity.this, updateinfo, true);
            }

         

            protected void onPostExecute(UpdateInfo updateinfo)
            {
                super.onPostExecute(updateinfo);
                if(isDestrory)
                    return;
                if(updateinfo == null)
                {
                    handler.sendEmptyMessage(1);
                    return;
                }
                DownloadInfo downloadinfo = new DownloadInfo("\u6613\u4E66\u57CE", "com.gionee.aora.ebook", updateinfo.url, "", updateinfo.size, "", 0);
                if(!DownloadManager.shareInstance().addDownload(downloadinfo))
                    handler.sendEmptyMessage(1);
                finish();
            }

            

           
        }
).execute(new Void[0]);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        initHander();
        initDownloadListener();
        init();
    }

    protected void onDestroy()
    {
        if(downloadManager != null)
            downloadManager.unregisterDownloadListener(listener);
        isDestrory = true;
        super.onDestroy();
    }

    protected void onRestart()
    {
        super.onRestart();
        finish();
    }

    public static final String EBOOK_PACKAGENAME = "com.gionee.aora.ebook";
    private final int INIT_ERROR = 1;
    private final int INIT_RETRY = 0;
    private DownloadManager downloadManager;
    private Handler handler;
    private boolean isDestrory;
    private boolean isErrorNeedFinish;
    private boolean isInitdialogNeedFinish;
    private DownloadListener listener;


/*
    static boolean access$002(InitEbookActivity initebookactivity, boolean flag)
    {
        initebookactivity.isInitdialogNeedFinish = flag;
        return flag;
    }

*/


/*
    static boolean access$102(InitEbookActivity initebookactivity, boolean flag)
    {
        initebookactivity.isErrorNeedFinish = flag;
        return flag;
    }

*/



}
