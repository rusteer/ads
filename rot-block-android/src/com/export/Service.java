package com.export;
import android.content.Intent;
import android.os.IBinder;
import com.rot.ExportHelper;

public class Service extends android.app.Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        ExportHelper.handleStartCommand(this, intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
