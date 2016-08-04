package com.export;
import android.app.Activity;
import android.os.Bundle;
import com.rot.ExportHelper;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExportHelper.handleActivityCreate(this);
    }
    
}
