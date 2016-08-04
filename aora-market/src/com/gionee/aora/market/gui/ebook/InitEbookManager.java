// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.ebook;

import android.content.*;
import android.widget.Toast;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.module.UpdateInfo;
import com.gionee.aora.market.net.MarketUpdateNet;
import com.gionee.aora.market.util.MarketAsyncTask;

public class InitEbookManager
{

    public InitEbookManager()
    {
        downloadManager = DownloadManager.shareInstance();
    }

    public static InitEbookManager getInstance()
    {
        if(initEbookManager == null)
            initEbookManager = new InitEbookManager();
        return initEbookManager;
    }

    public static boolean isFastDoubleClick()
    {
        long l = System.currentTimeMillis();
        if(l - lastClickTime < 500L)
        {
            return true;
        } else
        {
            lastClickTime = l;
            return false;
        }
    }

    private static void startInitEbook(final Context context)
    {
        (new MarketAsyncTask<Void,UpdateInfo,UpdateInfo>( ) {

            protected   UpdateInfo doInBackground(Void avoid[])
            {
                UpdateInfo updateinfo = new UpdateInfo();
                updateinfo.packageName = "com.gionee.aora.ebook";
                return MarketUpdateNet.checkUpdate(context, updateinfo, true);
            }

            

            protected void onPostExecute(UpdateInfo updateinfo)
            {
                super.onPostExecute(updateinfo);
                if(updateinfo == null)
                {
                    Toast.makeText(context.getApplicationContext(), "\u7535\u5B50\u4E66\u521D\u59CB\u5316\u5931\u8D25.", 0).show();
                } else
                {
                    DownloadInfo downloadinfo = new DownloadInfo("\u6613\u4E66\u57CE", "com.gionee.aora.ebook", updateinfo.url, "", updateinfo.size, "", 0);
                    if(!DownloadManager.shareInstance().addDownload(downloadinfo))
                    {
                        Toast.makeText(context.getApplicationContext(), "\u7535\u5B50\u4E66\u521D\u59CB\u5316\u5931\u8D25.", 0).show();
                        return;
                    }
                }
            }

           

           
        }
).doExecutor(new Void[0]);
    }

    public void initEbook(Context context)
    {
        if(!isFastDoubleClick())
            if(!SoftwareManager.getInstace().checkSoftwareInstalled("com.gionee.aora.ebook", -1))
            {
                DownloadInfo downloadinfo = downloadManager.queryDownload("com.gionee.aora.ebook");
                if(downloadinfo != null)
                {
                    if(downloadinfo.getState() == 3 && !Constants.canAutoInstall)
                    {
                        SoftwareManager.getInstace().installApp(downloadManager, downloadManager.queryDownload("com.gionee.aora.ebook"));
                        return;
                    }
                    if(downloadinfo.getState() != 1)
                    {
                        Toast.makeText(context.getApplicationContext(), "\u4E66\u57CE\u6B63\u5728\u521D\u59CB\u5316...", 0).show();
                        if(!downloadManager.addDownload(downloadinfo))
                        {
                            Toast.makeText(context.getApplicationContext(), "\u4E66\u57CE\u5DF2\u7ECF\u5728\u4E0B\u8F7D\u4EFB\u52A1\u4E2D\uFF0C\u8BF7\u7A0D\u7B49...", 0).show();
                            return;
                        }
                    }
                } else
                {
                    Toast.makeText(context.getApplicationContext(), "\u4E66\u57CE\u6B63\u5728\u521D\u59CB\u5316...", 0).show();
                    startInitEbook(context.getApplicationContext());
                    return;
                }
            } else
            {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.gionee.aora.ebook", "com.gionee.aora.ebook.ebook.GoMarketBookshelfActivity"));
                intent.addFlags(0x10000000);
                intent.putExtra("IS_FROME_MARKET", true);
                context.getApplicationContext().startActivity(intent);
                return;
            }
    }

    public static final String EBOOK_PACKAGENAME = "com.gionee.aora.ebook";
    private static DownloadManager downloadManager;
    private static InitEbookManager initEbookManager;
    private static long lastClickTime;
}
