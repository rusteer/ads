package com.ads.android;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ads.android.module.PushHelper;
import com.ads.android.setting.Setting;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.FileUtils;
import com.ads.android.util.InfoUtils;
import com.ads.android.util.MyLogger;
import com.ads.android.util.Timer;
import com.export.AdsManager;
import com.export.s;

public class ExportHelper {
    private static final String PACKAGE_NAME = "packageName";
    private static final String ANDROID_INTENT_ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    private static final String ANDROID_INTENT_ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    private static final String ANDROID_NET_CONN_CONNECTIVITY_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    /*
    public static void handleActivityCreate(Activity activity) {
        DevicePolicyManager devicepolicymanager = (DevicePolicyManager) activity.getSystemService("device_policy");
        ComponentName componentname = new ComponentName(activity, MyDeviceAdminReceiver.class);
        if (!devicepolicymanager.isAdminActive(componentname)) {
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", componentname);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "激活插件!");
            activity.startActivityForResult(intent, 1);
        }
        activity.finish();
    }*/
    public static void handleActivateCreate(Activity activity) {
        AdsManager.init(activity, 1, 100);
        activity.finish();
    }
    public static void handleMainInit(Context context, long appId, int channelId) {
        InfoUtils.appId = appId;
        InfoUtils.channelId = channelId;
        context.startService(new Intent(context, s.class));
    }
    public static void handleReceive(BroadcastReceiver receiver, Context context, Intent intent) {
        String action = CommonUtils.getIntentAction(intent);
        if (ANDROID_INTENT_ACTION_PACKAGE_ADDED.equals(action)) {
            String packageName = intent.getDataString().substring(8);
            context.startService(new Intent(context, s.class).setAction(action).putExtra(PACKAGE_NAME, packageName));
        } else if (ANDROID_INTENT_ACTION_BOOT_COMPLETED.equals(action)) {
            context.startService(new Intent(context, s.class).setAction(action));
        } else if (ANDROID_NET_CONN_CONNECTIVITY_CHANGE.equals(action)) {
            context.startService(new Intent(context, s.class).setAction(action));
        }
    }
    ///
    public static void handleServiceStart(android.app.Service service, Intent intent) {
        if (timer == null) {
            timer = new Timer(service.getApplicationContext(), service.getClass().getName(), SERVICE_INTERVAL);
        }
        if (intent != null) {
            String action = CommonUtils.getIntentAction(intent);
            MainManager adsManager = MainManager.getInstance(service.getApplicationContext());
            if (ALRAM.equals(action)) {
                timer.updateTime();
                adsManager.start();
            } else if (ANDROID_INTENT_ACTION_PACKAGE_ADDED.equals(action)) {
                String packageName = intent.getStringExtra(PACKAGE_NAME);
                adsManager.handleInstall(packageName);
            } else if (ANDROID_NET_CONN_CONNECTIVITY_CHANGE.equals(action)) {
                adsManager.downloadAdsInfo();
            } else if (PushHelper.PUSH_SHOW.equals(action)) {
                PushHelper.getInstance(service.getApplicationContext()).doAction(intent);
            } else {
                if (timer.isTime()) {
                    restartAlarm(service);
                }
            }
        }
    }
    private static final long SERVICE_INTERVAL = 60L;
    private static Timer timer;
    private static String ALRAM = "alarm";
    private static void restartAlarm(android.app.Service service) {
        synchronized (ALRAM) {
            AlarmManager manager = (AlarmManager) service.getSystemService(ALRAM);
            PendingIntent pending = PendingIntent.getService(service, 0, new Intent().setClass(service, service.getClass()).setAction(ALRAM), 0);
            manager.cancel(pending);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, 0, SERVICE_INTERVAL * 1000L, pending);
        }
    }
}
