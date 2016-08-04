// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.fihs.controler;
import android.content.Context;
import com.aora.base.util.PropertiesUtil;

public class Constants {
    public static class Properties {
        public String APPID;
        public String CONTROLER_CONFIG_ID;
        public String PROJECT_ITEM_ID;
        public Properties() {}
    }
    public static String getChannelId() {
        return channel;
    }
    public static String getProperty(String s) {
        return mProperties.getProperty(s, "");
    }
    public static void init(Context context, Properties properties) {
        if (mProperties == null) mProperties = PropertiesUtil.initProperties(context);
        if (channel == null) channel = PropertiesUtil.initChannel(context);
        APPID = properties.APPID;
        PROJECT_ITEM_ID = properties.PROJECT_ITEM_ID;
        String s = properties.CONTROLER_CONFIG_ID;
        String s1 = getProperty("PUSH_URL");
        String s2 = getProperty("PUSH_COLLECT_URL");
        CONTROLER_CONFIG_URL = new StringBuilder().append(s1).append("/pushservice/PushRequest?sc=").append(s).toString();
        PUSH_CONFIG_REPORT_URL = new StringBuilder().append(s1).append("/pushservice/ClientRequest").toString();
        PUSH_COLLECT_URL = new StringBuilder().append(s2).append("/pushweb/PushCollect?message_id=%1$s&mobiletype=%2$s&imei=%3$s&clientid=%4$s&createtime=%5$s").toString();
    }
    public static final String ACTION_APP_EXERCISEINFOMATIONACTIVITY = "com.gionee.aora.market.action.ExerciseInfomationActivity";
    public static final String ACTION_APP_GOSOFTINTRODUCTIONACTIVITY = "com.gionee.aora.market.action.GoSoftIntroductionActivity";
    public static final String ACTION_APP_INTEGRALACTIVITY = "com.gionee.aora.market.gui.integral.ManagerActivity";
    public static final String ACTION_APP_INTEGRALAPPACTIVITY = "com.gionee.aora.market.action.IntegralAppListActivity";
    public static final String ACTION_APP_MAINACTIVITY = "com.gionee.aora.market.action.MainActivity";
    public static final String ACTION_APP_SPECIALINFOMATIONACTIVITY = "com.gionee.aora.market.action.SpecialInfomationActivity";
    public static String APPID;
    public static final String CLIENTID_REPORT = "clientid_report";
    public static String CONTROLER_CONFIG_URL = "http://push.myaora.net:9999/pushservice/PushRequest?sc=1";
    public static final long CONTROLER_COUNT_DAY = 72L;
    public static final long CONTROLER_COUNT_MILLIS = 0x36ee80L;
    public static final int CONTROLER_DELAY_MILLIS = 0x36ee80;
    public static final long CONTROLER_INTERVAL_MILLIS = 0x1499700L;
    public static final String CONTROLER_SETTINGS = "controler_settings";
    public static final String DEBUG = "DEBUG";
    public static final String DELAY_HOUR = "delay_hour";
    public static final String DES_KEY = "589236";
    public static final String DOWNLOAD_DIR = "/downdir/";
    public static final String FIRST_LAUNCH_TIME = "first_launch_time";
    public static final long HOUR_MILLIS = 0x36ee80L;
    public static final long INSTALL_INTERVAL_MILLIS = 0xf731400L;
    public static final String LOG_TAG = "Aora_Push";
    public static final int MARKET_INTEGRAL = 3;
    public static final int MARKET_MAIN = 0;
    public static final int MAX_CACHE_COUNT = 5;
    public static final int MAX_DOWNLOAD_FAILD_COUNT = 10;
    public static String PROJECT_ITEM_ID;
    public static final String PUSH_CLIENT_ID = "clientId";
    public static String PUSH_COLLECT_URL = "http://push.myaora.net:3380/pushweb/PushCollect?message_id=%1$s&mobiletype=%2$s&imei=%3$s&clientid=%4$s&createtime=%5$s";
    public static String PUSH_CONFIG_REPORT_URL = "http://push.myaora.net:9999/pushservice/ClientRequest";
    public static final String PUSH_CONNECTED = "push_connected";
    public static final String PUSH_HOME = "P1";
    public static final String PUSH_INTEGRAL = "P4";
    public static final String PUSH_INTEGRAL_APP = "P5";
    public static final String PUSH_INTRODUCTION = "P2";
    public static final String PUSH_LAST_INSTALL_SUCCESS_TIME = "last_install_success_time";
    public static final String PUSH_REGISTER = "com.jinli.c2u.intent.REGISTER";
    public static final String PUSH_SPECIAL = "P3";
    public static final String PUSH_START_SERVICE = "com.jinli.c2u.intent.START_PUSH_SERVICE";
    public static final String PUSH_STOP_SERVICE = "com.jinli.c2u.intent.STOP_PUSH_SERVICE";
    public static final String PUSH_SWITCH = "push_switch";
    public static final String PUSH_SWITCH_USER = "push_switch_user";
    public static final boolean PUSH_SWITCH_USER_DEFAULT = true;
    public static final String PUSH_UNREGISTER = "com.jinli.c2u.intent.UNREGISTER";
    public static final int SWITCH_DISABLE = 1;
    public static final int SWITCH_ENABLE = 0;
    private static String channel;
    private static java.util.Properties mProperties;
    public Constants() {}
}
