// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.net;
import android.os.Build;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.integral.net.IntegralBaseNet;
import com.gionee.aora.market.module.*;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

// Referenced classes of package com.gionee.aora.market.net:
//            AnalysisMixtrueData
public class BoutiqueGameNet {
    public BoutiqueGameNet() {}
    public static List getBannerInfos(int i) {
        List list;
        try {
            JSONObject jsonobject = BaseNet.getBaseJSON("GAME_BANNER_LIST");
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("IS_APP", i);
            jsonobject.put("API_VERSION", 7);
            list = parseBannersResultJSON(BaseNet.doRequestHandleResultCode("GAME_BANNER_LIST", jsonobject));
        } catch (Exception exception) {
            DLog.e("BoutiqueGameNet", "getBannerInfos()#exception", exception);
            return null;
        }
        return list;
    }
    public static ArrayList getBoutiqueGameList(int i, int j, int k) {
        ArrayList arraylist;
        try {
            JSONObject jsonobject = BaseNet.getBaseJSON("INDEX_RECOMMEND");
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("TYPE", k);
            jsonobject.put("API_VERSION", 7);
            arraylist = AnalysisMixtrueData.analysisJSonList(BaseNet.doRequestHandleResultCode("INDEX_RECOMMEND", jsonobject));
        } catch (Exception exception) {
            DLog.e("BoutiqueGameNet", "getBoutiqueGameList()#exception", exception);
            return null;
        }
        return arraylist;
    }
    public static GiftInfo getGiftInfo(JSONObject jsonobject) throws JSONException {
        GiftInfo giftinfo = new GiftInfo();
        giftinfo.setId(jsonobject.getInt("GIFT_ID"));
        giftinfo.setName(jsonobject.getString("GIFT_NAME"));
        if (jsonobject.getInt("IS_GOT") == 0) giftinfo.setGotGift(false);
        else giftinfo.setGotGift(true);
        giftinfo.setSurplusCount(jsonobject.getString("SURPLUS_COUNT"));
        giftinfo.setEndTime(jsonobject.getString("END_DATE"));
        giftinfo.setSkipUrl(jsonobject.getString("SKIP_URL"));
        giftinfo.setIconUrl(jsonobject.getString("ICON_URL"));
        giftinfo.setAppId(jsonobject.getInt("ID"));
        giftinfo.setAppName(jsonobject.getString("NAME"));
        giftinfo.setAppPackageName(jsonobject.getString("PACKAGE_NAME"));
        giftinfo.setAppIconUrl(jsonobject.getString("ICON_URL"));
        giftinfo.setAppDownloadUrl(jsonobject.getString("APK_URL"));
        giftinfo.setAppLongSize(jsonobject.getLong("SIZE"));
        giftinfo.setAppVersionName(jsonobject.getString("VERSION_NAME"));
        try {
            giftinfo.setAppSize(StringUtil.getFormatSize(giftinfo.getAppLongSize()));
        } catch (Exception exception) {
            DLog.e("BoutiqueGameNet", "parseGiftsResultJson()#\u6570\u5B57\u683C\u5F0F\u5316\u5F02\u5E38exception", exception);
            return giftinfo;
        }
        return giftinfo;
    }
    public static List getGiftInfos(long l, String s, int i, int j) {
        List list;
        try {
            ArrayList arraylist = new ArrayList();
            JSONObject jsonobject = BaseNet.getBaseJSON("GAME_NEW_GIFT_LIST");
            jsonobject.put("ID", l);
            jsonobject.put("MODEL", s);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            list = parseGiftsResultJson(IntegralBaseNet.doRequestHandleResultCode("GAME_NEW_GIFT_LIST", jsonobject), arraylist);
        } catch (Exception exception) {
            DLog.e("BoutiqueGameNet", "getGiftInfos()#exception", exception);
            return null;
        }
        return list;
    }
    public static List<RecommendAdInfo> parseBannersResultJSON(JSONObject jsonobject) {
         List<RecommendAdInfo> arraylist = new ArrayList<RecommendAdInfo>();
        try {
            JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
            int i = 0;
            while (i < jsonarray.length()) {
                RecommendAdInfo recommendadinfo = new RecommendAdInfo();
                JSONObject jsonobject1 = jsonarray.getJSONObject(i);
                recommendadinfo.setType(jsonobject1.getInt("TYPE"));
                recommendadinfo.setUrl(jsonobject1.getString("IMG_URL"));
                recommendadinfo.setName(jsonobject1.getString("AD_NAME"));
                recommendadinfo.setId(jsonobject1.getString("ACTION_PARAM"));
                recommendadinfo.setDescription(jsonobject1.getString("DESCRIPTION"));
                if (jsonobject1.getInt("TYPE") != 4 && jsonobject1.getInt("TYPE") != 5) {//goto _L4; else goto _L3
                    if (jsonobject1.getInt("TYPE") == 8) {
                        if (jsonobject1.has("DATA")) {
                            JSONObject jsonobject3 = jsonobject1.getJSONObject("DATA");
                            LenjoyInfo lenjoyinfo = new LenjoyInfo();
                            lenjoyinfo.setId(jsonobject3.getInt("LX_ID"));
                            lenjoyinfo.setUserIconUrl(jsonobject3.getString("PORTRAIT_URL"));
                            lenjoyinfo.setUserSurname(jsonobject3.getString("SURNAME"));
                            recommendadinfo.setContent(lenjoyinfo);
                        } else {
                            recommendadinfo.setContent(null);
                        }
                    } else {
                        recommendadinfo.setContent(jsonobject1.getString("DATA"));
                    }
                } else {
                    JSONObject jsonobject2;
                    EvaluatInfo evaluatinfo;
                    jsonobject2 = jsonobject1.getJSONObject("DATA");
                    evaluatinfo = new EvaluatInfo();
                    evaluatinfo.setId(jsonobject2.getInt("ID"));
                    evaluatinfo.setName(jsonobject2.getString("TITLE"));
                    evaluatinfo.setSkipUrl(jsonobject2.getString("URL"));
                    evaluatinfo.setAppId(jsonobject2.getInt("SOFT_ID"));
                    evaluatinfo.setAppName(jsonobject2.getString("SOFT_NAME"));
                    evaluatinfo.setAppIconUrl(jsonobject2.getString("ICON_URL"));
                    evaluatinfo.setAppPackageName(jsonobject2.getString("PACKAGE_NAME"));
                    evaluatinfo.setAppDownloadUrl(jsonobject2.getString("APK_URL"));
                    evaluatinfo.setAppLongSize(jsonobject2.getLong("SIZE"));
                    evaluatinfo.setAppVersionName(jsonobject2.getString("VERSION_NAME"));
                    evaluatinfo.setAppSize(StringUtil.getFormatSize(evaluatinfo.getAppLongSize()));
                    evaluatinfo.setAppDownloadCount(StringUtil.getDownloadNumber(jsonobject2.getString("DOWNLOAD_COUNT")));
                    //_L6:
                    recommendadinfo.setContent(evaluatinfo);
                }
                //_L7:
                arraylist.add(recommendadinfo);
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraylist;
    }
    private static List parseGiftsResultJson(JSONObject jsonobject, List list) throws JSONException {
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for (int i = 0; i < jsonarray.length(); i++)
            list.add(getGiftInfo(jsonarray.getJSONObject(i)));
        return list;
    }
    private static final String GAME_BANNER_LIST = "GAME_BANNER_LIST";
    private static final String GAME_BEST_LIST = "INDEX_RECOMMEND";
    private static final String GAME_NEW_GIFT_LIST = "GAME_NEW_GIFT_LIST";
}
