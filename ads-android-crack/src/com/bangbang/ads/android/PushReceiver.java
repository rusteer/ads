package com.bangbang.ads.android;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.push.MyDownload;
import workspace.push.PLoader;
import workspace.util.MyLogger;
import workspace.util.ReportUtils;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;

public class PushReceiver extends BroadcastReceiver {
    private static void a(String s) {
        Iterator<App> iterator = getBean2List().iterator();
        App bean2;
        do {
            if (!iterator.hasNext()) { return; }
            bean2 = iterator.next();
        } while (!s.equals(bean2.id));
        MyLogger.info("info", new StringBuilder("=====").append(s).toString());
        downloadAD(bean2);
    }
    private static void b(String s) {
        SharedPreferences sharedpreferences1 = CONTEXT.getSharedPreferences(PUSH_DL_PAKNAME_SP_TAG, 0);
        Editor editor = sharedpreferences1.edit();
        Editor editor1 = CONTEXT.getSharedPreferences(PUSH_DOWNLOADING_SP_TAG, 0).edit();
        Iterator iterator = sharedpreferences1.getAll().entrySet().iterator();
        int l;
        String value;
        String key;
        do {
            if (!iterator.hasNext()) { return; }
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            value = (String) entry.getValue();
            key = (String) entry.getKey();
            l = Integer.parseInt(key);
        } while (!s.equals(value));
        ((NotificationManager) CONTEXT.getSystemService("notification")).cancel(l);
        editor1.remove(key);
        editor1.commit();
        editor.remove(key);
        editor.commit();
        //String appId = CONTEXT.getSharedPreferences(Constants.PUSH_SHOW_TAG, 0).getString("appid", "");
        //String s4 = PUtil.encodeInfo(CONTEXT);
        //String s5 = CONTEXT.getSharedPreferences(new StringBuilder(String.valueOf(Constants.PUSH_TAG)).append("Version").toString(), 0).getString("version", "0.0.0");
        //PUtil.httpGet(new StringBuilder(String.valueOf(CS.apkInstalled)).append("uuid=").append(s4).append("&app_id=").append(appId).append("&ad_id=").append(l).append("&ad_type=").append(3).append("&version=").append(s5).append("&survivaltime=").append(k).toString());
        ReportUtils.apkInstalledReport();
        Intent intent = CONTEXT.getPackageManager().getLaunchIntentForPackage(value);
        CONTEXT.startActivity(intent);
    }
    private static void downloadAD(App bean2) {
        MyDownload.download(CONTEXT, bean2.id);
        //Intent intent = new Intent(CONTEXT, DownLoadService.class).setAction("download");
        //intent.putExtra("id", bean2.id);
        //CONTEXT.startService(intent);
    }
    private static List<App> getBean2List() {
        String s = CONTEXT.getSharedPreferences(PUSH_SHOW_TAG, 0).getString("ImgJson", "");
        List<App> list = new ArrayList<App>();
        try {
            JSONArray jsonarray = new JSONArray(s);
            list = Json.optList(App.class, jsonarray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void handleReceive(Context context, Intent intent) {
        CONTEXT = context;
        INTENT = intent;
        SharedPreferences pref = CONTEXT.getApplicationContext().getSharedPreferences(PUSH_TAG + "Version", 0);
        String action = INTENT.getAction();
        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
            String s = INTENT.getDataString().substring(8);
            MyLogger.info("info", new StringBuilder("==packagename:").append(s).toString());
            b(s);
        }
        if (action.equals("com.android.psoho.init.StartDLReceiver")) {
            MyLogger.info("info", "调用DL，开始下载。。。。");
            a(INTENT.getExtras().getString("imageId"));
        }
        if (action.equals("android.intent.action.USER_PRESENT") || action.equals("android.intent.action.BOOT_COMPLETED")) {
            new PLoader().init(CONTEXT.getApplicationContext(), pref.getString("appid", ""));
        }
        ConnectivityManager connectivitymanager = (ConnectivityManager) CONTEXT.getSystemService("connectivity");
        android.net.NetworkInfo.State state = connectivitymanager.getNetworkInfo(1).getState();
        android.net.NetworkInfo.State state1 = connectivitymanager.getNetworkInfo(0).getState();
        if (state != null && state1 != null && android.net.NetworkInfo.State.CONNECTED != state && android.net.NetworkInfo.State.CONNECTED == state1) {
            reDownloading();
        } else {
            if (state != null && state1 != null && android.net.NetworkInfo.State.CONNECTED != state && android.net.NetworkInfo.State.CONNECTED != state1) {
                stopDownloading();
                return;
            }
            if (state != null && android.net.NetworkInfo.State.CONNECTED == state) {
                reDownloading();
                return;
            }
        }
    }
    private static void reDownloading() {
        MyDownload.reDownLoading(CONTEXT);
    }
    private static void stopDownloading() {
        MyDownload.stopDownLoading(CONTEXT);
    }
    private static final String PUSH_SHOW_TAG = "pushShow";
    private static final String PUSH_DOWNLOADING_SP_TAG = "pushDownLoadingSp";
    private static final String PUSH_DL_PAKNAME_SP_TAG = "pushDLPakNameSp";
    public static final String PUSH_TAG = "Push";
    private static Context CONTEXT;
    private static Intent INTENT;
    @Override
    public void onReceive(Context context, Intent intent) {
        handleReceive(context, intent);
    }
}
