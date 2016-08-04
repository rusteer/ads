// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.datacollect.DataCollectBaseInfo;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.module.MyQueue;
import java.util.*;
import org.json.*;

public class DataCollectNet
{

    public DataCollectNet()
    {
    }

    private static String getCollectUpdateRequest(MyQueue myqueue, HashMap hashmap)
    {
        JSONObject result = new JSONObject();
        try {
            JSONArray jsonarray = new JSONArray();
            Exception exception;
            result.put("TAG", "DATA_COLLECTION");
            result.put("API_VERSION", 3);
            java.util.Map.Entry entry;
            for(Iterator iterator = hashmap.entrySet().iterator(); iterator.hasNext(); result.put((String)entry.getKey(), entry.getValue().toString()))
                entry = (java.util.Map.Entry)iterator.next();

      // _L1:
                for(Iterator iterator1 = myqueue.iterator(); iterator1.hasNext(); jsonarray.put(((DataCollectBaseInfo)iterator1.next()).toJSONObject()));
                
                    result.put("ARRAY", jsonarray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
             
        String s = result.toString();
        DLog.i("DataCollectNet", (new StringBuilder()).append(" uploadDataCollect#request string= ").append(s).toString());
        return s;
        

         
    }

    private static boolean parseResult(String s)
    {
        boolean flag = true;
        if(s == null || s.equals(""))
            return false;
        int i;
        try
        {
            i = (new JSONObject(s)).getInt("RESULT");
        }
        catch(JSONException jsonexception)
        {
            DLog.e("DataCollectNet", " #parseSignInData#Exception ", jsonexception);
            return false;
        }
        if(i != 1)
            flag = false;
        return flag;
    }

    public static boolean uploadDataCollect(MyQueue myqueue, HashMap hashmap)
    {
        boolean flag;
        try
        {
            String s = getCollectUpdateRequest(myqueue, hashmap);
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DLog.i("DataCollectNet", (new StringBuilder()).append(" uploadDataCollect#response ").append(s1).toString());
            flag = parseResult(s1);
        }
        catch(Exception exception)
        {
            DLog.e("DataCollectNet", " #integralSignIn#Exception ", exception);
            return false;
        }
        return flag;
    }

    private static final String COLLECT_UPDATE_TAG = "DATA_COLLECTION";
    private static final String TAG = "DataCollectNet";
}
