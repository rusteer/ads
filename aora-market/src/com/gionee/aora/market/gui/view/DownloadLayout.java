// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;
import com.aora.base.util.DLog;
import com.gionee.aora.download.*;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.*;
import com.gionee.aora.market.gui.download.AppStateConstants;
import com.gionee.aora.market.gui.download.DownloadViewHolder;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            MyViewPager

public abstract class DownloadLayout extends HorizontalScrollView
{
    public class AppHolder extends DownloadViewHolder
    {

        private void init()
        {
            icon = (ImageView)baseView.findViewById(biz.AR.id.app_view_icon);
            appName = (TextView)baseView.findViewById(biz.AR.id.app_view_name);
        }

        public TextView appName;
        public ImageView icon;

        public AppHolder(View view)
        {
            super(view);
            init();
        }
    }

    class AutoInstallBroadCastReceive extends BroadcastReceiver
    {

        public void onReceive(Context context1, Intent intent)
        {
            if(intent.getAction().equals("com.gionee.aora.market.AutoInstallReceiver"))
            {
                DownloadInfo downloadinfo = (DownloadInfo)intent.getSerializableExtra("DOWNLOADINFO");
                int i = intent.getIntExtra("STATE", 3);
                if(i == 4)
                    sentMessage(downloadinfo, 1);
                else
                if(i != 2)
                {
                    DLog.d("DownloadLayout", (new StringBuilder()).append("---AutoInstallBroadCastReceive onReceive---info packageName =").append(downloadinfo.getPackageName()).append("state = ").append(i).toString());
                    refreshData(downloadinfo);
                    return;
                }
            }
        }

         
    }

    class SoftManagerInitFinishBroadCastReceive extends BroadcastReceiver
    {

        public void onReceive(Context context1, Intent intent)
        {
            if(intent.getAction().equals("com.gionee.aora.market.action.ACTION_SOFTWARE_UPDATE"))
                DLog.d("DownloadLayout", "---SoftManagerInitFinishBroadCastReceive onReceive---\u521D\u59CB\u5316\u5B8C\u6210");
        }

        
    }


    public DownloadLayout(Context context1)
    {
        super(context1);
        holdersList = null;
        listener = null;
        manager = null;
        handler = null;
        HANDLER_REFRESH_VIEW = 0;
        HANDLER_REFRESH_VIEW_DELETE = 1;
        receiver = null;
        softManagerInitFinishBroadCastReceiver = null;
        softwareManager = null;
        context = context1;
        init(context1);
    }

    public DownloadLayout(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        holdersList = null;
        listener = null;
        manager = null;
        handler = null;
        HANDLER_REFRESH_VIEW = 0;
        HANDLER_REFRESH_VIEW_DELETE = 1;
        receiver = null;
        softManagerInitFinishBroadCastReceiver = null;
        softwareManager = null;
        context = context1;
        init(context1);
    }

    public DownloadLayout(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        holdersList = null;
        listener = null;
        manager = null;
        handler = null;
        HANDLER_REFRESH_VIEW = 0;
        HANDLER_REFRESH_VIEW_DELETE = 1;
        receiver = null;
        softManagerInitFinishBroadCastReceiver = null;
        softwareManager = null;
        context = context1;
        init(context1);
    }

    private void init(Context context1)
    {
        setHorizontalScrollBarEnabled(false);
        setBackgroundResource(biz.AR.color.white);
        imageLoader = ImageLoaderManager.getInstance();
        isInEditMode();
    }

    private void initHandler()
    {
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                super.handleMessage(message);
                DownloadInfo downloadinfo = (DownloadInfo)message.obj;
                if(message.what == 0)
                    refreshData(downloadinfo);
                else
                if(message.what == 1)
                {
                    holdersList.clear();
                    return;
                }
            }

            
        }
