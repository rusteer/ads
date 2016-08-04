// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.integral.net.IntegralBaseNet;
import com.gionee.aora.integral.util.DES;
import org.json.JSONException;
import org.json.JSONObject;

public class CallTimeNet
{

    public CallTimeNet()
    {
    }

    public static int[] getCallTime(UserInfo userinfo)
    {
        int ai[];
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("FREE_CALL");
            jsonobject.put("METHOD", "CheckMin_I");
            JSONObject jsonobject1 = new JSONObject();
            jsonobject1.put("MODEL", Build.MODEL);
            jsonobject1.put("ID", userinfo.getID());
            jsonobject1.put("USER_ID", userinfo.getUSER_NAME());
            jsonobject1.put("HAND_KEY", userinfo.getHAND_KEY());
            jsonobject1.put("FLAG", userinfo.getUSER_TYPE_FLAG());
            jsonobject1.put("IMEI", DataCollectUtil.getImei());
            jsonobject.put("ENCRY_DATA", DES.desEncrypt(jsonobject1.toString()));
            jsonobject.put("API_VERSION", 6);
            ai = parseCallTime(IntegralBaseNet.doRequestHandleResultCode("FREE_CALL", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("CallTimeNet", "#getCallTime()", exception);
            return null;
        }
        return ai;
    }

    private static int[] parseCallTime(JSONObject jsonobject)
    {
        int ai[];
        try
        {
            ai = new int[3];
            ai[0] = jsonobject.getInt("FREE_CALL_TIME");
            ai[1] = jsonobject.getInt("COMMODITY_ID");
            ai[2] = jsonobject.getInt("COMMODITY_PRICE");
        }
        catch(JSONException jsonexception)
        {
            DLog.e("CallTimeNet", "#parseCallTime()", jsonexception);
            return null;
        }
        return ai;
    }

    public static final String CALL = "FREE_CALL";
    public static final String TAG = "CallTimeNet";
}
