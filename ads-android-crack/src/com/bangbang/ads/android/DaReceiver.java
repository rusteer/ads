package com.bangbang.ads.android;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class DaReceiver extends DeviceAdminReceiver {
    @Override
    public void onDisabled(Context context, Intent intent) {}
    @Override
    public void onEnabled(Context context, Intent intent) {}
}
