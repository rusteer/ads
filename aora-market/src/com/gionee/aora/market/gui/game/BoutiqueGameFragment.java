// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.game;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.CacheDataManager;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.application.DownloadActivity;
import com.gionee.aora.market.gui.application.SoarActivity;
import com.gionee.aora.market.gui.gift.GiftListActivity;
import com.gionee.aora.market.gui.home.MixtrueNewAdapter;
import com.gionee.aora.market.gui.home.RecommendAdGalaryAdapter;
import com.gionee.aora.market.gui.main.MainNewActivity;
import com.gionee.aora.market.gui.main.MarketBaseFragment;
import com.gionee.aora.market.gui.main.OnLoadData;
import com.gionee.aora.market.gui.view.ImageIndicateLayout;
import com.gionee.aora.market.gui.view.LoadMoreScrollListener;
import com.gionee.aora.market.gui.view.LoadMoreView;
import com.gionee.aora.market.gui.view.MarketExpandListView;
import com.gionee.aora.market.gui.view.MarketGallery;
import com.gionee.aora.market.module.MixtrueInfo;
import com.gionee.aora.market.net.BoutiqueGameNet;
import com.gionee.aora.market.util.BannerstartUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

// Referenced classes of package com.gionee.aora.market.gui.game:
//            ClassifyActivity
public class BoutiqueGameFragment extends MarketBaseFragment implements OnLoadData, android.view.View.OnClickListener {
    private static final int LOAD_BOUTIQUE_DATA = 2;
    private static final int LOAD_CACHE_DATA = 4;
    private static final int LOAD_DATA_SIZE = 5;
    private static final int LOAD_MORE_DATE = 1;
    private static final int LOAD_NET_DATA = 3;
    private static final String TAG = "BoutiqueGameFragment";
    private final String GAME_BANNER_CANCLE_FILE = "game_banner_cancle_data";
    private final String GAME_LIST_CANCLE_FILE = "game_list_cancle_data";
    private ArrayList appInfos;
    private MarketGallery banner;
    private RecommendAdGalaryAdapter bannerAdapter;
    private ImageIndicateLayout bannerIndicate;
    private List bannerInfos;
    private android.widget.AdapterView.OnItemClickListener bannerOnItemClickListener;
    private CacheDataManager cacheDataManager;
    private ArrayList cacheappInfos;
    private List cachebannerInfos;
    private DataCollectInfo datainfo;
    private View entryView;
    private boolean hascacheLoad;
    private View headerView;
    private boolean isFirstLoadData;
    private MixtrueNewAdapter listAdapter;
    private ArrayList listMoreInfos;
    private MarketExpandListView listView;
    private LoadMoreView loadMoreView;
    public BoutiqueGameFragment() {
        bannerAdapter = null;
        bannerInfos = null;
        cachebannerInfos = null;
        appInfos = null;
        cacheappInfos = null;
        listMoreInfos = null;
        hascacheLoad = false;
        loadMoreView = null;
        datainfo = null;
        isFirstLoadData = true;
        bannerOnItemClickListener = new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterview, View view, int i, long l) {
                if (bannerAdapter.getRecommendList().isEmpty()) {
                    return;
                } else {
                    DataCollectInfo datacollectinfo = datainfo.clone();
                    datacollectinfo.setModel("1");
                    datacollectinfo.setPosition(new StringBuilder().append(i % bannerAdapter.getRecommendList().size()).append("").toString());
                    BannerstartUtil.startBannerDetails(bannerAdapter.getRecommendList().get(i % bannerAdapter.getRecommendList().size()), getActivity(), datacollectinfo);
                    return;
                }
            }
        };
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        boolean flag = true;
        switch (ainteger[0].intValue()) {
            case 1://L2_L2:
                if (listMoreInfos != null) listMoreInfos.clear();
                listMoreInfos = BoutiqueGameNet.getBoutiqueGameList(ainteger[1].intValue(), 5, 12);
                if (listMoreInfos == null) {
                    DLog.i("lilijun",
                            "\u52A0\u8F7D\u66F4\u591A\u5217\u8868\u6570\u636E\u5931\u8D25(\u52A0\u8F7D\u5217\u8868\u4E0B\u4E00\u9875\u6570\u636E\u5931\u8D25)\uFF01\uFF01\uFF01");
                    return false;
                }
                return true;
            case 2://L3_L3:
                appInfos = BoutiqueGameNet.getBoutiqueGameList(ainteger[1].intValue(), 5, 12);
                if (appInfos == null) {
                    DLog.i("lilijun", "\u7F51\u7EDC\u52A0\u8F7D\u6570\u636E\u5931\u8D25\u4E86!!");
                    return false;
                }
                if (appInfos.size() == 0) return false;
                break;
            case 3://L4_L4:
                bannerInfos = BoutiqueGameNet.getBannerInfos(0);
                List list = bannerInfos;
                flag = false;
                if (list == null) return flag;
                if (bannerInfos.size() == 0) return false;
                break;
            case 4://L5_L5:
                cachebannerInfos = (List) cacheDataManager.getCacheDataToFile2(getActivity(), "game_banner_cancle_data");
                cacheappInfos = (ArrayList) cacheDataManager.getCacheDataToFile2(getActivity(), "game_list_cancle_data");
                boolean flag1;
                if (cachebannerInfos == null) {
                    DLog.i("denglihua", "\u7CBE\u54C1\u6E38\u620F\u6CA1\u6709\u7F13\u5B58\u6570\u636E!!");
                    flag1 = false;
                } else if (cacheappInfos.isEmpty()) {
                    DLog.i("denglihua", "\u7CBE\u54C1\u6E38\u620F\u6CA1\u6709\u7F13\u5B58\u6570\u636E!!");
                    flag1 = false;
                } else {
                    flag1 = true;
                }
                hascacheLoad = flag1;
                return flag1;
        }
        return flag;
    }
    @Override
    protected void initView(RelativeLayout relativelayout) {
        datainfo = new DataCollectInfo();
        datainfo.setPage("2");
        setCenterView(biz.AR.layout.recommend);
        setTitleBarViewVisibility(false);
        bannerInfos = new ArrayList();
        appInfos = new ArrayList();
        listView = (MarketExpandListView) relativelayout.findViewById(biz.AR.id.recommend_el);
        listAdapter = new MixtrueNewAdapter(getActivity(), appInfos, datainfo.clone());
        headerView = View.inflate(getActivity(), biz.AR.layout.boutique_game_header, null);
        banner = (MarketGallery) headerView.findViewById(biz.AR.id.game_banner);
        bannerIndicate = (ImageIndicateLayout) headerView.findViewById(biz.AR.id.game_banner_indicate);
        bannerAdapter = new RecommendAdGalaryAdapter(getActivity(), bannerInfos);
        banner.setOnItemClickListener(bannerOnItemClickListener);
        MarketGallery marketgallery = banner;
        ViewGroup aviewgroup[] = new ViewGroup[1];
        aviewgroup[0] = MainNewActivity.mainviewpage;
        marketgallery.setViewGroups(aviewgroup);
        entryView = View.inflate(getActivity(), biz.AR.layout.game_entry_layout, null);
        entryView.findViewById(biz.AR.id.game_entry0).setOnClickListener(this);
        entryView.findViewById(biz.AR.id.game_entry1).setOnClickListener(this);
        entryView.findViewById(biz.AR.id.game_entry2).setOnClickListener(this);
        entryView.findViewById(biz.AR.id.game_entry3).setOnClickListener(this);
        loadMoreView = new LoadMoreView(getActivity()) {
            @Override
            public void tryAgain() {
                loadMoreData();
            }
            /* final BoutiqueGameFragment this$0;


             {
                 this$0 = BoutiqueGameFragment.this;
                 LoadMoreView(context);
             }*/
        };
    }
    private void loadMoreData() {
        if (appInfos == null || loadingData || loadingDataEnd || loadMoreView.isShowTryAgain()) {
            return;
        } else {
            loadingData = true;
            Integer ainteger[] = new Integer[2];
            ainteger[0] = Integer.valueOf(1);
            ainteger[1] = Integer.valueOf(((MixtrueInfo) appInfos.get(-1 + appInfos.size())).getMixStart());
            doLoadData(ainteger);
            return;
        }
    }
    private void loadSuccessData() {
        int i = 0;
        listView.addHeaderView(headerView, null, false);
        listView.addHeaderView(entryView, null, false);
        listView.addFooterView(loadMoreView, null, false);
        listView.setAdapter(listAdapter);
        for (int j = listAdapter.getGroupCount(); i < j; i++)
            listView.expandGroup(i);
        listAdapter.notifyDataSetChanged();
        listView.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true,
                new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {
            @Override
            public void loadMoreWhenScrollToEnd() {
                loadMoreData();
            }
        }));
    }
    @Override
    public void onClick(View view) {
        DataCollectInfo datacollectinfo = datainfo.clone();
        datacollectinfo.setModel("3");
        datacollectinfo.setType("");
        switch (view.getId()) {
            default:
                return;
            case biz.AR.id.game_entry0:
                datacollectinfo.setPosition("0");
                DownloadActivity.startDownloadAcivity(getActivity(), datacollectinfo, 2);
                return;
            case biz.AR.id.game_entry1:
                datacollectinfo.setPosition("1");
                SoarActivity.startSoarAcivity(getActivity(), datacollectinfo, 2);
                return;
            case biz.AR.id.game_entry2:
                datacollectinfo.setPosition("3");
                ClassifyActivity.startClassifyActivity(2, getActivity(), datacollectinfo);
                return;
            case biz.AR.id.game_entry3:
                datacollectinfo.setPosition("2");
                GiftListActivity.startGiftListActivity(getActivity(), datacollectinfo);
                return;
        }
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        cacheDataManager = CacheDataManager.getInstance();
    }
    @Override
    public void onDestroy() {
        if (banner != null) banner.setCancle(true);
        super.onDestroy();
    }
    @Override
    public void onLoadData() {}
    @Override
    public void onResume() {
        super.onResume();
        if (banner != null && banner.isCancle()) banner.autoRefresh();
    }
    @Override
    public void onStop() {
        if (banner != null) banner.setCancle(true);
        super.onStop();
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            case 1://L2_L2:
                if (flag) {
                    if (!listMoreInfos.isEmpty()) {
                        appInfos.addAll(listMoreInfos);
                        int i = listAdapter.getGroupCount();
                        for (int j = 0; j < i; j++)
                            listView.expandGroup(j);
                        listAdapter.notifyDataSetChanged();
                    }
                    if (listMoreInfos.size() < 5) {
                        loadingDataEnd = true;
                        listView.removeFooterView(loadMoreView);
                        listView.addLoadEndView(getActivity());
                    }
                } else {
                    loadMoreView.showTryAgainButton(true);
                }
                loadingData = false;
                break;
            case 2://L3_L3:
                if (flag) {
                    loadingView.setVisibility(8);
                    boolean flag2 = cacheDataManager.saveCachDataToFile2(getActivity(), "game_list_cancle_data", appInfos);
                    DLog.d("denglihua", new StringBuilder().append("\u7CBE\u54C1\u6E38\u620F\u7F13\u5B58").append(flag2).toString());
                    listAdapter.setMixinfos(appInfos);
                    Integer ainteger1[];
                    if (!hascacheLoad) {
                        loadSuccessData();
                    } else {
                        listView.setAdapter(listAdapter);
                        int k = listAdapter.getGroupCount();
                        for (int l = 0; l < k; l++)
                            listView.expandGroup(l);
                        listAdapter.notifyDataSetChanged();
                    }
                    if (appInfos.size() < 5) {
                        DLog.i("BoutiqueGameFragment", "\u6CA1\u6709\u63A8\u8350\u6570\u636E\u4E86-------1");
                        loadingDataEnd = true;
                        listView.removeFooterView(loadMoreView);
                        listView.addLoadEndView(getActivity());
                        listAdapter.notifyDataSetChanged();
                    }
                    ainteger1 = new Integer[1];
                    ainteger1[0] = Integer.valueOf(3);
                    doLoadData(ainteger1);
                    return;
                }
                if (!hascacheLoad) {
                    showErrorView();
                    return;
                }
                break;
            case 3://L4_L4:
                if (flag) {
                    boolean flag1 = cacheDataManager.saveCachDataToFile2(getActivity(), "game_banner_cancle_data", bannerInfos);
                    DLog.d("denglihua", new StringBuilder().append("\u7CBE\u54C1\u6E38\u620Fbanner\u7F13\u5B58").append(flag1).toString());
                    bannerIndicate.setViewCount(bannerInfos.size());
                    bannerAdapter.setRecommendList(bannerInfos);
                    banner.setAdapter(bannerAdapter);
                    banner.setSelection(200);
                    if (!hascacheLoad) banner.autoRefresh();
                    setBannerPagerChangeLineAnimation();
                    return;
                }
                break;
            case 4://L5_L5:
                Integer ainteger2[];
                if (flag) {
                    DLog.d("denglihua", "\u7F13\u5B58\u6570\u636E\u52A0\u8F7D\u6210\u529F");
                    if (!cachebannerInfos.isEmpty()) {
                        DLog.i("denglihua", "\u7CBE\u54C1\u6E38\u620Fbanner\u7F13\u5B58\u6570\u636E\u52A0\u8F7D\u6210\u529F");
                        bannerIndicate.setViewCount(cachebannerInfos.size());
                        bannerAdapter.setRecommendList(cachebannerInfos);
                        bannerAdapter.notifyDataSetChanged();
                        banner.setAdapter(bannerAdapter);
                        banner.setSelection(200);
                        banner.autoRefresh();
                        setBannerPagerChangeLineAnimation();
                    }
                    listAdapter.setMixinfos(cacheappInfos);
                    loadSuccessData();
                    loadingView.setVisibility(8);
                } else {
                    loadingView.setVisibility(0);
                }
                DataCollectManager.addRecord(datainfo, new String[0]);
                ainteger2 = new Integer[2];
                ainteger2[0] = Integer.valueOf(2);
                ainteger2[1] = Integer.valueOf(0);
                doLoadData(ainteger2);
                break;
        }
    }
    private void setBannerPagerChangeLineAnimation() {
        banner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterview, View view, int i, long l) {
                if (bannerAdapter.getRecommendList() != null && bannerAdapter.getRecommendList().size() != 0) bannerIndicate
                .setCurView(i % bannerAdapter.getRecommendList().size());
            }
            @Override
            public void onNothingSelected(AdapterView adapterview) {}
        });
    }
    @Override
    protected void setDataAgain() {
        if (bannerInfos.isEmpty() || appInfos.isEmpty()) {
            showErrorView();
            return;
        }
        bannerIndicate.setViewCount(cachebannerInfos.size());
        bannerAdapter.setRecommendList(cachebannerInfos);
        bannerAdapter.notifyDataSetChanged();
        if (banner.isCancle()) {
            banner.setSelection(200);
            banner.autoRefresh();
            setBannerPagerChangeLineAnimation();
        }
        listAdapter.setMixinfos(cacheappInfos);
        int i = listAdapter.getGroupCount();
        for (int j = 0; j < i; j++)
            listView.expandGroup(j);
        listAdapter.notifyDataSetChanged();
    }
    @Override
    public void setUserVisibleHint(boolean flag) {
        super.setUserVisibleHint(flag);
        if (flag && !hadLoadData && isFirstLoadData) {
            Integer ainteger[] = new Integer[1];
            ainteger[0] = Integer.valueOf(4);
            doLoadData(ainteger);
            isFirstLoadData = false;
        }
    }
    @Override
    protected void tryAgain() {
        DLog.v("lilijun", "tryAgain");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(2);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
}