;
    }

    private void initListener()
    {
        listener = new DownloadListener() {

            public void onProgress(int i, DownloadInfo downloadinfo)
            {
                DLog.v("DownloadLayout", (new StringBuilder()).append("----onProgress---").append(downloadinfo.getPackageName()).append("\u4E0B\u8F7D\u8FDB\u5EA6\u4E3A  ").append(downloadinfo.getPercent()).append("  now percent =").append(i).toString());
            }

            public void onStateChange(int i, DownloadInfo downloadinfo)
            {
                DLog.v("DownloadLayout", (new StringBuilder()).append("----onStateChange---").append(downloadinfo.getPackageName()).append("\u4E0B\u8F7D\u72B6\u6001\u4E3A  ").append(downloadinfo.getState()).append("   now state =").append(i).toString());
                if(i != 3)
                    sentMessage(downloadinfo, 0);
                else
                if(!Constants.canAutoInstall)
                {
                    sentMessage(downloadinfo, 0);
                    return;
                }
            }

            public void onTaskCountChanged(int i, DownloadInfo downloadinfo)
            {
                sentMessage(downloadinfo, 1);
            }

            
        }
;
    }

    private boolean isContainHolder(AppHolder appholder)
    {
label0:
        {
            if(holdersList.size() == 0)
                break label0;
            Iterator iterator = holdersList.iterator();
            do
                if(!iterator.hasNext())
                    break label0;
            while(!((AppHolder)iterator.next()).getKey().equals(appholder.getKey()));
            return true;
        }
        return false;
    }

    private void refreshData(DownloadInfo downloadinfo)
    {
        AppHolder appholder = matchingTheRightHolder(downloadinfo);
        if(appholder != null)
            setDownloadData(appholder, downloadinfo);
    }

    private void registerAutoStallBroadCast(Context context1)
    {
        receiver = new AutoInstallBroadCastReceive();
        IntentFilter intentfilter = new IntentFilter("com.gionee.aora.market.AutoInstallReceiver");
        context1.registerReceiver(receiver, intentfilter);
    }

    private void registerSoftManagerFinisgBroadCast(Context context1)
    {
        softManagerInitFinishBroadCastReceiver = new SoftManagerInitFinishBroadCastReceive();
        IntentFilter intentfilter = new IntentFilter("com.gionee.aora.market.action.ACTION_SOFTWARE_UPDATE");
        context1.registerReceiver(softManagerInitFinishBroadCastReceiver, intentfilter);
    }

    private void sentMessage(DownloadInfo downloadinfo, int i)
    {
        Message message = new Message();
        message.what = i;
        message.obj = downloadinfo;
        handler.sendMessage(message);
    }

    private void unRegisterAutoStallBroadCast()
    {
        if(receiver != null)
            context.unregisterReceiver(receiver);
    }

    private void unRegisterSoftManagerFinishBroadCast()
    {
        if(softManagerInitFinishBroadCastReceiver != null)
            context.unregisterReceiver(softManagerInitFinishBroadCastReceiver);
    }

    protected void addToViewHolder(String s, AppHolder appholder)
    {
        appholder.setKey(s);
        if(!isContainHolder(appholder))
            holdersList.add(appholder);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        if(viewGroups != null)
        {
            for(int i = 0; i < viewGroups.length; i++)
            {
                viewGroups[i].requestDisallowInterceptTouchEvent(true);
                if(viewGroups[i] instanceof MyViewPager)
                    ((MyViewPager)viewGroups[i]).setDispatch(true);
            }

        }
        return super.dispatchTouchEvent(motionevent);
    }

    protected int getAppState(String s)
    {
        DownloadInfo downloadinfo = manager.queryDownload(s);
        DownloadInfo downloadinfo1 = SoftWareUpdateManager.getInstance().checkHadDownloadAPkFile(s);
        int i;
        if(downloadinfo != null)
        {
            if(downloadinfo.getState() == 3 && softwareManager.isInstalling(s))
                i = AppStateConstants.getAppState(1002, 0);
            else
                i = AppStateConstants.getAppState(1000, downloadinfo.getState());
        } else
        if(downloadinfo1 != null)
            i = AppStateConstants.getAppState(1000, downloadinfo1.getState());
        else
            i = AppStateConstants.getAppState(1001, softwareManager.getSoftwareCurStateByPackageName(s));
        DLog.v("DownloadLayout", (new StringBuilder()).append("---getAppState ---appState =  ").append(i).toString());
        return i;
    }

    protected abstract List getHoldersList();

    protected AppHolder matchingTheRightHolder(DownloadInfo downloadinfo)
    {
        if(downloadinfo == null)
            return null;
        if(holdersList == null)
        {
            DLog.d("DownloadLayout", "---matchingTheRightHolder ---holdersList = null");
            return null;
        }
        if(holdersList.size() == 0)
        {
            DLog.d("DownloadLayout", "---matchingTheRightHolder ---holdersList.size() == 0");
            return null;
        }
        for(Iterator iterator = holdersList.iterator(); iterator.hasNext();)
        {
            AppHolder appholder = (AppHolder)iterator.next();
            if(appholder.getKey().equals(downloadinfo.getPackageName()))
                return appholder;
        }

        return null;
    }

    public void onDestroy()
    {
        unregisterListener();
        unRegisterAutoStallBroadCast();
        unRegisterSoftManagerFinishBroadCast();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        if(viewGroups != null)
        {
            for(int i = 0; i < viewGroups.length; i++)
                viewGroups[i].requestDisallowInterceptTouchEvent(true);

        }
        return super.onInterceptTouchEvent(motionevent);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(viewGroups != null)
        {
            for(int i = 0; i < viewGroups.length; i++)
                viewGroups[i].requestDisallowInterceptTouchEvent(true);

        }
        return super.onTouchEvent(motionevent);
    }

    public void registerListener()
    {
        if(listener != null && manager != null)
        {
            manager.registerDownloadListener(listener);
            registerAutoStallBroadCast(context);
            registerSoftManagerFinisgBroadCast(context);
        }
    }

    public void removeHolder(String s)
    {
        int i = 0;
        do
        {
label0:
            {
                if(i < holdersList.size())
                {
                    if(!((AppHolder)holdersList.get(i)).getKey().equals(s))
                        break label0;
                    holdersList.remove(i);
                }
                return;
            }
            i++;
        } while(true);
    }

    protected void setDownloadData(AppHolder appholder, DownloadInfo downloadinfo)
    {
    }

    public void unregisterListener()
    {
        if(listener == null || manager == null)
            return;
        manager.unregisterDownloadListener(listener);
        unRegisterAutoStallBroadCast();
        unRegisterSoftManagerFinishBroadCast();
        
    }

    private static final String TAG = "DownloadLayout";
    private final int HANDLER_REFRESH_VIEW;
    private final int HANDLER_REFRESH_VIEW_DELETE;
    public Context context;
    protected Handler handler;
    protected List holdersList;
    public ImageLoaderManager imageLoader;
    protected DownloadListener listener;
    protected DownloadManager manager;
    protected AutoInstallBroadCastReceive receiver;
    protected SoftManagerInitFinishBroadCastReceive softManagerInitFinishBroadCastReceiver;
    protected SoftwareManager softwareManager;
    public ViewGroup viewGroups[];


}
