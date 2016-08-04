// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.application;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.download.RankListAdapter;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.LoadMoreScrollListener;
import com.gionee.aora.market.gui.view.LoadMoreView;
import com.gionee.aora.market.gui.view.MarketListView;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.DownloadNet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DownloadActivity extends MarketBaseActivity {
    public static void startDownloadAcivity(Context context, DataCollectInfo datacollectinfo, int i) {
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        intent.putExtra("TYPE", i);
        context.startActivity(intent);
    }
    private static final String TAG = "DownloadActivity";
    private final int LOAD_DATA = 0;
    private final int LOAD_HOTGAME_DATA_COUNT = 20;
    private final int LOAD_MORE_HOTGAME_DATA = 1;
    private ArrayList appInfos;
    private DataCollectInfo datainfo;
    private MarketListView listView;
    private List loadMoreInfos;
    private LoadMoreView loadMoreView;
    private RankListAdapter softListAdapter;
    private int type;
    public DownloadActivity() {
        loadMoreView = null;
        datainfo = null;
        type = 1;
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.boutique_game_layout);
        titleBarView.setTitle("\u4E0B\u8F7D\u699C");
        type = getIntent().getIntExtra("TYPE", 1);
        datainfo = DataCollectManager.getCollectInfo(this);
        listView = (MarketListView) findViewById(biz.AR.id.game_listview);
        softListAdapter = new RankListAdapter(this, listView, appInfos, datainfo.clone());
        loadMoreView = new LoadMoreView(this) {
            @Override
            public void tryAgain() {
                loadMoreData();
            }
        };
        listView.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true,
                new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {
                    @Override
                    public void loadMoreWhenScrollToEnd() {
                        loadMoreData();
                    }
                }));
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterview, View view, int i, long l) {
                DataCollectInfo datacollectinfo = datainfo.clone();
                datacollectinfo.setType("1");
                IntroductionDetailActivity.startIntroductionActivity(DownloadActivity.this, (AppInfo) appInfos.get(i), datacollectinfo);
            }
        });
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
        DataCollectManager.addRecord(datainfo, new String[0]);
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        boolean flag = true;
    switch (ainteger[0].intValue()) {
        case 0://L2_L2:
            ArrayList arraylist;
            appInfos = DownloadNet.getDowloadList(ainteger[1].intValue(), 20, type);
            arraylist = appInfos;
            flag = false;
            if (arraylist == null) return flag;
            return appInfos.size() != 0 ? true : true;
        case 1://L3_L3:
            List list;
            if (loadMoreInfos != null) {
                loadMoreInfos.clear();
                loadMoreInfos = null;
            }
            loadMoreInfos = DownloadNet.getDowloadList(ainteger[1].intValue(), 20, type);
            list = loadMoreInfos;
            flag = false;
            if (list == null) return flag;
            if (loadMoreInfos.size() == 0) return true;
                return true;
    }
        return flag;
    }
    private void loadMoreData() {
        if (appInfos == null || loadingData || loadingDataEnd || loadMoreView.isShowTryAgain()) {
            return;
        } else {
            loadingData = true;
            Integer ainteger[] = new Integer[2];
            ainteger[0] = Integer.valueOf(1);
            ainteger[1] = Integer.valueOf(appInfos.size());
            doLoadData(ainteger);
            return;
        }
    }
    @Override
    public void onDestroy() {
        if (softListAdapter != null) softListAdapter.onDestory();
        super.onDestroy();
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            default:
                return;
            case 0: // '\0'
                if (flag) {
                    setData();
                    return;
                } else {
                    showErrorView();
                    return;
                }
            case 1: // '\001'
                break;
        }
        if (flag) {
            if (loadMoreInfos.size() != 0) {
                appInfos.addAll(loadMoreInfos);
                softListAdapter.notifyDataSetChanged();
                DLog.d("denglh", new StringBuilder().append("LoadMoreAsyncTask loadingData end").append(loadMoreInfos.size()).toString());
            }
            if (loadMoreInfos.size() < 20) {
                loadingDataEnd = true;
                listView.removeFooterView(loadMoreView);
                listView.addLoadEndView(this);
                softListAdapter.notifyDataSetChanged();
            }
        } else {
            loadMoreView.showTryAgainButton(true);
        }
        loadingData = false;
    }
    public void setData() {
        if (appInfos == null) showErrorView();
        else if (appInfos.size() == 0) {
                showErrorView(biz.AR.drawable.no_update_apps, "\u65E0\u6570\u636E\u8FD4\u56DE", false);
                return;
            }
        if (appInfos.size() < 20) {
            loadingDataEnd = true;
            listView.addLoadEndView(this);
        } else {
            listView.addFooterView(loadMoreView, null, false);
        }
        softListAdapter.setAppInfos(appInfos);
        listView.setAdapter(softListAdapter);
        softListAdapter.notifyDataSetChanged();
    }
    @Override
    protected void tryAgain() {
        DLog.v("DownloadActivity", "tryAgain");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
}
