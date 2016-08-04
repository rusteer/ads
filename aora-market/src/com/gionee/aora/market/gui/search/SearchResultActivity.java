// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.search;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.integral.util.PortraitUtil;
import com.gionee.aora.market.control.*;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.download.DownloadViewHolder;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.search.view.MarketAutoCompleteTextView;
import com.gionee.aora.market.gui.view.*;
import com.gionee.aora.market.module.SearchResultInfo;
import com.gionee.aora.market.net.SearchResultNet;
import com.gionee.aora.market.util.Util;
import com.gionee.aora.market.util.VoiceSearchUtilAora;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.*;

// Referenced classes of package com.gionee.aora.market.gui.search:
//            SearchResultAdapter, LocalSearchAdapter
public class SearchResultActivity extends MarketBaseActivity implements android.view.View.OnClickListener {
    public SearchResultActivity() {
        defSearchKey = "";
        loadMoreView = null;
        action = null;
    }
    public static void hideInputMethodManagerKeyStore(Context context) {
        InputMethodManager inputmethodmanager = (InputMethodManager) context.getSystemService("input_method");
        if (inputmethodmanager != null) {
            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                android.os.IBinder ibinder = view.getWindowToken();
                if (ibinder != null) inputmethodmanager.hideSoftInputFromWindow(ibinder, 2);
            }
        }
    }
    private void loadMoreData() {
        if (info == null || loadingData || info.isDataEnd() || loadMoreView.isShowTryAgain()) {
            return;
        } else {
            loadingData = true;
            Integer ainteger[] = new Integer[1];
            ainteger[0] = Integer.valueOf(2);
            doLoadData(ainteger);
            return;
        }
    }
    private void showResultPage(String s, DataCollectInfo datacollectinfo) {
        datacollectinfo.put("keywords", s);
        hideInputMethodManagerKeyStore(this);
        preferences.setSearchHistory(s);
        defSearchKey = s;
        softListAdapter.setQueryString(defSearchKey);
        if (loadingView != null) loadingView.setVisibility(0);
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(1);
        doLoadData(ainteger);
    }
    protected void initCenterView() {
        setTitleBarViewVisibility(false);
        setCenterView(biz.AR.layout.softlist_layout);
        voiceSearchButton = (ImageView) findViewById(biz.AR.id.voice_search);
        searchButton = (Button) findViewById(biz.AR.id.search_go_btn);
        cleanButton = (ImageButton) findViewById(biz.AR.id.search_clear);
        qcanButton = (Button) findViewById(biz.AR.id.search_qrcode_btn);
        cleanButton.setOnClickListener(this);
        voiceSearchButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        listView = (MarketListView) findViewById(biz.AR.id.marketListView1);
        qcanButton.setOnClickListener(this);
        softListAdapter = new SearchResultAdapter(this, info, action) {
            public void onItemViewClick(String s) {
                textView.setThreshold(0x7fffffff);
                textView.setText(s);
                textView.setSelection(s.length());
                textView.dismissDropDown();
                textView.setThreshold(1);
                action.setModel("5");
                showResultPage(s, action);
            }

             
        };
        softListAdapter.setQueryString(defSearchKey);
        loadMoreView = new LoadMoreView(this) {
            public void tryAgain() {
                loadMoreData();
            }
        };
        listView.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true,
                new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {
                    public void loadMoreWhenScrollToEnd() {
                        loadMoreData();
                    }
                }));
        textView = (MarketAutoCompleteTextView) findViewById(biz.AR.id.search_input);
        textView.setText(defSearchKey);
        textView.setSelection(defSearchKey.length());
        DataCollectInfo datacollectinfo = action.clone();
        datacollectinfo.setModel("2");
        datacollectinfo.setType("1");
        LocalSearchAdapter localsearchadapter = new LocalSearchAdapter(this, datacollectinfo, MarketPreferences.getInstance(this).isSearchAtLocal());
        textView.setDropDownBackgroundDrawable(new ColorDrawable(0xffdddddd));
        textView.setAdapter(localsearchadapter);
        textView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong) {
                String str = SearchResultActivity.this.textView.getText().toString();
                if ((str == null) || (str.trim().equals(""))) { return; }
                SearchResultActivity.this.textView.setSelection(str.length());
                if (paramAnonymousInt == 0) {
                    try {
                        JSONArray localJSONArray1 = new JSONObject("").getJSONArray("Temp_BJoin");
                        for (int i = 0; i < localJSONArray1.length(); i++) {
                            JSONArray localJSONArray2 = localJSONArray1.getJSONArray(i);
                            for (int j = 0; j < localJSONArray2.length(); j++) {
                                localJSONArray2.getJSONObject(j);
                            }
                        }
                        SearchResultActivity.this.action.setModel("2");
                    } catch (JSONException localJSONException) {
                        localJSONException.printStackTrace();
                        SearchResultActivity.this.action.setModel("2");
                        DataCollectInfo localDataCollectInfo = SearchResultActivity.this.action.clone();
                        localDataCollectInfo.setPosition("0");
                        localDataCollectInfo.setType("0");
                        DataCollectManager.addRecord(localDataCollectInfo, new String[] { "keywords", str });
                        localDataCollectInfo.setType("1");
                        localDataCollectInfo.data.remove("keywords");
                        IntroductionDetailActivity.startIntroductionActivityForSoftId(SearchResultActivity.this, (String) paramAnonymousView.getTag(biz.AR.id.img),
                                localDataCollectInfo);
                        return;
                    }
                }
                SearchResultActivity.this.action.setPosition(paramAnonymousInt + "");
                SearchResultActivity.this.action.data.put("keywords", str);
                SearchResultActivity.this.showResultPage(str, SearchResultActivity.this.action);
            }
        });
        textView.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView textview, int j, KeyEvent keyevent) {
                if (j == 3) {
                    textView.post(new Runnable() {
                        public void run() {
                            textView.dismissDropDown();
                        }
                    });
                    action.setModel("1");
                    DataCollectInfo datacollectinfo1 = action.clone();
                    showResultPage(textview.getText().toString(), datacollectinfo1);
                }
                return false;
            }
        });
        localsearchadapter.setOnPlushClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view1) {
                String s = (String) view1.getTag(biz.AR.id.tv1);
                if (s != null && !s.equals("")) {
                    textView.setText(s);
                    textView.setSelection(s.length());
                }
            }
        });
        View view = findViewById(biz.AR.id.search_title_layout);
        int i = getWindowManager().getDefaultDisplay().getHeight();
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, 0), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
        textView.setDropDownHeight(i - view.getMeasuredHeight() - Util.getStatBarHeight(this));
    }
    protected boolean initData(Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            default:
                return false;
            case 1: // '\001'
                DataCollectInfo datacollectinfo = action;
                String as[] = new String[4];
                as[0] = "pageset";
                as[1] = "0";
                as[2] = "keywords";
                as[3] = defSearchKey;
                DataCollectManager.addRecord(datacollectinfo, as);
                action.data.remove("keywords");
                info = new SearchResultInfo();
                return SearchResultNet.getSearchResult(this, info, defSearchKey, 0, 20);
            case 2: // '\002'
                return SearchResultNet.getSearchResult(this, info, defSearchKey, info.getResultApps().size(), 20);
        }
    }
    protected final void onActivityResult(int i, int j, Intent intent) {
        ArrayList arraylist = VoiceSearchUtilAora.getSearchResult(i, j, intent);
        if (arraylist != null) {
            textView.setText((CharSequence) arraylist.get(0));
            textView.setSelection(((String) arraylist.get(0)).length());
        }
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case biz.AR.id.voice_search://L2_L2:
                if (!PortraitUtil.isFastDoubleClick(1000L)) {
                    VoiceSearchUtilAora.startVoiceSearch(this);
                    return;
                }
                return;
            case biz.AR.id.search_input://L1
                break;
            case biz.AR.id.search_go_btn://L3_L3:
                String s = textView.getText().toString();
                if (s != null && !s.trim().equals("")) {
                    action.setModel("1");
                    showResultPage(s, action.clone());
                    return;
                }
                return;
            case biz.AR.id.search_qrcode_btn://L4
                QRCodeManager.startScan(this);
                return;
            case biz.AR.id.search_clear://L5_L5:
                textView.setText("");
                return;
        }
    }
    protected void onCreate(Bundle bundle) {
        defSearchKey = getIntent().getStringExtra("search_key");
        action = DataCollectManager.getCollectInfo(this);
        preferences = MarketPreferences.getInstance(this);
        super.onCreate(bundle);
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(1);
        doLoadData(ainteger);
    }
    protected void onDestroy() {
        if (softListAdapter != null) softListAdapter.unregisterListener();
        super.onDestroy();
    }
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            default:
                return;
            case 1: // '\001'
                listView.removeFooterView(loadMoreView);
                listView.removeLoadEndView();
                if (flag) {
                    setData();
                    return;
                } else {
                    showErrorView();
                    return;
                }
            case 2: // '\002'
                break;
        }
        if (flag) {
            if (info.isDataEnd()) {
                listView.removeFooterView(loadMoreView);
                listView.addLoadEndView(this);
            }
            softListAdapter.notifyDataSetChanged();
        } else {
            loadMoreView.showTryAgainButton(true);
        }
        loadingData = false;
    }
    public void setData() {
        DataCollectInfo datacollectinfo;
        if (info == null) showErrorView();
        else if (info.getResultApps().size() == 0) {
            showErrorView(
                    biz.AR.drawable.no_update_apps,
                    "\u62B1\u6B49\uFF0C\u6CA1\u6709\u627E\u5230\u76F8\u5E94\u7684\u7ED3\u679C\n\u5EFA\u8BAE\u60A8\uFF1A\n1.\u68C0\u67E5\u8F93\u5165\u7684\u5173\u952E\u5B57\u662F\u5426\u6709\u8BEF\n2.\u5C1D\u8BD5\u641C\u7D22\u5176\u4ED6\u5E94\u7528",
                    false);
            return;
        }
        if (info.isDataEnd()) listView.addLoadEndView(this);
        else listView.addFooterView(loadMoreView, null, false);
        datacollectinfo = action.clone();
        softListAdapter.setAppInfos(info);
        softListAdapter.setAction(datacollectinfo);
        listView.setAdapter(softListAdapter);
        softListAdapter.notifyDataSetChanged();
    }
    protected void tryAgain() {
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(1);
        doLoadData(ainteger);
        super.tryAgain();
    }
    public static final String KEY_IS_SEARCH_FROM_TAG = "is_click_tag";
    public static final String SEARCH_KEY = "search_key";
    private final int LOAD_COUNT_EACH_TIME = 20;
    private final int TAG_INIT = 1;
    private final int TAG_LOADMORE = 2;
    private DataCollectInfo action;
    private ImageButton cleanButton;
    private String defSearchKey;
    private SearchResultInfo info;
    private MarketListView listView;
    private LoadMoreView loadMoreView;
    private MarketPreferences preferences;
    private Button qcanButton;
    private Button searchButton;
    private SearchResultAdapter softListAdapter;
    private MarketAutoCompleteTextView textView;
    private ImageView voiceSearchButton;
}
