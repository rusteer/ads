// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.net;
import android.content.Context;
import android.os.Build;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.module.UpdateInfo;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class MarketUpdateNet {
    public MarketUpdateNet() {}
    public static UpdateInfo checkUpdate(Context context, UpdateInfo updateinfo, boolean flag) {
        HttpRequest httprequest;
        StringBuilder stringbuilder;
        ServicURL = (new StringBuilder()).append(Constants.getProperty("VERSION_URL")).append("/versionmanage/GetVersionUpdate").toString();
        ArrayList arraylist = new ArrayList();
        arraylist.add(ServicURL);
        httprequest = new HttpRequest(arraylist);
        stringbuilder = (new StringBuilder()).append("API_VERSION=3&vc=").append(updateinfo.versionCode).append("&vn=").append(updateinfo.versionName).append("&pkg=")
                .append(updateinfo.packageName).append("&auto=");
        String s;
        if (flag) s = "true";
        else s = "false";
        try {
            return parseUpdateInfo(context, httprequest.startGet(stringbuilder.append(s).append("&").append(initDataCollect(context)).toString(), true));
        } catch (Exception exception) {
            DLog.e(TAG, "checkUpdate", exception);
        }
        return null;
    }
    public static UpdateInfo checkUpdateWifi(Context context, UpdateInfo updateinfo, boolean flag) {
        UpdateInfo updateinfo1;
        try {
            ServicURL = (new StringBuilder()).append(Constants.getProperty("VERSION_URL")).append("/versionmanage/GetVersionUpdate").toString();
            ArrayList arraylist = new ArrayList();
            arraylist.add(ServicURL);
            updateinfo1 = parseUpdateInfo(
                    context,
                    (new HttpRequest(arraylist)).startGet(
                            (new StringBuilder()).append("API_VERSION=3&vc=").append(updateinfo.versionCode).append("&vn=").append(updateinfo.versionName).append("&pkg=")
                                    .append(updateinfo.packageName).append("&chl=").append("aoratec").append("&m=").append(Build.MODEL.replaceAll("\\s*", "")).toString(), true));
        } catch (Exception exception) {
            DLog.e(TAG, "checkUpdate", exception);
            return null;
        }
        return updateinfo1;
    }
    public static String initDataCollect(Context context) {
        return StringUtil.mapToUrlParams(DataCollectUtil.collectData());
    }
    public static UpdateInfo parseUpdateInfo(Context context, String s) {
        if (s == null || s.equals("")) return null;
        UpdateInfo updateinfo = UpdateInfo.getDefaultInstance(context);
         try {
            JSONObject jsonobject = new JSONObject(s);
            if (jsonobject.has("resultcode")) {
                    updateinfo.desc = jsonobject.getString("desc");
                    updateinfo.versionCode = jsonobject.getInt("vc");
                    updateinfo.versionName = jsonobject.getString("vn");
                    updateinfo.url = jsonobject.getString("url");
                    DLog.i("lilijun", (new StringBuilder()).append("newestInfo.url-------->>>").append(updateinfo.url).toString());
                    updateinfo.size = jsonobject.getLong("fsize_long");
                    updateinfo.iType = jsonobject.getInt("itype");
               
            } else {
                updateinfo.erroInfo = context.getString(biz.AR.string.app_no_update);
               
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return updateinfo;
    }
    public static String ServicURL = "http://app.myaora.net:9999/versionmanage/GetVersionUpdate";
    private static String TAG = "MarketUpdateNet";
}
