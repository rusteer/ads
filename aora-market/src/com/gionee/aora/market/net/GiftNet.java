// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.integral.net.IntegralBaseNet;
import com.gionee.aora.market.module.GiftInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class GiftNet
{

    public GiftNet()
    {
    }

    public static List getGiftDetailsInfo(int i, UserInfo userinfo, String s)
    {
        ArrayList arraylist = new ArrayList();
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("GET_GIFT_INFO");
            jsonobject.put("GIFT_ID", i);
            jsonobject.put("USER_ID", userinfo.getUSER_NAME());
            jsonobject.put("FLAG", userinfo.getUSER_TYPE_FLAG());
            jsonobject.put("MODEL", s);
            JSONObject jsonobject1 = IntegralBaseNet.doRequestHandleResultCode("GET_GIFT_INFO", jsonobject);
            arraylist.add(jsonobject1.getString("SURPLUS_COUNT"));
            arraylist.add(jsonobject1.getString("ITEM_CODE"));
        }
        catch(Exception exception)
        {
            DLog.e("GiftNet", "getGiftDetailsInfo()#exception", exception);
            return arraylist;
        }
        return arraylist;
    }

    public static GiftInfo getGiftInfo(JSONObject jsonobject)
        throws JSONException
    {
        GiftInfo giftinfo = new GiftInfo();
        giftinfo.setId(jsonobject.getInt("GIFT_ID"));
        giftinfo.setName(jsonobject.getString("GIFT_NAME"));
        giftinfo.setCode(jsonobject.getString("ACTIVATION_KEY"));
        giftinfo.setAppId(jsonobject.getInt("ID"));
        giftinfo.setAppName(jsonobject.getString("NAME"));
        giftinfo.setAppPackageName(jsonobject.getString("PACKAGE_NAME"));
        giftinfo.setAppSize(StringUtil.getFormatSize(jsonobject.getLong("SIZE")));
        giftinfo.setAppIconUrl(jsonobject.getString("ICON_URL"));
        giftinfo.setAppDownloadUrl(jsonobject.getString("APK_URL"));
        giftinfo.setSurplusCount(jsonobject.getString("SURPLUS_COUNT"));
        giftinfo.setAppVersionName(jsonobject.getString("VERSION_NAME"));
        return giftinfo;
    }

    public static List getMyGiftInfos(String s, int i, String s1, int j, int k)
    {
        List list;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("MY_GIFT_LIST");
            jsonobject.put("USER_ID", s);
            jsonobject.put("FLAG", i);
            jsonobject.put("MODEL", s1);
            jsonobject.put("INDEX_START", j);
            jsonobject.put("INDEX_SIZE", k);
            list = parstMyGiftInfosResult(IntegralBaseNet.doRequestHandleResultCode("MY_GIFT_LIST", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("MyGiftNet", "getMyGiftInfos()#Exception=", exception);
            return null;
        }
        return list;
    }

    private static void parseSubmitResultInfo(JSONObject jsonobject, GiftInfo giftinfo)
        throws JSONException
    {
        giftinfo.setSurplusCount(jsonobject.getString("SURPLUS_COUNT"));
        giftinfo.setCode(jsonobject.getString("ITEM_CODE"));
        giftinfo.setGetGiftResultCode(jsonobject.getInt("RESULT_FLAG"));
        giftinfo.setFailedMsg(jsonobject.getString("MSG"));
    }

    private static List parstMyGiftInfosResult(JSONObject jsonobject)
        throws Exception
    {
        ArrayList arraylist = new ArrayList();
        JSONArray jsonarray = jsonobject.getJSONArray("ARRAY");
        for(int i = 0; i < jsonarray.length(); i++)
            arraylist.add(getGiftInfo(jsonarray.getJSONObject(i)));

        return arraylist;
    }

    public static GiftInfo submitGetGiftInfo(int i, UserInfo userinfo, String s)
    {
        GiftInfo giftinfo = new GiftInfo();
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("SUBMIT_GIFT_INFO");
            jsonobject.put("GIFT_ID", i);
            jsonobject.put("USER_ID", userinfo.getUSER_NAME());
            jsonobject.put("FLAG", userinfo.getUSER_TYPE_FLAG());
            jsonobject.put("MODEL", s);
            parseSubmitResultInfo(IntegralBaseNet.doRequestHandleResultCode("SUBMIT_GIFT_INFO", jsonobject), giftinfo);
        }
        catch(Exception exception)
        {
            DLog.e("GiftNet", "submitGetGiftInfo()#exception", exception);
            return giftinfo;
        }
        return giftinfo;
    }

    private static final String GET_GIFT_INFO = "GET_GIFT_INFO";
    private static final String MY_GIFT_LIST = "MY_GIFT_LIST";
    private static final String SUBMIT_GIFT_INFO = "SUBMIT_GIFT_INFO";
}
