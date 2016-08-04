// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.os.Build;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import java.util.List;
import org.json.JSONObject;

// Referenced classes of package com.gionee.aora.market.net:
//            BoutiqueGameNet

public class RecommendAdNet
{

    public RecommendAdNet()
    {
    }

    public static List getRecommendAdList()
    {
        List list;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("GET_RECOMMEND_LIST_4");
            jsonobject.put("MODEL", Build.MODEL);
            jsonobject.put("API_VERSION", 8);
            list = BoutiqueGameNet.parseBannersResultJSON(BaseNet.doRequestHandleResultCode("GET_RECOMMEND_LIST_4", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("RecommendAdNet", exception);
            return null;
        }
        return list;
    }

    public static final String GET_RECOMMEND_LIST_4 = "GET_RECOMMEND_LIST_4";
    public static final String TAG = "RecommendAdNet";
}
