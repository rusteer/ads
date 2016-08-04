package com.rot.utils;
import android.util.Log;

public class MyLogger {
    private static final String TAG = "xxxxxxx";
    public static void error(String msg) {
        Log.e(TAG, msg);
    }
    public static void error(String msg, Throwable e) {
        Log.e(TAG, msg, e);
    }
    public static void error(Throwable e) {
        Log.e(TAG, e.getMessage(), e);
    }
    public static void info(String s) {
        Log.i(TAG, s);
    }
}
