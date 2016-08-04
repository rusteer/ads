// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.net;
import android.content.Context;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.market.module.ExperienceInfo;
import java.util.ArrayList;
import org.json.*;

public class ExperienceNet {
    public ExperienceNet() {}
    private static ArrayList analysisListExperienceData(JSONObject jsonobject, Context context) {
        ArrayList<ExperienceInfo> arraylist = new ArrayList<ExperienceInfo>();
        try { String s = jsonobject.getString("ARRAY");
        if (s != null && s.length() > 0) {
           
                JSONArray jsonarray = new JSONArray(s);
                int i = 0;
                while (i < jsonarray.length()) {
                    try {
                        ExperienceInfo experienceinfo = new ExperienceInfo();
                        JSONObject jsonobject1 = jsonarray.getJSONObject(i);
                        experienceinfo.setExId(jsonobject1.getInt("EXPERIENCE_ID"));
                        experienceinfo.setExImage(jsonobject1.getString("IMAGE"));
                        experienceinfo.setExTitle(jsonobject1.getString("TITLE"));
                        experienceinfo.setExIntro(jsonobject1.getString("INTRO"));
                        experienceinfo.setImageHeight(getHeight(context, jsonobject1.getInt("WIDTH"), jsonobject1.getInt("HEIGHT")));
                        experienceinfo.setExAppId(jsonobject1.getString("ID"));
                        experienceinfo.setExName(jsonobject1.getString("NAME"));
                        experienceinfo.setExIcon(jsonobject1.getString("ICON_URL"));
                        experienceinfo.setExPackageName(jsonobject1.getString("PACKAGE_NAME"));
                        experienceinfo.setExSize(jsonobject1.getLong("SIZE"));
                        experienceinfo.setExDownloadUrl(jsonobject1.getString("APK_URL"));
                        experienceinfo.setExVersionName(jsonobject1.getString("VERSION_NAME"));
                        experienceinfo.setExDownloadCount(StringUtil.getDownloadNumber(jsonobject1.getString("DOWNLOAD_COUNT")));
                        experienceinfo.setExiIntegral(jsonobject1.getInt("INTEGRAL"));
                        experienceinfo.setExSkipUrl(jsonobject1.getString("SKIP_URL"));
                        experienceinfo.setPackageName(experienceinfo.getExPackageName());
                        arraylist.add(experienceinfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
           
        } } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return arraylist;
    }
    public static ArrayList getExperience(Context context, int i, int j) {
        ArrayList arraylist;
        try {
            JSONObject jsonobject = BaseNet.getBaseJSON("GET_EXPERIENCE_LIST");
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("MODEL", Build.MODEL);
            arraylist = analysisListExperienceData(BaseNet.doRequestHandleResultCode("GET_EXPERIENCE_LIST", jsonobject), context);
        } catch (Exception exception) {
            DLog.e("ExperienceNet", " #getExperience# ", exception);
            return null;
        }
        return arraylist;
    }
    private static int getHeight(Context context, int i, int j) {
        return (j * ((-60 + ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth()) / 2)) / i;
    }
    public static final String EXPERIENCE = "GET_EXPERIENCE_LIST";
    public static final String TAG = "ExperienceNet";
}
