// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.util.PortraitUtil;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.download.ParticularListAdapter;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.share.ShareActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.gui.view.MarketListView;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.module.RecommendAdInfo;
import com.gionee.aora.market.net.SpecialInfomationNet;
import com.gionee.aora.market.util.BannerstartUtil;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.special:
//            SpecialBannerAdapter

public class SpecialInfomationActivity extends MarketBaseActivity
{

    public SpecialInfomationActivity()
    {
        result = null;
        specialId = "";
        datainfo = null;
        isFromOtherApp = false;
    }

    protected void initCenterView()
    {
        int i = biz.AR.color.white;
        setTitleBarViewVisibility(true);
        titleBarView.setTitle(getString(biz.AR.string.special));
        specialId = getIntent().getStringExtra("specialId");
        isFromOtherApp = getIntent().getBooleanExtra("IS_FROM_OTHER_APP", false);
        datainfo = DataCollectManager.getCollectInfo(this);
        DataCollectInfo datacollectinfo = datainfo;
        String as[] = new String[2];
        as[0] = "vid";
        as[1] = specialId;
        DataCollectManager.addRecord(datacollectinfo, as);
        setCenterView(biz.AR.layout.special_layout);
        ImageView imageview = new ImageView(this);
        imageview.setImageResource(biz.AR.drawable.title_share);
        imageview.setPadding(0, 0, getResources().getDimensionPixelSize(biz.AR.dimen.dip10), 0);
        imageview.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(result != null)
                {
                    StringBuffer stringbuffer = new StringBuffer();
                    stringbuffer.append(result[1]);
                    stringbuffer.append(result[0]);
                    ShareActivity.share(SpecialInfomationActivity.this, stringbuffer.toString(), (String)result[0], (String)result[1]);
                }
            }

            
        }
);
        titleBarView.setRightView(imageview);
        listview = (MarketListView)findViewById(biz.AR.id.special_infomation_marketListView);
        listview.setDividerHeight(0);
        webView = new WebView(this);
        android.widget.AbsListView.LayoutParams layoutparams = new android.widget.AbsListView.LayoutParams(-1, -2);
        webView.setLayoutParams(layoutparams);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        adapter = new ParticularListAdapter(this, listview, infos, datainfo.clone());
        adapter.setvId(Integer.parseInt(specialId));
        listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i1, long l1)
            {
                if(infos != null)
                {
                    ((AppInfo)infos.get(i1 - listview.getHeaderViewsCount())).setvId(Integer.valueOf(specialId).intValue());
                    IntroductionDetailActivity.startIntroductionActivity(SpecialInfomationActivity.this, (AppInfo)infos.get(i1 - listview.getHeaderViewsCount()), datainfo);
                }
            }

             
        }
);
        headerview = View.inflate(this, biz.AR.layout.recommend_banner_layout, null);
        headerview.setBackgroundResource(i);
        int j = getResources().getDisplayMetrics().widthPixels;
        int k = (int)getResources().getDimension(biz.AR.dimen.dip7);
        int l = (int)((double)(j - k * 3) / 4.7999999999999998D);
        bannergv = (GridView)headerview.findViewById(biz.AR.id.recommend_banner_gv);
        android.widget.RelativeLayout.LayoutParams layoutparams1 = new android.widget.RelativeLayout.LayoutParams(-1, k + l * 2);
        layoutparams1.setMargins(k, k, k, 0);
        bannergv.setLayoutParams(layoutparams1);
        adInfos = new ArrayList();
        banneradapter = new SpecialBannerAdapter(this, adInfos, l);
        bannergv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i1, long l1)
            {
                if(PortraitUtil.isFastDoubleClick())
                {
                    return;
                } else
                {
                    DataCollectInfo datacollectinfo1 = datainfo.clone();
                    datacollectinfo1.setModel("4");
                    datacollectinfo1.setPosition((new StringBuilder()).append(i1).append("").toString());
                    BannerstartUtil.startBannerDetails((RecommendAdInfo)banneradapter.getInfos().get(i1), SpecialInfomationActivity.this, datacollectinfo1);
                    finish();
                    return;
                }
            }

            
        }
);
        title = View.inflate(this, biz.AR.layout.special_footer_layout, null);
        footer = new View(this);
        footer.setBackgroundResource(i);
        android.widget.AbsListView.LayoutParams layoutparams2 = new android.widget.AbsListView.LayoutParams(-1, k);
        footer.setLayoutParams(layoutparams2);
    }

    protected   boolean initData(Integer ainteger[])
    {
        result = SpecialInfomationNet.getSpecialInfomationList(Integer.valueOf(specialId).intValue());
        return result != null;
    }

    public void onBackPressed()
    {
        finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        infos = new ArrayList();
        doLoadData(new Integer[0]);
    }

    protected void onDestroy()
    {
        if(adapter != null)
            adapter.onDestory();
        super.onDestroy();
    }

    protected   void refreshView(boolean flag, Integer ainteger[])
    {
        if(flag)
        {
            infos = (ArrayList)result[2];
            adInfos = (ArrayList)result[3];
            if(infos.size() != 0)
            {
                webView.loadUrl((new StringBuilder()).append(result[0]).append("&h=1").toString());
                banneradapter.setInfos(adInfos);
                bannergv.setAdapter(banneradapter);
                adapter.setAppInfos(infos);
                listview.addHeaderView(webView, null, false);
                listview.addFooterView(title, null, false);
                listview.addFooterView(headerview, null, false);
                listview.addFooterView(footer, null, false);
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
    }

    protected void tryAgain()
    {
        DLog.v("SpecialInfomationActivity", "tryAgain");
        doLoadData(new Integer[0]);
    }

    private static final String TAG = "SpecialInfomationActivity";
    private ArrayList adInfos;
    private ParticularListAdapter adapter;
    private SpecialBannerAdapter banneradapter;
    private GridView bannergv;
    private DataCollectInfo datainfo;
    private View footer;
    private View headerview;
    private ArrayList infos;
    private boolean isFromOtherApp;
    private MarketListView listview;
    private Object result[];
    private String specialId;
    private View title;
    private WebView webView;






}
