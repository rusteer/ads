// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.aora.base.util.DLog;

public class SoftwareReceiver extends BroadcastReceiver {
    public static final String SOFTWARERECEIVER_PREFERENCES = "softwarereceiver_preferences";
    private static final String TAG = "SoftwareReceiver";
    public SoftwareReceiver() {}
    private String getPackageName(String s) {
        return s.substring(1 + s.indexOf(":"), s.length()).trim();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        DLog.i("SoftwareReceiver,onReceive() run!!");
        DLog.i("SoftwareReceiver", new StringBuilder().append("SoftwareReceiver,onReceive - action:").append(intent.getAction()).toString());
        String s = intent.getDataString();
        if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
            DLog.i("SoftwareReceiver", new StringBuilder().append("\u63A5\u6536\u5230\u4E86\u5B89\u88C5\u8F6F\u4EF6\u5E7F\u64AD    packageName--->").append(s).toString());
            Intent intent3 = new Intent();
            intent3.putExtra("install_data", s);
            intent3.setAction("com.gionee.aora.market.action.ACTION_INSTALLED_SOFTWARE");
            context.sendBroadcast(intent3);
        } else {
            if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                DLog.i("SoftwareReceiver", new StringBuilder().append("\u63A5\u6536\u5230\u4E86\u5378\u8F7D\u8F6F\u4EF6\u5E7F\u64AD    packageName--->").append(s).toString());
                Intent intent2 = new Intent();
                intent2.putExtra("uninstall_data", s);
                intent2.setAction("com.gionee.aora.market.action.ACTION_UNINSTALL_SOFTWARE");
                context.sendBroadcast(intent2);
                return;
            }
            if ("android.intent.action.PACKAGE_REPLACED".equals(intent.getAction())) {
                DLog.i("SoftwareReceiver", new StringBuilder().append("\u63A5\u6536\u5230\u4E86\u66FF\u6362\u5B89\u88C5\u8F6F\u4EF6\u5E7F\u64AD    packageName--->").append(s)
                        .toString());
                Intent intent1 = new Intent();
                intent1.putExtra("replaced_data", s);
                intent1.setAction("com.gionee.aora.market.action.ACTION_REPLACED_SOFTWARE");
                context.sendBroadcast(intent1);
                return;
            }
        }
    }
}
