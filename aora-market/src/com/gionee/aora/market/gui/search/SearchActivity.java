// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.search;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.util.PortraitUtil;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.LocalSearchManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.control.QRCodeManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.gui.main.MarketBaseFragment;
import com.gionee.aora.market.gui.search.view.MarketAutoCompleteTextView;
import com.gionee.aora.market.util.Util;
import com.gionee.aora.market.util.VoiceSearchUtilAora;

// Referenced classes of package com.gionee.aora.market.gui.search:
//            LocalSearchAdapter, SearchNewKeysRankFragment, SearchResultActivity, SearchHintAdapter
public class SearchActivity extends FragmentActivity implements android.view.View.OnClickListener {
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
    private final String TAG = "SearchActivity";
    private DataCollectInfo action;
    private ImageButton cleanButton;
    private FragmentManager fragmentManager;
    public SearchHintAdapter hintAdapter;
    private String hintAppName;
    private boolean isInitiative;
    public MarketBaseFragment nowShowFragment;
    private MarketPreferences preferences;
    private Button qcanButton;
    public SearchNewKeysRankFragment rankFragment;
    private Button searchButton;
    private MarketAutoCompleteTextView textView;
    private ImageView voiceSearchButton;
    /*
        static boolean access$302(SearchActivity searchactivity, boolean flag)
        {
            searchactivity.isInitiative = flag;
            return flag;
        }

     */
    public SearchActivity() {
        isInitiative = true;
        action = new DataCollectInfo("", "7", "", "", "0");
        hintAppName = " \u6613\u7528\u6C47";
    }
    private void displaySearchResult(String s) {
        String s1 = s.trim();
        DataCollectInfo datacollectinfo = action.clone();
        if (s1.equals("")) {
            s1 = hintAppName.trim();
            datacollectinfo.setModel("7");
        } else {
            datacollectinfo.setModel("1");
        }
        datacollectinfo.put("keywords", s1);
        preferences.setSearchHistory(s1);
        textView.setText(s1);
        textView.setSelection(s1.length());
        showResultPage(s1, false, datacollectinfo);
    }
    private void getCurHintText() {
        String s = preferences.getCurSearchHint();
        int i = s.indexOf(":");
        if (i != -1) hintAppName = new StringBuilder().append(" ").append(s.substring(i + 1)).toString();
    }
    private void initView() {
        LocalSearchManager.getLocalSearchData(this);
        DataCollectManager.addRecord(action, new String[0]);
        fragmentManager = getSupportFragmentManager();
        preferences = MarketPreferences.getInstance(this);
        getCurHintText();
        voiceSearchButton = (ImageView) findViewById(biz.AR.id.voice_search);
        searchButton = (Button) findViewById(biz.AR.id.search_go_btn);
        cleanButton = (ImageButton) findViewById(biz.AR.id.search_clear);
        qcanButton = (Button) findViewById(biz.AR.id.search_qrcode_btn);
        textView = (MarketAutoCompleteTextView) findViewById(biz.AR.id.search_input);
        textView.setHint(hintAppName);
        DataCollectInfo datacollectinfo = action.clone();
        datacollectinfo.setModel("2");
        datacollectinfo.setType("1");
        LocalSearchAdapter localsearchadapter = new LocalSearchAdapter(this, datacollectinfo, MarketPreferences.getInstance(this).isSearchAtLocal());
        textView.setDropDownBackgroundDrawable(new ColorDrawable(0xffdddddd));
        textView.setAdapter(localsearchadapter);
        textView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterview, View view1, int j, long l) {
                String s = textView.getText().toString();
                if (s == null || s.trim().equals("")) return;
                textView.setSelection(s.length());
                if (j == 0) {
                    DataCollectInfo datacollectinfo2 = action.clone();
                    datacollectinfo2.setModel("2");
                    datacollectinfo2.setPosition("0");
                    datacollectinfo2.put("keywords", s);
                    datacollectinfo2.setType("0");
                    DataCollectManager.addRecord(datacollectinfo2, new String[] { "keywords", s });
                    datacollectinfo2.setType("1");
                    datacollectinfo2.data.remove("keywords");
                    IntroductionDetailActivity.startIntroductionActivityForSoftId(SearchActivity.this, (String) view1.getTag(biz.AR.id.img), datacollectinfo2);
                    return;
                } else {
                    DataCollectInfo datacollectinfo1 = action.clone();
                    datacollectinfo1.setModel("2");
                    datacollectinfo1.setPosition(new StringBuilder().append(j).append("").toString());
                    datacollectinfo1.put("keywords", s);
                    showResultPage(s, false, datacollectinfo1);
                    return;
                }
            }
        });
        textView.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textview, int j, KeyEvent keyevent) {
                if (j == 3) {
                    isInitiative = true;
                    textView.dismissDropDown();
                    displaySearchResult(textview.getText().toString());
                }
                return false;
            }
        });
        localsearchadapter.setOnPlushClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                String s = (String) view1.getTag(biz.AR.id.tv1);
                if (s != null && !s.equals("")) {
                    textView.setText(s);
                    textView.setSelection(s.length());
                }
            }
        });
        cleanButton.setOnClickListener(this);
        voiceSearchButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        View view = findViewById(biz.AR.id.search_title_layout);
        int i = getWindowManager().getDefaultDisplay().getHeight();
        view.measure(android.view.View.MeasureSpec.makeMeasureSpec(0, 0), android.view.View.MeasureSpec.makeMeasureSpec(0, 0));
        textView.setDropDownHeight(i - view.getMeasuredHeight() - Util.getStatBarHeight(this));
        qcanButton.setOnClickListener(this);
        setShowItem();
    }
    @Override
    protected final void onActivityResult(int i, int j, Intent intent) {
        ArrayList arraylist = VoiceSearchUtilAora.getSearchResult(i, j, intent);
        if (arraylist != null) {
            isInitiative = true;
            textView.setText((CharSequence) arraylist.get(0));
            textView.setSelection(((String) arraylist.get(0)).length());
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case biz.AR.id.voice_search://L2_L2:
                if (!PortraitUtil.isFastDoubleClick(1000L)) {
                    VoiceSearchUtilAora.startVoiceSearch(this);
                    return;
                }
                break;
            case biz.AR.id.search_input://L3_L3:
                isInitiative = true;
                return;
            case biz.AR.id.search_go_btn://L4_L4:
                isInitiative = true;
                displaySearchResult(textView.getText().toString());
                return;
            case biz.AR.id.search_qrcode_btn://L5_L5:
                QRCodeManager.startScan(this);
                return;
            case biz.AR.id.search_clear://L6_L6:
                textView.setText("");
                return;
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.search_main);
        initView();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    public void removeLastFragment(MarketBaseFragment marketbasefragment) {
        if (marketbasefragment != null && fragmentManager != null && marketbasefragment.isAdded()) {
            DLog.e("lung ", new StringBuilder().append("removeLastFragment =").append(marketbasefragment).toString());
            fragmentManager.beginTransaction().remove(marketbasefragment).commitAllowingStateLoss();
        }
    }
    public void setNowFragment2(MarketBaseFragment marketbasefragment) {
        if (nowShowFragment != null) fragmentManager.beginTransaction().hide(nowShowFragment).commitAllowingStateLoss();
        nowShowFragment = marketbasefragment;
    }
    private void setShowItem() {
        getIntent().getStringExtra("key_value");
        rankFragment = SearchNewKeysRankFragment.newInstance(new SearchNewKeysRankFragment.KeyClickCallback() {
            @Override
            public void onItemViewClick(String s, boolean flag) {
                textView.setText(s);
                textView.setSelection(s.length());
                DataCollectInfo datacollectinfo = action.clone();
                datacollectinfo.setModel("3");
                if (flag) datacollectinfo.setModel("4");
                else datacollectinfo.setModel("3");
                showResultPage(s, true, datacollectinfo);
            }
        });
        fragmentManager.beginTransaction().add(biz.AR.id.search_main_relativelayout, rankFragment).commitAllowingStateLoss();
        nowShowFragment = rankFragment;
    }
    private void showResultPage(String s, boolean flag, DataCollectInfo datacollectinfo) {
        hideInputMethodManagerKeyStore(this);
        preferences.setSearchHistory(s);
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("search_key", s);
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        startActivity(intent);
    }
}
