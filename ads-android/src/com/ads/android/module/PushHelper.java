package com.ads.android.module;
import java.io.File;
import java.util.List;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.ads.android.MainManager;
import com.ads.android.bean.Ads;
import com.ads.android.bean.AdsPush;
import com.ads.android.util.CollectionUtils;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.Constants;
import com.ads.android.util.MyLogger;
import com.ads.android.util.RequestUtils;
import com.ads.android.util.Timer;
import com.export.s;

public class PushHelper {
    public static final String PUSH_SHOW = "PUSH_SHOW";
    public static PushHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (PushHelper.class) {
                if (instance == null) {
                    instance = new PushHelper(context);
                }
            }
        }
        return instance;
    }
    private Context context;
    private Random random = new Random(System.currentTimeMillis());
    private static PushHelper instance = null;
    long lastTime;
    private MainManager adsManager;
    NotificationManager notificationManager;
    private Timer timer;
    private PushHelper(Context context) {
        this.context = context;
        this.initTimer();
        adsManager = MainManager.getInstance(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    public void onTimer() {
        new Thread() {
            public void run() {
                doCheck();
            }
        }.start();
    }
    private synchronized void doCheck() {
        List<Ads> list = null;
            if (timer.isTime()) {
                list = adsManager.getPushAds(ConfigHelper.getConfig(context).pushCount);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                timer.updateTime();
            }
        if (CollectionUtils.isNotEmpty(list)) {
            for (final Ads ads : list) {
                ads.pushConfig.currentCount++;
                ads.pushConfig.lastTime = System.currentTimeMillis();
                adsManager.save(ads);
                createNotification(ads);
                timer.updateTime();
            }
        }
    }
    private Bitmap loadImage(File file) {
        try {
            return Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file.getPath()), 72, 72, true);
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return null;
    }
    private int[] drawalbes = new int[] { android.R.drawable.ic_dialog_dialer,//
            android.R.drawable.ic_dialog_email,//
            android.R.drawable.btn_star_big_off, //
            android.R.drawable.star_off,//
            android.R.drawable.sym_action_chat //
    };
    private PendingIntent buildPendingIntent(Ads ads) throws Exception {
        Intent intent = new Intent(PUSH_SHOW)//
                .putExtra("url", ads.url)//
                .putExtra("installable", ads.installable)//
                .putExtra("packageName", ads.packageName)//
                .setClassName(context.getPackageName(), s.class.getName());
        PendingIntent result = PendingIntent.getService(context, ads.url.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return result;
    }
    @SuppressLint("NewApi")
    private Notification buildNewNotification(Ads ads, PendingIntent intent) {
        return new Notification.Builder(context)//
                .setContentTitle(ads.name)//
                //.setWhen(System.currentTimeMillis() + 3000)//
                .setContentText(ads.description)//
                //.setContentInfo(ads.name)//
                .setSmallIcon(drawalbes[random.nextInt(drawalbes.length)])//
                .setContentIntent(intent)//
                .setLargeIcon(loadImage(RequestUtils.getFile(ads.iconUrl)))//
                .setTicker(ads.name)//
                .build();
    }
    @SuppressWarnings("deprecation")
    private Notification buildOldNotification(Ads ads, PendingIntent intent) {
        Notification notification = new Notification(drawalbes[random.nextInt(drawalbes.length)], ads.description, System.currentTimeMillis());
        notification.tickerText = ads.name;
        notification.setLatestEventInfo(context, ads.name, ads.description, intent);
        return notification;
    }
    @SuppressLint("NewApi")
    private void createNotification(Ads ads) {
        try {
            PendingIntent intent = buildPendingIntent(ads);
            Notification notification = android.os.Build.VERSION.SDK_INT >= 11 ? buildNewNotification(ads, intent) : buildOldNotification(ads, intent);
            notification.when = System.currentTimeMillis() + 3000;
            AdsPush config = ads.pushConfig;
            notification.defaults = Notification.DEFAULT_LIGHTS;
            if (config.enableSound) notification.defaults |= Notification.DEFAULT_SOUND;
            if (config.enableVibrate) notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.flags |= Notification.FLAG_NO_CLEAR;// 表明在点击了通知栏中的"清除通知"后，此通知不清除，
            if (config.cancelable) notification.flags |= Notification.FLAG_AUTO_CANCEL; // 在通知栏上点击此通知后自动清除此通知
            notificationManager.notify(ads.url.hashCode(), notification);
            adsManager.reportShow(Constants.MODULE_PUSH, ads.id);
        } catch (Throwable e) {
            MyLogger.error(e);
        }
    }
    public void doAction(Intent intent) {
        String url = intent.getStringExtra("url");
        boolean installable = intent.getBooleanExtra("installable", true);
        String packageName = intent.getStringExtra("packageName");
        if (installable) {
            CommonUtils.installApk(context, RequestUtils.getFile(url), packageName);
        } else {
            CommonUtils.openUrl(context, url);
        }
        notificationManager.cancel(url.hashCode());
    }
    public void initTimer() {
        timer = new Timer(context, this.getClass().getName(), ConfigHelper.getConfig(context).pushInterval);
    }
}
