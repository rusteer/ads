// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Context;
import android.os.Bundle;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.*;
import com.gionee.aora.market.net.PrefectureRecomendFragmentNet;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.special:
//            PrefectureRecomendAdapter

public class SpecialListActivity extends MarketBaseActivity
{

    public SpecialListActivity()
    {
        loadMoreView = null;
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
        titleBarView.setTitle("\u4E13\u9898");
        setCenterView(biz.AR.layout.special_recomend_faragment);
        listView = (MarketListView)findViewById(biz.AR.id.special_recoment_listview);
        listView.setHeard(this);
        adapter = new PrefectureRecomendAdapter(this, infos, null);
        loadMoreView = new LoadMoreView(this) {

            public void tryAgain()
            {
                loadMoreData();
            }

            
        }
;
        listView.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true, new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {

            public void loadMoreWhenScrollToEnd()
            {
                loadMoreData();
            }

             
        }
));
    }

    protected   boolean initData(Integer ainteger[])
    { boolean flag = true;
       switch( ainteger[0].intValue()){
           case 0://L2_L2:
               List list1;
               infos = PrefectureRecomendFragmentNet.getPrefectureRecomendList(ainteger[1].intValue(), 20);
               list1 = infos;
               flag = false;
               if(list1 == null) return flag;
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
               loadMoreInfos = PrefectureRecomendFragmentNet.getPrefectureRecomendList(ainteger[1].intValue(), 20);
               list = loadMoreInfos;
               flag = false;
               if(list == null) return flag;
               if(loadMoreInfos.size() == 0)
                   return true;
               break;
       }
         
        return flag;

         
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        DLog.i("lilijun", "PrefectureRecomendFragment onCreate()");
        infos = new ArrayList();
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
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
                setData();
                return;
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
                DLog.d("lilijun", (new StringBuilder()).append("LoadMoreAsyncTask loadingData end").append(loadMoreInfos.size()).toString());
            }
            if(loadMoreInfos.size() < 20 && !loadingDataEnd)
            {
                loadingDataEnd = true;
                listView.removeFooterView(loadMoreView);
                listView.addLoadEndView(this);
                adapter.notifyDataSetChanged();
            }
        } else
        {
            loadMoreView.showTryAgainButton(true);
        }
        loadingData = false;
    }

    public void setData()
    {
        if(infos.size() != 0)
        {
            adapter.setPrefectureInfos(infos);
            if(!loadingDataEnd)
                listView.addFooterView(loadMoreView, null, false);
            if(infos.size() < 20)
            {
                loadingDataEnd = true;
                listView.removeFooterView(loadMoreView);
                listView.addLoadEndView(this);
            }
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    protected void tryAgain()
    {
        DLog.v("PrefectureRecomendFragment", "tryAgain");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }

    private static final String TAG = "PrefectureRecomendFragment";
    private final int LOAD_DATA = 0;
    private final int LOAD_DATA_COUNT = 20;
    private final int LOAD_MORE_DATA = 1;
    private String action;
    private PrefectureRecomendAdapter adapter;
    private List infos;
    private MarketListView listView;
    private List loadMoreInfos;
    private LoadMoreView loadMoreView;

}
