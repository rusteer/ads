// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.necessary;

import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.*;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.*;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.NecessaryNet;
import java.util.*;

// Referenced classes of package com.gionee.aora.market.gui.necessary:
//            FirstInNecessaryCheckedAppManager, FirstInNecessaryAdapter

public class FirstInNecessaryActivity extends MarketBaseActivity
{

    public FirstInNecessaryActivity()
    {
        listView = null;
        adapter = null;
        necessaryData = null;
        groupList = null;
        childrenList = null;
        dataCount = 0;
        datainfo = null;
        handler = new Handler() {

            public void handleMessage(Message message)
            {
                List list;
                switch(message.what)
                {
                default:
                    return;

                case 1: // '\001'
                    list = checkedAppManager.getCheckedAppInfoList();
                    break;
                }
                Iterator iterator = list.iterator();
                long l;
                for(l = 0L; iterator.hasNext(); l += ((AppInfo)iterator.next()).getLongSize());
                if(list.isEmpty())
                {
                    checkedInfoTextView.setText("\u672A\u9009\u62E9\u5E94\u7528");
                } else
                {
                    String s = StringUtil.getFormatSize(l);
                    TextView textview = checkedInfoTextView;
                    Object aobj[] = new Object[2];
                    aobj[0] = (new StringBuilder()).append(list.size()).append("").toString();
                    aobj[1] = s;
                    textview.setText(String.format("\u5DF2\u9009%s\u6B3E,\u5171\u9700%s\u7A7A\u95F4", aobj));
                }
                if(list.size() != dataCount)
                {
                    allCheckBox.setChecked(false);
                    return;
                } else
                {
                    allCheckBox.setChecked(true);
                    return;
                }
            }

             
        }
;
    }

    private void fillData()
    {
        groupList.addAll((List)necessaryData.get("group_name"));
        childrenList.addAll((List)necessaryData.get("items"));
label0:
        for(int i = 0; i < groupList.size(); i++)
        {
            Iterator iterator = ((List)childrenList.get(i)).iterator();
            do
            {
                if(!iterator.hasNext())
                    continue label0;
                AppInfo appinfo = (AppInfo)iterator.next();
                if(!softwareManager.getSoftwaresMap().containsKey(appinfo.getPackageName()))
                {
                    dataCount = 1 + dataCount;
                    checkedAppManager.addAppInfo(appinfo);
                }
            } while(true);
        }

        handler.sendEmptyMessage(1);
    }

    private void showNetErrorDialog()
    {
        Intent intent = new Intent();
        intent.setClass(getBaseContext(),  NetErrorDialog.class);
        intent.setFlags(0x10000000);
        getBaseContext().startActivity(intent);
    }

