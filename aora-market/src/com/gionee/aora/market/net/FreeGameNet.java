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

public class FreeGameNet
{

    public FreeGameNet()
    {
    }

    public static ArrayList getFreeGame(int i, int j)
    {
        ArrayList arraylist;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("FREE_GAME_LIST");
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            arraylist = AnalysisData.analysisList(BaseNet.doRequestHandleResultCode("FREE_GAME_LIST", jsonobject).toString());
        }
        catch(Exception exception)
        {
            DLog.i("FeedbackNet", (new StringBuilder()).append("FreeGameNet#getFreeGame").append(exception).toString());
            return null;
        }
        return arraylist;
    }

    private static final String FREE_GAME = "FREE_GAME_LIST";
    private static final String TAG = "FeedbackNet";
}
