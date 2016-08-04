package com.export;
import com.ads.android.ExportHelper;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class r extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ExportHelper.handleReceive(this, context, intent);
    }
}
