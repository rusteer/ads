// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.net;
import android.os.Build;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.integral.net.IntegralBaseNet;
import com.gionee.aora.integral.util.DES;
import com.gionee.aora.market.module.CallRecordsInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class CallRecordsNet {
    public CallRecordsNet() {}
    public static List getCallRecords(int i, int j, UserInfo userinfo) {
        List list;
        try {
            JSONObject jsonobject = BaseNet.getBaseJSON("FREE_CALL");
            jsonobject.put("METHOD", "ContactData");
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            JSONObject jsonobject1 = new JSONObject();
            jsonobject1.put("MODEL", Build.MODEL);
            jsonobject1.put("ID", userinfo.getID());
            jsonobject1.put("USER_ID", userinfo.getUSER_NAME());
            jsonobject1.put("HAND_KEY", userinfo.getHAND_KEY());
            jsonobject1.put("FLAG", userinfo.getUSER_TYPE_FLAG());
            jsonobject1.put("IMEI", DataCollectUtil.getImei());
            jsonobject.put("ENCRY_DATA", DES.desEncrypt(jsonobject1.toString()));
            jsonobject.put("API_VERSION", 6);
            list = parseCallRecords(IntegralBaseNet.doRequestHandleResultCode("FREE_CALL", jsonobject));
        } catch (Exception exception) {
            DLog.e("CallRecordsNet", "#getCallRecords()", exception);
            return null;
        }
        return list;
    }
    private static List<CallRecordsInfo> parseCallRecords(JSONObject jsonobject) {
        List<CallRecordsInfo> arraylist = new ArrayList<CallRecordsInfo>();
        try {
            String s = jsonobject.getString("ARRAY");
            if (!(s==null ||s.length()==0 || s.equals("null"))) {
                JSONArray jsonarray = new JSONArray(s);
                int i = 0;
                while (i < jsonarray.length()) {
                    JSONObject jsonobject1 = jsonarray.getJSONObject(i);
                    CallRecordsInfo callrecordsinfo = new CallRecordsInfo();
                    String s1 = jsonobject1.getString("PHONE");
                    String s2 = jsonobject1.getString("CALLEE");
                    String s3 = jsonobject1.getString("TIME");
                    String s4 = jsonobject1.getString("HOLDSTR");
                    callrecordsinfo.setPhone(s1);
                    callrecordsinfo.setCallee(s2);
                    callrecordsinfo.setSale(s4);
                    callrecordsinfo.setTime(s3);
                    arraylist.add(callrecordsinfo);
                    i++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arraylist;
    }
    public static final String CALL = "FREE_CALL";
    public static final String TAG = "CallRecordsNet";
}