    protected void initCenterView()
    {
        datainfo = new DataCollectInfo();
        datainfo.setPage("6");
        setTitleBarViewVisibility(false);
        setCenterView(biz.AR.layout.first_in_necessary_layout);
        listView = (MarketExpandListView)findViewById(biz.AR.id.necessaryExpandListView);
        installAllBtn = (Button)findViewById(biz.AR.id.necessary_install_all_btn);
        installAllBtn.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(!NetUtil.isNetworkAvailable(getBaseContext()))
                {
                    showNetErrorDialog();
                    return;
                }
                if(!checkedAppManager.getCheckedAppInfoList().isEmpty())
                {
                    view.setClickable(false);
                    com.gionee.aora.download.DownloadInfo downloadinfo;
                    for(Iterator iterator = checkedAppManager.getCheckedAppInfoList().iterator(); iterator.hasNext(); downloadManager.addDownload(downloadinfo))
                    {
                        AppInfo appinfo = (AppInfo)iterator.next();
                        if(downloadManager.queryDownload(appinfo.getPackageName()) == null)
                        {
                            datainfo.setAction("13");
                            DataCollectInfo datacollectinfo = datainfo;
                            String as[] = new String[6];
                            as[0] = "app_id";
                            as[1] = appinfo.getSoftId();
                            as[2] = "cpversion";
                            as[3] = appinfo.getUpdateVersionName();
                            as[4] = "setup_flag";
                            as[5] = "0";
                            DataCollectManager.addRecord(datacollectinfo, as);
                        }
                        downloadinfo = appinfo.toDownloadInfo();
                    }

                    finish();
                    return;
                } else
                {
                    Toast.makeText(FirstInNecessaryActivity.this, "\u60A8\u6CA1\u6709\u9009\u62E9\u5B89\u88C5\u5E94\u7528\u5594~~", 0).show();
                    return;
                }
            }

            
        }
);
        groupList = new ArrayList();
        childrenList = new ArrayList();
        adapter = new FirstInNecessaryAdapter(this, groupList, childrenList, handler);
        titleBarView.setRightViewVisibility();
        allCheckBox = (CheckBox)findViewById(biz.AR.id.necessary_new_check_all_checkbox);
        checkAllLay = (LinearLayout)findViewById(biz.AR.id.necessary_new_check_all_lay);
        checkAllLay.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(allCheckBox.isChecked())
                {
                    checkedAppManager.removeAllAppInfos();
                    allCheckBox.setChecked(false);
                } else
                {
label0:
                    for(int i = 0; i < groupList.size(); i++)
                    {
                        Iterator iterator = ((List)childrenList.get(i)).iterator();
                        do
                        {
                            if(!iterator.hasNext())
                                continue label0;
                            AppInfo appinfo = (AppInfo)iterator.next();
                            if(!softwareManager.getSoftwaresMap().containsKey(appinfo.getPackageName()))
                                checkedAppManager.addAppInfo(appinfo);
                        } while(true);
                    }

                    allCheckBox.setChecked(true);
                }
                DLog.i("lilijun", "\u70B9\u51FB\u4E86\u5168\u9009");
                adapter.notifyDataSetChanged();
                handler.sendEmptyMessage(1);
            }

             
        }
);
        checkedInfoTextView = (TextView)findViewById(biz.AR.id.necessary_checked_info_textview);
        skipBtn = (Button)findViewById(biz.AR.id.necessary_skip_btn);
        skipBtn.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                finish();
            }

             
        }
);
        listView.setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView expandablelistview, View view, int i, long l)
            {
                return true;
            }

            
        }
);
    }

    protected   boolean initData(Integer ainteger[])
    {
        necessaryData = NecessaryNet.getFirstNecessaryList();
        if(necessaryData == null)
            return false;
        return ((List)necessaryData.get("group_name")).size() != 0;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        softwareManager = SoftwareManager.getInstace();
        downloadManager = DownloadManager.shareInstance();
        checkedAppManager = FirstInNecessaryCheckedAppManager.getInstance();
        checkedAppManager.removeAllAppInfos();
        doLoadData(new Integer[0]);
    }

    protected   void refreshView(boolean flag, Integer ainteger[])
    {
        int i = 0;
        if(flag)
        {
            fillData();
            if(necessaryData.isEmpty())
            {
                showErrorView(biz.AR.drawable.net_error, "\u65E0\u6570\u636E\u8FD4\u56DE", false);
            } else
            {
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                int j = adapter.getGroupCount();
                while(i < j) 
                {
                    listView.expandGroup(i);
                    i++;
                }
            }
            return;
        } else
        {
            showErrorView();
            return;
        }
    }

    protected void tryAgain()
    {
        doLoadData(new Integer[0]);
    }

    public static final int CHECKED_APPINFO_COUNT_CHANGE = 1;
    private FirstInNecessaryAdapter adapter;
    private CheckBox allCheckBox;
    private LinearLayout checkAllLay;
    private FirstInNecessaryCheckedAppManager checkedAppManager;
    private TextView checkedInfoTextView;
    private List childrenList;
    private int dataCount;
    private DataCollectInfo datainfo;
    private DownloadManager downloadManager;
    private List groupList;
    private Handler handler;
    private Button installAllBtn;
    private MarketExpandListView listView;
    private Map necessaryData;
    private Button skipBtn;
    private SoftwareManager softwareManager;












}
