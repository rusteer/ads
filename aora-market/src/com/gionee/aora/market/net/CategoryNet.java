// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.BaseNet;
import com.aora.base.util.DLog;
import com.gionee.aora.market.module.CategoryInfo;
import java.util.ArrayList;
import org.json.*;

public class CategoryNet
{

    public CategoryNet()
    {
    }

    private static ArrayList analysisClassifyList(JSONObject jsonobject)
        throws JSONException
    {
        ArrayList arraylist = new ArrayList();
        JSONArray jsonarray = jsonobject.getJSONArray("LABLE_DATA");
        CategoryInfo acategoryinfo[] = new CategoryInfo[jsonarray.length()];
        for(int i = 0; i < jsonarray.length(); i++)
        {
            JSONObject jsonobject3 = jsonarray.getJSONObject(i);
            CategoryInfo categoryinfo2 = new CategoryInfo();
            categoryinfo2.setSortId(jsonobject3.getString("LABEL_ID"));
            categoryinfo2.setSortName(jsonobject3.getString("LABEL_NAME"));
            acategoryinfo[i] = categoryinfo2;
        }

        arraylist.add(acategoryinfo);
        JSONArray jsonarray1 = jsonobject.getJSONArray("CLASSIFY_DATA");
        for(int j = 0; j < jsonarray1.length(); j++)
        {
            JSONObject jsonobject1 = jsonarray1.getJSONObject(j);
            JSONArray jsonarray2 = jsonobject1.getJSONArray("LITTLE_CLASS");
            CategoryInfo acategoryinfo1[] = new CategoryInfo[1 + jsonarray2.length()];
            CategoryInfo categoryinfo = new CategoryInfo();
            categoryinfo.setSortIcon(jsonobject1.getString("ICON"));
            categoryinfo.setSortId(jsonobject1.getString("LARGE_CLASS_ID"));
            categoryinfo.setSortName(jsonobject1.getString("LARGE_CLASS_NAME"));
            acategoryinfo1[0] = categoryinfo;
            for(int k = 0; k < jsonarray2.length(); k++)
            {
                JSONObject jsonobject2 = jsonarray2.getJSONObject(k);
                CategoryInfo categoryinfo1 = new CategoryInfo();
                categoryinfo1.setSortId(jsonobject2.getString("CLASSIFY_ID"));
                categoryinfo1.setSortName(jsonobject2.getString("CLASSIFY_NAME"));
                acategoryinfo1[k + 1] = categoryinfo1;
            }

            arraylist.add(acategoryinfo1);
        }

        return arraylist;
    }

    public static ArrayList getClassify(int i)
    {
        ArrayList arraylist;
        try
        {
            JSONObject jsonobject = BaseNet.getBaseJSON("SUB_CATEGORY2");
            jsonobject.put("ID", i);
            jsonobject.put("API_VERSION", 3);
            jsonobject.put("VERSION_CODE", 6100);
            arraylist = analysisClassifyList(BaseNet.doRequest("SUB_CATEGORY2", jsonobject));
        }
        catch(Exception exception)
        {
            DLog.e("SortNet", " #getClassify# ", exception);
            return null;
        }
        return arraylist;
    }

    public static final String SUB_CATEGORY2 = "SUB_CATEGORY2";
    public static final String TAG = "SortNet";
}
