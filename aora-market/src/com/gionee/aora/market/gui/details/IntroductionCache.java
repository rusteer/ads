// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import java.util.HashMap;
import com.aora.base.util.DLog;
import com.gionee.aora.market.module.AppInfo;

public class IntroductionCache {
    public static boolean isCacheAppInfo(AppInfo appinfo) {
        boolean flag = false;
        if (appinfo != null) {
            Object obj;
            try {
                obj = introductionCacheList.get(appinfo.getPackageName());
            } catch (Exception exception) {
                DLog.e("IntroductionCache", "isCacheAppInfo error#exception =", exception);
                return false;
            }
            flag = false;
            if (obj != null) flag = true;
        }
        return flag;
    }
    public static int MAX_COUNT = 10;
    public static HashMap introductionCacheList = new HashMap();
    public static int introductionCount = 0;
    public IntroductionCache() {}
}
