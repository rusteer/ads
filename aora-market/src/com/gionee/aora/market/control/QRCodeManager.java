// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.google.zxing.client.android.CaptureActivity;

public class QRCodeManager extends BroadcastReceiver {
    public static final void executeResult(Context context, String s) {
        if (s != null && !s.equals("")) {
            Matcher matcher = Pattern.compile("https?://[\\w-]+[\\w-\\.,@?=%&;:/~+#]*").matcher(s);
            if (matcher.find()) {
                s = matcher.group();
                DLog.v(new StringBuilder().append("matcher result=").append(s).toString());
            }
            if (s.startsWith("http")) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setFlags(0x10000000);
                    intent.setData(Uri.parse(s));
                    context.startActivity(intent);
                    return;
                } catch (Exception exception) {
                    DLog.e(exception.fillInStackTrace().toString());
                }
                Toast.makeText(context, "\u9519\u8BEF\u7684\u7F51\u5740", 0).show();
                return;
            }
            Matcher matcher1 = Pattern.compile("[0-9a-zA-Z.]+").matcher(s);
            if (matcher1.find()) {
                String s1 = matcher1.group();
                DLog.v(new StringBuilder().append("\u975E\u6613\u7528\u6C47\u5185\u90E8\u63A5\u53E3matcher result=").append(s1).toString());
                if (Pattern.compile("[0-9]*").matcher(s1).matches()) {
                    Toast.makeText(context, "\u626B\u63CF\u5185\u5BB9\u65E0\u6CD5\u8BC6\u522B", 0).show();
                    return;
                } else {
                    IntroductionDetailActivity.startIntroductionActivityForPackageName(context, s1, null);
                    return;
                }
            } else {
                Toast.makeText(context, "\u626B\u63CF\u5185\u5BB9\u65E0\u6CD5\u8BC6\u522B", 0).show();
                return;
            }
        } else {
            Toast.makeText(context, "\u626B\u63CF\u5185\u5BB9\u65E0\u6CD5\u8BC6\u522B", 0).show();
            return;
        }
    }
    public static final String getScanResult(int i, Intent intent) {
        if (i == 11 && intent != null) return intent.getStringExtra("QRCODE");
        else return null;
    }
    public static final void getScanResultAndExec(Context context, int i, int j, Intent intent) {
        if (i != 11) {
            return;
        } else {
            DLog.v(new StringBuilder().append("requestCode=").append(i).append(",resultCode").append(j).append(",data=").append(intent).toString());
            String s = getScanResult(j, intent);
            DLog.v(new StringBuilder().append("result=").append(s).toString());
            executeResult(context, s);
            return;
        }
    }
    public static final void startScan(Activity activity) {
        activity.startActivityForResult(new Intent(activity, CaptureActivity.class), 11);
    }
    public QRCodeManager() {}
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.Zxing.Demo.CaptureActivity.scanfinish") && intent.hasExtra("QRCODE")) executeResult(context, intent.getStringExtra("QRCODE"));
    }
}
