package com.export;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ads.android.MainManager;

public class MainReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MainManager.handleReceive(this,context,intent);
    }
}
