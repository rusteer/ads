// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.module.AppInfo;
import java.util.*;
import org.json.*;

public class FirstNet
{

    public FirstNet()
    {
    }

    public static List getFirstDayNames(String s)
    {
        ArrayList arraylist;
        try
        {
            arraylist = new ArrayList();
            JSONObject jsonobject = BaseNet.getBaseJSON(FIRST_PUBLISH_LIST);
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("DATE", s);
            jsonobject.put("GO_METHOD", "TIME_LINE");
            parseFirstDayNames(BaseNet.doRequestHandleResultCode(FIRST_PUBLISH_LIST, jsonobject), arraylist);
        }
        catch(Exception exception)
        {
            DLog.e("GiftNet", "getFirstDayNames()#exception", exception);
            return null;
        }
        return arraylist;
    }

    public static Map getFirstPublishList(String s, int i, int j)
    {
        HashMap hashmap;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON(FIRST_PUBLISH_LIST);
            jsonobject.put("START_ID", s);
            jsonobject.put("INDEX_SIZE", i);
            jsonobject.put("TYPE", j);
            jsonobject.put("API_VERSION", 6);
            JSONObject jsonobject1 = BaseNet.doRequestHandleResultCode(FIRST_PUBLISH_LIST, jsonobject);
            ArrayList arraylist = new ArrayList();
            ArrayList arraylist1 = new ArrayList();
            hashmap = new HashMap();
            parseFirstPublishList(arraylist, arraylist1, jsonobject1);
            hashmap.put("items", arraylist);
            hashmap.put("group_name", arraylist1);
        }
        catch(Exception exception)
        {
            DLog.e("FirstNet", "getFirstPublishList()#exception", exception);
            return null;
        }
        return hashmap;
    }

    private static void parseFirstDayNames(JSONObject jsonobject, List list)
        throws JSONException
    {
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
            list.add(jsonarray.getString(i));

    }

    private static void parseFirstPublishList(List list, List list1, JSONObject jsonobject)
        throws JSONException
    {
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
        {
            JSONObject jsonobject1 = jsonarray.getJSONObject(i);
            ArrayList arraylist = new ArrayList();
            list1.add(jsonobject1.getString("DATE"));
            JSONArray jsonarray1 = jsonobject1.getJSONArray("APP_LIST");
            int j = 0;
            while(j < jsonarray1.length()) 
            {
                AppInfo appinfo = new AppInfo();
                JSONObject jsonobject2 = jsonarray1.getJSONObject(j);
                appinfo.setSoftId(jsonobject2.getString("ID"));
                appinfo.setName(jsonobject2.getString("NAME"));
                appinfo.setPackageName(jsonobject2.getString("PACKAGE_NAME"));
                appinfo.setDeveloper(jsonobject2.getString("DEVELOPER"));
                appinfo.setIconUrl(jsonobject2.getString("ICON_URL"));
                appinfo.setDownloadUrl(jsonobject2.getString("APK_URL"));
                appinfo.setCurVersionName(jsonobject2.getString("VERSION_NAME"));
                appinfo.setCurVersionCode(jsonobject2.getInt("VERSION_CODE"));
                appinfo.setDescribe(jsonobject2.getString("INTRO"));
                appinfo.setUpdateSoftSize(jsonobject2.getInt("SIZE"));
                try
                {
                    appinfo.setAppStars(Float.parseFloat(jsonobject2.getString("STAR")));
                    appinfo.setDownload_region(StringUtil.getDownloadNumber(jsonobject2.getString("DOWNLOAD_COUNT")));
                    appinfo.setSize(StringUtil.getFormatSize(appinfo.getUpdateSoftSize()));
                }
                catch(Exception exception)
                {
                    DLog.e("GiftNet", "parseResult(),excption#\u6570\u5B57\u683C\u5F0F\u5316\u5F02\u5E38", exception);
                }
                arraylist.add(appinfo);
                j++;
            }
            list.add(arraylist);
        }

    }

    public static String FIRST_PUBLISH_LIST = "FIRST_PUBLISH_LIST";

}
