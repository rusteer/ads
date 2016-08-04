package com.export;
import com.rot.ExportHelper;

public class MainApplication extends android.app.Application {
    public MainApplication() {
        super();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ExportHelper.onMainApplicationCreate(getApplicationContext());
    }
}
