// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.details;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.aora.base.util.NetUtil;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.gui.details.view.FullScreenShotsActivity;
import com.gionee.aora.market.gui.details.view.IntroductionDownloadLayout;
import com.gionee.aora.market.gui.details.view.ScreenShotHorizontalScrollView;
import com.gionee.aora.market.gui.details.view.ScreenShotsImageView;
import com.gionee.aora.market.gui.evaluation.EvaluationDetailsActivity;
import com.gionee.aora.market.gui.exercise.ExerciseDetailsActivity;
import com.gionee.aora.market.gui.game.ClassifyDetailActivity;
import com.gionee.aora.market.gui.gift.GiftDetailsActivity;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.MarketListView;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.module.Comment;
import com.gionee.aora.market.module.EvaluatInfo;
import com.gionee.aora.market.module.GiftInfo;
import com.gionee.aora.market.net.BoutiqueGameNet;
import com.gionee.aora.market.net.CommentNet;
import com.gionee.aora.market.net.EvaluationNet;
import com.gionee.aora.market.net.ExerciseNet;
import com.gionee.aora.market.net.IntroductionDetailNet;
import com.gionee.aora.market.net.JokesNet;
import com.gionee.aora.market.net.MaylikeNet;
import com.gionee.aora.market.util.MarketAsyncTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

