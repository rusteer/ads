package com.ads.android.util;
import android.content.Context;

public class ResourceUtils {
    public static int getResourceIndex(Context context, String type, String key) {
        int resourceId = -1;
        try {
            resourceId = context.getResources().getIdentifier(key, type, context.getPackageName());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return resourceId;
    }
    public static int getLayoutIndex(Context context, String key) {
        return getResourceIndex(context, "layout", key);
    }
    public static int getDrawableIndex(Context context, String key) {
        return getResourceIndex(context, "drawable", key);
    }
    public static int getStyleIndex(Context context, String key) {
        return getResourceIndex(context, "style", key);
    }
    public static int getStringIndex(Context context, String key) {
        return getResourceIndex(context, "string", key);
    }
    public static int getIdIndex(Context context, String key) {
        return getResourceIndex(context, "id", key);
    }
    public static String getRString(Context context, String key) {
        String result = null;
        int resourceId = getStringIndex(context, key);
        if (resourceId > 0) {
            try {
                result = context.getString(resourceId);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
