// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.gionee.aora.market.net:
//            AnalysisData

public class OtherAppNet
{

    public OtherAppNet()
    {
    }

    public static List getOtherAppList(String s, String s1, int i, int j)
    {
        java.util.ArrayList arraylist;
        try
        {
            String s2 = getRequestData("SEARCH_EXACT_MAYBE2", s, s1, i, j);
            long l = System.currentTimeMillis();
            String s3 = HttpRequest.getDefaultHttpRequest().startPost(s2);
            DataCollectManager.addNetRecord("SEARCH_EXACT_MAYBE2", l, System.currentTimeMillis());
            arraylist = AnalysisData.analysisList(s3);
        }
        catch(Exception exception)
        {
            DLog.e("OtherAppNet", exception);
            return null;
        }
        return arraylist;
    }

    private static String getRequestData(String s, String s1, String s2, int i, int j)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", s);
            jsonobject.put("PACKAGE_NAME", s1);
            jsonobject.put("QUERY", s2);
            jsonobject.put(" CONDITION", "3");
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("API_VERSION", 3);
        }
        catch(JSONException jsonexception)
        {
            DLog.e("OtherAppNet", jsonexception);
        }
        return jsonobject.toString();
    }

    public static final String SEARCH_EXACT_MAYBE = "SEARCH_EXACT_MAYBE2";
}
