// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.main;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.util.DLog;
import com.aora.base.util.NetUtil;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.control.UserManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.login.LoginActivity;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.control.NotLaunchManager;
import com.gionee.aora.market.control.QRCodeManager;
import com.gionee.aora.market.control.SoftWareUpdateManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.control.UpdateManager;
import com.gionee.aora.market.gui.call.BoundPhoneActivity;
import com.gionee.aora.market.gui.call.FreePhoneActivity;
import com.gionee.aora.market.gui.home.RecommendFragment;
import com.gionee.aora.market.gui.manager.MarketSettingActivity;
import com.gionee.aora.market.gui.manager.SoftInstalledActivity;
import com.gionee.aora.market.gui.manager.SoftwareUpdateActivity;
import com.gionee.aora.market.gui.recommend.PersonalRecommandActivity;
import com.gionee.aora.market.gui.view.MainTitleView;
import com.gionee.aora.market.gui.view.NetErrorDialog;
import com.gionee.aora.market.gui.view.dragview.MyDragLayout;
import com.gionee.aora.market.gui.wifi.InitWifiManager;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.personalRecommendNet;
import com.gionee.aora.market.util.MarketAsyncTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainTabActivity extends FragmentActivity implements android.widget.RadioGroup.OnCheckedChangeListener, android.view.View.OnClickListener {
    public static interface ChangePageListener {
        public abstract void changePage(MyDragLayout mydraglayout);
    }
    public class LoginSucessBroadcastReceiver extends BroadcastReceiver {
        public LoginSucessBroadcastReceiver() {
            super();
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            label0: {
            if (intent.hasExtra("SKIP_ACTIVITY") && intent.getStringExtra("SKIP_ACTIVITY").equals("FREE_CALL")) {
                UserInfo userinfo1 = UserStorage.getDefaultUserInfo(MainTabActivity.this);
                preferences.setShowNewCall(false);
                new_call.setVisibility(8);
                if (userinfo1.getFreePhone() == null || userinfo1.getFreePhone().equals("")) break label0;
                startActivity(new Intent(MainTabActivity.this, FreePhoneActivity.class));
            }
            return;
        }
        startActivity(new Intent(MainTabActivity.this, BoundPhoneActivity.class));
        }
    }
    private class SoftmanagerReceiver extends BroadcastReceiver {
        private SoftmanagerReceiver() {
            super();
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            DLog.i(TAG, new StringBuilder().append("SoftwareUpdateActivity.SoftmanagerReceiver,onReceive,action------>").append(intent.getAction()).toString());
            setLeftUpdateData();
        }
    }
    public static interface StartToLoadData {
        public abstract void start();
    }
    private class WakupPersonalRecommendTask extends MarketAsyncTask<Integer, ArrayList, ArrayList> {
        private WakupPersonalRecommendTask() {
            super();
        }
        @Override
        protected ArrayList doInBackground(Integer ainteger[]) {
            return personalRecommendNet.getWakupPersonalAppInfos();
        }
        @Override
        protected void onPostExecute(ArrayList arraylist) {
            super.onPostExecute(arraylist);
            if (arraylist != null) {
                Intent intent = new Intent(MainTabActivity.this, PersonalRecommandActivity.class);
                intent.putExtra("data", arraylist);
                startActivity(intent);
            }
        }
    }
    public static MyDragLayout actionsContentView;
    public static int current_falg = 2;
    public static boolean isShowLenjoy = false;
    private String TAG;
    private RelativeLayout baseLeftLay1;
    private ScrollView base_left;
    private String curSearchHint;
    private Dialog dialog;
    private DownloadManager downloadManager;
    private long exitTime;
    private int height;
    private ImageLoader imageLoader;
    private LinearLayout leftHaveUpdateLay;
    private LinearLayout leftNoUpdateLay;
    private Button leftUpdateAllBtn;
    private TextView leftUpdateCountText;
    private ImageView leftUpdateIcon1;
    private ImageView leftUpdateIcon2;
    private ImageView leftUpdateIcon3;
    private ImageView leftUpdateIcon4;
    private ImageView leftUpdateMoreImg;
    private LoginSucessBroadcastReceiver loginReceiver;
    private FragmentManager mFragmentManager;
    private Fragment mNowShowFragment;
    public RadioGroup mTabRadioGroup;
    public MainTitleView mainTitleView;
    private ImageView new_call;
    private DisplayImageOptions options;
    private MarketPreferences preferences;
    public RadioButton rb[];
    public RecommendFragment recommendNewFragment;
    private SoftmanagerReceiver softmanagerReceiver;
    private SoftwareManager softwareManager;
    private SoftWareUpdateManager updateManager;
    private UserInfo userinfo;
    public MainTabActivity() {
        TAG = "MainTabActivity";
        exitTime = 0L;
        dialog = null;
        rb = new RadioButton[5];
        curSearchHint = "";
    }
    public void changePage(Fragment fragment) {
        if (!fragment.isAdded()) {
            //L1_L1:
            mFragmentManager.beginTransaction().add(biz.AR.id.mainLayout, fragment).commitAllowingStateLoss();
            ((StartToLoadData) fragment).start();
            setNowFragment(fragment);
        } else {
            //L2_L2:
            if (fragment.isHidden()) {
                mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
                setNowFragment(fragment);
            }
        }
        ((ChangePageListener) fragment).changePage(actionsContentView);
        return;
    }
    private void doAllUpdate() {
        for (Object element : softwareManager.getUpdate_softwaresMap().entrySet()) {
            AppInfo appinfo = (AppInfo) ((java.util.Map.Entry) element).getValue();
            DownloadInfo downloadinfo = updateManager.checkHadDownloadAPkFile(appinfo.getPackageName());
            if (downloadinfo != null) softwareManager.installApp(downloadManager, downloadinfo);
            else if (!appinfo.isSameSign()) {
                showDialog(appinfo);
            } else {
                DownloadInfo downloadinfo1 = downloadManager.queryDownload(appinfo.getPackageName());
                if (downloadinfo1 == null) downloadManager.addDownload(appinfo.toDownloadInfo());
                else if (downloadinfo1.getState() == 3) softwareManager.installApp(downloadManager, downloadinfo1);
                else downloadManager.addDownload(downloadinfo1);
            }
        }
    }
    private void exit() {
        DLog.e(TAG, "MainActivity@onOptionsItemSelected,id=Menu.FIRST");
        NotLaunchManager.addAlarm(this);
        try {
            DownloadManager.shareInstance().destory();
        } catch (Exception exception) {
            DLog.e(TAG, new StringBuilder().append("MainActivity@DownloadManager.shareInstance().destory()#Exception=").append(exception).toString());
        }
        try {
            finish();
        } catch (Exception exception1) {
            DLog.e(TAG, new StringBuilder().append("MainActivity@finish()#Exception=").append(exception1).toString());
        }
        try {
            Process.killProcess(Process.myPid());
            System.exit(0);
            return;
        } catch (Exception exception2) {
            DLog.e(TAG, new StringBuilder().append("MainActivity@System.exit(0)#Exception=").append(exception2).toString());
        }
    }
    private String getHint() {
        String as[] = preferences.getSearchHint();
        return as[Math.abs((int) (Math.random() * as.length))];
    }
    private DisplayImageOptions getImageLoaderOption() {
        return new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().showStubImage(biz.AR.drawable.cp_defaulf).cacheInMemory().build();
    }
    private void init() {
        if (android.os.Build.VERSION.SDK_INT > 11) getWindow().setFlags(0x1000000, 0x1000000);
        softwareManager = SoftwareManager.getInstace();
        downloadManager = DownloadManager.shareInstance();
        updateManager = SoftWareUpdateManager.getInstance();
        imageLoader = ImageLoader.getInstance();
        options = getImageLoaderOption();
        mFragmentManager = getSupportFragmentManager();
        mTabRadioGroup = (RadioGroup) findViewById(biz.AR.id.tabGroup);
        base_left = (ScrollView) findViewById(biz.AR.id.base_left);
        base_left.setPadding(0, height, 0, height);
        actionsContentView = (MyDragLayout) findViewById(biz.AR.id.actionsContentView);
        actionsContentView.setDragListener(new com.gionee.aora.market.gui.view.dragview.DragLayout.DragListener() {
            @Override
            public void onClose() {
                MainTabActivity.actionsContentView.setCanScroll(true);
            }
            @Override
            public void onDrag(float f) {}
            @Override
            public void onOpen() {
                MainTabActivity.actionsContentView.setCanScroll(false);
                MainTabActivity.actionsContentView.setOpening(false);
            }
        });
        recommendNewFragment = new RecommendFragment();
        mTabRadioGroup.setOnCheckedChangeListener(this);
        baseLeftLay1 = (RelativeLayout) findViewById(biz.AR.id.base_left_1);
        new_call = (ImageView) findViewById(biz.AR.id.left_0_new);
        IntentFilter intentfilter;
        if (preferences.getShowNewCall().booleanValue()) new_call.setVisibility(0);
        else new_call.setVisibility(8);
        baseLeftLay1.setOnClickListener(this);
        findViewById(biz.AR.id.base_left_0).setOnClickListener(this);
        findViewById(biz.AR.id.base_left_2).setOnClickListener(this);
        findViewById(biz.AR.id.base_left_3).setOnClickListener(this);
        findViewById(biz.AR.id.base_left_4).setOnClickListener(this);
        findViewById(biz.AR.id.base_left_5).setOnClickListener(this);
        findViewById(biz.AR.id.base_left_6).setOnClickListener(this);
        rb[0] = (RadioButton) findViewById(biz.AR.id.radio0);
        rb[1] = (RadioButton) findViewById(biz.AR.id.radio1);
        rb[2] = (RadioButton) findViewById(biz.AR.id.radio2);
        rb[3] = (RadioButton) findViewById(biz.AR.id.radio3);
        rb[4] = (RadioButton) findViewById(biz.AR.id.radio4);
        mainTitleView = (MainTitleView) findViewById(biz.AR.id.main_titlebar);
        current_falg = getIntent().getIntExtra("MAIN_FALG", 2);
        if (current_falg != 2) rb[current_falg].setChecked(true);
        else changePage(recommendNewFragment);
        leftNoUpdateLay = (LinearLayout) findViewById(biz.AR.id.base_left_no_update_lay);
        leftHaveUpdateLay = (LinearLayout) findViewById(biz.AR.id.base_left_have_update_lay);
        leftUpdateIcon1 = (ImageView) findViewById(biz.AR.id.base_left_1_bottom_icon1);
        leftUpdateIcon2 = (ImageView) findViewById(biz.AR.id.base_left_1_bottom_icon2);
        leftUpdateIcon3 = (ImageView) findViewById(biz.AR.id.base_left_1_bottom_icon3);
        leftUpdateIcon4 = (ImageView) findViewById(biz.AR.id.base_left_1_bottom_icon4);
        leftUpdateCountText = (TextView) findViewById(biz.AR.id.base_left_1_mid_text);
        leftUpdateMoreImg = (ImageView) findViewById(biz.AR.id.base_left_1_bottom_more_text);
        leftUpdateAllBtn = (Button) findViewById(biz.AR.id.base_left_1_mid_update_all_btn);
        leftUpdateAllBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetUtil.isNetworkAvailable(MainTabActivity.this)) {
                    showNetErrorDialog();
                    return;
                } else {
                    doAllUpdate();
                    return;
                }
            }
        });
        softmanagerReceiver = new SoftmanagerReceiver();
        intentfilter = new IntentFilter();
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_UPDATE");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_ADD");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_DELETE");
        intentfilter.addAction("com.gionee.aora.market.action.ACTION_SOFTWARE_IGNORE");
        registerReceiver(softmanagerReceiver, intentfilter);
        setLeftUpdateData();
        setPersonalRecommend();
    }
    public boolean isRoot() {
        return new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
    }
    @Override
    protected void onActivityResult(int i, int j, Intent intent) {
        if (mNowShowFragment != null) mNowShowFragment.onActivityResult(i, j, intent);
        super.onActivityResult(i, j, intent);
    }
    @Override
    public void onBackPressed() {
        if (actionsContentView.status == com.gionee.aora.market.gui.view.dragview.DragLayout.Status.Open) {
            actionsContentView.close();
            return;
        }
        if (System.currentTimeMillis() - exitTime > 2000L) {
            Toast.makeText(this, "\u518D\u6309\u4E00\u6B21\u9000\u51FA\u7A0B\u5E8F", 0).show();
            exitTime = System.currentTimeMillis();
            return;
        }
        List list = DownloadManager.shareInstance().getAllTaskInfo();
        int i = list.size();
        int j = 0;
        int k = 0;
        while (j < i) {
            DownloadInfo downloadinfo = (DownloadInfo) list.get(j);
            int l;
            if (downloadinfo.getState() == 0 || downloadinfo.getState() == 1) l = k + 1;
            else l = k;
            j++;
            k = l;
        }
        if (k == 0) {
            exit();
            return;
        } else {
            showExitDialog(k);
            return;
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup radiogroup, int i) {
        switch (i) {
            default:
                return;
            case biz.AR.id.radio0:
                current_falg = 0;
                mainTitleView.setVisibility(0);
                curSearchHint = getHint();
                mainTitleView.setSearchTextHint(curSearchHint);
                preferences.setCurSearchHint(curSearchHint);
                return;
            case biz.AR.id.radio1:
                current_falg = 1;
                mainTitleView.setVisibility(0);
                curSearchHint = getHint();
                mainTitleView.setSearchTextHint(curSearchHint);
                preferences.setCurSearchHint(curSearchHint);
                return;
            case biz.AR.id.radio2:
                current_falg = 2;
                mainTitleView.setVisibility(0);
                curSearchHint = getHint();
                mainTitleView.setSearchTextHint(curSearchHint);
                preferences.setCurSearchHint(curSearchHint);
                changePage(recommendNewFragment);
                return;
            case biz.AR.id.radio3:
                current_falg = 3;
                mainTitleView.setVisibility(0);
                curSearchHint = getHint();
                mainTitleView.setSearchTextHint(curSearchHint);
                preferences.setCurSearchHint(curSearchHint);
                return;
            case biz.AR.id.radio4:
                current_falg = 4;
                break;
        }
        mainTitleView.setVisibility(8);
        curSearchHint = getHint();
        mainTitleView.setSearchTextHint(curSearchHint);
        preferences.setCurSearchHint(curSearchHint);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                return;
            case biz.AR.id.base_left_0:
                UserInfo userinfo1 = UserStorage.getDefaultUserInfo(this);
                if (userinfo1.getLOGIN_STATE() != 2 || "".equals(userinfo1.getPHONE()) || userinfo1.getUSER_TYPE_FLAG() == 1) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("isShowText", false);
                    intent.putExtra("SKIP", "FREE_CALL");
                    startActivity(intent);
                    return;
                }
                preferences.setShowNewCall(false);
                new_call.setVisibility(8);
                if (userinfo1.getFreePhone() != null && !userinfo1.getFreePhone().equals("")) {
                    startActivity(new Intent(this, FreePhoneActivity.class));
                    return;
                } else {
                    startActivity(new Intent(this, BoundPhoneActivity.class));
                    return;
                }
            case biz.AR.id.base_left_1:
                SoftwareUpdateActivity.startSoftwareUpdateActivity(this, null);
                return;
            case biz.AR.id.base_left_2:
                startActivity(new Intent(this, SoftInstalledActivity.class));
                return;
            case biz.AR.id.base_left_3:
                QRCodeManager.startScan(this);
                return;
            case biz.AR.id.base_left_4:
                UpdateManager.getInstance().checkUpdate(this, getPackageName(), getString(biz.AR.string.app_name), DataCollectUtil.getVersionCode(), false);
                return;
            case biz.AR.id.base_left_5:
                startActivity(new Intent(this, MarketSettingActivity.class));
                return;
            case biz.AR.id.base_left_6:
                InitWifiManager.getInstance().initWifi(this, null);
                return;
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        preferences = MarketPreferences.getInstance(this);
        setContentView(biz.AR.layout.activity_main);
        userinfo = UserStorage.getDefaultUserInfo(this);
        height = (int) (0.1F * getResources().getDisplayMetrics().heightPixels);
        init();
        IntentFilter intentfilter;
        if (preferences.isFirstInShowSetUpdateDialog().booleanValue() && !Constants.isGioneeVerison) if (Constants.checkInstallPermission(this).booleanValue()) preferences
        .setAutoUpdateType(2);
        else preferences.setAutoUpdateType(3);
        preferences.setFirstInShowSetUpdateDiaglog(Boolean.valueOf(false));
        DLog.d(TAG, "\u81EA\u52A8\u767B\u9646``````");
        UserManager.getInstance(this).doLogin(userinfo, "1", "", "");
        loginReceiver = new LoginSucessBroadcastReceiver();
        intentfilter = new IntentFilter("com.gionee.aora.integral.gui.login.LOGIN_SUUESS_ACTION");
        registerReceiver(loginReceiver, intentfilter);
    }
    @Override
    protected void onDestroy() {
        mainTitleView.onDestory();
        if (loginReceiver != null) unregisterReceiver(loginReceiver);
        super.onDestroy();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        current_falg = intent.getIntExtra("MAIN_FALG", 2);
        rb[current_falg].setChecked(true);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (actionsContentView.status != com.gionee.aora.market.gui.view.dragview.DragLayout.Status.Open) actionsContentView.open();
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onResume() {
        curSearchHint = getHint();
        mainTitleView.setSearchTextHint(curSearchHint);
        preferences.setCurSearchHint(curSearchHint);
        setShowLenjoyFragment();
        super.onResume();
    }
    @Override
    protected void onSaveInstanceState(Bundle bundle) {}
    @Override
    protected void onStop() {
        super.onStop();
    }
    private void setLeftUpdateData() {
        if (softwareManager.getUpdateSoftwaresCount() != 0) {
            leftHaveUpdateLay.setVisibility(0);
            leftNoUpdateLay.setVisibility(8);
            baseLeftLay1.setBackgroundColor(0);
            Object aobj[] = new Object[1];
            aobj[0] = new StringBuilder().append(softwareManager.getUpdateSoftwaresCount()).append("").toString();
            String s = String.format("\u6709 %s \u6B3E\u5E94\u7528\u82E6\u5F85\u4E3B\u4E0A\u5347\u7EA7", aobj);
            leftUpdateCountText.setText(setTextWithColor(s, new StringBuilder().append(softwareManager.getUpdateSoftwaresCount()).append("").toString()));
            Iterator iterator = softwareManager.getUpdate_softwaresMap().entrySet().iterator();
            int i = 0;
            while (iterator.hasNext()) {
                AppInfo appinfo = (AppInfo) ((java.util.Map.Entry) iterator.next()).getValue();
                if (i <= 4) {
                    if (i == 0) {
                        imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon1, options);
                        leftUpdateIcon1.setVisibility(0);
                        leftUpdateIcon2.setVisibility(8);
                        leftUpdateIcon3.setVisibility(8);
                        leftUpdateIcon4.setVisibility(8);
                    } else if (i == 1) {
                        imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon2, options);
                        leftUpdateIcon2.setVisibility(0);
                        leftUpdateIcon3.setVisibility(8);
                        leftUpdateIcon4.setVisibility(8);
                    } else if (i == 2) {
                        imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon3, options);
                        leftUpdateIcon3.setVisibility(0);
                        leftUpdateIcon4.setVisibility(8);
                    } else if (i == 3) {
                        imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon4, options);
                        leftUpdateIcon4.setVisibility(0);
                    }
                } else if (i == 0) {
                    imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon1, options);
                    leftUpdateIcon1.setVisibility(0);
                } else if (i == 1) {
                    imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon2, options);
                    leftUpdateIcon2.setVisibility(0);
                } else if (i == 2) {
                    imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon3, options);
                    leftUpdateIcon3.setVisibility(0);
                } else if (i == 3) {
                    imageLoader.displayImage(appinfo.getIconUrl(), leftUpdateIcon4, options);
                    leftUpdateIcon4.setVisibility(0);
                }
                i++;
            }
            if (softwareManager.getUpdateSoftwaresCount() <= 4) {
                leftUpdateMoreImg.setVisibility(8);
                return;
            } else {
                leftUpdateMoreImg.setVisibility(0);
                return;
            }
        } else {
            baseLeftLay1.setBackgroundResource(biz.AR.drawable.main_menu_list_selector);
            leftNoUpdateLay.setVisibility(0);
            leftHaveUpdateLay.setVisibility(8);
            return;
        }
    }
    private void setNowFragment(Fragment fragment) {
        if (mNowShowFragment != null) mFragmentManager.beginTransaction().hide(mNowShowFragment).commitAllowingStateLoss();
        mNowShowFragment = fragment;
    }
    private void setPersonalRecommend() {
        NotLaunchManager.removeAlarm(this);
        NotLaunchManager.cancelNotification(this);
        NotLaunchManager.addAlarm(this);
        boolean flag = getIntent().getBooleanExtra("isShowPersonalityRecommend", false);
        Log.e("test", new StringBuilder().append("\u662F\u5426\u5524\u9192").append(flag).toString());
        if (flag) new WakupPersonalRecommendTask().doExecutor(new Integer[0]);
    }
    private void setShowLenjoyFragment() {
        if (isShowLenjoy) {
            rb[3].setChecked(true);
            isShowLenjoy = false;
        }
    }
    private SpannableStringBuilder setTextWithColor(String s, String s1) {
        SpannableStringBuilder spannablestringbuilder = new SpannableStringBuilder(s);
        int i = s.indexOf(s1);
        if (i >= 0) spannablestringbuilder.setSpan(new ForegroundColorSpan(-1), i, i + s1.length(), 33);
        return spannablestringbuilder;
    }
    private void showDialog(final AppInfo appInfo) {
        MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(this);
        marketfloatedialogbuilder.setMessage(new StringBuilder().append("\u4F60\u539F\u6709\u7684\u3010").append(appInfo.getName())
                .append("\u3011\u4E0E\u65B0\u7248\u672C\u4E0D\u517C\u5BB9\uFF0C\u9700\u5378\u8F7D\u540E\u91CD\u65B0\u5B89\u88C5\uFF0C\u662F\u5426\u7EE7\u7EED\u66F4\u65B0\uFF1F")
                .toString());
        marketfloatedialogbuilder.setCancelable(true);
        final Dialog dialog = marketfloatedialogbuilder.crteate();
        marketfloatedialogbuilder.setLeftButton("\u5FFD\u7565\u66F4\u65B0", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                softwareManager.changeUpdateToIgnore(appInfo.getPackageName());
                dialog.dismiss();
            }
        });
        marketfloatedialogbuilder.setRightButton("\u7EE7\u7EED\u66F4\u65B0", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DownloadInfo downloadinfo = appInfo.toDownloadInfo();
                if (downloadManager.addDownload(downloadinfo)) softwareManager.uninstallApk(appInfo.getPackageName());
            }
        });
        dialog.show();
    }
    private void showExitDialog(int i) {
        View view = LayoutInflater.from(this).inflate(biz.AR.layout.exit_dialog, null);
        TextView textview = (TextView) view.findViewById(biz.AR.id.download_size);
        Resources resources = getResources();
        int j = biz.AR.string.exit_download_size;
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(i);
        textview.setText(resources.getString(j, aobj));
        final CheckBox isSelect = (CheckBox) view.findViewById(biz.AR.id.exit_dialog_cb);
        MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(this);
        marketfloatedialogbuilder.setMessage(getString(biz.AR.string.t_gprs_title));
        marketfloatedialogbuilder.setCancelable(false);
        marketfloatedialogbuilder.setCenterView(view, null);
        marketfloatedialogbuilder.setLeftButton("\u53D6\u6D88", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                dialog.cancel();
            }
        });
        marketfloatedialogbuilder.setRightButton("\u9000\u51FA", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                DLog.d("denglh", new StringBuilder().append("\u662F\u5426\u52FE\u9009").append(isSelect.isChecked()).toString());
                if (isSelect.isChecked()) {
                    finish();
                    return;
                } else {
                    exit();
                    return;
                }
            }
        });
        dialog = marketfloatedialogbuilder.crteate();
        marketfloatedialogbuilder.show();
    }
    private void showNetErrorDialog() {
        Intent intent = new Intent();
        intent.setClass(this, NetErrorDialog.class);
        intent.setFlags(0x10000000);
        startActivity(intent);
    }
    private void showSetDialog() {
        MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(this);
        marketfloatedialogbuilder.setCancelable(false);
        View view = View.inflate(this, biz.AR.layout.set_wifi_update_dialog, null);
        marketfloatedialogbuilder.setCenterView(view, null);
        final CheckBox checkBox = (CheckBox) view.findViewById(biz.AR.id.update_dialog_checkbox);
        checkBox.setChecked(true);
        marketfloatedialogbuilder.setTitle("\u6613\u7528\u6C47 \u201C\u5F00\u542F\u81EA\u52A8\u66F4\u65B0\u201D");
        marketfloatedialogbuilder.setRightButton("\u786E\u5B9A", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                label0: {
                if (checkBox.isChecked()) {
                    if (!Constants.checkInstallPermission(MainTabActivity.this).booleanValue()) break label0;
                    preferences.setAutoUpdateType(2);
                }
                return;
            }
            preferences.setAutoUpdateType(3);
            }
        });
        marketfloatedialogbuilder.show();
    }
}
