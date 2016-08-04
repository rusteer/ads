package com.ads.android.module;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.Timer;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

public class TaskManager {
    private static final String ACTIVITY = "activity";
    private static String lastPackageName = null;
    private static final String blackPackages = "com.kingroot.kinguser,";
    private static boolean topActivityTouching = false;
    private static Timer timer;
    public static void touch(final Context context) {
        if (timer == null) {
            timer = new Timer(context, TaskManager.class.getName(), 60);
        }
        new Thread() {
            @Override
            public void run() {
                touchTopActivity(context);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                touchTimer(context);
            }
        }.start();
    }
    public static void initTimer(Context context) {
        BannerHelper.getInstance(context).initTimer();
        PopHelper.getInstance(context).initTimer();
        PushHelper.getInstance(context).initTimer();
        ShortcutHelper.getInstance(context).initTimer();
    }
    public static void touchTopActivity(Context context) {
        if (topActivityTouching) return;
        topActivityTouching = true;
        final BannerHelper bannerManager = BannerHelper.getInstance(context);
        final PopHelper popManager = PopHelper.getInstance(context);
        while (true) {
            ActivityManager activitymanager = (ActivityManager) context.getSystemService(ACTIVITY);
            ComponentName componentName = activitymanager.getRunningTasks(1).get(0).topActivity;
            String packageName = componentName.getPackageName();
            if (!blackPackages.contains(packageName) && !context.getPackageName().equals(packageName)) {
                if (!packageName.equals(lastPackageName)) {
                    lastPackageName = packageName;
                    new Thread() {
                        @Override
                        public void run() {
                            if (!bannerManager.checkPackageChange(lastPackageName)) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        popManager.checkPackageChange(lastPackageName);
                                    }
                                }.start();
                            }
                        }
                    }.start();
                }
            }
            CommonUtils.sleep(1000);
        }
    }
    private static void touchTimer(Context context) {
        if (timer.isTime()) {
            PushHelper.getInstance(context).onTimer();
            ShortcutHelper.getInstance(context).onTimer();
            timer.updateTime();
        }
    }
}
