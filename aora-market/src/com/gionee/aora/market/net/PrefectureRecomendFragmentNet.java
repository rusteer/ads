// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import android.text.TextUtils;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.module.PrefectureInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class PrefectureRecomendFragmentNet
{

    public PrefectureRecomendFragmentNet()
    {
    }

    private static List analysisPrefectureRecomendList(String s)
        throws Exception
    {
        ArrayList arraylist = new ArrayList();
        String s1 = (new JSONObject(s)).getString("ARRAY");
        if(!TextUtils.isEmpty(s1) && !"null".equals(s1) && !TextUtils.isEmpty(s))
        {
            JSONArray jsonarray = new JSONArray(s1);
            for(int i = 0; i < jsonarray.length(); i++)
            {
                JSONArray jsonarray1 = jsonarray.getJSONArray(i);
                arraylist.add(new PrefectureInfo(jsonarray1.getString(0), jsonarray1.getInt(1), jsonarray1.getString(2), jsonarray1.getInt(9), jsonarray1.getString(10), jsonarray1.getString(11), false));
            }

        }
        return arraylist;
    }

    public static List getPrefectureRecomendList(int i, int j)
    {
        List list;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("SUBJECT_SIM");
            jsonobject.put("TAG", "SUBJECT_SIM");
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("API_VERSION", 6);
            long l = System.currentTimeMillis();
            JSONObject jsonobject1 = BaseNet.doRequestHandleResultCode("SUBJECT_SIM", jsonobject);
            DataCollectManager.addNetRecord("SUBJECT_SIM", l, System.currentTimeMillis());
            list = parseResult(jsonobject1);
        }
        catch(Exception exception)
        {
            DLog.e(TAG, "getPrefectureRecomendList()#Exception=", exception);
            return null;
        }
        return list;
    }

    private static String getRequestData(int i, int j)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", "SUBJECT_SIM");
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("API_VERSION", 3);
        }
        catch(Exception exception)
        {
            DLog.e(TAG, "getRequestData()#Exception=", exception);
        }
        return jsonobject.toString();
    }

    private static List parseResult(JSONObject jsonobject)
        throws Exception
    {
        ArrayList arraylist = new ArrayList();
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
        {
            JSONObject jsonobject1 = jsonarray.getJSONObject(i);
            PrefectureInfo prefectureinfo = new PrefectureInfo();
            prefectureinfo.setIconUrl(jsonobject1.getString("PR_ICON_URL"));
            prefectureinfo.setId(jsonobject1.getInt("PR_ID"));
            prefectureinfo.setName(jsonobject1.getString("PR_NAME"));
            prefectureinfo.setBrowseCount(jsonobject1.getInt("PR_BROWS_COUNT"));
            prefectureinfo.setTime(jsonobject1.getString("PR_TIME"));
            prefectureinfo.setDescrible(jsonobject1.getString("PR_DES"));
            JSONArray jsonarray1 = jsonobject1.getJSONArray("APP_ARRAY");
            int j = 0;
            while(j < jsonarray1.length()) 
            {
                JSONObject jsonobject2 = jsonarray1.getJSONObject(j);
                AppInfo appinfo = new AppInfo();
                appinfo.setvId(prefectureinfo.getId());
                appinfo.setSoftId(jsonobject2.getString("ID"));
                appinfo.setName(jsonobject2.getString("NAME"));
                appinfo.setPackageName(jsonobject2.getString("PACKAGE_NAME"));
                appinfo.setDeveloper(jsonobject2.getString("DEVELOPER"));
                appinfo.setIconUrl(jsonobject2.getString("ICON_URL"));
                appinfo.setDownloadUrl(jsonobject2.getString("APK_URL"));
                appinfo.setCurVersionName(jsonobject2.getString("VERSION_NAME"));
                appinfo.setCurVersionCode(jsonobject2.getInt("VERSION_CODE"));
                try
                {
                    appinfo.setAppStars(Float.parseFloat(jsonobject2.getString("STAR")));
                    appinfo.setDownload_region(StringUtil.getDownloadNumber(jsonobject2.getString("DOWNLOAD_COUNT")));
                    appinfo.setSize(StringUtil.getFormatSize(jsonobject2.getLong("SIZE")));
                }
                catch(Exception exception)
                {
                    DLog.e(TAG, "parseResult(),excption#\u6570\u5B57\u683C\u5F0F\u5316\u5F02\u5E38", exception);
                }
                prefectureinfo.getAppInfos().add(appinfo);
                j++;
            }
            arraylist.add(prefectureinfo);
        }

        return arraylist;
    }

    public static final String SUBJECT_SIM = "SUBJECT_SIM";
    private static String TAG = "PrefectureRecomendFragmentNet";

}
