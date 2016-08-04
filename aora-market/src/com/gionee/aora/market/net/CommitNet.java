// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import org.json.JSONException;
import org.json.JSONObject;

public class CommitNet
{

    public CommitNet()
    {
    }

    public static boolean getCommit(String s, int i, String s1)
    {
        boolean flag;
        try
        {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("TAG", "PROBLEMS");
            jsonobject.put("ID", s);
            jsonobject.put("API_VERSION", 3);
            jsonobject.put("PROBLEMS_VALUE", s1);
            jsonobject.put("PROBLEMS_NO", i);
            long l = System.currentTimeMillis();
            String s2 = HttpRequest.getDefaultHttpRequest().startPost(jsonobject.toString());
            DataCollectManager.addNetRecord("PROBLEMS", l, System.currentTimeMillis());
            DLog.i("CommitNet", (new StringBuilder()).append("CommitNet#getCommit content =").append(s2).toString());
            flag = parseCommit(s2);
        }
        catch(Exception exception)
        {
            DLog.i("CommitNet", (new StringBuilder()).append("CommitNet#getCommit").append(exception).toString());
            return false;
        }
        return flag;
    }

    private static boolean parseCommit(String s)
        throws JSONException
    {
        JSONObject jsonobject = new JSONObject(s);
        DLog.i("CommitNet", (new StringBuilder()).append("CommitInfo#parseCommit content = ").append(s).toString());
        String s1 = jsonobject.getString("TAG");
        DLog.i("CommitNet", (new StringBuilder()).append("TAG = ").append(s1).toString());
        return "PROBLEMS".equals(s1);
    }

    private static final String PROBLEMS = "PROBLEMS";
    private static final String TAG = "CommitNet";
}
