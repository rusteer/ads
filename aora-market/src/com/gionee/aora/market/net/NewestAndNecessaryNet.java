// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import java.util.ArrayList;
import org.json.JSONObject;

// Referenced classes of package com.gionee.aora.market.net:
//            AnalysisData

public class NewestAndNecessaryNet
{

    public NewestAndNecessaryNet()
    {
    }

    public static ArrayList getNewestAndNecessary(int i)
    {
        ArrayList arraylist;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("LOCAL_INFO_MODULE");
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("TAG_NUM", i);
            arraylist = AnalysisData.analysisJSonList(BaseNet.doRequestHandleResultCode("LOCAL_INFO_MODULE", jsonobject).toString());
        }
        catch(Exception exception)
        {
            DLog.e("NewestAndNecessaryNet", "#getNewestAndNecessary()", exception);
            return null;
        }
        return arraylist;
    }

    public static final String NEWESTANDNECESSARY = "LOCAL_INFO_MODULE";
    public static final String TAG = "NewestAndNecessaryNet";
}
