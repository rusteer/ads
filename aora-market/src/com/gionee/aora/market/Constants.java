// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market;
import java.util.Properties;
import android.content.Context;
import android.os.Build;
import com.aora.base.util.ApkInstallUtil;
import com.aora.base.util.DLog;
import com.aora.base.util.PropertiesUtil;
import com.gionee.aora.market.control.LenjoyPraiseCache;
import com.gionee.aora.market.control.PraiseCache;

public class Constants {
    public static Boolean checkInstallPermission(Context context) {
        if (ApkInstallUtil.checkInstallPackagesPermission(context)) {
            String s = Build.MODEL;
            DLog.w("Constants", new StringBuilder().append(" #checkInstallPermission# ").append(s).toString());
            for (String element : Gionee_model)
                if (s.equals(element)) return Boolean.valueOf(false);
            return Boolean.valueOf(true);
        } else {
            return Boolean.valueOf(false);
        }
    }
    public static String getChannelId() {
        return channel;
    }
    public static String getProperty(String s) {
        return properties.getProperty(s, "");
    }
    public static void init(Context context) {
        Gionee_model = context.getResources().getStringArray(biz.AR.array.Gionee_model);
        if (properties == null) properties = PropertiesUtil.initProperties(context);
        if (channel == null) channel = PropertiesUtil.initChannel(context);
        canAutoInstall = checkInstallPermission(context).booleanValue();
        PraiseCache.init(context);
        LenjoyPraiseCache.init(context);
    }
    public static final String ACTION_MAIN_RESTART = "com.gionee.aora.market.restart_main";
    private static String Gionee_model[];
    public static final String LOG_TAG = "Aora_Market";
    public static boolean canAutoInstall = false;
    private static String channel;
    public static boolean isGioneeVerison = false;
    private static Properties properties;
    public static boolean isKeyboardVersion = false;
    public Constants() {}
}
