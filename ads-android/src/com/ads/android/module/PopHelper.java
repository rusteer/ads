package com.ads.android.module;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import com.ads.android.MainManager;
import com.ads.android.bean.Ads;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.Constants;
import com.ads.android.util.RequestUtils;
import com.ads.android.util.Timer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class PopHelper {
    public static PopHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (PushHelper.class) {
                if (instance == null) {
                    instance = new PopHelper(context);
                }
            }
        }
        return instance;
    }
    private AlertDialog alertDialog;
    private int height;
    private int width;
    private ImageView imgCancle;
    private RelativeLayout layout;
    private RelativeLayout rlImgcancle;
    private android.widget.RelativeLayout.LayoutParams layoutparams;
    private android.widget.RelativeLayout.LayoutParams rlpImgcancle;
    private WindowManager windowManager;
    private Context context;
    private static PopHelper instance = null;
    private boolean popShowed;
    private long lastShowTime;
    private MainManager adsManager;
    private Timer timer;
    private PopHelper(final Context context) {
        this.context = context;
        this.initTimer();
        adsManager = MainManager.getInstance(context);
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                init(context);
            }
        });
    }
    public void initTimer() {
        timer = new Timer(context, this.getClass().getName(), ConfigHelper.getConfig(context).popInterval);
    }
    private Object locker = new Object();
    public boolean checkPackageChange(String lastPackageName) {
        Ads ads = null;
        boolean result = false;
        synchronized (locker) {
            if (timer.isTime()) {//
                ads = adsManager.getPopAds(lastPackageName);
            }
            if (result = (ads != null)) {//
                timer.updateTime();
            }
        }
        if (ads != null) showPop(ads);
        return result;
    }
    private void showPop(final Ads ads) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                synchronized (locker) {
                    ads.popConfig.currentCount++;
                    ads.popConfig.lastTime = System.currentTimeMillis();
                    adsManager.save(ads);
                    if (!popShowed) {//
                        if ((System.currentTimeMillis() - lastShowTime) / 1000 < 20) { return; }
                        lastShowTime = System.currentTimeMillis();
                        popShowed = true;
                        doShow(ads);
                    }
                }
            }
        });
    }
    @SuppressWarnings("deprecation")
    private void init(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        // width = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        //height = getWindow().getWindowManager().getDefaultDisplay().getHeight();
        alertDialog = new android.app.AlertDialog.Builder(context).create();
        alertDialog.requestWindowFeature(1);
        alertDialog.getWindow().setFlags(256, 256);
        alertDialog.getWindow().setType(2010);
        alertDialog.setCancelable(false);
        alertDialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialoginterface, int i, KeyEvent keyevent) {
                return i == 84 || i == 4;
            }
        });
        alertDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                handleDismiss();
            }
        });
        alertDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handleDismiss();
            }
        });
        layout = new RelativeLayout(context);
    }
    private void handleDismiss() {
        popShowed = false;
        lastShowTime = System.currentTimeMillis();
    }
    public boolean isScreenChange() {
        int i = context.getResources().getConfiguration().orientation;
        if (i == 2) { return true; }
        return i != 1 ? false : false;
    }
    @SuppressLint("ClickableViewAccessibility")
    private void doShow(final Ads ads) {
        layout.removeAllViewsInLayout();
        ImageView imageview = new ImageView(context);
        Window window = alertDialog.getWindow();
        android.view.WindowManager.LayoutParams layoutparams1 = window.getAttributes();
        layoutparams1.alpha = 1.0F;
        layoutparams1.dimAmount = 1.0F;
        window.setAttributes(layoutparams1);
        alertDialog.show();
        int k = width / 10;
        File file = RequestUtils.getFile(ads.screenUrl);
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (isScreenChange()) {
            layoutparams = new android.widget.RelativeLayout.LayoutParams(height, width);
            Matrix matrix = new Matrix();
            matrix.setRotate(-90F);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            k = height / 10;
        } else {
            layoutparams = new android.widget.RelativeLayout.LayoutParams(width, height);
        }
        imageview.setImageBitmap(bitmap);
        imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        alertDialog.setContentView(imageview, layoutparams);
        imageview.setOnTouchListener(new android.view.View.OnTouchListener() {
            private boolean clicked;
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                synchronized (this) {
                    if (clicked) { return true; }
                    clicked = true;
                }
                String packageName = ads.packageName;
                if (CommonUtils.apkExits(context, packageName)) {
                    alertDialog.dismiss();
                    CommonUtils.startApp(context, packageName);
                } else {
                    File file = RequestUtils.getFile(ads.url);
                    CommonUtils.installApk(context, file, packageName);
                }
                alertDialog.dismiss();
                handleDismiss();
                return true;
            }
        });
        imgCancle = new ImageView(context);
        try {
            imgCancle.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("android/proj/ads_cancel.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgCancle.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
        imgCancle.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                popShowed = false;
            }
        });
        rlImgcancle = new RelativeLayout(context);
        rlImgcancle.setLayoutParams(new android.view.WindowManager.LayoutParams(-1, -1));
        rlpImgcancle = new android.widget.RelativeLayout.LayoutParams(k, k);
        rlpImgcancle.addRule(10);
        if (new Random().nextInt(2) == 0) {
            rlpImgcancle.addRule(9);
        } else {
            rlpImgcancle.addRule(11);
        }
        alertDialog.addContentView(rlImgcancle, new android.widget.RelativeLayout.LayoutParams(-1, -1));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    rlImgcancle.removeViewInLayout(imgCancle);
                    rlImgcancle.addView(imgCancle, rlpImgcancle);
                    alertDialog.show();
                    return;
                } catch (Exception exception1) {
                    exception1.printStackTrace();
                }
                return;
            }
        }, ads.popConfig.cancelDelaySeconds * 1000L);
        adsManager.reportShow(Constants.MODULE_POP, ads.id);
    }
}
