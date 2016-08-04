// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import android.content.Context;
import android.os.Build;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.module.NotificationPushInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationPushNet
{

    public NotificationPushNet()
    {
    }

    private static JSONObject getIconRequestData(Context context)
        throws JSONException
    {
        JSONObject jsonobject = BaseNet.getBaseJSON("INDEX_PENDANT");
        jsonobject.put("IMEI", DataCollectUtil.getImei());
        jsonobject.put("USER_ID", UserStorage.getDefaultUserInfo(context).getUSER_NAME());
        jsonobject.put("MODEL", Build.MODEL);
        jsonobject.put("API_VERSION", "6");
        return jsonobject;
    }

    public static NotificationPushInfo getPushIconInfo(Context context)
    {
        NotificationPushInfo notificationpushinfo;
        try
        {
            JSONObject jsonobject = BaseNet.doRequest("INDEX_PENDANT", getIconRequestData(context));
            DLog.i("NotificationPushNet", (new StringBuilder()).append("getPushIconInfo(),respond=").append(jsonobject.toString()).toString());
            notificationpushinfo = new NotificationPushInfo(jsonobject);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        return notificationpushinfo;
    }

    public static NotificationPushInfo getPushNotifyInfo(Context context, String s)
    {
        NotificationPushInfo notificationpushinfo;
        try
        {
            JSONObject jsonobject = BaseNet.doRequest("GET_PUSH_NOTIFY", getRequestData(context, s));
            DLog.i("NotificationPushNet", (new StringBuilder()).append("getPushNotifyInfo(),respond=").append(jsonobject.toString()).toString());
            notificationpushinfo = new NotificationPushInfo(jsonobject);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        return notificationpushinfo;
    }

    private static JSONObject getRequestData(Context context, String s)
        throws JSONException
    {
        JSONObject jsonobject = BaseNet.getBaseJSON("GET_PUSH_NOTIFY");
        jsonobject.put("IMEI", DataCollectUtil.getImei());
        jsonobject.put("USER_ID", UserStorage.getDefaultUserInfo(context).getUSER_NAME());
        jsonobject.put("MODEL", Build.MODEL);
        jsonobject.put("VERSIONNAME", DataCollectUtil.getVersionName());
        jsonobject.put("API_VERSION", "7");
        jsonobject.put("IDS_SHOWED", s);
        return jsonobject;
    }

    private static final String TAG_GET_PUSH_ICON = "INDEX_PENDANT";
    private static final String TAG_GET_PUSH_NOTIFY = "GET_PUSH_NOTIFY";
}
