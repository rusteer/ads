// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import java.util.ArrayList;
import org.json.JSONObject;

// Referenced classes of package com.gionee.aora.market.net:
//            AnalysisData

public class NewestNet
{

    public NewestNet()
    {
    }

    public static ArrayList getNewestList(int i, int j)
    {
        ArrayList arraylist;
        try
        {
            String s = getRequestData(i, j);
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            DataCollectManager.addNetRecord("GIONEE_NEW_HOME", l, System.currentTimeMillis());
            arraylist = AnalysisData.analysisList(s1);
        }
        catch(Exception exception)
        {
            DLog.e(TAG, "getNewestList()#Exception=", exception);
            return null;
        }
        return arraylist;
    }

    private static String getRequestData(int i, int j)
    {
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject.put("TAG", "GIONEE_NEW_HOME");
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

    public static final String GIONEE_NEW_HOME = "GIONEE_NEW_HOME";
    private static String TAG = "NewestNet";

}
