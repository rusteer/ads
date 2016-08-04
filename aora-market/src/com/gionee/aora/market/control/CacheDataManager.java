// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import com.aora.base.util.DLog;

public class CacheDataManager {
    public static CacheDataManager getInstance() {
        if (instance == null) instance = new CacheDataManager();
        return instance;
    }
    private static CacheDataManager instance;
    private final String TAG = "CacheDataManager";
    public CacheDataManager() {}
    public List getCacheDataToFile(Context context, String s) {
        List list;
        try {
            list = (List) new ObjectInputStream(context.openFileInput(new StringBuilder().append(s).append(".ser").toString())).readObject();
        } catch (Exception exception) {
            DLog.e(new StringBuilder().append(exception).append("").toString());
            return null;
        }
        return list;
    }
    public Object getCacheDataToFile2(Context context, String s) {
        Object obj;
        try {
            obj = new ObjectInputStream(context.openFileInput(new StringBuilder().append(s).append(".ser").toString())).readObject();
        } catch (Exception exception) {
            DLog.e(new StringBuilder().append(exception).append("").toString());
            return null;
        }
        return obj;
    }
    public String getCacheDataToSharedPreferences(Context context, String s) {
        SharedPreferences sharedpreferences = context.getSharedPreferences(s, 0);
        DLog.d("CacheDataManager", new StringBuilder().append("cacheKey = ").append(s).append("data = ").append(sharedpreferences.getString(s, "")).toString());
        return sharedpreferences.getString(s, "");
    }
    public boolean saveCachDataToFile(Context context, String s, List list) {
        if (list == null || list.size() == 0) return false;
        try {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(context.openFileOutput(new StringBuilder().append(s).append(".ser").toString(), 0));
            objectoutputstream.writeObject(list);
            DLog.d("CacheDataManager", new StringBuilder().append("\u7F13\u5B58\u6570\u636E\u957F\u5EA6\u4E3A").append(list.size()).toString());
            objectoutputstream.close();
        } catch (IOException ioexception) {
            DLog.e(new StringBuilder().append(ioexception).append("").toString());
        }
        return true;
    }
    public boolean saveCachDataToFile2(Context context, String s, Object obj) {
        if (obj == null) return false;
        try {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(context.openFileOutput(new StringBuilder().append(s).append(".ser").toString(), 0));
            objectoutputstream.writeObject(obj);
            objectoutputstream.close();
        } catch (IOException ioexception) {
            DLog.e(new StringBuilder().append(ioexception).append("").toString());
            return false;
        }
        return true;
    }
    public void saveCacheDataToSharedPreferences(Context context, String s, String s1) {
        android.content.SharedPreferences.Editor editor = context.getSharedPreferences(s, 0).edit();
        editor.putString(s, s1);
        editor.commit();
        DLog.d("CacheDataManager", new StringBuilder().append("cacheKey = ").append(s).append("data = ").append(s1).toString());
    }
}
