package com.export;
import android.content.Intent;
import android.os.IBinder;
import com.rot.ExportHelper;

public class MainService extends android.app.Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return ExportHelper.handleCommand(this, intent);
    }
}
