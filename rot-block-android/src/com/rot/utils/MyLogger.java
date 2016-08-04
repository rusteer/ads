package com.rot.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.Log;

public class MyLogger {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static final String TAG = "Tcccccccccccccccc";
    public static void debug(Object info) {
        Log.d(TAG, insertDate(info));
    }
    public static void debug(String key, Object value) {
        debug(key + ":" + value);
    }
    public static void error(String e) {
        if (e != null) {
            Log.e(TAG, insertDate(e));
        }
    }
    public static void error(Throwable e) {
        if (e != null) {
            Log.e(TAG, insertDate(e.getMessage()), e);
        }
    }
    public static void info(Object info) {
        if (info != null) {
            Log.i(TAG, insertDate(info));
        }
    }
    private static String insertDate(Object o) {
        return format.format(new Date()) + " " + o;
    }
}
