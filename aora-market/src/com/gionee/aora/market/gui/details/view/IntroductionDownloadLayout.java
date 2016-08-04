// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.details.view;
import java.io.File;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.util.DLog;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadListener;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.control.PraiseCache;
import com.gionee.aora.market.control.SoftWareUpdateManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.gui.share.ShareActivity;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.net.PraiseSumbitNet;
import com.gionee.aora.market.net.ShareToAddNet;
import com.gionee.aora.market.util.Util;

public class IntroductionDownloadLayout extends RelativeLayout implements android.view.View.OnClickListener {
    class AutoInstallBroadCastReceive extends BroadcastReceiver {
        AutoInstallBroadCastReceive() {
            super();
        }
        @Override
        public void onReceive(Context context1, Intent intent) {
            if (intent.getAction().equals("com.gionee.aora.market.AutoInstallReceiver")) {
                if (intent.getIntExtra("STATE", 3) != 2) {
                    DLog.d("denglh", new StringBuilder().append("\u662F\u5426\u4E3A\u7A7A\uFF1A").append(packageName).toString());
                    if (packageName != null) initLayout(packageName);
                }
                if (installOrUnstallListener != null) installOrUnstallListener.installStateChange();
            }
        }
    }
    public static interface InstallOrUnstallListener {
        public abstract void installStateChange();
    }
    public static interface SetAddToDownload {
        public abstract void addToDownload();
    }
    class SoftManagerInitFinishBroadCastReceive extends BroadcastReceiver {
        SoftManagerInitFinishBroadCastReceive() {
            super();
        }
        @Override
        public void onReceive(Context context1, Intent intent) {
            if (intent.getAction().equals("com.gionee.aora.market.action.ACTION_SOFTWARE_UPDATE")) {
                DLog.d("IntroductionDownloadLayout", "---SoftManagerInitFinishBroadCastReceive onReceive---\u521D\u59CB\u5316\u5B8C\u6210");
                if (packageName != null) initLayout(packageName);
            }
        }
    }
    private static final String TAG = "IntroductionDownloadLayout";
    private final int HANDLER_REFRESH_ADD_OR_DELETE;
    private final int HANDLER_REFRESH_PROGRESS;
    private final int HANDLER_REFRESH_SATAE;
    private AppInfo appInfo;
    private Context context;
    private DataCollectInfo datainfo;
    private TextView delete;
    private Button downloadButton;
    private DownloadInfo downloadInfo;
    private DownloadManager downloadManager;
    private TextView goon;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            downloadInfo = (DownloadInfo) message.obj;
            if (message.what == 1) {
                progressBar.setProgress(message.arg1);
                progressBarStop.setProgress(message.arg1);
                percentTextView.setText(new StringBuilder().append(message.arg1).append("%").toString());
                setViewIsGoneWhenHasDownloadInfo(1);
            } else {
                if (message.what == 0) {
                    setViewIsGoneWhenHasDownloadInfo(downloadInfo.getState());
                    return;
                }
                if (message.what == 2) {
                    initLayout(packageName);
                    if (installOrUnstallListener != null) {
                        installOrUnstallListener.installStateChange();
                        return;
                    }
                }
            }
        }
    };
    private Button installButton;
    private InstallOrUnstallListener installOrUnstallListener;
    private Button instanningButton;
    boolean isPrise;
    private DownloadListener listener;
    private Button openButton;
    private String packageName;
    private TextView percentTextView;
    private TextView priseCountsTextView;
    private ImageView priseImageView;
    private LinearLayout priseLayout;
    private ProgressBar progressBar;
    private ProgressBar progressBarStop;
    protected AutoInstallBroadCastReceive receiver;
    private TextView retry;
    private SetAddToDownload setAddToDownload;
    private TextView sharebButton;
    protected SoftManagerInitFinishBroadCastReceive softManagerInitFinishBroadCastReceiver;
    private SoftwareManager softwareManager;
    private TextView stop;
    private Button uninstallButton;
    private Button updateButton;
    private DownloadInfo updateInfo;
    private Button waitButton;
    /*
        static DownloadInfo access$002(IntroductionDownloadLayout introductiondownloadlayout, DownloadInfo downloadinfo)
        {
            introductiondownloadlayout.downloadInfo = downloadinfo;
            return downloadinfo;
        }

     */
    public IntroductionDownloadLayout(Context context1) {
        super(context1);
        appInfo = null;
        downloadInfo = null;
        updateInfo = null;
        softwareManager = null;
        HANDLER_REFRESH_SATAE = 0;
        HANDLER_REFRESH_PROGRESS = 1;
        HANDLER_REFRESH_ADD_OR_DELETE = 2;
        packageName = null;
        receiver = null;
        softManagerInitFinishBroadCastReceiver = null;
        datainfo = null;
        isPrise = false;
        init(context1);
    }
    public IntroductionDownloadLayout(Context context1, AttributeSet attributeset) {
        super(context1, attributeset);
        appInfo = null;
        downloadInfo = null;
        updateInfo = null;
        softwareManager = null;
        HANDLER_REFRESH_SATAE = 0;
        HANDLER_REFRESH_PROGRESS = 1;
        HANDLER_REFRESH_ADD_OR_DELETE = 2;
        packageName = null;
        receiver = null;
        softManagerInitFinishBroadCastReceiver = null;
        datainfo = null;
        isPrise = false;
        init(context1);
    }
    public IntroductionDownloadLayout(Context context1, AttributeSet attributeset, int i) {
        super(context1, attributeset, i);
        appInfo = null;
        downloadInfo = null;
        updateInfo = null;
        softwareManager = null;
        HANDLER_REFRESH_SATAE = 0;
        HANDLER_REFRESH_PROGRESS = 1;
        HANDLER_REFRESH_ADD_OR_DELETE = 2;
        packageName = null;
        receiver = null;
        softManagerInitFinishBroadCastReceiver = null;
        datainfo = null;
        isPrise = false;
        init(context1);
    }
    private void init(Context context1) {
        context = context1;
        addView(View.inflate(context1, biz.AR.layout.intorduction_control_layout, null), new android.widget.RelativeLayout.LayoutParams(-1, -2));
        downloadButton = (Button) findViewById(biz.AR.id.introduction_download_button);
        installButton = (Button) findViewById(biz.AR.id.introduction_install_button);
        waitButton = (Button) findViewById(biz.AR.id.introduction_waitting_button);
        uninstallButton = (Button) findViewById(biz.AR.id.introduction_uninstall_button);
        openButton = (Button) findViewById(biz.AR.id.introduction_open_button);
        updateButton = (Button) findViewById(biz.AR.id.introduction_update_button);
        instanningButton = (Button) findViewById(biz.AR.id.introduction_installing_button);
        sharebButton = (TextView) findViewById(biz.AR.id.introduction_share_button);
        delete = (TextView) findViewById(biz.AR.id.introduction_delete);
        stop = (TextView) findViewById(biz.AR.id.introduction_stop);
        goon = (TextView) findViewById(biz.AR.id.introduction_goon);
        retry = (TextView) findViewById(biz.AR.id.introduction_tryAgain);
        progressBar = (ProgressBar) findViewById(biz.AR.id.introduction_downloadPresent);
        progressBarStop = (ProgressBar) findViewById(biz.AR.id.introduction_downloadPresent_stop);
        priseLayout = (LinearLayout) findViewById(biz.AR.id.id_good_lay);
        priseImageView = (ImageView) findViewById(biz.AR.id.soft_list_praise);
        priseCountsTextView = (TextView) findViewById(biz.AR.id.soft_list_shot_count);
        percentTextView = (TextView) findViewById(biz.AR.id.introduction_downloadPresent_text);
        downloadButton.setOnClickListener(this);
        installButton.setOnClickListener(this);
        uninstallButton.setOnClickListener(this);
        openButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        sharebButton.setOnClickListener(this);
        delete.setOnClickListener(this);
        stop.setOnClickListener(this);
        goon.setOnClickListener(this);
        retry.setOnClickListener(this);
        priseLayout.setOnClickListener(this);
        downloadManager = DownloadManager.shareInstance();
        softwareManager = SoftwareManager.getInstace();
        initListener();
        downloadManager.registerDownloadListener(listener);
        registerAutoStallBroadCast(context1);
        registerSoftManagerFinisgBroadCast(context1);
    }
    private void initLayout(String s) {
        if (packageName == null) packageName = s;
        isPrise = PraiseCache.checkIsCachePraise(s);
        StringBuilder stringbuilder;
        boolean flag;
        if (isPrise) priseImageView.setBackgroundResource(biz.AR.drawable.good_press);
        else priseImageView.setBackgroundResource(biz.AR.drawable.good);
        downloadInfo = downloadManager.queryDownload(s);
        stringbuilder = new StringBuilder().append(s).append("::");
        if (downloadInfo == null) flag = true;
        else flag = false;
        DLog.d("denglh", stringbuilder.append(flag).toString());
        updateInfo = SoftWareUpdateManager.getInstance().checkHadDownloadAPkFile(s);
        if (downloadInfo != null) {
            progressBar.setProgress(downloadInfo.getPercent());
            progressBarStop.setProgress(downloadInfo.getPercent());
            percentTextView.setText(new StringBuilder().append(downloadInfo.getPercent()).append("%").toString());
            if (downloadInfo.getState() == 3 && softwareManager.isInstalling(s)) {
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(0);
                delete.setVisibility(8);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(8);
                percentTextView.setVisibility(8);
                instanningButton.setVisibility(0);
                priseLayout.setVisibility(0);
                return;
            } else {
                setViewIsGoneWhenHasDownloadInfo(downloadInfo.getState());
                return;
            }
        }
        if (updateInfo != null) {
            progressBar.setProgress(updateInfo.getPercent());
            progressBarStop.setProgress(updateInfo.getPercent());
            percentTextView.setText(new StringBuilder().append(updateInfo.getPercent()).append("%").toString());
            if (updateInfo.getState() == 3 && softwareManager.isInstalling(s)) {
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(0);
                delete.setVisibility(8);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(8);
                percentTextView.setVisibility(8);
                instanningButton.setVisibility(0);
                priseLayout.setVisibility(0);
                return;
            } else {
                setViewIsGoneWhenHasDownloadInfo(updateInfo.getState());
                return;
            }
        }
        switch (softwareManager.getSoftwareCurStateByPackageName(s)) {
            default:
                return;
            case 1: // '\001'
                downloadButton.setVisibility(0);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(0);
                delete.setVisibility(8);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(8);
                percentTextView.setVisibility(8);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(0);
                return;
            case 2: // '\002'
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(0);
                openButton.setVisibility(0);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(0);
                delete.setVisibility(8);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(8);
                percentTextView.setVisibility(8);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(0);
                return;
            case 3: // '\003'
                downloadButton.setVisibility(8);
                break;
        }
        installButton.setVisibility(8);
        waitButton.setVisibility(8);
        uninstallButton.setVisibility(0);
        openButton.setVisibility(8);
        updateButton.setVisibility(0);
        sharebButton.setVisibility(0);
        delete.setVisibility(8);
        stop.setVisibility(8);
        goon.setVisibility(8);
        retry.setVisibility(8);
        progressBar.setVisibility(8);
        progressBarStop.setVisibility(8);
        percentTextView.setVisibility(8);
        instanningButton.setVisibility(8);
        priseLayout.setVisibility(0);
    }
    private void initListener() {
        listener = new DownloadListener() {
            @Override
            public void onProgress(int i, DownloadInfo downloadinfo) {
                if (downloadinfo.getPackageName().equals(packageName)) sentMessage(downloadinfo, 1, i);
            }
            @Override
            public void onStateChange(int i, DownloadInfo downloadinfo) {
                label0: {
                if (downloadinfo.getPackageName().equals(packageName)) {
                    if (i == 3) break label0;
                    sentMessage(downloadinfo, 0, i);
                }
                return;
            }
            if (!Constants.canAutoInstall) {
                sentMessage(downloadinfo, 0, i);
                return;
            } else {
                handler.sendEmptyMessage(2);
                return;
            }
            }
            @Override
            public void onTaskCountChanged(int i, DownloadInfo downloadinfo) {
                if (downloadinfo.getPackageName().equals(packageName)) handler.sendEmptyMessage(2);
            }
        };
    }
    @Override
    public void onClick(View view) {
        if (appInfo != null) {
            switch (view.getId()) {
                case biz.AR.id.soft_list_praise://L_L3
                case biz.AR.id.soft_list_shot_count://L_L3
                case biz.AR.id.introduction_nodownload_layout://L_L3
                case biz.AR.id.introduction_installing_button://L_L3
                case biz.AR.id.introduction_waitting_button://L_L3
                case biz.AR.id.stop_layout://L_L3
                    break;
                case biz.AR.id.id_good_lay://L_L4 _L4:
                    if (!isPrise && appInfo != null) {
                        priseImageView.setBackgroundResource(biz.AR.drawable.good_press);
                        sentPraise(appInfo.getSoftId());
                        PraiseCache.savePrise(appInfo.getPackageName(), appInfo.getSoftId());
                        isPrise = true;
                        try {
                            priseCountsTextView.setText(new StringBuilder().append(1 + Integer.parseInt(appInfo.getPraiseCount())).append("\u6B21\u8D5E").toString());
                            appInfo.setPraiseCount(new StringBuilder().append(1 + Integer.parseInt(appInfo.getPraiseCount())).append("").toString());
                            return;
                        } catch (Exception exception) {
                            DLog.e("DwonloadSoftListAdapter", "analysisList#exception =", exception);
                        }
                        return;
                    }
                    break;
                case biz.AR.id.introduction_share_button://L_L5 _L5:
                    showShare();
                    break;
                case biz.AR.id.introduction_install_button://L_L6 _L6:
                    if (downloadInfo != null) {
                        DLog.v(new StringBuilder().append("detail install \u8BE6\u60C5\u9875\u7528\u6237\u624B\u673A\u70B9\u51FB\u5B89\u88C5")
                                .append(downloadInfo.getPackageName()).append("---").append(downloadInfo.getName()).toString());
                        softwareManager.installApp(DownloadManager.shareInstance(), downloadInfo);
                        return;
                    }
                    if (updateInfo != null) {
                        DLog.v(new StringBuilder().append("detail install \u8BE6\u60C5\u9875\u7528\u6237\u624B\u673A\u70B9\u51FB\u5B89\u88C5").append(updateInfo.getPackageName())
                                .append("---").append(updateInfo.getName()).toString());
                        softwareManager.installApp(DownloadManager.shareInstance(), updateInfo);
                        return;
                    }
                    break;
                case biz.AR.id.introduction_download_button://L_L7 _L7:
                    if (Util.checkNetworkValid(context) && Util.checkGprslimt(context, appInfo.getUpdateSoftSize(), appInfo.toDownloadInfo())) {
                        if (Util.checkMemorySize(context, appInfo.toDownloadInfo())) {
                            downloadManager.addDownload(appInfo.toDownloadInfo());
                            sentDataCollection();
                        }
                        if (setAddToDownload != null) {
                            setAddToDownload.addToDownload();
                            return;
                        }
                    }
                    break;
                case biz.AR.id.introduction_uninstall_button://L_L8 _L8:
                    softwareManager.uninstallApk(packageName);
                    break;
                case biz.AR.id.introduction_open_button://L_L9 _L9:
                    openApk();
                    break;
                case biz.AR.id.introduction_update_button://L_L10 _L10:
                    DownloadInfo downloadinfo = SoftWareUpdateManager.getInstance().checkHadDownloadAPkFile(appInfo.getPackageName());
                    if (downloadinfo != null) {
                        if (new File(downloadinfo.getPath()).exists()) {
                            softwareManager.installApp(DownloadManager.shareInstance(), downloadinfo);
                            return;
                        }
                        SoftWareUpdateManager.getInstance().deleteDownloadInfo(downloadinfo.getPackageName());
                    }
                    if (Util.checkNetworkValid(context) && Util.checkGprslimt(context, appInfo.getUpdateSoftSize(), appInfo.toDownloadInfo())
                            && Util.checkMemorySize(context, appInfo.toDownloadInfo())) {
                        if (softwareManager.getUpdate_softwaresMap().get(appInfo.getPackageName()) != null
                                && !softwareManager.getUpdate_softwaresMap().get(appInfo.getPackageName()).isSameSign()) {
                            showDialog();
                            return;
                        }
                        downloadManager.addDownload(softwareManager.getDownloadInfoByPackageName(appInfo.getPackageName()));
                        sentDataCollection();
                        if (setAddToDownload != null) {
                            setAddToDownload.addToDownload();
                            return;
                        }
                    }
                    break;
                case biz.AR.id.introduction_stop://L_L11 _L16:
                    downloadManager.stopDownload(downloadInfo);
                    break;
                case biz.AR.id.introduction_goon://L_L12
                case biz.AR.id.introduction_tryAgain://L_L12
                    if (Util.checkNetworkValid(context)) downloadManager.addDownload(downloadInfo);
                    break;
                case biz.AR.id.introduction_delete://L_L13_L13:
                    if (downloadInfo != null) {
                        if (downloadInfo.getState() == 3) {
                            DLog.v("IntroductionDownloadLayout", "\u5B89\u88C5\u4E2D\uFF0C\u4E0D\u80FD\u53D6\u6D88");
                        } else {
                            downloadManager.deleteDownload(downloadInfo, MarketPreferences.getInstance(context).getDeleteDownloadPackage().booleanValue());
                            DLog.v("IntroductionDownloadLayout",
                                    new StringBuilder().append("detail delete \u8BE6\u60C5\u9875\u9762\u7528\u6237\u70B9\u51FB\u53D6\u6D88\u4E0B\u8F7D\u4EFB\u52A1")
                                    .append(downloadInfo.getName()).append(" ").append(downloadInfo.getPackageName()).toString());
                        }
                    }
                    break;
            }
        }
    }
    public void onDestory() {
        if (downloadManager != null) {
            downloadManager.unregisterDownloadListener(listener);
            unRegisterAutoStallBroadCast();
            unRegisterSoftManagerFinishBroadCast();
            unRegisterSoftManagerFinishBroadCast();
        }
    }
    private void openApk() {
        if (appInfo == null || appInfo.getPackageName() == null) return;
        ResolveInfo resolveinfo;
        String s;
        String s1;
        Intent intent1;
        try {
            PackageInfo packageinfo = context.getPackageManager().getPackageInfo(appInfo.getPackageName(), 0);
            Intent intent = new Intent("android.intent.action.MAIN", null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(packageinfo.packageName);
            resolveinfo = context.getPackageManager().queryIntentActivities(intent, 0).iterator().next();
        } catch (Exception exception) {
            DLog.e("IntroductionDownloadLayout", "openApk()#Exception=", exception);
            return;
        }
        if (resolveinfo == null) return;
        s = resolveinfo.activityInfo.packageName;
        s1 = resolveinfo.activityInfo.name;
        intent1 = new Intent("android.intent.action.MAIN");
        intent1.addCategory("android.intent.category.LAUNCHER");
        intent1.addFlags(0x200000);
        intent1.setFlags(0x10000000);
        intent1.putExtra("IS_FROME_MARKET", true);
        intent1.setComponent(new ComponentName(s, s1));
        context.startActivity(intent1);
    }
    private void registerAutoStallBroadCast(Context context1) {
        receiver = new AutoInstallBroadCastReceive();
        IntentFilter intentfilter = new IntentFilter("com.gionee.aora.market.AutoInstallReceiver");
        context1.registerReceiver(receiver, intentfilter);
    }
    private void registerSoftManagerFinisgBroadCast(Context context1) {
        softManagerInitFinishBroadCastReceiver = new SoftManagerInitFinishBroadCastReceive();
        IntentFilter intentfilter = new IntentFilter("com.gionee.aora.market.action.ACTION_SOFTWARE_UPDATE");
        context1.registerReceiver(softManagerInitFinishBroadCastReceiver, intentfilter);
    }
    private void sentDataCollection() {
        DLog.i("lilijun", new StringBuilder().append("\u8BE6\u60C5\u6570\u636E\u91C7\u96C6vid------->>>").append(appInfo.getvId()).toString());
        DLog.i("lilijun", new StringBuilder().append("\u8BE6\u60C5\u6570\u636E\u91C7\u96C6app_id------->>>").append(appInfo.getId()).toString());
        if (appInfo.getvId() != 0 || appInfo.getvId() != -1) {
            DataCollectInfo datacollectinfo = datainfo;
            String as[] = new String[8];
            as[0] = "setup_flag";
            as[1] = "0";
            as[2] = "app_id";
            as[3] = appInfo.getSoftId();
            as[4] = "cpversion";
            as[5] = new StringBuilder().append(appInfo.getUpdateVersionName()).append("").toString();
            as[6] = "vid";
            as[7] = new StringBuilder().append(appInfo.getvId()).append("").toString();
            DataCollectManager.addRecord(datacollectinfo, as);
            return;
        } else {
            DataCollectInfo datacollectinfo1 = datainfo;
            String as1[] = new String[6];
            as1[0] = "setup_flag";
            as1[1] = "0";
            as1[2] = "app_id";
            as1[3] = appInfo.getSoftId();
            as1[4] = "cpversion";
            as1[5] = new StringBuilder().append(appInfo.getCurVersionName()).append("").toString();
            DataCollectManager.addRecord(datacollectinfo1, as1);
            return;
        }
    }
    private void sentMessage(DownloadInfo downloadinfo, int i, int j) {
        Message message = new Message();
        message.what = i;
        message.obj = downloadinfo;
        message.arg1 = j;
        handler.sendMessage(message);
    }
    private void sentPraise(final String softId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PraiseSumbitNet.getPraiseSumbit(softId);
            }
        }).start();
    }
    public void setAddToDownloadListener(SetAddToDownload setaddtodownload) {
        setAddToDownload = setaddtodownload;
    }
    public void setDada(AppInfo appinfo, DataCollectInfo datacollectinfo) {
        if (appinfo == null) {
            return;
        } else {
            appInfo = appinfo;
            datainfo = datacollectinfo;
            datacollectinfo.setAction("12");
            initLayout(appinfo.getPackageName());
            return;
        }
    }
    public void setDada(DownloadInfo downloadinfo) {
        downloadInfo = downloadinfo;
        initLayout(downloadinfo.getPackageName());
    }
    public void setDada(String s, DataCollectInfo datacollectinfo) {
        datainfo = datacollectinfo;
        datacollectinfo.setAction("12");
        initLayout(s);
    }
    public void setInstallOrUnstallListener(InstallOrUnstallListener installorunstalllistener) {
        installOrUnstallListener = installorunstalllistener;
    }
    public void setPriseCount(AppInfo appinfo, String s) {
        appInfo = appinfo;
        priseCountsTextView.setText(new StringBuilder().append(s).append("\u6B21\u8D5E").toString());
    }
    private void setViewIsGoneWhenHasDownloadInfo(int i) {
        switch (i) {
            default:
                return;
            case 0: // '\0'
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(0);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(0);
                delete.setVisibility(8);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(8);
                percentTextView.setVisibility(8);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(0);
                return;
            case 1: // '\001'
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(8);
                delete.setVisibility(0);
                stop.setVisibility(0);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(0);
                percentTextView.setVisibility(0);
                progressBarStop.setVisibility(8);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(8);
                return;
            case 2: // '\002'
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(8);
                delete.setVisibility(0);
                stop.setVisibility(8);
                goon.setVisibility(0);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(0);
                percentTextView.setVisibility(0);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(8);
                return;
            case 4: // '\004'
                downloadButton.setVisibility(8);
                installButton.setVisibility(8);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(8);
                delete.setVisibility(0);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(0);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(0);
                percentTextView.setVisibility(0);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(8);
                return;
            case 3: // '\003'
                downloadButton.setVisibility(8);
                installButton.setVisibility(0);
                waitButton.setVisibility(8);
                uninstallButton.setVisibility(8);
                openButton.setVisibility(8);
                updateButton.setVisibility(8);
                sharebButton.setVisibility(0);
                delete.setVisibility(8);
                stop.setVisibility(8);
                goon.setVisibility(8);
                retry.setVisibility(8);
                progressBar.setVisibility(8);
                progressBarStop.setVisibility(8);
                percentTextView.setVisibility(8);
                instanningButton.setVisibility(8);
                priseLayout.setVisibility(0);
                return;
        }
    }
    private void shareToAddCoin(final UserInfo userInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShareToAddNet.shareToAdd(context, new StringBuilder().append(userInfo.getID()).append("").toString(), userInfo.getHAND_KEY(),
                        new StringBuilder().append(userInfo.getUSER_TYPE_FLAG()).append("").toString(), userInfo.getUSER_NAME(), DataCollectUtil.getValue("m"),
                        DataCollectUtil.getValue("imei"), appInfo.getSoftId());
            }
        }).start();
    }
    private void showDialog() {
        MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(context);
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
                if (downloadManager.addDownload(softwareManager.getDownloadInfoByPackageName(appInfo.getPackageName()))) softwareManager.uninstallApk(appInfo.getPackageName());
                sentDataCollection();
            }
        });
        dialog.show();
    }
    private void showShare() {
        label0: {
        if (appInfo != null && appInfo.getSoftId() != null) {
            if (UserStorage.getDefaultUserInfo(context).getLOGIN_STATE() != 3) break label0;
            Toast.makeText(context, "\u8BF7\u5148\u767B\u5F55\u624D\u80FD\u5206\u4EAB", 0).show();
        }
        return;
    }
    StringBuffer stringbuffer = new StringBuffer();
    Resources resources = context.getResources();
    int i = biz.AR.string.share_name;
    Object aobj[] = new Object[3];
    aobj[0] = appInfo.getName();
    Object aobj1[] = new Object[1];
    aobj1[0] = appInfo.getSoftId();
    aobj[1] = String.format("http://adres.myaora.net:81/function/download_apk.php?softid=%s&chl=fenxiang", aobj1);
    aobj[2] = String.format("http://adres.myaora.net:81/function/download_apk.php?softid=%s&chl=fenxiang", new Object[] { "10901" });
    stringbuffer.append(resources.getString(i, aobj));
    String s = new StringBuilder().append("\u563F\uFF01\u6211\u5728\u6613\u7528\u6C47\u53D1\u73B0\u4E86\u3010").append(appInfo.getName())
            .append("\u3011\uFF0C\u5FEB\u6765\u4E0B\u8F7D\u5427").toString();
    ShareActivity.share(context, stringbuffer.toString(), appInfo.getWapUrl(), s);
    }
    private void unRegisterAutoStallBroadCast() {
        if (receiver != null) context.unregisterReceiver(receiver);
    }
    private void unRegisterSoftManagerFinishBroadCast() {
        if (softManagerInitFinishBroadCastReceiver != null) context.unregisterReceiver(softManagerInitFinishBroadCastReceiver);
    }
}
