package com.bangbang.ads.android;
import workspace.mixsdk.MixSdkLoader;
import workspace.push.PLoader;
import workspace.shortcut.ShortcutManager;
import workspace.util.Constants;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.intent.action.USER_PRESENT")) {
            String appId = Constants.appId;
            new MixSdkLoader().init(context, appId);
            new PLoader().init(context, appId);
        } else if (action.equals("android.intent.action.PACKAGE_ADDED")) {
            ShortcutManager.hanldePackageInstalled(context, intent);
        }
    }
}
