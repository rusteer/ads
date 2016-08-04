package com.bangbang.ads.android;
import java.util.Map;
import workspace.util.Constants;
import workspace.util.MyLogger;
import workspace.util.ReportUtils;
import workspace.util.WUtil;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class WmfReciver extends BroadcastReceiver {
    private Context context;
    public WmfReciver() {}
    private void a(String s) {
        SharedPreferences pref = context.getSharedPreferences(Constants.downloadLock, 0);
        Editor editor = pref.edit();
        SharedPreferences sharedpreferences1 = context.getSharedPreferences("", 0);
        Editor editor1 = sharedpreferences1.edit();
        Map<String, ?> map = pref.getAll();
        for (String key : map.keySet()) {
            String value = (String) map.get(key);
            int notiId = Integer.parseInt(key);
            if (s.equals(value)) {
                ((NotificationManager) context.getSystemService("notification")).cancel(notiId);
                editor1.remove(key);
                editor1.commit();
                editor.remove(key);
                editor.commit();
                ReportUtils.apkInstalledReport();
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(value);
                context.startActivity(intent);
                return;
            }
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.context = context;
        if (Constants.context == null) {
            Constants.context = context;
        }
        if (action.equals("android.intent.action.SCREEN_OFF")) {
            WUtil.b(context);
        }
        if (action.equals("android.intent.action.BOOT_COMPLETED") || action.equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, WmfService.class));
            WUtil.b(context);
        }
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String s1 = intent.getDataString().substring(8);
            MyLogger.info("info", new StringBuilder("==packagename:").append(s1).toString());
            a(s1);
        }
    }
}
