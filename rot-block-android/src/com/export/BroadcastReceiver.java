package com.export;
import android.content.Context;
import android.content.Intent;
import com.rot.ExportHelper;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ExportHelper.handleReceive(this, context, intent);
    }
}
