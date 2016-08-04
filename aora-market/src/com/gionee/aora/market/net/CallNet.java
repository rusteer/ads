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

public class CallNet
{

    public CallNet()
    {
    }

    public static Object[] getCall(String s, UserInfo userinfo)
    {
        Object aobj[];
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("FREE_CALL");
            jsonobject.put("METHOD", "Call");
            JSONObject jsonobject1 = new JSONObject();
            jsonobject1.put("MODEL", Build.MODEL);
            jsonobject1.put("CALLEE", s);
            jsonobject1.put("ID", userinfo.getID());
            jsonobject1.put("USER_ID", userinfo.getUSER_NAME());
            jsonobject1.put("HAND_KEY", userinfo.getHAND_KEY());
            jsonobject1.put("FLAG", userinfo.getUSER_TYPE_FLAG());
            jsonobject1.put("IMEI", DataCollectUtil.getImei());
            jsonobject.put("ENCRY_DATA", DES.desEncrypt(jsonobject1.toString()));
            jsonobject.put("API_VERSION", 6);
            aobj = parseCall(IntegralBaseNet.doRequest("FREE_CALL", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("CallNet", "#getCall()", exception);
            return null;
        }
        return aobj;
    }

    private static Object[] parseCall(JSONObject jsonobject)
    {
        Object aobj[] = new Object[2];
        try {
            if(jsonobject.getInt("RESULT_CODE") == 0)
            {
            aobj[0] = Boolean.valueOf(true);
            }else{
                aobj[0] = Boolean.valueOf(false); 
            }
            aobj[1] = jsonobject.getString("MSG");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return aobj;
        
        
        
    }

    public static final String CALL = "FREE_CALL";
    public static final String TAG = "CallNet";
}
