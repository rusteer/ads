// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.wifi;

import android.app.Dialog;
import android.content.*;
import android.view.View;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.module.UpdateInfo;
import com.gionee.aora.market.net.MarketUpdateNet;
import com.gionee.aora.market.util.MarketAsyncTask;

public class InitWifiManager
{

    public InitWifiManager()
    {
        downloadManager = DownloadManager.shareInstance();
    }

    public static InitWifiManager getInstance()
    {
        if(initWifiManager == null)
            initWifiManager = new InitWifiManager();
        return initWifiManager;
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

    private void showDownload(final Context context, final DataCollectInfo datainfo)
    {
        MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(context);
        marketfloatedialogbuilder.setTitle("\u4E0B\u8F7D\u63D0\u793A");
        marketfloatedialogbuilder.setMessage("WiFi\u7545\u6E38\u662F\u5C45\u5BB6\u65C5\u6E38\u5FC5\u5907\u514D\u8D39\u4E0A\u7F51\u795E\u5668\uFF1A\n1\u3001\u56FD\u5185\u652F\u6301CMCC(CMCC-WEB)\u3001ChinaNet\u8FD0\u8425\u5546\uFF0C\u540C\u65F6\u5F00\u901A\u6E2F\u6FB3\u53F0\u3001\u6CF0\u56FD\u7B49\u5730\u70ED\u70B9\u8FDE\u63A5\n2\u3001\u8D85\u767E\u4E07\u7F51\u53CB\u5206\u4EABWiFi\u70ED\u70B9\n3\u3001\u65E0\u9700\u79EF\u5206\u3001\u771F\u6B63\u514D\u8D39\u4E0D\u9650\u65F6\u957F\n4\u3001\u5206\u4EABWiFi\u8FD8\u80FD\u8D5A\u73B0\u91D1");
        marketfloatedialogbuilder.setCancelable(true);
        marketfloatedialogbuilder.setLeftButton("\u53D6\u6D88", new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(dialog != null)
                    dialog.cancel();
            }

           
        }
);
        marketfloatedialogbuilder.setRightButton("\u4E0B\u8F7D", new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                InitWifiManager.startInitWifi(context.getApplicationContext(), datainfo);
            }

             
        }
);
        dialog = marketfloatedialogbuilder.crteate();
        marketfloatedialogbuilder.show();
    }

    private static void startInitWifi(final Context context, final DataCollectInfo datacollectinfo)
    {
        (new MarketAsyncTask<Void,UpdateInfo,UpdateInfo>( ) {

            protected   UpdateInfo doInBackground(Void avoid[])
            {
                UpdateInfo updateinfo = new UpdateInfo();
                updateinfo.packageName = "com.aoratec.wifimanager";
                updateinfo.versionCode = 1000;
                updateinfo.versionName = "1.0.0.0";
                return MarketUpdateNet.checkUpdateWifi(context, updateinfo, true);
            }

            

            protected void onPostExecute(UpdateInfo updateinfo)
            {
                super.onPostExecute(updateinfo);
                if(updateinfo == null)
                {
                    Toast.makeText(context.getApplicationContext(), "wifi\u7545\u6E38\u521D\u59CB\u5316\u5931\u8D25.", 0).show();
                    return;
                }
                DownloadInfo downloadinfo = new DownloadInfo("wifi\u7545\u6E38", "com.aoratec.wifimanager", updateinfo.url, "", updateinfo.size, "", 0);
                if(!DownloadManager.shareInstance().addDownload(downloadinfo))
                    Toast.makeText(context.getApplicationContext(), "wifi\u7545\u6E38\u521D\u59CB\u5316\u5931\u8D25.", 0).show();
                DataCollectInfo datacollectinfo1 = datacollectinfo.clone();
                datacollectinfo1.setAction("13");
                DataCollectManager.addRecord(datacollectinfo1, new String[] {
                    "setup_flag", "0", "app_id", "", "cpversion", ""
                });
            }

          

            
        }
).doExecutor(new Void[0]);
    }

    public void initWifi(Context context, DataCollectInfo datacollectinfo)
    {
        if(!isFastDoubleClick())
            if(!SoftwareManager.getInstace().checkSoftwareInstalled("com.aoratec.wifimanager", -1))
            {
                DownloadInfo downloadinfo = downloadManager.queryDownload("com.aoratec.wifimanager");
                if(downloadinfo != null)
                {
                    if(downloadinfo.getState() == 3 && !Constants.canAutoInstall)
                    {
                        SoftwareManager.getInstace().installApp(downloadManager, downloadManager.queryDownload("com.aoratec.wifimanager"));
                        return;
                    }
                    if(downloadinfo.getState() == 1)
                    {
                        Toast.makeText(context.getApplicationContext(), "wifi\u7545\u6E38\u5DF2\u7ECF\u5728\u4E0B\u8F7D\u4EFB\u52A1\u4E2D\uFF0C\u8BF7\u7A0D\u7B49...", 0).show();
                        return;
                    }
                    if(!downloadManager.addDownload(downloadinfo))
                    {
                        Toast.makeText(context.getApplicationContext(), "wifi\u7545\u6E38\u5DF2\u7ECF\u5728\u4E0B\u8F7D\u4EFB\u52A1\u4E2D\uFF0C\u8BF7\u7A0D\u7B49...", 0).show();
                        return;
                    }
                } else
                {
                    showDownload(context, datacollectinfo);
                    return;
                }
            } else
            {
                try
                {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.aoratec.wifimanager", "com.aoratec.wifimanager.gui.splash.ActivitySplashScreen_"));
                    intent.addFlags(0x10000000);
                    intent.putExtra("IS_FROME_MARKET", true);
                    context.getApplicationContext().startActivity(intent);
                    DataCollectManager.addRecord(datacollectinfo, new String[0]);
                    return;
                }
                catch(Exception exception)
                {
                    showDownload(context, datacollectinfo);
                }
                return;
            }
    }

    public static final String WIFI_PACKAGENAME = "com.aoratec.wifimanager";
    private static DownloadManager downloadManager;
    private static InitWifiManager initWifiManager;
    private static long lastClickTime;
    private Dialog dialog;


}
