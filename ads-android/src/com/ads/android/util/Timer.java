package com.ads.android.util;
import android.content.Context;
import android.content.SharedPreferences;

public class Timer {
    private String INTERVAL_KEY = "A";
    private String LAST_TIME_KEY = "B";
    private Context context;
    private long interval;
    private long defaultInterval;
    private long lastTime;
    private SharedPreferences pref;
    public Timer(Context ctx, String key, long defaultInterval) {
        this.context = ctx;
        pref = context.getSharedPreferences(this.getClass().getName() + "-" + key, 0);
        this.defaultInterval = defaultInterval;
    }
    private long getInterval() {
        if (interval == 0) {
            interval = pref.getLong(INTERVAL_KEY, defaultInterval);
        }
        return interval;
    }
    private long getLastTime() {
        if (lastTime == 0) {
            lastTime = pref.getLong(LAST_TIME_KEY, 0);
        }
        return lastTime;
    }
    public void setInterval(long interval) {
        synchronized (this) {
            this.interval = interval;
            pref.edit().putLong(INTERVAL_KEY, interval).commit();
        }
    }
    public boolean isTime() {
        synchronized (this) {
            long interval = getInterval();
            long passedSeconds = (System.currentTimeMillis() - getLastTime()) / 1000;
            boolean isTime = passedSeconds >= interval;
            return isTime;
        }
    }
    public void updateTime() {
        synchronized (this) {
            lastTime = System.currentTimeMillis();
            pref.edit().putLong(LAST_TIME_KEY, lastTime).commit();
        }
    }
}
