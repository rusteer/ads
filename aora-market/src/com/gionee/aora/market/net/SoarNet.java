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
//            AnalysisData

public class SoarNet
{

    public SoarNet()
    {
    }

    private static String getRequestData(String s, int i, int j, int k)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", s);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("PCATID", k);
            jsonobject.put("API_VERSION", 3);
        }
        catch(JSONException jsonexception)
        {
            DLog.e("SoarNet", " #getRequestData# ", jsonexception);
        }
        return jsonobject.toString();
    }

    public static ArrayList getSoarList(int i, int j, int k)
    {
        ArrayList arraylist;
        try
        {
            String s = getRequestData("GET_SOAR_LIST", i, j, k);
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("GET_SOAR_LIST", l, System.currentTimeMillis());
            DLog.i("SoarNet", (new StringBuilder()).append(" getSoarList#response ").append(s1).toString());
            arraylist = AnalysisData.analysisJSonList(s1);
        }
        catch(Exception exception)
        {
            DLog.e("SoarNet", " #getSoarList# ", exception);
            return null;
        }
        return arraylist;
    }

    public static final String GET_SOAR_LIST = "GET_SOAR_LIST";
    public static final String TAG = "SoarNet";
}