// Referenced classes of package com.gionee.aora.market.gui.details:
//            IntroductionCache, PermissionAdapter, IntroductionCommentAdapter, IntroductionVideoActivity,
//            IntroductionAllCommnentsActivity, IntroductionProblemActivity
public class IntroductionDetailActivity extends MarketBaseActivity implements android.view.View.OnClickListener {
    class CommentBroadcastReceiver extends BroadcastReceiver {
        CommentBroadcastReceiver() {
            super();
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            Comment comment = (Comment) intent.getSerializableExtra("COMMENTS");
            comments.add(0, comment);
            adapter.setComments(comments);
            adapter.notifyDataSetChanged();
        }
    }
    public class MyImageLoadingListener implements ImageLoadingListener {
        public MyImageLoadingListener() {
            super();
        }
        @Override
        public void onLoadingCancelled(String s, View view) {}
        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {}
        @Override
        public void onLoadingFailed(String s, View view, FailReason failreason) {
            if (!isFinishing()) {
                imageLoaderManager.displayImage(s, (ImageView) view, this);
                DLog.i("lung", new StringBuilder().append("url = ").append(s).toString());
                return;
            } else {
                DLog.i("lung", "finish ");
                return;
            }
        }
        @Override
        public void onLoadingStarted(String s, View view) {}
    }
    public static void startIntroductionActivity(Context context, AppInfo appinfo, DataCollectInfo datacollectinfo) {
        Intent intent = new Intent();
        intent.setAction("com.gionee.aora.market.action.GoSoftIntroductionActivity");
        intent.putExtra("APPINFO", appinfo);
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        context.startActivity(intent);
    }
    public static void startIntroductionActivityForPackageName(Context context, String s, DataCollectInfo datacollectinfo) {
        Intent intent = new Intent();
        intent.setFlags(0x10000000);
        intent.setAction("com.gionee.aora.market.action.GoSoftIntroductionActivity");
        intent.putExtra("PACKAGENAME", s);
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        context.startActivity(intent);
    }
    public static void startIntroductionActivityForSoftId(Context context, String s, DataCollectInfo datacollectinfo) {
        Intent intent = new Intent();
        intent.setAction("com.gionee.aora.market.action.GoSoftIntroductionActivity");
        intent.putExtra("SOFTID", s);
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        context.startActivity(intent);
    }
    public static final String ACTION = "com.gionee.aora.market.action.GoSoftIntroductionActivity";
    private final int LOAD_COMMENT_COUNT = 4;
    private final int LOAD_MAYBELIKE_DATA = 2;
    private final int LOAD__COMMENTS_DATA = 0;
    private final int LOAD__INTRODUCTION_DATA = 1;
    private final String TAG = "IntroductionDetailActivity";
    private IntroductionCommentAdapter adapter;
    private TextView allcomment;
    private AppInfo appInfo;
    private ImageView authenticateButton;
    private LinearLayout authenticateLayout;
    private TextView bannerDetailTextView;
    private RelativeLayout bannerLayout;
    private TextView bannerTitleTextView;
    private Button classfly;
    private Button classflyThree;
    private List comments;
    private TextView cpDeveloper;
    private TextView cpName;
    private TextView cpSize;
    private DataCollectInfo datainfo;
    private TextView description;
    private RelativeLayout description_more;
    private ImageView description_more_iv;
    private TextView description_more_tv;
    private TextView downloadRegion;
    private TextView flag;
    private View footerView;
    private View headerView;
    private ScreenShotHorizontalScrollView horizontalScrollView;
    private ImageView icon;
    private ImageLoaderManager imageLoaderManager;
    private IntroductionDownloadLayout introductionDownloadLayout;
    private ImageView introductionReport;
    private boolean isDatacollect;
    private boolean isOpenAuthenticate;
    private boolean isOpenDescription;
    private boolean ismorePermission;
    private TextView jokeTextView;
    private MarketListView listView;
    private ListView lvpermission;
    private PopupWindow mPopupWindow;
    private MarketPreferences mPreferences;
    private List maybeLikeList;
    private RelativeLayout morePermission;
    private ImageView morePermission_iv;
    private TextView morePermission_tv;
    private TextView notAd;
    private DisplayImageOptions options;
    private String packageName;
    private PermissionAdapter padapter;
    private List permission;
    private TextView plugin;
    private TextView proulgateTime;
    private CommentBroadcastReceiver receiver;
    private ArrayList screenShotsUrls;
    private String softId;
    private RatingBar star;
    private TextView version;
    public int vid;
    private Button videoButton;
    private TextView virus;
    public IntroductionDetailActivity() {
        int i = biz.AR.drawable.soft_introduction_default_screenshot;
        headerView = null;
        footerView = null;
        vid = 0;
        packageName = "";
        softId = "";
        datainfo = null;
        version = null;
        proulgateTime = null;
        downloadRegion = null;
        flag = null;
        notAd = null;
        plugin = null;
        virus = null;
        classfly = null;
        classflyThree = null;
        description = null;
        description_more = null;
        description_more_tv = null;
        description_more_iv = null;
        horizontalScrollView = null;
        screenShotsUrls = null;
        maybeLikeList = null;
        isOpenDescription = false;
        cpName = null;
        lvpermission = null;
        padapter = null;
        permission = null;
        morePermission = null;
        morePermission_tv = null;
        morePermission_iv = null;
        ismorePermission = false;
        cpDeveloper = null;
        icon = null;
        star = null;
        cpSize = null;
        imageLoaderManager = null;
        authenticateLayout = null;
        allcomment = null;
        authenticateButton = null;
        isOpenAuthenticate = false;
        isDatacollect = false;
        receiver = null;
        options = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory()
                .cacheOnDisc().build();
    }
    private void init() {
        mPreferences = MarketPreferences.getInstance(this);
        appInfo = (AppInfo) getIntent().getSerializableExtra("APPINFO");
        softId = getIntent().getStringExtra("SOFTID");
        packageName = getIntent().getStringExtra("PACKAGENAME");
        if (appInfo != null) {
            vid = appInfo.getvId();
            setDetailInfo();
        }
        if (IntroductionCache.isCacheAppInfo(appInfo)) {
            appInfo = (AppInfo) IntroductionCache.introductionCacheList.get(appInfo.getPackageName());
            setDetailInfo();
            return;
        }
        Integer ainteger[];
        try {
            if (getIntent().getData().getQueryParameter("id") != null) packageName = getIntent().getData().getQueryParameter("id");
        } catch (Exception exception) {
            DLog.e("IntroductionDetailActivity", new StringBuilder().append("getQueryParameter key = null ").append(exception).toString());
        }
        if (packageName != null) introductionDownloadLayout.setDada(packageName, datainfo);
        ainteger = new Integer[1];
        ainteger[0] = Integer.valueOf(1);
        doLoadData(ainteger);
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.introduction_main_layout);
        imageLoaderManager = ImageLoaderManager.getInstance();
        headerView = LayoutInflater.from(this).inflate(biz.AR.layout.introduction_header_new_layout, null, true);
        titleBarView.setTitle("\u5E94\u7528\u8BE6\u60C5");
        titleBarView.backImg.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                onBackPressed();
            }
        });
        comments = new ArrayList();
        listView = (MarketListView) findViewById(biz.AR.id.marketListView1);
        listView.setDividerHeight(0);
        adapter = new IntroductionCommentAdapter(this, comments);
        footerView = View.inflate(this, biz.AR.layout.introduction_footer_layout, null);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        listView.setHeaderDividersEnabled(false);
        listView.setAdapter(adapter);
        cpName = (TextView) headerView.findViewById(biz.AR.id.detail_app_name);
        cpSize = (TextView) headerView.findViewById(biz.AR.id.detail_app_size);
        icon = (ImageView) headerView.findViewById(biz.AR.id.detail_app_icon);
        star = (RatingBar) headerView.findViewById(biz.AR.id.detail_app_star);
        downloadRegion = (TextView) headerView.findViewById(biz.AR.id.detail_app_downloadRegion);
        lvpermission = (ListView) footerView.findViewById(biz.AR.id.detail_permission);
        permission = new ArrayList();
        padapter = new PermissionAdapter(permission, this);
        morePermission = (RelativeLayout) footerView.findViewById(biz.AR.id.detail_permission_morelayout);
        morePermission_tv = (TextView) footerView.findViewById(biz.AR.id.detail_permission_more);
        morePermission_iv = (ImageView) footerView.findViewById(biz.AR.id.detail_permission_more_iv);
        cpDeveloper = (TextView) footerView.findViewById(biz.AR.id.detail_developer);
        version = (TextView) footerView.findViewById(biz.AR.id.detail_version);
        proulgateTime = (TextView) footerView.findViewById(biz.AR.id.detail_proulgate_time);
        introductionReport = (ImageView) footerView.findViewById(biz.AR.id.detail_report);
        flag = (TextView) headerView.findViewById(biz.AR.id.detail_no_flag);
        notAd = (TextView) headerView.findViewById(biz.AR.id.detail_no_noad);
        plugin = (TextView) headerView.findViewById(biz.AR.id.detail_no_plugin);
        virus = (TextView) headerView.findViewById(biz.AR.id.detail_no_virus);
        classfly = (Button) headerView.findViewById(biz.AR.id.detail_sub_1);
        classflyThree = (Button) headerView.findViewById(biz.AR.id.detail_sub_2);
        description = (TextView) headerView.findViewById(biz.AR.id.detail_app_description);
        description_more = (RelativeLayout) headerView.findViewById(biz.AR.id.detail_description_morelayout);
        description_more_tv = (TextView) headerView.findViewById(biz.AR.id.detail_description_more);
        description_more_iv = (ImageView) headerView.findViewById(biz.AR.id.detail_description_more_iv);
        horizontalScrollView = (ScreenShotHorizontalScrollView) headerView.findViewById(biz.AR.id.detail_screen_shot_horizontalScrollView);
        introductionDownloadLayout = (IntroductionDownloadLayout) findViewById(biz.AR.id.DownloadLayout);
        authenticateButton = (ImageView) headerView.findViewById(biz.AR.id.detail_authenticate_flag);
        authenticateLayout = (LinearLayout) headerView.findViewById(biz.AR.id.detail_authenticate_layout);
        allcomment = (TextView) headerView.findViewById(biz.AR.id.detail_comment_all);
        allcomment.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent();
                intent.setClass(IntroductionDetailActivity.this, IntroductionAllCommnentsActivity.class);
                intent.putExtra("DATACOLLECT_INFO", datainfo.clone());
                intent.putExtra("APPINFO", appInfo);
                startActivity(intent);
            }
        });
        authenticateButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                showMoreAuthenticateLayoutDescription();
            }
        });
        introductionReport.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (appInfo != null && appInfo.getSoftId() != null) {
                    Intent intent = new Intent(IntroductionDetailActivity.this, IntroductionProblemActivity.class);
                    intent.putExtra("APPINFO", appInfo.getSoftId());
                    startActivity(intent);
                }
            }
        });
        description_more.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                showMoreDescription();
            }
        });
        classfly.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (appInfo != null) startClassify(IntroductionDetailActivity.this, appInfo, 0);
            }
        });
        classflyThree.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (appInfo != null) {
                    int i = 0;
                    int j = 0;
                    for (; i < appInfo.getClassifyThree().size(); i++)
                        if (new StringBuilder().append(appInfo.getClassifyThreeId()).append("").toString().equals(appInfo.getClassifyThree().get(i).getSortId())) j = i;
                    startClassify(IntroductionDetailActivity.this, appInfo, j + 1);
                }
            }
        });
        morePermission.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                showMorePermission();
            }
        });
        if (screenShotsUrls == null) setPreSetScreenShots();
        View view = View.inflate(this, biz.AR.layout.dialog_joke, null);
        jokeTextView = (TextView) view.findViewById(biz.AR.id.jokeText);
        ((CheckBox) view.findViewById(biz.AR.id.joke_checkbox)).setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag1) {
                MarketPreferences marketpreferences = mPreferences;
                boolean flag2;
                if (!flag1) flag2 = true;
                else flag2 = false;
                marketpreferences.setShowJokes(Boolean.valueOf(flag2));
            }
        });
        ((Button) view.findViewById(biz.AR.id.closeDialog)).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if (mPopupWindow != null) mPopupWindow.dismiss();
            }
        });
        mPopupWindow = new PopupWindow(view, -1, -2, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        introductionDownloadLayout.setAddToDownloadListener(new com.gionee.aora.market.gui.details.view.IntroductionDownloadLayout.SetAddToDownload() {
            @Override
            public void addToDownload() {
                if (mPreferences.getShowJokes().booleanValue()) showJokes();
            }
        });
        bannerTitleTextView = (TextView) headerView.findViewById(biz.AR.id.detail_banner_title);
        bannerDetailTextView = (TextView) headerView.findViewById(biz.AR.id.detail_banner_detail);
        bannerLayout = (RelativeLayout) headerView.findViewById(biz.AR.id.detail_banner_layout);
        videoButton = (Button) headerView.findViewById(biz.AR.id.detail_videoButton);
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        boolean flag1;
        flag1 = true;
        if (ainteger.length < 0) return false;
        switch (ainteger[0].intValue()) {
            case 0://L2_L2:
                if (appInfo != null && appInfo.getSoftId() != null) comments = CommentNet.getCommentList(appInfo.getSoftId(), ainteger[1].intValue(), 4);
                if (comments == null) flag1 = false;
                break;
            case 1://L3_L3:
                if (appInfo == null) {
                    if (packageName != null && !packageName.equals("")) {
                        DLog.d("IntroductionDetailActivity", new StringBuilder().append("appInfo == null ,getInstroductionDetailForPackageName ").append(packageName).toString());
                        appInfo = IntroductionDetailNet.getInstroductionDetailForPackageName(packageName);
                    } else if (softId != null && !softId.equals("")) {
                        DLog.d("IntroductionDetailActivity", new StringBuilder().append("appInfo == null ,getInstroductionDetailForSoftId ").append(softId).toString());
                        appInfo = IntroductionDetailNet.getInstroductionDetailForSoftId(softId);
                    } else {
                        appInfo = null;
                    }
                } else if (appInfo.getSoftId() != null && !appInfo.getSoftId().equals("")) {
                    DLog.d("IntroductionDetailActivity", new StringBuilder().append("appInfo != null ,getInstroductionDetailForSoftId ").append(appInfo.getSoftId()).toString());
                    appInfo = IntroductionDetailNet.getInstroductionDetailForSoftId(appInfo.getSoftId());
                } else if (appInfo.getPackageName() != null && !appInfo.getPackageName().equals("")) {
                    DLog.d("IntroductionDetailActivity", new StringBuilder().append("appInfo != null ,getInstroductionDetailForPackageName ").append(appInfo.getPackageName())
                            .toString());
                    appInfo = IntroductionDetailNet.getInstroductionDetailForPackageName(appInfo.getPackageName());
                } else {
                    appInfo = null;
                }
                if (appInfo == null) flag1 = false;
                break;
            case 2://L4_L4:
                maybeLikeList = MaylikeNet.getMayBeLikeList(appInfo.getPackageName());
                if (maybeLikeList == null) flag1 = false;
                break;
        }
        return flag1;
    }
    @Override
    public void onBackPressed() {
        IntroductionCache.introductionCount = -1 + IntroductionCache.introductionCount;
        super.onBackPressed();
    }
    @Override
    public void onClick(View view) {
        int i = ((Integer) view.getTag()).intValue();
        if (appInfo.getScreenShotsUrl() != null && appInfo.getScreenShotsUrl().size() != 0) {
            Intent intent = new Intent();
            intent.setClass(this, FullScreenShotsActivity.class);
            intent.putExtra("POSITION", i);
            intent.putStringArrayListExtra("SCREENSHOTSURLS", appInfo.getScreenShotsUrl());
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        datainfo = DataCollectManager.getCollectInfo(this);
        datainfo.setAction("9");
        init();
        IntroductionCache.introductionCount = 1 + IntroductionCache.introductionCount;
        if (IntroductionCache.introductionCount > IntroductionCache.MAX_COUNT) titleBarView.searchImg.setVisibility(8);
        receiver = new CommentBroadcastReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("IntroductionCommentActivy_ADD_COMMENT");
        registerReceiver(receiver, intentfilter);
    }
    @Override
    public void onDestroy() {
        if (introductionDownloadLayout != null) introductionDownloadLayout.onDestory();
        if (receiver != null) unregisterReceiver(receiver);
        super.onDestroy();
    }
    @Override
    protected void refreshView(boolean flag1, Integer ainteger[]) {
        if (ainteger.length < 0) return;
        switch (ainteger[0].intValue()) {
            default:
                return;
            case 0: // '\0'
                if (flag1) {
                    adapter.setComments(comments);
                    adapter.notifyDataSetChanged();
                    return;
                } else {
                    DLog.e("TAG", "LOAD__COMMENTS_DATA = null");
                    return;
                }
            case 1: // '\001'
                break;
        }
        if (flag1) {
            loadingView.setVisibility(8);
            IntroductionCache.introductionCacheList.put(appInfo.getPackageName(), appInfo);
            setDetailInfo();
            return;
        } else {
            showErrorView();
            return;
        }
    }
    private void sentDataCollection() {
        if (!isDatacollect) {
            if (appInfo != null) {
                if (vid != 0) {
                    DataCollectInfo datacollectinfo1 = datainfo;
                    String as1[] = new String[6];
                    as1[0] = "app_id";
                    as1[1] = appInfo.getSoftId();
                    as1[2] = "cpversion";
                    as1[3] = new StringBuilder().append(appInfo.getUpdateVersionName()).append("").toString();
                    as1[4] = "vid";
                    as1[5] = new StringBuilder().append(vid).append("").toString();
                    DataCollectManager.addRecord(datacollectinfo1, as1);
                } else {
                    DataCollectInfo datacollectinfo = datainfo;
                    String as[] = new String[4];
                    as[0] = "app_id";
                    as[1] = appInfo.getSoftId();
                    as[2] = "cpversion";
                    as[3] = new StringBuilder().append(appInfo.getUpdateVersionName()).append("").toString();
                    DataCollectManager.addRecord(datacollectinfo, as);
                }
            } else {
                DataCollectManager.addRecord(datainfo, new String[0]);
            }
            isDatacollect = true;
        }
    }
    private void setDetailInfo() {
        sentDataCollection();
        if (appInfo.getSoftId() != null && !appInfo.getSoftId().equals("")) {
            Integer ainteger[] = new Integer[2];
            ainteger[0] = Integer.valueOf(0);
            ainteger[1] = Integer.valueOf(0);
            doLoadData(ainteger);
        }
        if (appInfo.getScreenShotsUrlNotHD() != null) {
            screenShotsUrls = appInfo.getScreenShotsUrlNotHD();
            setScreenShotsData();
        }
        TextView textview = version;
        Resources resources = getResources();
        int i = biz.AR.string.introduction_cp_version_text;
        Object aobj[] = new Object[1];
        aobj[0] = appInfo.getUpdateVersionName();
        textview.setText(resources.getString(i, aobj));
        TextView textview1 = proulgateTime;
        Resources resources1 = getResources();
        int j = biz.AR.string.introduction_proulgate_time_text;
        Object aobj1[] = new Object[1];
        aobj1[0] = appInfo.getProulgateTime();
        textview1.setText(resources1.getString(j, aobj1));
        TextView textview2 = downloadRegion;
        Resources resources2 = getResources();
        int k = biz.AR.string.introduction_download_region_text;
        Object aobj2[] = new Object[1];
        aobj2[0] = appInfo.getDownload_region();
        textview2.setText(resources2.getString(k, aobj2));
        description.setText(appInfo.getDescription());
        TextView textview3 = cpDeveloper;
        Resources resources3 = getResources();
        int l = biz.AR.string.introduction_cp_developer;
        Object aobj3[] = new Object[1];
        aobj3[0] = appInfo.getDeveloper();
        textview3.setText(resources3.getString(l, aobj3));
        cpName.setText(appInfo.getName());
        cpSize.setText(appInfo.getSize());
        imageLoaderManager.displayImage(appInfo.getIconUrl(), icon, imageLoaderManager.getImageLoaderOptions());
        star.setRating(appInfo.getAppStars());
        allcomment.setText(new StringBuilder().append("\u5168\u90E8\u8BC4\u8BBA(").append(appInfo.getComment_count()).append(")").toString());
        if (appInfo.getPermission() != null) {
            padapter.setPermission(appInfo.getPermission());
            lvpermission.setAdapter(padapter);
            padapter.notifyDataSetChanged();
        }
        if (appInfo.getClassifyId() != 0) {
            classfly.setText(appInfo.getClassifyName());
            classfly.setVisibility(0);
        } else {
            classfly.setVisibility(8);
        }
        if (appInfo.getClassifyThreeId() != 0) {
            classflyThree.setText(appInfo.getClassifyThreeName());
            classflyThree.setVisibility(0);
        } else {
            classflyThree.setVisibility(8);
        }
        if (appInfo.isOfficial()) flag.setVisibility(0);
        else flag.setVisibility(8);
        if (appInfo.isNotAD()) notAd.setVisibility(0);
        else notAd.setVisibility(8);
        if (appInfo.isVirus()) virus.setVisibility(8);
        else virus.setVisibility(0);
        if (appInfo.isPlugin()) plugin.setVisibility(8);
        else plugin.setVisibility(0);
        if (appInfo.getAuthenticateItems() != null) {
            android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(-1, -2);
            layoutparams.topMargin = (int) (0.5F + 5F * getResources().getDisplayMetrics().density);
            int i1 = (int) getResources().getDimension(biz.AR.dimen.dip16);
            android.widget.LinearLayout.LayoutParams layoutparams1 = new android.widget.LinearLayout.LayoutParams(i1, i1);
            layoutparams1.leftMargin = (int) (0.5F + 6.6F * getResources().getDisplayMetrics().density);
            layoutparams1.rightMargin = (int) (0.5F + 6.6F * getResources().getDisplayMetrics().density);
            android.widget.LinearLayout.LayoutParams layoutparams2 = new android.widget.LinearLayout.LayoutParams(-2, -2);
            layoutparams2.gravity = 16;
            for (int j1 = 0; j1 < appInfo.getAuthenticateItems().size(); j1++) {
                LinearLayout linearlayout = new LinearLayout(this);
                linearlayout.setOrientation(0);
                linearlayout.setLayoutParams(layoutparams);
                ImageView imageview = new ImageView(this);
                imageview.setLayoutParams(layoutparams1);
                TextView textview4 = new TextView(this);
                textview4.setTextColor(Color.parseColor("#555555"));
                textview4.setTextSize(12F);
                textview4.setText(appInfo.getAuthenticateItems().get(j1).getName());
                textview4.setLayoutParams(layoutparams2);
                linearlayout.addView(imageview);
                linearlayout.addView(textview4);
                authenticateLayout.addView(linearlayout);
                imageLoaderManager.displayImage(appInfo.getAuthenticateItems().get(j1).getIconUrl(), imageview, imageLoaderManager.getImageLoaderOptions());
                authenticateButton.setVisibility(0);
            }
        } else {
            authenticateButton.setVisibility(8);
        }
        appInfo.setvId(vid);
        introductionDownloadLayout.setDada(appInfo, datainfo);
        introductionDownloadLayout.setPriseCount(appInfo, appInfo.getPraiseCount());
        if (!(appInfo.getCpAction() == null || appInfo.getCpAction().equals("") || appInfo.getCpAction().equals("[]"))) {
            JSONObject jsonobject;
            final DataCollectInfo tmp;
            int k1;
            bannerLayout.setVisibility(0);
            try {
                jsonobject = new JSONObject(appInfo.getCpAction());
                bannerDetailTextView.setText(jsonobject.getString("ACTION_CONTENT"));
                bannerTitleTextView.setText(jsonobject.getString("ACTION_NAME"));
                tmp = datainfo.clone();
                tmp.setModel("4");
                k1 = Integer.parseInt(jsonobject.getString("ACTION_TYPE"));
                switch (k1) {
                    case 0://L4_L4:
                        try {
                            final EvaluatInfo exercise = ExerciseNet.getExerciseInfo(jsonobject);
                            bannerLayout.setOnClickListener(new android.view.View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    tmp.setType("5");
                                    ExerciseDetailsActivity.startExerciseDetailsActivity(IntroductionDetailActivity.this, exercise, tmp);
                                }
                            });
                        } catch (JSONException jsonexception) {
                            DLog.e("IntroductionDetailActivity", new StringBuilder().append(" cp action error =").append(jsonexception).toString());
                        }
                        break;
                    case 1://L5_L5:
                        final EvaluatInfo evaluatInfo = EvaluationNet.getEvaluatInfo(jsonobject);
                        bannerLayout.setOnClickListener(new android.view.View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tmp.setType("4");
                                EvaluationDetailsActivity.startEvaluationDetailsActivity(IntroductionDetailActivity.this, evaluatInfo, tmp);
                            }
                        });
                        break;
                    case 2://L6
                        final GiftInfo giftInfo = BoutiqueGameNet.getGiftInfo(jsonobject);
                        bannerLayout.setOnClickListener(new android.view.View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tmp.setType("12");
                                GiftDetailsActivity.startGiftDetailsActivity(IntroductionDetailActivity.this, giftInfo, tmp);
                            }
                        });
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //_L2:
        if (appInfo.getVideoUrl() != null && !appInfo.getVideoUrl().equals("") && Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
            showVideoButton();
        } else {
            videoButton.setVisibility(8);
        }
        return;
    }
    private void setPreSetScreenShots() {
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(0);
        int i = 0;
        while (i < 3) {
            ScreenShotsImageView screenshotsimageview = new ScreenShotsImageView(this);
            screenshotsimageview.setLayoutParams(new android.widget.AbsListView.LayoutParams((int) getResources().getDimension(biz.AR.dimen.screen_shot_img_width),
                    (int) getResources().getDimension(biz.AR.dimen.screen_shot_img_height)));
            int j = (int) getResources().getDimension(biz.AR.dimen.dip7);
            int k = (int) getResources().getDimension(biz.AR.dimen.dip10);
            if (i != 2) screenshotsimageview.setPadding(j, k, 0, k);
            else screenshotsimageview.setPadding(j, k, j, k);
            screenshotsimageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            screenshotsimageview.setImageDrawable(getResources().getDrawable(biz.AR.drawable.soft_introduction_default_screenshot));
            linearlayout.addView(screenshotsimageview);
            i++;
        }
        horizontalScrollView.removeAllViews();
        horizontalScrollView.addView(linearlayout);
    }
    private void setScreenShotsData() {
        LinearLayout linearlayout = new LinearLayout(this);
        linearlayout.setOrientation(0);
        if (screenShotsUrls != null) {
            int i = 0;
            while (i < screenShotsUrls.size()) {
                ScreenShotsImageView screenshotsimageview = new ScreenShotsImageView(this);
                screenshotsimageview.setLayoutParams(new android.widget.AbsListView.LayoutParams((int) getResources().getDimension(biz.AR.dimen.screen_shot_img_width),
                        (int) getResources().getDimension(biz.AR.dimen.screen_shot_img_height)));
                int j = (int) getResources().getDimension(biz.AR.dimen.dip7);
                int k = (int) getResources().getDimension(biz.AR.dimen.dip10);
                if (i == -1 + screenShotsUrls.size() && screenShotsUrls.size() >= 3) screenshotsimageview.setPadding(j, k, j, k);
                else screenshotsimageview.setPadding(j, k, 0, k);
                screenshotsimageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
                screenshotsimageview.setOnClickListener(this);
                screenshotsimageview.setTag(Integer.valueOf(i));
                screenshotsimageview.setImageDrawable(getResources().getDrawable(biz.AR.drawable.soft_introduction_default_screenshot));
                imageLoaderManager.displayImage((String) screenShotsUrls.get(i), screenshotsimageview, options);
                linearlayout.addView(screenshotsimageview);
                i++;
            }
            horizontalScrollView.removeAllViews();
            horizontalScrollView.addView(linearlayout);
        }
    }
    protected MarketAsyncTask showJokes() {
        MarketAsyncTask marketasynctask = new MarketAsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void avoid[]) {
                return JokesNet.getJokes();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null && !isDestory() && !s.equals("")) {
                    jokeTextView.setText(s);
                    mPopupWindow.showAsDropDown(introductionDownloadLayout, 0, -introductionDownloadLayout.getHeight()
                            - (int) (0.5F + 201F * getResources().getDisplayMetrics().density));
                }
            }
        };
        marketasynctask.doExecutor(new Void[0]);
        return marketasynctask;
    }
    private void showMoreAuthenticateLayoutDescription() {
        if (!isOpenAuthenticate) {
            authenticateButton.setImageDrawable(getResources().getDrawable(biz.AR.drawable.soft_introduction_include_up));
            authenticateLayout.setVisibility(0);
            authenticateLayout.invalidate();
            isOpenAuthenticate = true;
            return;
        } else {
            authenticateButton.setImageDrawable(getResources().getDrawable(biz.AR.drawable.soft_introduction_include_down));
            authenticateLayout.setVisibility(8);
            authenticateLayout.setBackgroundResource(0);
            authenticateLayout.invalidate();
            isOpenAuthenticate = false;
            return;
        }
    }
    private void showMoreDescription() {
        if (!isOpenDescription) {
            description.setMaxLines(100);
            description.invalidate();
            isOpenDescription = true;
            description_more_tv.setText("\u6536\u8D77");
            description_more_iv.setImageResource(biz.AR.drawable.decrible_more_up);
            return;
        } else {
            description.setMaxLines(3);
            description.invalidate();
            isOpenDescription = false;
            description_more_tv.setText("\u66F4\u591A\u63CF\u8FF0");
            description_more_iv.setImageResource(biz.AR.drawable.decrible_more_down);
            return;
        }
    }
    private void showMorePermission() {
        if (!ismorePermission) {
            ListAdapter listadapter = lvpermission.getAdapter();
            if (listadapter == null) {
                return;
            } else {
                View view = listadapter.getView(0, null, lvpermission);
                view.measure(0, 0);
                int i = view.getMeasuredHeight() * listadapter.getCount();
                android.view.ViewGroup.LayoutParams layoutparams1 = lvpermission.getLayoutParams();
                layoutparams1.height = i + (int) getResources().getDimension(biz.AR.dimen.dip12) * listadapter.getCount();
                lvpermission.setLayoutParams(layoutparams1);
                padapter.notifyDataSetChanged();
                ismorePermission = true;
                morePermission_tv.setText("\u6536\u8D77");
                morePermission_iv.setImageResource(biz.AR.drawable.decrible_more_up);
                return;
            }
        } else {
            android.view.ViewGroup.LayoutParams layoutparams = lvpermission.getLayoutParams();
            layoutparams.height = (int) getResources().getDimension(biz.AR.dimen.dip54);
            lvpermission.setLayoutParams(layoutparams);
            padapter.notifyDataSetChanged();
            ismorePermission = false;
            morePermission_tv.setText("\u66F4\u591A\u63CF\u8FF0");
            morePermission_iv.setImageResource(biz.AR.drawable.decrible_more_down);
            return;
        }
    }
    private void showVideoButton() {
        videoButton.setVisibility(0);
        ScaleAnimation scaleanimation = new ScaleAnimation(0.7F, 1.0F, 0.7F, 1.0F, 1, 0.5F, 1, 0.5F);
        scaleanimation.setDuration(500L);
        videoButton.setAnimation(scaleanimation);
        videoButton.startAnimation(scaleanimation);
        videoButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetUtil.getNetMode(IntroductionDetailActivity.this).equals("WIFI")) {
                    Intent intent = new Intent();
                    intent.setClass(IntroductionDetailActivity.this, IntroductionVideoActivity.class);
                    intent.putExtra("DATACOLLECT_INFO", datainfo);
                    intent.putExtra("VIDEO_URL", appInfo.getVideoUrl());
                    intent.putExtra("vid", vid);
                    intent.putExtra("APPID", appInfo.getSoftId());
                    startActivity(intent);
                    return;
                } else {
                    MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(IntroductionDetailActivity.this);
                    marketfloatedialogbuilder
                    .setMessage("\u4E3B\u4E0A\uFF0C\u60A8\u73B0\u5728\u5904\u4E8E\u975EWLAN\u7F51\u7EDC\uFF0C\u64AD\u653E\u89C6\u9891\u4F1A\u8017\u8D39\u60A8\u8F83\u591A\u6D41\u91CF\u54E6\uFF0C\u7EE7\u7EED\u64AD\u653E\u89C6\u9891\u5417\uFF1F ");
                    marketfloatedialogbuilder.setCancelable(true);
                    marketfloatedialogbuilder.setTitle("\u6E29\u99A8\u63D0\u793A");
                    marketfloatedialogbuilder.setLeftButton("\u4E0D\u770B\u4E86", new android.view.View.OnClickListener() {
                        @Override
                        public void onClick(View view) {}
                    });
                    marketfloatedialogbuilder.setRightButton("\u7EE7\u7EED\u64AD\u653E", new android.view.View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(IntroductionDetailActivity.this, IntroductionVideoActivity.class);
                            intent.putExtra("VIDEO_URL", appInfo.getVideoUrl());
                            intent.putExtra("vid", vid);
                            intent.putExtra("APPID", appInfo.getSoftId());
                            intent.putExtra("DATACOLLECT_INFO", datainfo);
                            startActivity(intent);
                        }
                    });
                    marketfloatedialogbuilder.show();
                    return;
                }
            }
        });
    }
    public void startClassify(Context context, AppInfo appinfo, int i) {
        boolean flag1 = true;
        String as[] = new String[1 + appinfo.getClassifyThree().size()];
        String as1[] = new String[1 + appinfo.getClassifyThree().size()];
        as[0] = new StringBuilder().append(appinfo.getClassifyId()).append("").toString();
        as1[0] = "\u5168\u90E8";
        for (int j = flag1 ? 1 : 0; j < 1 + appinfo.getClassifyThree().size(); j++) {
            as[j] = appinfo.getClassifyThree().get(j - 1).getSortId();
            as1[j] = appinfo.getClassifyThree().get(j - 1).getSortName();
        }
        DataCollectInfo datacollectinfo = datainfo.clone();
        datacollectinfo.setType("");
        datacollectinfo.setAction("");
        datacollectinfo.setPosition("3");
        datacollectinfo.setModel("3");
        Intent intent = new Intent(context, ClassifyDetailActivity.class);
        intent.putExtra("defaultPage", i);
        intent.putExtra("CLASSIFY_ID", as);
        intent.putExtra("CLASSIFY_NAME", as1);
        if (appinfo.getClassify() != 1) flag1 = false;
        intent.putExtra("IS_GAME", flag1);
        intent.putExtra("TITLE", appinfo.getClassifyName());
        intent.putExtra("DATACOLLECT_INFO", datacollectinfo);
        context.startActivity(intent);
    }
    @Override
    protected void tryAgain() {
        super.tryAgain();
        init();
    }
}
