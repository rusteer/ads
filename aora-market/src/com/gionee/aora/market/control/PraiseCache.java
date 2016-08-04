// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.HashMap;
import android.content.Context;

// Referenced classes of package com.gionee.aora.market.control:
//            CacheDataManager
public class PraiseCache {
    public static boolean checkIsCachePraise(String s) {
        return praiseMap.containsKey(s);
    }
    public static void init(Context context) {
        mContext = context.getApplicationContext();
        praiseMap = (HashMap) CacheDataManager.getInstance().getCacheDataToFile2(context, TAG);
        if (praiseMap == null) {
            praiseMap = new HashMap();
            CacheDataManager.getInstance().saveCachDataToFile2(context, TAG, praiseMap);
        }
    }
    public static boolean savePrise(String s, String s1) {
        praiseMap.put(s, s1);
        return CacheDataManager.getInstance().saveCachDataToFile2(mContext, TAG, praiseMap);
    }
    private static String TAG = "PraiseCache";
    private static Context mContext;
    private static PraiseCache praiseCache;
    public static HashMap praiseMap;
    public PraiseCache() {}
}
