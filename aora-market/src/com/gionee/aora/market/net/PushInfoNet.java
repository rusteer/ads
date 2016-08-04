// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.content.Context;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.module.EvaluatInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class PushInfoNet
{

    public PushInfoNet()
    {
    }

    public static EvaluatInfo getEvaluatInfo(Context context)
    {
        EvaluatInfo evaluatinfo=null;
        try {
            JSONObject jsonobject = BaseNet.getBaseJSON("LOGIN_PUSH_INFO");
            jsonobject.put("CHL", DataCollectUtil.getValue("chl"));
            jsonobject.put("MODEL", DataCollectUtil.getValue("m"));
            JSONObject jsonobject1 = BaseNet.doRequest("LOGIN_PUSH_INFO", jsonobject);
            if(Long.parseLong(jsonobject1.getString("TIME")) <= Long.parseLong(MarketPreferences.getInstance(context).getPushInfoLastTime()))
               return null;
            MarketPreferences.getInstance(context).setPushInfoLastTime(jsonobject1.getString("TIME"));
            evaluatinfo = parseResult(context, jsonobject1);
        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
        return evaluatinfo;
        
        
        
    }

    private static EvaluatInfo parseResult(Context context, JSONObject jsonobject)
    {
        EvaluatInfo evaluatinfo = new EvaluatInfo();
        try {
            evaluatinfo.setId(jsonobject.getInt("ACT_ID"));
            evaluatinfo.setName(jsonobject.getString("AD_NAME"));
            evaluatinfo.setIconUrl(jsonobject.getString("IMG_URL"));
            evaluatinfo.setSkipUrl(jsonobject.getString("SKIP_URL"));
            evaluatinfo.setStartDate(jsonobject.getString("START_DATE"));
            evaluatinfo.setAppId(jsonobject.getInt("ID"));
            evaluatinfo.setAppName(jsonobject.getString("NAME"));
            evaluatinfo.setAppIconUrl(jsonobject.getString("ICON_URL"));
            evaluatinfo.setAppPackageName(jsonobject.getString("PACKAGE_NAME"));
            evaluatinfo.setAppDownloadUrl(jsonobject.getString("APK_URL"));
            evaluatinfo.setAppLongSize(jsonobject.getLong("SIZE"));
            evaluatinfo.setAppVersionName(jsonobject.getString("VERSION_NAME"));
 
                evaluatinfo.setAppSize(StringUtil.getFormatSize(evaluatinfo.getAppLongSize()));
                evaluatinfo.setBrowseCount(StringUtil.getDownloadNumber(jsonobject.getString("BROWSE_COUNT")));
                evaluatinfo.setAppDownloadCount(StringUtil.getDownloadNumber(jsonobject.getString("DOWNLOAD_COUNT")));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
         
        return evaluatinfo;
        
    }

    public static final String REQUEST_TAG = "LOGIN_PUSH_INFO";
}
