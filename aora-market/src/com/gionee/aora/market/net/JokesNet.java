// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.BaseNet;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import org.json.JSONException;
import org.json.JSONObject;

public class JokesNet
{

    public JokesNet()
    {
    }

    public static String getJokes()
    {
        String s1;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("GET_JOKES_INFO");
            long l = System.currentTimeMillis();
            String s = HttpRequest.getDefaultHttpRequest().startPost(jsonobject.toString());
            DataCollectManager.addNetRecord("GET_JOKES_INFO", l, System.currentTimeMillis());
            s1 = parseJokes(s);
        }
        catch(Exception exception)
        {
            DLog.i("JokesNet", (new StringBuilder()).append("getJokes#error =").append(exception).toString());
            return null;
        }
        return s1;
    }

    private static String parseJokes(String s)
        throws JSONException
    {
        return (new JSONObject(s)).getString("JOKE");
    }

    private static final String GET_JOKES_INFO = "GET_JOKES_INFO";
    private static final String TAG = "JokesNet";
}
