// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.*;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.aora.base.util.DLog;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.control.SoftWareUpdateManager;
import java.io.File;
import java.util.*;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            NetErrorDialogAdapter

public class NetErrorDialog extends MarketFloatAcitivityBase
{

    public NetErrorDialog()
    {
        softWareUpdateManager = null;
        downloadManager = null;
        finishedDownloadInfos = null;
        cachePath = "";
        receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent)
            {
                DLog.i("lilijun", "NetErrorDialog,\u63A5\u6536\u5230\u8F6F\u4EF6\u5B89\u88C5\u6210\u529F\u7684\u5E7F\u64AD\uFF01\u518D\u6B21\u5237\u65B0\u96F6\u6D41\u91CF\u66F4\u65B0Dialog!!");
                if(finishedDownloadInfos.size() == 1)
                {
                    DLog.i("lilijun", "NetErrorDialog,\u5173\u95ED\u4E86\u5F53\u524DDialog!!");
                    finish();
                    return;
                } else
                {
                    DLog.i("lilijun", "NetErrorDialog,\u91CD\u65B0\u52A0\u8F7DDialog!!");
                    loadView();
                    return;
                }
            }

             
        }
;
    }

    private void checkDownloadManagerTask()
    {
        Iterator iterator = downloadManager.getAllTaskInfo().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            DownloadInfo downloadinfo = (DownloadInfo)iterator.next();
            if(downloadinfo.getState() == 3)
                finishedDownloadInfos.add(downloadinfo);
        } while(true);
    }

    private void loadView()
    {
        if(softWareUpdateManager.getAllDownloadTasks().isEmpty())
        {
            messageTv.setText("\u7F51\u7EDC\u8FDE\u63A5\u9519\u8BEF,\u8BF7\u68C0\u67E5\u7F51\u7EDC!");
            return;
        }
        finishedDownloadInfos.clear();
        finishedDownloadInfos = softWareUpdateManager.getFinishDownloadInfos(cachePath);
        checkDownloadManagerTask();
        if(finishedDownloadInfos.isEmpty())
        {
            messageTv.setText("\u7F51\u7EDC\u8FDE\u63A5\u9519\u8BEF,\u8BF7\u68C0\u67E5\u7F51\u7EDC!");
            return;
        } else
        {
            setHeightPercent(0.6F);
            titleTv.setText("\u7F51\u7EDC\u8FDE\u63A5\u9519\u8BEF,\u8BF7\u68C0\u67E5\u7F51\u7EDC!");
            View view = View.inflate(this, biz.AR.layout.net_error_dialog, null);
            setCenterView(view);
            TextView textview = (TextView)view.findViewById(biz.AR.id.net_error_dialog_msg);
            Object aobj[] = new Object[1];
            aobj[0] = Integer.valueOf(finishedDownloadInfos.size());
            String s = String.format("%s\u6B3E\u5E94\u7528\u65E0\u7F51\u7EDC\u4E5F\u80FD\u66F4\u65B0\u5566", aobj);
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append((new StringBuilder()).append("&nbsp;&nbsp;&nbsp;").append(s).append("<br>").toString());
            stringbuilder.append("&nbsp;&nbsp;&nbsp;\u5DF2\u5728WLAN\u5907\u597D\u5B89\u88C5\u5305,\u7ACB\u53730\u6D41\u91CF\u79D2\u66F4\u65B0");
            textview.setText(formatHtmlString(stringbuilder.toString(), "0", "#ff0000"));
            ((ListView)findViewById(biz.AR.id.net_error_listview)).setAdapter(new NetErrorDialogAdapter(getApplicationContext(), finishedDownloadInfos));
            return;
        }
    }

    public Spanned formatHtmlString(String s, String s1, String s2)
    {
        return Html.fromHtml(s.replaceFirst(s1, (new StringBuilder()).append("<font color=\"").append(s2).append("\">").append(s1).append("</font>").toString()));
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
                Intent intent = new Intent("android.settings.SETTINGS");
                startActivity(intent);
            }
 
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u53D6\u6D88", "\u8BBE\u7F6E\u7F51\u7EDC"
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
        softWareUpdateManager = SoftWareUpdateManager.getInstance();
        downloadManager = DownloadManager.shareInstance();
        finishedDownloadInfos = new ArrayList();
        cachePath = getCacheDir().getPath();
        IntentFilter intentfilter = new IntentFilter("com.gionee.aora.market.action.ACTION_SOFTWARE_ADD");
        registerReceiver(receiver, intentfilter);
        loadView();
    }

    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private String cachePath;
    private DownloadManager downloadManager;
    private List finishedDownloadInfos;
    private BroadcastReceiver receiver;
    private SoftWareUpdateManager softWareUpdateManager;


}
