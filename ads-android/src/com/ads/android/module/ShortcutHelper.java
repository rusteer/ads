package com.ads.android.module;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.ads.android.MainManager;
import com.ads.android.bean.Ads;
import com.ads.android.util.CollectionUtils;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.Constants;
import com.ads.android.util.RequestUtils;
import com.ads.android.util.Timer;

public class ShortcutHelper {
    private static final String DUPLICATE = "duplicate";
    private static final String APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE = "application/vnd.android.package-archive";
    private static final String ANDROID_INTENT_ACTION_VIEW = "android.intent.action.VIEW";
    private static final String COM_ANDROID_LAUNCHER_ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String ANDROID_INTENT_EXTRA_SHORTCUT_ICON = "android.intent.extra.shortcut.ICON";
    private static final String ANDROID_INTENT_EXTRA_SHORTCUT_INTENT = "android.intent.extra.shortcut.INTENT";
    private static final String ANDROID_INTENT_EXTRA_SHORTCUT_NAME = "android.intent.extra.shortcut.NAME";
    public static ShortcutHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (ShortcutHelper.class) {
                if (instance == null) {
                    instance = new ShortcutHelper(context);
                }
            }
        }
        return instance;
    }
    private static String PREF_APP_HINT_INSTALL_TODO = "plgdldddgggggappsp";
    private Context context;
    private static ShortcutHelper instance = null;
    private MainManager adsManager;
    private Timer timer;
    private ShortcutHelper(Context context) {
        this.context = context;
        this.initTimer();
        adsManager = MainManager.getInstance(context);
    }
    public void onTimer() {
        new Thread() {
            public void run() {
                doCheck();
            }
        }.start();
    }
    private synchronized void doCheck() {
        List<Ads> list = null;
        synchronized (this) {
            if (timer.isTime()) {
                list = adsManager.getShortcutAds(ConfigHelper.getConfig(context).shortcutCount);
            }
            if (CollectionUtils.isNotEmpty(list)) {
                timer.updateTime();
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            installShortcut(list);
        }
    }
    private void installShortcut(List<Ads> list) {
        for (final Ads ads : list) {
            if (!has(ads.name) && !isInstalled(ads.packageName)) {
                ads.shortcutConfig.currentCount++;
                ads.shortcutConfig.lastTime = System.currentTimeMillis();
                adsManager.save(ads);
                final boolean installable = ads.installable;
                if (installable) {
                    Intent viewIntent = new Intent();
                    viewIntent.addFlags(0x10000000);
                    viewIntent.setAction(ANDROID_INTENT_ACTION_VIEW);
                    viewIntent.setDataAndType(Uri.fromFile(RequestUtils.getFile(ads.url)), APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE);
                    Intent broadcaseIntent = new Intent(COM_ANDROID_LAUNCHER_ACTION_INSTALL_SHORTCUT);
                    broadcaseIntent.putExtra(ANDROID_INTENT_EXTRA_SHORTCUT_NAME, ads.name);
                    broadcaseIntent.putExtra(ANDROID_INTENT_EXTRA_SHORTCUT_INTENT, viewIntent);
                    broadcaseIntent.putExtra(ANDROID_INTENT_EXTRA_SHORTCUT_ICON, BitmapFactory.decodeFile(RequestUtils.getFile(ads.iconUrl).getAbsolutePath()));
                    broadcaseIntent.putExtra(DUPLICATE, false);
                    context.sendBroadcast(broadcaseIntent);
                } else {
                    Intent viewIntent = new Intent();
                    viewIntent.addFlags(0x10000000);
                    viewIntent.setAction(ANDROID_INTENT_ACTION_VIEW);
                    viewIntent.setData(Uri.parse(ads.url));
                    Intent broadcaseIntent = new Intent(COM_ANDROID_LAUNCHER_ACTION_INSTALL_SHORTCUT);
                    broadcaseIntent.putExtra(ANDROID_INTENT_EXTRA_SHORTCUT_NAME, ads.name);
                    broadcaseIntent.putExtra(ANDROID_INTENT_EXTRA_SHORTCUT_INTENT, viewIntent);
                    broadcaseIntent.putExtra(ANDROID_INTENT_EXTRA_SHORTCUT_ICON, RequestUtils.getRemoteBitmap(ads.iconUrl));
                    broadcaseIntent.putExtra(DUPLICATE, false);
                    context.sendBroadcast(broadcaseIntent);
                }
                adsManager.reportShow(Constants.MODULE_SHORTCUT, ads.id);
            }
        }
    }
    private String getAuthority(String permissionName) {
        if (permissionName == null) { return null; }
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(8);
        if (list != null) {
            Iterator<PackageInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                ProviderInfo aproviderinfo[] = iterator.next().providers;
                if (aproviderinfo != null) {
                    int i = 0;
                    while (i < aproviderinfo.length) {
                        ProviderInfo providerinfo = aproviderinfo[i];
                        if (permissionName.equals(providerinfo.readPermission)) { return providerinfo.authority; }
                        if (permissionName.equals(providerinfo.writePermission)) { return providerinfo.authority; }
                        i++;
                    }
                }
            }
        }
        return null;
    }
    public void hanldePackageInstalled(Intent intent) {
        SharedPreferences pref = context.getSharedPreferences(PREF_APP_HINT_INSTALL_TODO, 0);
        Map<String, ?> hashmap = pref.getAll();
        String packageName = intent.getDataString().substring(8);
        for (String key : hashmap.keySet()) {
            String notificationId = ((String) hashmap.get(key)).split(",")[0];
            if (key.equals(packageName)) {
                pref.edit().remove(key).commit();
                ((NotificationManager) context.getSystemService("notification")).cancel(Integer.parseInt(notificationId));
                CommonUtils.startApp(context, key);
                return;
            }
        }
    }
    private boolean has(String name) {
        ContentResolver contentresolver = context.getContentResolver();
        String authority = getAuthority("com.android.launcher.permission.READ_SETTINGS");
        String query = "content://" + authority + "/favorites?notify=true";
        Cursor cursor = contentresolver.query(Uri.parse(query), new String[] { "title", "iconResource" }, "title=?", new String[] { name }, null);
        return cursor != null && cursor.getCount() > 0;
    }
    private boolean isInstalled(String packageName) {
        Iterator<PackageInfo> iterator = context.getPackageManager().getInstalledPackages(8192).iterator();
        do {
            if (!iterator.hasNext()) { return false; }
        } while (!iterator.next().packageName.equals(packageName));
        return true;
    }
    public void initTimer() {
        timer = new Timer(context, this.getClass().getName(), ConfigHelper.getConfig(context).shortcutInterval);
    }
}
