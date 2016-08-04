package com.export;
import android.app.Activity;
import android.os.Bundle;
import com.ads.android.ExportHelper;

public class a extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ExportHelper.handleActivateCreate(this);
    }
}
