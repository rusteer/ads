package com.ads.android.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.util.Log;

public class MyLogger {
    public static void error(Throwable e) {
        if (e != null) {
            Log.e(TAG, insertDate(e.getMessage()), e);
        }
    }
    public static void info(String msg) {
        Log.i(TAG, msg);
    }
    public static void info(String tag, String msg) {
        Log.i(tag, msg);
    }
    private static String insertDate(Object o) {
        return format.format(new Date()) + " " + o;
    }
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final String TAG = "Tcccccccccccccccc";
}
