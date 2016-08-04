// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.module.PreloadInfo;
import java.util.ArrayList;
import org.json.*;

public class PreloadedNet
{

    public PreloadedNet()
    {
    }

    public static ArrayList getPreloadedApp()
    {
        ArrayList arraylist;
        try
        {
            long l = System.currentTimeMillis();
            String s = getPreloadedAppRequest();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("GET_POP_APPS", l, System.currentTimeMillis());
            arraylist = parsePreloadedApp(s1);
        }
        catch(Exception exception)
        {
            DLog.e("PreloadedNet", "#getPreloadedApp()", exception);
            return null;
        }
        return arraylist;
    }

    private static String getPreloadedAppRequest()
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", "GET_POP_APPS");
            jsonobject.put("API_VERSION", 3);
        }
        catch(JSONException jsonexception)
        {
            DLog.e("PreloadedNet", "#getPreloadedAppRequest()", jsonexception);
        }
        return jsonobject.toString();
    }

    private static ArrayList parsePreloadedApp(String s)
    {
        ArrayList arraylist;
        if(s == null || s.equals(""))
            return null;
        arraylist = new ArrayList();
        try {
            JSONArray jsonarray = (new JSONObject(s)).getJSONArray("ARRAY");
            int i = 0;
            while(i < jsonarray.length()){
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                arraylist.add(new PreloadInfo(jsonobject.getString("SOFT_ID"), jsonobject.getString("APP_NAME"), jsonobject.getString("PACKAGE_NAME"), jsonobject.getString("ICON_URL"), jsonobject.getString("APK_URL"), Integer.parseInt(jsonobject.getString("SIZE")), jsonobject.getInt("COIN"), jsonobject.getString("VER_NAME"), true));
                i++;
            }
        } catch ( Exception e) {
            e.printStackTrace();
        }
            
        return arraylist;
    }

    private static final String GET_POP_APPS = "GET_POP_APPS";
    private static final String TAG = "PreloadedNet";
}
