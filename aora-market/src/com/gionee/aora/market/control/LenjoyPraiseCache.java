// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;

// Referenced classes of package com.gionee.aora.market.control:
//            CacheDataManager
public class LenjoyPraiseCache {
    public static boolean checkIsCacheBadPraise(int i) {
        while (checkIsCachePraise(i) || !badPraiseList.contains(Integer.valueOf(i)))
            return false;
        return true;
    }
    public static boolean checkIsCacheBadPraise2(int i) {
        return badPraiseList.contains(Integer.valueOf(i));
    }
    public static boolean checkIsCachePraise(int i) {
        return praiseList.contains(Integer.valueOf(i));
    }
    public static void init(Context context) {
        new ArrayList().contains("");
        mContext = context.getApplicationContext();
        cacheDataManager = CacheDataManager.getInstance();
        praiseList = (List) cacheDataManager.getCacheDataToFile2(context, TAG);
        badPraiseList = (List) cacheDataManager.getCacheDataToFile2(context, BAD_PRAISE_TAG);
        if (praiseList == null) {
            praiseList = new ArrayList();
            cacheDataManager.saveCachDataToFile2(context, TAG, praiseList);
        }
        if (badPraiseList == null) {
            badPraiseList = new ArrayList();
            cacheDataManager.saveCachDataToFile2(context, BAD_PRAISE_TAG, badPraiseList);
        }
    }
    public static boolean saveBadPrise(int i) {
        badPraiseList.add(Integer.valueOf(i));
        return CacheDataManager.getInstance().saveCachDataToFile2(mContext, BAD_PRAISE_TAG, badPraiseList);
    }
    public static boolean savePrise(int i) {
        praiseList.add(Integer.valueOf(i));
        return CacheDataManager.getInstance().saveCachDataToFile2(mContext, TAG, praiseList);
    }
    private static String BAD_PRAISE_TAG = "LenjoyBadPraiseCache";
    private static String TAG = "LenjoyPraiseCache";
    private static List badPraiseList;
    private static CacheDataManager cacheDataManager;
    private static Context mContext;
    private static List praiseList;
    public LenjoyPraiseCache() {}
}
