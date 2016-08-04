package com.export;
import com.ads.android.ExportHelper;
import android.content.Intent;
import android.os.IBinder;

public class s extends android.app.Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ExportHelper.handleServiceStart(this, intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
