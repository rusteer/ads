// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.util.DLog;
import com.gionee.aora.fihs.controler.ControlerReceiver;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.market.control.CacheDataManager;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.LocalSearchManager;
import com.gionee.aora.market.control.LoginManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.control.UpdateManager;
import com.gionee.aora.market.gui.login.GuideActivity;
import com.gionee.aora.market.gui.main.MainNewActivity;
import com.gionee.aora.market.module.RecommendAdInfo;
import com.gionee.aora.market.util.BannerstartUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

// Referenced classes of package com.gionee.aora.market:
//            ProjectConfig
public class GoApkLoginAndRegister extends Activity {
    private static final String TAG = "GoApkLoginAndRegister";
    private static boolean isLogin = false;
    public static boolean isMarketRunning = false;
    private CacheDataManager cacheDataManager;
    private DataCollectInfo datainfo;
    private long delaytime;
    private float downX;
    private Handler handler;
    private boolean isBanner;
    private Boolean isShowGprs;
    private LinearLayout loadclose;
    private LoginManager loginManager;
    private ImageView loginbg;
    private DisplayImageOptions options;
    private MarketPreferences pref;
    public GoApkLoginAndRegister() {
        options = new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().displayer(new FadeInBitmapDisplayer(1000)).build();
        delaytime = 100L;
        downX = 0.0F;
        isBanner = false;
        datainfo = null;
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    default:
                        return;
                    case 2: // '\002'
                        DLog.i("GoApkLoginAndRegister", "MarketLoginManager.splash.MSG_WHAT_SUCCESS_SPLASH");
                        downloadSplash((RecommendAdInfo) message.obj);
                        return;
                    case 3: // '\003'
                        DLog.i("GoApkLoginAndRegister", "MarketLoginManager.splash.MSG_WHAT_FAILD_SPLASH");
                        break;
                }
            }
        };
    }
    private void addShortCut() {
        DLog.i("GoApkLoginAndRegister", "addShortCut()");
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("android.intent.extra.shortcut.NAME", getString(biz.AR.string.shortcut_name));
        intent.putExtra("duplicate", false);
        Intent intent1 = new Intent("android.intent.action.MAIN");
        intent1.addCategory("android.intent.category.LAUNCHER");
        intent1.setFlags(0x10000000);
        intent1.addFlags(0x200000);
        intent1.setComponent(new ComponentName(getApplicationContext(), GoApkLoginAndRegister.class));
        intent.putExtra("android.intent.extra.shortcut.INTENT", intent1);
        intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", android.content.Intent.ShortcutIconResource.fromContext(this, biz.AR.drawable.icon));
        sendBroadcast(intent);
    }
    private void downloadSplash(final RecommendAdInfo adinfo) {
        DLog.i("GoApkLoginAndRegister", new StringBuilder().append("downloadSplash#splashUrl=").append(adinfo.getUrl()).toString());
        if (URLUtil.isHttpUrl(adinfo.getUrl())) {
            ImageLoadingListener imageloadinglistener = new ImageLoadingListener() {
                @Override
                public void onLoadingCancelled(String s, View view) {}
                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    adinfo.setUrl(s);
                    cacheDataManager.saveCachDataToFile2(GoApkLoginAndRegister.this, "login_ad_data", adinfo);
                    pref.setSplashUrl(s);
                    DLog.i("GoApkLoginAndRegister", new StringBuilder().append("onLoadingComplete#imageUri=").append(s).toString());
                }
                @Override
                public void onLoadingFailed(String s, View view, FailReason failreason) {}
                @Override
                public void onLoadingStarted(String s, View view) {}
            };
            ImageLoader.getInstance().loadImage(adinfo.getUrl(), options, imageloadinglistener);
            return;
        } else {
            pref.setSplashUrl("");
            cacheDataManager.saveCachDataToFile2(this, "login_ad_data", adinfo);
            DLog.e("GoApkLoginAndRegister", "downloadSplash#splashUrl  \u4E0D\u5408\u6CD5");
            return;
        }
    }
    private String getAuthorityFromPermission(Context context, String s) {
        if (s == null) return null;
        List list = context.getPackageManager().getInstalledPackages(8);
        if (list != null) {
            Iterator iterator = list.iterator();
            do {
                if (!iterator.hasNext()) break;
                ProviderInfo aproviderinfo[] = ((PackageInfo) iterator.next()).providers;
                if (aproviderinfo != null) {
                    int i = aproviderinfo.length;
                    int j = 0;
                    while (j < i) {
                        ProviderInfo providerinfo = aproviderinfo[j];
                        if (s.equals(providerinfo.readPermission)) return providerinfo.authority;
                        if (s.equals(providerinfo.writePermission)) return providerinfo.authority;
                        j++;
                    }
                }
            } while (true);
        }
        return null;
    }
    private List getHomes() {
        ArrayList arraylist = new ArrayList();
        PackageManager packagemanager = getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        for (Iterator iterator = packagemanager.queryIntentActivities(intent, 0x10000).iterator(); iterator.hasNext(); arraylist
                .add(((ResolveInfo) iterator.next()).activityInfo.packageName));
        return arraylist;
    }
    private void handleLogin() {
        SoftwareManager.getInstace().initAll();
        startMainActivity();
        int i = pref.getFirstInstallVersionCode();
        int j = DataCollectUtil.getVersionCode();
        if (i != j) {
            startActivity(new Intent(this, GuideActivity.class));
            pref.saveFirstInstallVersionCode(j);
        }
        byte byte0 = 2;
        if (ProjectConfig.showThreeSoftupdata.booleanValue()) byte0 = 3;
        int k = pref.getIntoMarketTimes();
        if (k < byte0) {
            int l = k + 1;
            pref.setIntoMarketTimes(l);
        } else {
            UpdateManager.getInstance().checkUpdate(this);
        }
        finish();
    }
    private boolean hasShortcut() {
        boolean flag;
        String s = getAuthorityFromPermission(this, "com.android.launcher.permission.READ_SETTINGS");
        if (s == null) s = getAuthorityFromPermission(this, "com.android.launcher.permission.WRITE_SETTINGS");
        if (s == null) {
            DLog.e("GoApkLoginAndRegister", "hasShortcut()# no authority");
            return false;
        }
        DLog.i("GoApkLoginAndRegister", new StringBuilder().append("hasShortcut()#  authority=").append(s).toString());
        Cursor cursor;
        try {
            String s1 = getResources().getString(biz.AR.string.shortcut_name);
            Uri uri = Uri.parse(new StringBuilder().append("content://").append(s).append("/favorites?notify=true").toString());
            cursor = getContentResolver().query(uri, null, "title=?", new String[] { s1 }, null);
        } catch (Exception exception) {
            DLog.e("GoApkLoginAndRegister", "hasShortcut()#excption", exception);
            return false;
        }
        if (cursor == null) return false;
        if (cursor.getCount() > 0) flag = true;
        else flag = false;
        cursor.close();
        DLog.i("GoApkLoginAndRegister", new StringBuilder().append("hasShortcut()#  hasShortcut=").append(flag).toString());
        return flag;
    }
    private boolean isHome() {
        List list = ((ActivityManager) getSystemService("activity")).getRunningTasks(1);
        return getHomes().contains(((android.app.ActivityManager.RunningTaskInfo) list.get(0)).topActivity.getPackageName());
    }
    private void loadSplash() {
        loginbg = (ImageView) findViewById(biz.AR.id.loginbg);
        loadclose = (LinearLayout) findViewById(biz.AR.id.login_close);
        String s = pref.getSplashUrl();
        if (s != null && !s.equals("")) {
            DLog.i("GoApkLoginAndRegister", new StringBuilder().append("loadSplash#url!=null").append(s).toString());
            final RecommendAdInfo adinfo = (RecommendAdInfo) cacheDataManager.getCacheDataToFile2(this, "login_ad_data");
            delaytime = 2700L;
            ImageLoader.getInstance().displayImage(adinfo.getUrl(), loginbg, options);
            loginbg.setOnTouchListener(new android.view.View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionevent) {
                    if (motionevent.getAction() == 0) {
                        //L1_L1:
                        downX = motionevent.getRawX();
                    } else if (motionevent.getAction() == 2) {
                        if (motionevent.getAction() != 1 || downX - motionevent.getRawX() >= 20F || motionevent.getRawX() - downX >= 20F) return true;
                        isBanner = true;
                        BannerstartUtil.startBannerDetails(adinfo, GoApkLoginAndRegister.this, datainfo);
                        return true;
                    } else {
                        if (downX - motionevent.getRawX() > 20F) {
                            startMainActivity();
                            finish();
                            overridePendingTransition(biz.AR.anim.login_left_in, biz.AR.anim.login_left_out);
                        }
                        return true;
                    }
                    return true;
                }
            });
            loadclose.setVisibility(0);
            return;
        } else {
            DLog.i("GoApkLoginAndRegister", "loadSplash#url=null");
            delaytime = 100L;
            loginbg.setBackgroundResource(biz.AR.drawable.loading);
            return;
        }
    }
    private void login() {
        DataCollectManager.addRecord(datainfo, new String[0]);
        if (pref.getFirstInstallVersionCode() != DataCollectUtil.getVersionCode()) pref.setSplashUrl("");
        loadSplash();
        LocalSearchManager.getLocalSearchData(getApplicationContext());
        DLog.i("DEBUG", "\u8FD0\u884C\u6613\u7528\u6C47 - \u542F\u52A8\u63A7\u5236\u5668\u76D1\u6D4B\u5668");
        ControlerReceiver.runControlerMonitor(this);
        if (!ProjectConfig.noshowCut.booleanValue() && !pref.isCreatedShortcut() && !hasShortcut()) {
            addShortCut();
            pref.setCreatedShortcut(true);
        }
        loginManager.getSplashUrl();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isHome()) {
                    DLog.i("lilijun", "\u5F53\u524D\u8FDB\u7A0B\u4E0D\u662F\u6613\u7528\u6C47\u3001\u4E0D\u8FDB\u5165\u4E3B\u9875\u9762");
                    finish();
                } else if (!isBanner) {
                        DLog.i("lilijun", "\u5F53\u524D\u8FDB\u7A0B\u4E3A\u6613\u7528\u6C47\u3001\u8FDB\u5165\u4E3B\u9875\u9762");
                        handleLogin();
                        return;
                    }
            }
        }, delaytime);
    }
    @Override
    public void onBackPressed() {}
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        datainfo = new DataCollectInfo();
        datainfo.setPage("5");
        if (isLogin) {
            startMainActivity();
            finish();
            return;
        }
        isLogin = true;
        isMarketRunning = true;
        cacheDataManager = CacheDataManager.getInstance();
        loginManager = new LoginManager(this, handler);
        pref = MarketPreferences.getInstance(this);
        setContentView(biz.AR.layout.main_splash);
        if (pref.isShowGprsTips()) {
            showGprsDialog();
            return;
        } else {
            login();
            return;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isBanner) {
            startMainActivity();
            finish();
        }
    }
    private void showGprsDialog() {
        isShowGprs = Boolean.valueOf(pref.isShowGprsTips());
        MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(this);
        View view = View.inflate(this, biz.AR.layout.dialog_gprs, null);
        marketfloatedialogbuilder.setCancelable(false);
        marketfloatedialogbuilder.setCenterView(view, null);
        marketfloatedialogbuilder.setDialogHeight((int) getResources().getDimension(biz.AR.dimen.gprs_dialog_height));
        final Dialog dialog = marketfloatedialogbuilder.crteate();
        ((CheckBox) view.findViewById(biz.AR.id.gprs_checkbox)).setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag) {
                isShowGprs = Boolean.valueOf(flag);
            }
        });
        marketfloatedialogbuilder.setLeftButton("\u9000\u51FA", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                dialog.dismiss();
                finish();
                Process.killProcess(Process.myPid());
                System.exit(0);
            }
        });
        marketfloatedialogbuilder.setRightButton("\u786E\u5B9A", new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                MarketPreferences marketpreferences = pref;
                boolean flag;
                if (!isShowGprs.booleanValue()) flag = true;
                else flag = false;
                marketpreferences.setShowGprsTips(flag);
                dialog.dismiss();
                login();
            }
        });
        dialog.setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialoginterface) {
                if (!isFinishing()) {
                    finish();
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                }
            }
        });
        dialog.show();
    }
    private void showStartAnimation() {
        ImageView imageview = (ImageView) findViewById(biz.AR.id.loading_anim);
        imageview.setVisibility(0);
        imageview.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), biz.AR.anim.progressbar_animate));
    }
    private void startMainActivity() {
        Intent intent = new Intent(this, MainNewActivity.class);
        intent.putExtra("isShowPersonalityRecommend", getIntent().getBooleanExtra("isShowPersonalityRecommend", false));
        startActivity(intent);
    }
    /*
        static float access$302(GoApkLoginAndRegister goapkloginandregister, float f)
        {
            goapkloginandregister.downX = f;
            return f;
        }

    */
    /*
        static boolean access$502(GoApkLoginAndRegister goapkloginandregister, boolean flag)
        {
            goapkloginandregister.isBanner = flag;
            return flag;
        }

    */
    /*
        static Boolean access$902(GoApkLoginAndRegister goapkloginandregister, Boolean boolean1)
        {
            goapkloginandregister.isShowGprs = boolean1;
            return boolean1;
        }

    */
}
