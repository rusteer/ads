// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.module.AppInfo;
import java.util.ArrayList;
import org.json.*;

public class AoraAppNet
{

    public AoraAppNet()
    {
    }

    private static String getAoraAppListRequest()
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", "GET_AORA_APPS");
            jsonobject.put("API_VERSION", 3);
        }
        catch(JSONException jsonexception)
        {
            DLog.e("AoraAppNet", "#getAoraAppListRequest()", jsonexception);
        }
        return jsonobject.toString();
    }

    public static ArrayList getAppList()
    {
        ArrayList arraylist;
        try
        {
            long l = System.currentTimeMillis();
            String s = getAoraAppListRequest();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("GET_AORA_APPS", l, System.currentTimeMillis());
            arraylist = parseAoraAppList(s1);
        }
        catch(Exception exception)
        {
            DLog.e("AoraAppNet", "#getAppList()", exception);
            return null;
        }
        return arraylist;
    }

    private static ArrayList parseAoraAppList(String s)
    {
        if(s == null || s.equals(""))
            return null;
        ArrayList  arraylist = new ArrayList();
        try {
           
            JSONArray jsonarray = (new JSONObject(s)).getJSONArray("ARRAY");
            int i = 0;
            while(i < jsonarray.length()){
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                arraylist.add(new AppInfo(jsonobject.getString("SOFT_ID"), jsonobject.getString("ICON_URL"), jsonobject.getString("APP_NAME"), 0.0F, jsonobject.getString("PACKAGE_NAME"), jsonobject.getString("SIZE"), jsonobject.getString("APK_URL"), "", jsonobject.getInt("COIN")));
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraylist;
    }

    private static final String TAG_GETAPPLIST = "GET_AORA_APPS";
}
