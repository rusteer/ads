package com.ads.android.util;
import java.io.File;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;

public class CommonUtils {
    public static boolean apkExits(Context context, String packageName) {
        for (PackageInfo info : context.getPackageManager().getInstalledPackages(8192)) {
            if (info.packageName.equals(packageName)) { return true; }
        }
        return false;
    }
    public static String getIntentAction(Intent intent) {
        return intent == null ? null : intent.getAction();
    }
    public static void startApp(Context context, String packageName) {
        android.content.Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s);
    }
    public static void installApk(Context context, File file, String s) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    public static void openUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(it);
    }
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static String getPassword(Context context) {
        return getPasswordByPackageName(context.getPackageName());
    }
    private static String getPasswordByPackageName(String packageName) {
        if (packageName.length() >= 16) {
            return packageName.substring(0, 16);
        } else {
            while (packageName.length() < 16) {
                packageName += "w";
            }
            return packageName;
        }
    }
}
