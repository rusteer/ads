package com.export;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.rot.ExportHelper;

public class MainReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ExportHelper.doReceive(context, intent);
    }
}