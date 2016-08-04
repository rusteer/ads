// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedbackNet
{

    public FeedbackNet()
    {
    }

    public static boolean getFeedback(String s, String s1, String s2, int i)
    {
        boolean flag;
        try
        {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("TAG", "REEDBACK");
            jsonobject.put("USER_ID", "");
            jsonobject.put("MSG", s);
            jsonobject.put("IMEI", s1);
            jsonobject.put("Device", s2);
            jsonobject.put("Version", i);
            jsonobject.put("API_VERSION", 3);
            long l = System.currentTimeMillis();
            String s3 = HttpRequest.getDefaultHttpRequest().startPost(jsonobject.toString());
            DataCollectManager.addNetRecord("REEDBACK", l, System.currentTimeMillis());
            DLog.i("FeedbackNet", (new StringBuilder()).append("FeedbackNet#getFeedback content =").append(s3).toString());
            flag = parseFeedback(s3);
        }
        catch(Exception exception)
        {
            DLog.i("FeedbackNet", (new StringBuilder()).append("FeedbackNet#getFeedback").append(exception).toString());
            return false;
        }
        return flag;
    }

    private static boolean parseFeedback(String s)
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject(s);
        DLog.i("FeedbackNet", (new StringBuilder()).append("FeedbackInfo#parseFeedback content = ").append(s).toString());
        String s1 = jsonobject.getString("TAG");
        DLog.i("FeedbackNet", (new StringBuilder()).append("TAG = ").append(s1).toString());
        return "REEDBACK".equals(s1);
    }

    private static final String REEDBACK = "REEDBACK";
    private static final String TAG = "FeedbackNet";
}
