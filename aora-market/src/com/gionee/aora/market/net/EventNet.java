// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.module.EvaluatInfo;
import java.util.ArrayList;
import org.json.*;

public class EventNet
{

    public EventNet()
    {
    }

    public static ArrayList getEventInfos()
    {
        ArrayList arraylist;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("LOCAL_INFO_MODULE");
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("TAG_NUM", 1);
            arraylist = parseEvent(BaseNet.doRequestHandleResultCode("LOCAL_INFO_MODULE", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("EventNet", "getEventInfos()#exception", exception);
            return null;
        }
        return arraylist;
    }

    private static ArrayList parseEvent(JSONObject jsonobject)
    {
        ArrayList arraylist = new ArrayList();
        try {
            JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
            int i = 0;
            while(i < jsonarray.length()) {
                    try {
                        JSONObject jsonobject1 = jsonarray.getJSONObject(i);
                        EvaluatInfo evaluatinfo = new EvaluatInfo();
                        evaluatinfo.setId(jsonobject1.getInt("ID"));
                        evaluatinfo.setIconUrl(jsonobject1.getString("IMG"));
                        evaluatinfo.setSkipUrl(jsonobject1.getString("URL"));
                        if( jsonobject1.getString("FLAG").equals("0")){
                            //L3_L3:
                            evaluatinfo.setFlag(true);
                        }else{
                            //L4_L4:
                            evaluatinfo.setFlag(false);
                        }
     //_L6:
                        evaluatinfo.setAppId(jsonobject1.getInt("SOFT_ID"));
                        evaluatinfo.setAppName(jsonobject1.getString("SOFT_NAME"));
                        evaluatinfo.setAppIconUrl(jsonobject1.getString("ICON_URL"));
                        evaluatinfo.setAppPackageName(jsonobject1.getString("PACKAGE_NAME"));
                        evaluatinfo.setAppDownloadUrl(jsonobject1.getString("APK_URL"));
                        evaluatinfo.setAppLongSize(jsonobject1.getLong("SIZE"));
                        evaluatinfo.setAppVersionName(jsonobject1.getString("VERSION_NAME"));
                        evaluatinfo.setAppDownloadCount(StringUtil.getDownloadNumber(jsonobject1.getString("DOWNLOAD_COUNT")));
     //_L7:
                        evaluatinfo.setAppSize(StringUtil.getFormatSize(evaluatinfo.getAppLongSize()));
                        arraylist.add(evaluatinfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraylist;
        
    }

    public static final String EVENT = "LOCAL_INFO_MODULE";
    public static final String TAG = "EventNet";
}
