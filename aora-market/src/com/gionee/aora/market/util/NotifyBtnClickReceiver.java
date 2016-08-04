// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.util;

import android.app.NotificationManager;
import android.content.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.aora.base.util.NetUtil;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.market.control.*;
import com.gionee.aora.market.gui.download.DownloadManagerActivity;
import com.gionee.aora.market.gui.view.NetErrorDialog;
import com.gionee.aora.market.gui.view.SignDifferentDialog;
import com.gionee.aora.market.module.AppInfo;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class NotifyBtnClickReceiver extends BroadcastReceiver
{

    public NotifyBtnClickReceiver()
    {
    }

    public static void collapseStatusBar(Context context)
    {
        try {
            Method method1;
            Object obj = context.getSystemService("statusbar");
            if(android.os.Build.VERSION.SDK_INT > 16)
            {
                method1 = obj.getClass().getMethod("collapsePanels", new Class[0]);
            }else{
                method1 = obj.getClass().getMethod("collapse", new Class[0]);
            }
            method1.invoke(obj, new Object[0]);
        } catch ( Exception e) {
            e.printStackTrace();
        }
        
       
    }

    private void doAllUpdate(Context context)
    {
        for(Iterator iterator = softwareManager.getUpdate_softwaresMap().entrySet().iterator(); iterator.hasNext();)
        {
            AppInfo appinfo = (AppInfo)((java.util.Map.Entry)iterator.next()).getValue();
            DownloadInfo downloadinfo = softWareUpdateManager.checkHadDownloadAPkFile(appinfo.getPackageName());
            if(downloadinfo != null)
                softwareManager.installApp(softWareUpdateManager.getDownloadManager(), downloadinfo);
            else
            if(!appinfo.isSameSign())
            {
                showSignDifferntDialog(context, appinfo);
            } else
            {
                if(downloadManager.queryDownload(appinfo.getPackageName()) == null)
                {
                    DataCollectInfo datacollectinfo = new DataCollectInfo("58", "9", "", "", "11");
                    String as[] = new String[6];
                    as[0] = "app_id";
                    as[1] = appinfo.getSoftId();
                    as[2] = "cpversion";
                    as[3] = appinfo.getUpdateVersionName();
                    as[4] = "setup_flag";
                    as[5] = "0 ";
                    DataCollectManager.addRecord(datacollectinfo, as);
                }
                DownloadInfo downloadinfo1 = appinfo.toDownloadInfo();
                downloadManager.addDownload(downloadinfo1);
            }
        }

    }

    private void showNetErrorDialog(Context context)
    {
        Intent intent = new Intent();
        intent.setClass(context,  NetErrorDialog.class);
        intent.setFlags(0x10000000);
        context.startActivity(intent);
    }

    private void showSignDifferntDialog(Context context, AppInfo appinfo)
    {
        Intent intent = new Intent();
        intent.setClass(context,  SignDifferentDialog.class);
        intent.setFlags(0x10000000);
        intent.putExtra("APPINFO", appinfo);
        context.startActivity(intent);
    }

    public void onReceive(Context context, Intent intent)
    {
        collapseStatusBar(context);
        softwareManager = SoftwareManager.getInstace();
        softWareUpdateManager = SoftWareUpdateManager.getInstance();
        if( intent.getAction().equals("com.gionee.aora.market.NOTIFY_UPDATE_ALLBTN_CLICK_ACTION")) {
            //L1_L1:
            DLog.i("lilijun", "\u901A\u77E5\u680F\u4E00\u952E\u66F4\u65B0\u6309\u94AE\u88AB\u70B9\u51FB\u4E86");
            downloadManager = DownloadManager.shareInstance();
            if(!NetUtil.isNetworkAvailable(context)) {
                //L3_L3:
                showNetErrorDialog(context);
            }else{
                //L4_L4:
                doAllUpdate(context);
                ((NotificationManager)context.getSystemService("notification")).cancel(110);
                Intent intent1 = new Intent(context,  DownloadManagerActivity.class);
                intent1.addFlags(0x10000000);
                context.startActivity(intent1);
                return;
            }
        }else{
            //L2_L2:
            if(intent.getAction().equals("com.gionee.aora.market.ACTION_NOTIFY_INSTALL_ALL_BTN_CLICK"))
            {
                DLog.i("lilijun", "\u901A\u77E5\u680F\u4E00\u952E\u5B89\u88C5\u6309\u94AE\u88AB\u70B9\u51FB\u4E86");
                Iterator iterator = softWareUpdateManager.getFinishDownloadInfos(context.getCacheDir().getPath()).iterator();
                while(iterator.hasNext()) 
                {
                    DownloadInfo downloadinfo = (DownloadInfo)iterator.next();
                    softwareManager.installApp(softWareUpdateManager.getDownloadManager(), downloadinfo);
                }
            }
        }


 
    }

    public static final String ACTION_NOTIFY_INSTALL_ALL_BTN_CLICK = "com.gionee.aora.market.ACTION_NOTIFY_INSTALL_ALL_BTN_CLICK";
    public static final String ACTION_NOTIFY_UPDATE_ALL_BTN_CLICK = "com.gionee.aora.market.NOTIFY_UPDATE_ALLBTN_CLICK_ACTION";
    private DownloadManager downloadManager;
    private SoftWareUpdateManager softWareUpdateManager;
    private SoftwareManager softwareManager;
}
