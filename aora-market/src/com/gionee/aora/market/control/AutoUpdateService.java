// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import com.aora.base.util.DLog;

// Referenced classes of package com.gionee.aora.market.control:
//            MarketPreferences, SoftWareUpdateManager
public class AutoUpdateService extends Service {
    private BroadcastReceiver batteryChangedReceiver;
    private boolean isIn;
    private int type;
    public AutoUpdateService() {
        type = 1;
        isIn = true;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        type = MarketPreferences.getInstance(this).getAutoUpdateType();
        DLog.d("denglh", new StringBuilder().append("\u7C7B\u578B").append(type).toString());
        if (type == 1) {
            stopSelf();
            return;
        } else {
            batteryChangedReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int i;
                    int j;
                    label0: {
                        if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                            i = intent.getIntExtra("level", 0);
                            j = intent.getIntExtra("scale", 100);
                            if (i * 100 / j > 40) break label0;
                            stopSelf();
                        }
                        return;
                    }
                    DLog.d("denglh", new StringBuilder().append("\u7535\u91CF").append(i * 100 / j).toString());
                    SoftWareUpdateManager.getInstance().addToDownloadManager(type, isIn);
                    stopSelf();
                }
            };
            registerReceiver(batteryChangedReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            return;
        }
    }
    @Override
    public void onDestroy() {
        if (batteryChangedReceiver != null) try {
            unregisterReceiver(batteryChangedReceiver);
        } catch (Exception exception) {
            DLog.e("ShowBatteryActivity", "onDestroy()#exception =", exception);
        }
        super.onDestroy();
    }
    @Override
    public int onStartCommand(Intent intent, int i, int j) {
        isIn = intent.getBooleanExtra("IS_IN", true);
        return super.onStartCommand(intent, i, j);
    }
}
