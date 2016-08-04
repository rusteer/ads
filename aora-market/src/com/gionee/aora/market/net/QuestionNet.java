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
import org.json.JSONObject;

public class QuestionNet
{

    public QuestionNet()
    {
    }

    public static String getQuestion(UserInfo userinfo)
    {
        String s;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("GET_QUESTIONNAIRE");
            jsonobject.put("IMEI", DataCollectUtil.getImei());
            jsonobject.put("TIME", System.currentTimeMillis());
            jsonobject.put("USER_ID", userinfo.getUSER_NAME());
            jsonobject.put("FLAG", userinfo.getUSER_TYPE_FLAG());
            jsonobject.put("MODEL", Build.MODEL);
            s = IntegralBaseNet.doRequestHandleResultCode("GET_QUESTIONNAIRE", jsonobject).getString("URL");
        }
        catch(Exception exception)
        {
            DLog.i("JokesNet", (new StringBuilder()).append("QuestionNet#getQuestion =").append(exception).toString());
            return "";
        }
        return s;
    }

    private static final String QUESTIONNAIRE = "GET_QUESTIONNAIRE";
    private static final String TAG = "JokesNet";
}
