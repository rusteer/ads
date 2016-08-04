// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.module.LableInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class LableListNet
{

    public LableListNet()
    {
    }

    private static ArrayList analysisLableItemListResult(JSONObject jsonobject)
        throws JSONException
    {
        ArrayList arraylist = new ArrayList();
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        int i = 0;
        while(i < jsonarray.length()) 
        {
            JSONObject jsonobject1 = jsonarray.getJSONObject(i);
            AppInfo appinfo = new AppInfo();
            appinfo.setSoftId(jsonobject1.getString("ID"));
            appinfo.setIconUrl(jsonobject1.getString("ICON_URL"));
            appinfo.setName(jsonobject1.getString("NAME"));
            try
            {
                appinfo.setAppStars(Float.parseFloat(jsonobject1.getString("STAR")) / 2.0F);
            }
            catch(NumberFormatException numberformatexception)
            {
                DLog.e("LableListNet", "analysisLableItemListResult()#NumberFormatException", numberformatexception);
            }
            appinfo.setPackageName(jsonobject1.getString("PACKAGE_NAME"));
            appinfo.setSize(StringUtil.getFormatSize(jsonobject1.getLong("SIZE")));
            appinfo.setDownloadUrl(jsonobject1.getString("APK_URL"));
            appinfo.setDescribe(jsonobject1.getString("INTRO"));
            appinfo.setIntegral(jsonobject1.getInt("INTEGRAL"));
            appinfo.setDownload_region(StringUtil.getDownloadNumber(jsonobject1.getString("DOWNLOAD_COUNT")));
            arraylist.add(appinfo);
            i++;
        }
        return arraylist;
    }

    private static List analysisLablesResult(JSONObject jsonobject, List list)
        throws Exception
    {
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
        {
            JSONObject jsonobject1 = jsonarray.getJSONObject(i);
            LableInfo lableinfo = new LableInfo();
            lableinfo.setId(jsonobject1.getInt("VID"));
            lableinfo.setName(jsonobject1.getString("NAME"));
            list.add(lableinfo);
        }

        return list;
    }

    public static ArrayList getLableItemList(int i, int j, int k)
    {
        ArrayList arraylist;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("GET_LABEL_LIST");
            jsonobject.put("ID", i);
            jsonobject.put("INDEX_START", j);
            jsonobject.put("INDEX_SIZE", k);
            arraylist = analysisLableItemListResult(BaseNet.doRequestHandleResultCode("GET_LABEL_LIST", jsonobject));
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        return arraylist;
    }

    public static List getLables(int i)
    {
        ArrayList arraylist = new ArrayList();
        List list;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("LABEL_LIST");
            jsonobject.put("TYPE", i);
            list = analysisLablesResult(BaseNet.doRequestHandleResultCode("LABEL_LIST", jsonobject), arraylist);
        }
        catch(Exception exception)
        {
            DLog.e("LableListNet", "getLables()#exception", exception);
            return arraylist;
        }
        return list;
    }

    private static final String TAG = "LableListNet";
}
