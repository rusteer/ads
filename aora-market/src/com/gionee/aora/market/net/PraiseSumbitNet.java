// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import org.json.JSONException;
import org.json.JSONObject;

public class PraiseSumbitNet
{

    public PraiseSumbitNet()
    {
    }

    public static boolean getPraiseSumbit(String s)
    {
        boolean flag;
        try
        {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("TAG", "SUBIMIT_PRAISE");
            jsonobject.put("ID", s);
            jsonobject.put("IMEI", DataCollectUtil.getImei());
            jsonobject.put("API_VERSION", 3);
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(jsonobject.toString());
            DataCollectManager.addNetRecord("SUBIMIT_PRAISE", l, System.currentTimeMillis());
            flag = parsePraiseSumbit(s1);
        }
        catch(Exception exception)
        {
            DLog.i("PraiseSumbitNet", (new StringBuilder()).append("PraiseSumbitNet#getPraiseSumbit").append(exception).toString());
            return false;
        }
        return flag;
    }

    private static boolean parsePraiseSumbit(String s)
        throws JSONException
    {
        return "0".equals((new JSONObject(s)).getString("RESULT_CODE"));
    }

    private static final String SUBIMIT_PRAISE = "SUBIMIT_PRAISE";
    private static final String TAG = "PraiseSumbitNet";
}
