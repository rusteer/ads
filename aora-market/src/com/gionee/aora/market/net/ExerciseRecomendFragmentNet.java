// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.text.TextUtils;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.module.PrefectureInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExerciseRecomendFragmentNet
{

    public ExerciseRecomendFragmentNet()
    {
    }

    private static List analysisExerciseRecomendList(String s)
        throws Exception
    {
        ArrayList arraylist = new ArrayList();
        String s1 = (new JSONObject(s)).getString("ARRAY");
        if(!TextUtils.isEmpty(s1) && !"null".equals(s1) && !TextUtils.isEmpty(s))
        {
            JSONArray jsonarray = new JSONArray(s1);
            int i = 0;
            while(i < jsonarray.length()) 
            {
                JSONArray jsonarray1 = jsonarray.getJSONArray(i);
                String s2 = jsonarray1.getString(0);
                int j = jsonarray1.getInt(1);
                String s3 = jsonarray1.getString(2);
                int k = jsonarray1.getInt(9);
                String s4 = jsonarray1.getString(10);
                String s5 = jsonarray1.getString(11);
                boolean flag;
                if(jsonarray1.getInt(12) == 1)
                    flag = true;
                else
                    flag = false;
                arraylist.add(new PrefectureInfo(s2, j, s3, k, s4, s5, flag));
                i++;
            }
        }
        DLog.i(TAG, (new StringBuilder()).append("\u89E3\u6790\u51FA\u6765\u7684\u6570\u636E\u957F\u5EA6--->").append(arraylist.size()).toString());
        return arraylist;
    }

    public static List getExerciseRecomendList(int i, int j)
    {
        List list;
        try
        {
            String s = getRequestData(i, j);
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("ACTIVITY_SIM", l, System.currentTimeMillis());
            list = analysisExerciseRecomendList(s1);
        }
        catch(Exception exception)
        {
            DLog.e(TAG, "getExerciseRecomendList()#Exception=", exception);
            return null;
        }
        return list;
    }

    private static String getRequestData(int i, int j)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", "ACTIVITY_SIM");
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

    public static final String ACTIVITY_SIM = "ACTIVITY_SIM";
    private static String TAG = "ExerciseRecomendFragmentNet";

}
