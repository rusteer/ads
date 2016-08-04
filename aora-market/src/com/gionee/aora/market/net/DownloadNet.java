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

public class DownloadNet
{

    public DownloadNet()
    {
    }

    public static ArrayList getDowloadList(int i, int j, int k)
    {
        ArrayList arraylist;
        try
        {
            String s = getRequestData(DOWNLOAD, i, j, k);
            long l = System.currentTimeMillis();
            String s1 = HttpRequest.getDefaultHttpRequest().startPost(s);
            long l1 = System.currentTimeMillis();
            DataCollectManager.addNetRecord(DOWNLOAD, l, l1);
            DLog.i("DownloadNet", (new StringBuilder()).append(" getDowloadList#response ").append(s1).toString());
            arraylist = AnalysisData.analysisJSonList(s1);
        }
        catch(Exception exception)
        {
            DLog.e("DownloadNet", " #getDowloadList# ", exception);
            return null;
        }
        return arraylist;
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
            DLog.e("DownloadNet", " #getRequestData# ", jsonexception);
        }
        return jsonobject.toString();
    }

    public static String DOWNLOAD = "SOFT_HOT";
    public static final String TAG = "DownloadNet";

}
