package com.ads.android.module;
import java.io.File;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ads.android.MainManager;
import com.ads.android.bean.Ads;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.Constants;
import com.ads.android.util.RequestUtils;
import com.ads.android.util.Timer;

public class BannerHelper {
    public static BannerHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (PushHelper.class) {
                if (instance == null) {
                    instance = new BannerHelper(context);
                }
            }
        }
        return instance;
    }
    private RelativeLayout bannerLayout;
    private int height;
    private int width;
    private ImageView icoCancle;
    private RelativeLayout rlICOcancle;
    private RelativeLayout rlicon;
    private android.widget.RelativeLayout.LayoutParams rlpICOcancle;
    private android.widget.RelativeLayout.LayoutParams rlpicon;
    private WindowManager windowManager;
    private Context context;
    private Handler handler;
    private static BannerHelper instance = null;
    private boolean bannerShowed;
    protected Ads APP;
    private MainManager adsManager;
    private Timer timer;
    private BannerHelper(final Context context) {
        this.context = context;
        initTimer();
        adsManager = MainManager.getInstance(context);
        handler = new Handler(context.getMainLooper());
        handler.post(new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                width = display.getWidth();
                height = display.getHeight();
                bannerLayout = new RelativeLayout(context);
            }
        });
    }
    private Object locker = new Object();
    public boolean checkPackageChange(String lastPackageName) {
        boolean result = false;
        Ads ads = null;
        synchronized (locker) {
            if (lastAds != null && lastAds.bannerConfig.autoExit) {
                hide();
            }
            if (timer.isTime()) ads = adsManager.getBannerAds(lastPackageName);
            if (result = (ads != null)) timer.updateTime();
        }
        if (ads != null) showBanner(ads);
        return result;
    }
    private void showBanner(final Ads ads) {
        lastAds = ads;
        hide();
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                synchronized (locker) {
                    ads.bannerConfig.currentCount++;
                    ads.bannerConfig.lastTime = System.currentTimeMillis();
                    adsManager.save(ads);
                    show(ads);
                }
            }
        });
    }
    public void initTimer() {
        timer = new Timer(context, this.getClass().getName(), ConfigHelper.getConfig(context).bannerInterval);
    }
    private Ads lastAds;
    public void hide() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                bannerShowed = false;
                bannerLayout.setVisibility(View.GONE);
                try {
                    windowManager.removeView(bannerLayout);
                } catch (Exception e) {}
            }
        });
    }
    private void show(final Ads app) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (bannerShowed) { return; }
                bannerShowed = true;
                doShow(app);
            }
        });
    }
    private void doShow(final Ads ads) {
        android.view.WindowManager.LayoutParams layoutparams = new android.view.WindowManager.LayoutParams(1024, height / 10);
        layoutparams.type = 2003;
        layoutparams.format = 1;
        layoutparams.gravity = 80;
        layoutparams.flags = 8;
        layoutparams.height = height / 10;
        bannerLayout.setBackgroundColor(Color.parseColor("#80000000"));
        ImageView imageview1 = new ImageView(context);
        try {
            imageview1.setImageBitmap(BitmapFactory.decodeFile(RequestUtils.getFile(ads.iconUrl).getAbsolutePath()));
        } catch (Exception e) {}
        imageview1.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        rlicon = new RelativeLayout(context);
        rlicon.setLayoutParams(new android.view.WindowManager.LayoutParams(-1, -1));
        int i = 8 * height / 100;
        rlpicon = new android.widget.RelativeLayout.LayoutParams(i, i);
        rlpicon.addRule(9);
        rlicon.addView(imageview1, rlpicon);
        TextView textview = new TextView(context);
        textview.setText(ads.name);
        textview.setTextColor(-1);
        textview.setTextSize(20F);
        textview.setPadding(height / 10, height / 100, 0, 0);
        bannerLayout.addView(rlicon, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        bannerLayout.addView(textview, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        bannerLayout.setVisibility(View.VISIBLE);
        bannerLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hide();
                String packageName = ads.packageName;
                if (CommonUtils.apkExits(context, packageName)) {
                    CommonUtils.startApp(context, packageName);
                    windowManager.removeView(bannerLayout);
                } else {
                    try {
                        File file = RequestUtils.getFile(ads.url);
                        CommonUtils.installApk(context, file, packageName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        bannerLayout.setPadding(height / 100, height / 100, height / 100, height / 100);
        try {
            windowManager.addView(bannerLayout, layoutparams);
            icoCancle = new ImageView(context);
            icoCancle.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("android/proj/ads_cancel.png")));
            icoCancle.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
            icoCancle.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bannerShowed = false;
                    bannerLayout.setVisibility(8);
                }
            });
            rlICOcancle = new RelativeLayout(context);
            rlICOcancle.setLayoutParams(new android.view.WindowManager.LayoutParams(-1, -1));
            int j = 1 * width / 15;
            rlpICOcancle = new android.widget.RelativeLayout.LayoutParams(j, j);
            rlpICOcancle.addRule(10);
            rlpICOcancle.addRule(11);
            bannerLayout.addView(rlICOcancle, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        adsManager.reportShow(Constants.MODULE_BANNER, ads.id);
    }
}
