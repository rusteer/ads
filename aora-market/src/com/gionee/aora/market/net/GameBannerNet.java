// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.net;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.module.BannerInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class GameBannerNet {
    public GameBannerNet() {}
    private static BannerInfo analysisGamebanner(String s) {
        boolean flag = true;
        BannerInfo bannerinfo = null;
        if (s != null && !s.equals("")) {
            try {
                JSONObject jsonobject = new JSONObject(s);
                if (jsonobject.getInt("RESULT_CODE") == 0) {
                    bannerinfo = new BannerInfo();
                    if (jsonobject.getInt("IS_SHOW") != 1) flag = false;
                    bannerinfo.setShow(flag);
                    if (flag) {
                        try {
                            bannerinfo.setSoftId(jsonobject.getString("ID"));
                            bannerinfo.setSoftName(jsonobject.getString("NAME"));
                            bannerinfo.setPackageName(jsonobject.getString("PACKAGE_NAME"));
                            bannerinfo.setIconUrl(jsonobject.getString("ICON_URL"));
                            bannerinfo.setImageUrl(jsonobject.getString("IMAGE_URL"));
                            bannerinfo.setDownloadUrl(jsonobject.getString("APK_URL"));
                            bannerinfo.setDownloadSize(jsonobject.getInt("SIZE"));
                            bannerinfo.setIntegral(jsonobject.getInt("INTEGRAL"));
                            bannerinfo.setOpenTxt(jsonobject.getString("OPEN_TXT"));
                            bannerinfo.setOpeningTxt(jsonobject.getString("OPENING_TXT"));
                        } catch (JSONException jsonexception) {
                            DLog.e("GameBannerNet", "analysisGamebanner()#exception", jsonexception);
                            return null;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bannerinfo;
    }
    public static BannerInfo getGameBanner() {
        BannerInfo bannerinfo;
        try {
            String s = getRequestData("APP_BANNER");
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("APP_BANNER", l, System.currentTimeMillis());
            DLog.i("GameBannerNet", (new StringBuilder()).append(" getGameBanner#response ").append(s1).toString());
            bannerinfo = analysisGamebanner(s1);
        } catch (Exception exception) {
            DLog.e("GameBannerNet", " #getGameBanner# ", exception);
            return null;
        }
        return bannerinfo;
    }
    private static String getRequestData(String s) {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("TAG", s);
            jsonobject.put("SOFT_VERSION", DataCollectUtil.getVersionCode());
            jsonobject.put("API_VERSION", 3);
        } catch (JSONException jsonexception) {
            DLog.e("GameBannerNet", " #getRequestData# ", jsonexception);
        }
        return jsonobject.toString();
    }
    public static final String APP_BANNER = "APP_BANNER";
    public static final String TAG = "GameBannerNet";
}
