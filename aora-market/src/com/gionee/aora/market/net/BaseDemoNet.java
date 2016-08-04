// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.gionee.aora.market.module.AppInfo;
import java.util.ArrayList;
import org.json.*;

public class BaseDemoNet
{

    public BaseDemoNet()
    {
    }

    public static ArrayList getXXX(String s, int i)
    {
        ArrayList arraylist;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("TAG_XXX");
            jsonobject.put("arg0", s);
            jsonobject.put("arg1", i);
            arraylist = parseJSON(BaseNet.doRequestHandleResultCode("TAG_XXX", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("AoraXXXNet", "#getXXXList()", exception);
            return null;
        }
        return arraylist;
    }

    private static ArrayList parseJSON(JSONObject jsonobject)
        throws JSONException
    {
        ArrayList arraylist = new ArrayList();
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
        {
            JSONObject jsonobject1 = jsonarray.getJSONObject(i);
            arraylist.add(new AppInfo(jsonobject1.getString("SOFT_ID"), jsonobject1.getString("ICON_URL"), jsonobject1.getString("APP_NAME"), 0.0F, jsonobject1.getString("PACKAGE_NAME"), jsonobject1.getString("SIZE"), jsonobject1.getString("APK_URL"), "", jsonobject1.getInt("COIN")));
        }

        return arraylist;
    }

    public static final String REQUEST_TAG = "TAG_XXX";
}
