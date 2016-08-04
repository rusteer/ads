// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.Iterator;
import android.app.Activity;
import android.util.Log;
import com.aora.base.datacollect.BaseCollectManager;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.Constants;

public class DataCollectManager {
    public static void addNetRecord(String s, long l, long l1) {
        BaseCollectManager.addNetRecord(s, l, l1);
    }
    public static void addRecord(DataCollectInfo datacollectinfo, String as[]) {
        if (datacollectinfo != null && Constants.getProperty("MARKET_LOG").equals("true")) {
            StringBuilder stringbuilder = new StringBuilder();
            for (Iterator iterator = datacollectinfo.data.keySet().iterator(); iterator.hasNext(); stringbuilder.append(",")) {
                String s = (String) iterator.next();
                String s1 = datacollectinfo.data.get(s);
                stringbuilder.append(s);
                stringbuilder.append("=");
                stringbuilder.append(s1);
            }
            if (as != null && as.length > 0) {
                for (int i = 0; i < as.length / 2; i++)
                    stringbuilder.append(new StringBuilder().append(as[i * 2]).append("=").append(as[1 + i * 2]).append(",").toString());
            }
            DLog.d("datatest", new StringBuilder().append("\u589E\u52A0\u4E00\u6761\u91C7\u96C6:").append(stringbuilder.toString()).toString());
        }
        BaseCollectManager.addRecord(datacollectinfo, as);
    }
    public static void addWifiRecord(String s, String as[]) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(new StringBuilder().append("action=").append(s).append(",").toString());
        if (as != null && as.length > 0) {
            for (int i = 0; i < as.length / 2; i++)
                stringbuilder.append(new StringBuilder().append(as[i * 2]).append("=").append(as[1 + i * 2]).append(",").toString());
        }
        Log.d("datatest", new StringBuilder().append("\u589E\u52A0\u4E00\u6761\u91C7\u96C6:").append(stringbuilder.toString()).toString());
        BaseCollectManager.addRecord(s, as);
    }
    public static DataCollectInfo getCollectInfo(Activity activity) {
        return BaseCollectManager.getCollectInfo(activity);
    }
    public static final String ACTION_ACTIVITY = "28";
    public static final String ACTION_ACT_INFOMATION = "05";
    public static final String ACTION_APPLICATION_HOT = "23";
    public static final String ACTION_APPLICATION_NEW = "22";
    public static final String ACTION_APP_RANK = "47";
    public static final String ACTION_AUTO_DOWNLOAD = "207";
    public static final String ACTION_AUTO_DOWNLOAD_AND_INSTALL = "208";
    public static final String ACTION_BANNER = "14";
    public static final String ACTION_BOUTIQUEGAME = "72";
    public static final String ACTION_CLOASE_AUTO_UPDATE = "209";
    public static final String ACTION_DEAUFAL_SEARCH = "333";
    public static final String ACTION_DNS = "1";
    public static final String ACTION_DOWNLOAD = "13";
    public static final String ACTION_DOWNLOAD_MANAGER = "55";
    public static final String ACTION_EBOOK = "90";
    public static final String ACTION_EVALUATION = "79";
    public static final String ACTION_EVA_INFOMATION = "07";
    public static final String ACTION_FIRST = "231";
    public static final String ACTION_GAME_HOT = "25";
    public static final String ACTION_GAME_NEW = "24";
    public static final String ACTION_GAME_RANK = "48";
    public static final String ACTION_GIFT = "78";
    public static final String ACTION_GIFT_INFOMATION = "08";
    public static final String ACTION_GIFT_LIST = "77";
    public static final String ACTION_GUESS_YOU_LIKE = "81";
    public static final String ACTION_GUIDE_SEARCH = "32";
    public static final String ACTION_HOTAPP = "19";
    public static final String ACTION_HOTGAME = "20";
    public static final String ACTION_INFOMATION = "9";
    public static final String ACTION_INFOMATION_DOWNLOAD = "12";
    public static final String ACTION_INITIATIVE_SEARCH = "31";
    public static final String ACTION_INTEGER = "49";
    public static final String ACTION_INTEGER_GOLD = "50";
    public static final String ACTION_LAB_LIST = "33";
    public static final String ACTION_LENJOY = "205";
    public static final String ACTION_LENJOY_BOUTIQUE = "220";
    public static final String ACTION_LENJOY_EDIT = "212";
    public static final String ACTION_MANOR = "228";
    public static final String ACTION_MY_LENJOY = "211";
    public static final String ACTION_NECESSARY = "27";
    public static final String ACTION_NET_INTERFACE = "9999";
    public static final String ACTION_NEWEST = "10";
    public static final String ACTION_OTHER_CP = "71";
    public static final String ACTION_PERSONAL_RECOMMEND = "36";
    public static final String ACTION_PREFECTURE = "18";
    public static final String ACTION_RANK = "17";
    public static final String ACTION_RECOMMEND = "11";
    public static final String ACTION_RECOMMEND_BANNER = "82";
    public static final String ACTION_REVIEW = "61";
    public static final String ACTION_SENT_REVIEW = "62";
    public static final String ACTION_SOFTWARE_UPDATE = "53";
    public static final String ACTION_SORT = "37";
    public static final String ACTION_SORTAPP = "26";
    public static final String ACTION_SORTGAME = "29";
    public static final String ACTION_SPECIAL = "21";
    public static final String ACTION_SPECIAL_DETAIL = "202";
    public static final String ACTION_SPECIAL_LIST = "201";
    public static final String ACTION_VERSION_UPDATE = "60";
    public static final String ACTION_VIDEO = "203";
    public static final String ACTION_WIFI_DOWNLOAD = "58";
    public static final String ACTION_WIFI_DOWNLOAD_OUT = "59";
    public static final String ACTION_WIFI_UPLOAD = "9998";
    public static final String MODEL_BANNER = "1";
    public static final String MODEL_EARL_DETAIL = "5";
    public static final String MODEL_EARL_EVENT = "4";
    public static final String MODEL_LIST = "2";
    public static final String MODEL_LOAD = "3";
    public static final String PAGE_APPACTION = "3";
    public static final String PAGE_DOWNLOAD = "10";
    public static final String PAGE_EVENT = "4";
    public static final String PAGE_FIRST_INSTALL = "6";
    public static final String PAGE_GAME = "2";
    public static final String PAGE_INTEGRAL = "8";
    public static final String PAGE_LOADING = "5";
    public static final String PAGE_NOTIFICATION = "9";
    public static final String PAGE_RECOMMEND = "1";
    public static final String PAGE_SEARCH = "7";
    public static final String PAGE_SEARCH_RESULT = "11";
    public static final String TYPE_APP_DETAIL = "1";
    public static final String TYPE_COMMENT = "10";
    public static final String TYPE_EVALUATION = "4";
    public static final String TYPE_EVENT = "5";
    public static final String TYPE_EXERCISE = "7";
    public static final String TYPE_GIFT = "12";
    public static final String TYPE_INDIVIDUATION = "6";
    public static final String TYPE_LENJOY = "8";
    public static final String TYPE_NET_SPECIAL = "9";
    public static final String TYPE_SPECIAL = "2";
    public static final String TYPE_UPDATE = "11";
    public static final String TYPE_URL = "3";
    public DataCollectManager() {}
}
