// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.download.ParticularListAdapter;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.share.ShareActivity;
import com.gionee.aora.market.gui.view.*;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.SpecialInfomationNet;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

public class IndividuationActivity extends MarketBaseActivity
{

    public IndividuationActivity()
    {
        loadMoreView = null;
        loadingData = false;
        loadingDataEnd = false;
        specialId = "";
        datainfo = null;
    }

    private void loadMoreData()
    {
        if(infos == null || loadingData || loadingDataEnd || loadMoreView.isShowTryAgain())
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
        setTitleBarViewVisibility(true);
        specialId = getIntent().getStringExtra("specialId");
        name = getIntent().getStringExtra("NAME");
        intro = getIntent().getStringExtra("INTRO");
        datainfo = DataCollectManager.getCollectInfo(this);
        DataCollectInfo datacollectinfo = datainfo;
        String as[] = new String[2];
        as[0] = "vid";
        as[1] = specialId;
        DataCollectManager.addRecord(datacollectinfo, as);
        titleBarView.setTitle(name);
        setCenterView(biz.AR.layout.special_layout);
        ImageView imageview = new ImageView(this);
        imageview.setImageResource(biz.AR.drawable.title_share);
        imageview.setPadding(0, 0, getResources().getDimensionPixelSize(biz.AR.dimen.dip10), 0);
        imageview.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(infos != null && infos.size() != 0)
                {
                    StringBuffer stringbuffer = new StringBuffer();
                    stringbuffer.append(((AppInfo)infos.get(0)).getSpecialTitle());
                    stringbuffer.append(((AppInfo)infos.get(0)).getSpecialSkipUrl());
                    ShareActivity.share(IndividuationActivity.this, stringbuffer.toString(), ((AppInfo)infos.get(0)).getSpecialSkipUrl(), ((AppInfo)infos.get(0)).getSpecialTitle());
                }
            }

             
        }
);
        titleBarView.setRightView(imageview);
        listview = (MarketListView)findViewById(biz.AR.id.special_infomation_marketListView);
        adapter = new ParticularListAdapter(this, listview, infos, datainfo);
        adapter.setvId(Integer.parseInt(specialId));
        loadMoreView = new LoadMoreView(this) {

            public void tryAgain()
            {
                loadMoreData();
            }

             
        }
;
        listview.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true, new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {

            public void loadMoreWhenScrollToEnd()
            {
                loadMoreData();
            }

             
        }
));
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                if(infos != null)
                {
                    ((AppInfo)infos.get(i - listview.getHeaderViewsCount())).setvId(Integer.valueOf(specialId).intValue());
                    datainfo.setPosition((new StringBuilder()).append(i - listview.getHeaderViewsCount()).append("").toString());
                    IntroductionDetailActivity.startIntroductionActivity(IndividuationActivity.this, (AppInfo)infos.get(i - listview.getHeaderViewsCount()), datainfo);
                }
            }

         
        }
);
    }

    protected   boolean initData(Integer ainteger[])
    { boolean flag = true;
        switch(ainteger[0].intValue()){
            case 0://L2_L2:
                ArrayList arraylist;
                infos = SpecialInfomationNet.getSpecialInfomationList(Integer.valueOf(specialId).intValue(), ainteger[1].intValue(), 20);
                arraylist = infos;
                flag = false;
                if(arraylist == null)  return flag;
                if(infos.size() < 20)
                    loadingDataEnd = true;
                break;
            case 1://L3_L3:
                List list;
                if(loadMoreInfos != null)
                {
                    loadMoreInfos.clear();
                    loadMoreInfos = null;
                }
                loadMoreInfos = SpecialInfomationNet.getSpecialInfomationList(Integer.valueOf(specialId).intValue(), ainteger[1].intValue(), 20);
                list = loadMoreInfos;
                flag = false;
                if(list == null)  return flag;
                if(loadMoreInfos.size() == 0)
                    return false;
                break;
        }
        
        return flag;
    }

    public void onBackPressed()
    {
        finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        infos = new ArrayList();
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }

    protected void onDestroy()
    {
        if(adapter != null)
            adapter.onDestory();
        super.onDestroy();
    }

    protected   void refreshView(boolean flag, Integer ainteger[])
    {
        switch(ainteger[0].intValue())
        {
        default:
            return;

        case 0: // '\0'
            if(flag)
            {
                if(infos.size() != 0)
                {
                    adapter.setAppInfos(infos);
                    if(!loadingDataEnd)
                        listview.addFooterView(loadMoreView, null, false);
                    if(infos.size() < 20)
                    {
                        loadingDataEnd = true;
                        listview.removeFooterView(loadMoreView);
                        listview.addLoadEndView(this);
                    }
                    TextView textview = (TextView)View.inflate(this, biz.AR.layout.intro_header, null);
                    textview.setText(intro);
                    listview.addHeaderView(textview, null, false);
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return;
                } else
                {
                    showErrorView(biz.AR.drawable.net_error, "\u65E0\u6570\u636E\u8FD4\u56DE", false);
                    return;
                }
            } else
            {
                showErrorView();
                return;
            }

        case 1: // '\001'
            break;
        }
        if(flag)
        {
            if(loadMoreInfos.size() != 0)
            {
                infos.addAll(loadMoreInfos);
                adapter.notifyDataSetChanged();
                DLog.d("IndividuationActivity", (new StringBuilder()).append("LoadMoreAsyncTask loadingData end").append(loadMoreInfos.size()).toString());
            }
            if(loadMoreInfos.size() < 20 && !loadingDataEnd)
            {
                loadingDataEnd = true;
                listview.removeFooterView(loadMoreView);
                listview.addLoadEndView(this);
                adapter.notifyDataSetChanged();
            }
        } else
        {
            loadMoreView.showTryAgainButton(true);
        }
        loadingData = false;
    }

    protected void tryAgain()
    {
        DLog.v("IndividuationActivity", "tryAgain");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }

    private static final String TAG = "IndividuationActivity";
    private final int LOAD_DATA = 0;
    private final int LOAD_DATA_COUNT = 20;
    private final int LOAD_MORE_DATA = 1;
    private ParticularListAdapter adapter;
    private DataCollectInfo datainfo;
    private ArrayList infos;
    private String intro;
    private MarketListView listview;
    private List loadMoreInfos;
    private LoadMoreView loadMoreView;
    private boolean loadingData;
    private boolean loadingDataEnd;
    private String name;
    private String specialId;





}
