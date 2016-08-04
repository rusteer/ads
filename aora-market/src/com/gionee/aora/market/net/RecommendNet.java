// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.gionee.aora.market.net:
//            AnalysisMixtrueData

public class RecommendNet
{

    public RecommendNet()
    {
    }

    public static ArrayList getRecommendList(int i, int j)
    {
        ArrayList arraylist;
        try
        {
            String s = getRequestData("INDEX_RECOMMEND", i, j);
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("INDEX_RECOMMEND", l, System.currentTimeMillis());
            arraylist = AnalysisMixtrueData.analysisJSonList(new JSONObject(s1));
        }
        catch(Exception exception)
        {
            DLog.e("RecommendNet", exception);
            return null;
        }
        return arraylist;
    }

    private static String getRequestData(String s, int i, int j)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", s);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("TYPE", 10);
            jsonobject.put("API_VERSION", 7);
        }
        catch(JSONException jsonexception)
        {
            DLog.e("RecommendNet", jsonexception);
        }
        return jsonobject.toString();
    }

    public static final String GIONEE_FEATURES_HOME = "INDEX_RECOMMEND";
}
