// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.necessary;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.main.MarketBaseFragment;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.gui.view.MarketExpandListView;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.NecessaryNet;
import java.util.*;

// Referenced classes of package com.gionee.aora.market.gui.necessary:
//            NecessaryNewAdapter

public class NecessaryFragment extends MarketBaseFragment
{

    public NecessaryFragment()
    {
        listView = null;
        adapter = null;
        necessaryData = null;
        groupList = null;
        childrenList = null;
        action = "27";
    }

    private void fillData()
    {
        groupList.addAll((List)necessaryData.get("group_name"));
        childrenList.addAll((List)necessaryData.get("items"));
    }

    protected   boolean initData(Integer ainteger[])
    {
        necessaryData = NecessaryNet.getNecessaryList();
        if(necessaryData == null)
            return false;
        return ((List)necessaryData.get("group_name")).size() != 0;
    }

    protected void initView(RelativeLayout relativelayout)
    {
        setTitleBarViewVisibility(false);
        setCenterView(biz.AR.layout.necessary_layout);
        listView = (MarketExpandListView)relativelayout.findViewById(biz.AR.id.necessaryExpandListView);
        listView.setHeard(getActivity(), true);
        groupList = new ArrayList();
        childrenList = new ArrayList();
        adapter = new NecessaryNewAdapter(getActivity(), groupList, childrenList, null);
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
                IntroductionDetailActivity.startIntroductionActivity(getActivity(), (AppInfo)((List)childrenList.get(i)).get(j), null);
                return false;
            }

             
        }
);
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    public void onDestroy()
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

    protected void setDataAgain()
    {
        boolean flag;
        Integer ainteger[];
        if(necessaryData == null)
            flag = false;
        else
        if(((List)necessaryData.get("group_name")).size() == 0)
            flag = false;
        else
            flag = true;
        ainteger = new Integer[1];
        ainteger[0] = Integer.valueOf(-1);
        refreshView(flag, ainteger);
    }

    public void setUserVisibleHint(boolean flag)
    {
        super.setUserVisibleHint(flag);
        if(flag && !hadLoadData)
            doLoadData(new Integer[0]);
    }

    protected void tryAgain()
    {
        doLoadData(new Integer[0]);
    }

    String action;
    private NecessaryNewAdapter adapter;
    private List childrenList;
    private List groupList;
    private MarketExpandListView listView;
    private Map necessaryData;

}
