// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.module.EvaluatInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class EvaluationNet
{

    public EvaluationNet()
    {
    }

    public static EvaluatInfo getEvaluatInfo(JSONObject jsonobject)
        throws JSONException
    {
        EvaluatInfo evaluatinfo = new EvaluatInfo();
        evaluatinfo.setId(jsonobject.getInt("EVA_ID"));
        evaluatinfo.setName(jsonobject.getString("AD_NAME"));
        evaluatinfo.setIconUrl(jsonobject.getString("IMG_URL"));
        evaluatinfo.setAuthor(jsonobject.getString("AUTHOR"));
        evaluatinfo.setDate(jsonobject.getString("DATE"));
        evaluatinfo.setSkipUrl(jsonobject.getString("SKIP_URL"));
        evaluatinfo.setAppId(jsonobject.getInt("ID"));
        evaluatinfo.setAppName(jsonobject.getString("NAME"));
        evaluatinfo.setAppIconUrl(jsonobject.getString("ICON_URL"));
        evaluatinfo.setAppPackageName(jsonobject.getString("PACKAGE_NAME"));
        evaluatinfo.setAppDownloadUrl(jsonobject.getString("APK_URL"));
        evaluatinfo.setAppLongSize(jsonobject.getLong("SIZE"));
        evaluatinfo.setAppVersionName(jsonobject.getString("VERSION_NAME"));
        try
        {
            evaluatinfo.setAppSize(StringUtil.getFormatSize(evaluatinfo.getAppLongSize()));
            evaluatinfo.setBrowseCount(StringUtil.getDownloadNumber(jsonobject.getString("BROWSE_COUNT")));
            evaluatinfo.setAppDownloadCount(StringUtil.getDownloadNumber(jsonobject.getString("DOWNLOAD_COUNT")));
        }
        catch(Exception exception)
        {
            DLog.e("EvaluationNet", "parseEvaluationResultJson()#\u6570\u5B57\u683C\u5F0F\u5316\u5F02\u5E38exception", exception);
            return evaluatinfo;
        }
        return evaluatinfo;
    }

    public static List getEvaluatInfos(String s, int i, int j)
    {
        ArrayList arraylist;
        try
        {
            arraylist = new ArrayList();
            JSONObject jsonobject = BaseNet.getBaseJSON("GAME_EVA_LIST");
            jsonobject.put("MODEL", s);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            parseEvaluationResultJson(BaseNet.doRequestHandleResultCode("GAME_EVA_LIST", jsonobject), arraylist);
        }
        catch(Exception exception)
        {
            DLog.e("EvaluationNet", "getEvaluatInfos()#exception", exception);
            return null;
        }
        return arraylist;
    }

    private static void parseEvaluationResultJson(JSONObject jsonobject, List list)
        throws JSONException
    {
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
            list.add(getEvaluatInfo(jsonarray.getJSONObject(i)));

    }

    private static final String GAME_EVA_LIST = "GAME_EVA_LIST";
}
