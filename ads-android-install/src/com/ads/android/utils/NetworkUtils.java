package com.ads.android.utils;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    public static boolean isNetworkConnected(Context context) {
        boolean result = false;
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = manager.getActiveNetworkInfo();
            if (mNetworkInfo != null) result = mNetworkInfo.isAvailable();
        }
        return result;
    }
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivitymanager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivitymanager != null) {
            NetworkInfo networkinfo = connectivitymanager.getNetworkInfo(1);
            if (networkinfo != null) {
                android.net.NetworkInfo.State state = networkinfo.getState();
                if (state != null) {
                    if (android.net.NetworkInfo.State.CONNECTED == state) return true;
                }
            }
        }
        return false;
    }
}
