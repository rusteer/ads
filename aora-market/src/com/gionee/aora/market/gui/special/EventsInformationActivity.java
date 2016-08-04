// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.exercise.ExerciseDetailsActivity;
import com.gionee.aora.market.gui.exercise.ExerciseListAdapter;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.*;
import com.gionee.aora.market.module.EvaluatInfo;
import com.gionee.aora.market.net.ExerciseNet;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

public class EventsInformationActivity extends MarketBaseActivity
{

    public EventsInformationActivity()
    {
        loadMoreView = null;
        datainfo = null;
    }

    private void loadMoreData()
    {
        if(infos.isEmpty() || loadingData || loadingDataEnd || loadMoreView.isShowTryAgain())
        {
            return;
        } else
        {
            loadingData = true;
            Integer ainteger[] = new Integer[2];
            ainteger[0] = Integer.valueOf(1);
            ainteger[1] = Integer.valueOf(infos.size());
            doLoadData(ainteger);
            return;
        }
    }

    protected void initCenterView()
    {
        datainfo = DataCollectManager.getCollectInfo(this);
        datainfo.setModel("10");
        DataCollectManager.addRecord(datainfo, new String[0]);
        titleBarView.setTitle(getString(biz.AR.string.events_information));
        setCenterView(biz.AR.layout.evaluation_horn_list_activity);
        listView = (MarketListView)findViewById(biz.AR.id.evaluation_listview);
        infos = new ArrayList();
        adapter = new ExerciseListAdapter(this, infos, datainfo);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                DataCollectInfo datacollectinfo = datainfo.clone();
                datacollectinfo.setPosition((new StringBuilder()).append(i).append("").toString());
                ExerciseDetailsActivity.startExerciseDetailsActivity(EventsInformationActivity.this, (EvaluatInfo)infos.get(i), datacollectinfo);
            }

            
        }
);
        listView.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true, new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {

            public void loadMoreWhenScrollToEnd()
            {
                loadMoreData();
            }

            
        }
));
        loadMoreView = new LoadMoreView(this) {

            public void tryAgain()
            {
                loadMoreData();
            }

            
        }
;
    }

    protected   boolean initData(Integer ainteger[])
    {boolean flag = true;
        switch(ainteger[0].intValue()){
            case 0://L2_L2:
                infos = ExerciseNet.getExerciseInfos(Build.MODEL, 0, 10, 3);
                if(infos == null)
                    return false;
                break;
            case 1://L3_L3:
                List list;
                if(loadMoreInfos != null)
                    loadMoreInfos.clear();
                loadMoreInfos = ExerciseNet.getExerciseInfos(Build.MODEL, ainteger[1].intValue(), 10, 3);
                list = loadMoreInfos;
                flag = false;
                if(list == null)  return flag;
                if(loadMoreInfos.size() < 10)
                    loadingDataEnd = true;
                break;
        }
        
        return flag;

       
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(0);
        doLoadData(ainteger);
    }

    protected   void refreshView(boolean flag, Integer ainteger[])
    {
        switch(ainteger[0].intValue()){
            case 0://L2_L2:
                if(flag)
                {
                    if(infos.size() != 0)
                    {
                        loadingView.setVisibility(8);
                        adapter.setEvaluatInfos(infos);
                        if(infos.size() < 10)
                        {
                            listView.addLoadEndView(this);
                            loadingDataEnd = true;
                        } else
                        {
                            listView.addFooterView(loadMoreView, null, false);
                        }
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        
                    } else
                    {
                        showErrorView(biz.AR.drawable.no_update_apps, "\u6682\u65E0\u6D3B\u52A8\u516C\u544A\u54E6", false);
                    }
                   
                } else
                {
                    showErrorView();
                }
                return;
            case 1://L3_L3:
                if(flag)
                {
                    if(!loadMoreInfos.isEmpty())
                    {
                        infos.addAll(loadMoreInfos);
                        adapter.setEvaluatInfos(infos);
                        adapter.notifyDataSetChanged();
                    }
                    if(loadingDataEnd)
                    {
                        listView.removeFooterView(loadMoreView);
                        listView.addLoadEndView(this);
                        return;
                    }
                } else
                {
                    loadMoreView.showTryAgainButton(true);
                    return;
                }
                break;
        }
         
    }

    protected void tryAgain()
    {
        DLog.v("EventsInformationActivity", "tryAgain");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }

    private static final String TAG = "EventsInformationActivity";
    private final int LOAD_DATA = 0;
    private final int LOAD_DATA_COUNT = 10;
    private final int LOAD_MORE_DATA = 1;
    private ExerciseListAdapter adapter;
    private DataCollectInfo datainfo;
    private List infos;
    private MarketListView listView;
    private List loadMoreInfos;
    private LoadMoreView loadMoreView;



}
