package com.export;
import com.ads.android.ExportHelper;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ExportHelper.handleActivityCreate(this);
    }
}
