// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.necessary;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.gui.view.MarketExpandListView;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.NecessaryNet;
import java.util.*;

// Referenced classes of package com.gionee.aora.market.gui.necessary:
//            NecessaryNewAdapter

public class NecessaryNewActivity extends MarketBaseActivity
{

    public NecessaryNewActivity()
    {
        listView = null;
        adapter = null;
        necessaryData = null;
        groupList = null;
        childrenList = null;
        datainfo = null;
    }

    private void fillData()
    {
        groupList.addAll((List)necessaryData.get("group_name"));
        childrenList.addAll((List)necessaryData.get("items"));
    }

    public static void startNecessaryActivity(Context context, DataCollectInfo datacollectinfo)
    {
        Intent intent = new Intent(context, NecessaryNewActivity.class);
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        context.startActivity(intent);
    }

    protected void initCenterView()
    {
        setCenterView(biz.AR.layout.necessary_layout);
        datainfo = DataCollectManager.getCollectInfo(this);
        listView = (MarketExpandListView)findViewById(biz.AR.id.necessaryExpandListView);
        listView.setHeard(this, true);
        groupList = new ArrayList();
        childrenList = new ArrayList();
        adapter = new NecessaryNewAdapter(this, groupList, childrenList, datainfo.clone());
        titleBarView.setTitle(getResources().getString(biz.AR.string.necessary));
        listView.setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener() {

            public boolean onGroupClick(ExpandableListView expandablelistview, View view, int i, long l)
            {
                return true;
            }

            
        }
);
        listView.setOnChildClickListener(new android.widget.ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView expandablelistview, View view, int i, int j, long l)
            {
                DataCollectInfo datacollectinfo = datainfo.clone();
                datacollectinfo.setType("1");
                IntroductionDetailActivity.startIntroductionActivity(NecessaryNewActivity.this, (AppInfo)((List)childrenList.get(i)).get(j), datacollectinfo);
                return false;
            }

            
        }
);
    }

    protected   boolean initData(Integer ainteger[])
    {
        necessaryData = NecessaryNet.getNecessaryList();
        if(necessaryData == null)
            return false;
        return ((List)necessaryData.get("group_name")).size() != 0;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        DataCollectManager.addRecord(datainfo, new String[0]);
        doLoadData(new Integer[0]);
    }

    protected void onDestroy()
    {
        if(adapter != null)
            adapter.unregisterListener();
        super.onDestroy();
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

    private NecessaryNewAdapter adapter;
    private List childrenList;
    private DataCollectInfo datainfo;
    private List groupList;
    private MarketExpandListView listView;
    private Map necessaryData;


}
