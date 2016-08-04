// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.net;
import android.text.TextUtils;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.module.Comment;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

public class CommentNet {
    public CommentNet() {}
    private static List<Comment> analysisList(String s) {
        List<Comment> list = null;
        int i = 0;
        if (s != null) {
            try {
                list = new ArrayList<Comment>();
                String s1 = (new JSONObject(s)).getString("ARRAY");
                if (!TextUtils.isEmpty(s1)) {
                    JSONArray jsonarray = new JSONArray(s1);
                    while (i < jsonarray.length()) {
                        try {
                            JSONArray jsonarray1;
                            Comment comment;
                            String s2;
                            boolean flag;
                            jsonarray1 = jsonarray.getJSONArray(i);
                            comment = new Comment();
                            comment.setId(jsonarray1.getString(0));
                            s2 = jsonarray1.getString(1);
                            flag = TextUtils.isEmpty(s2);
                            if (!flag) {
                                float f1 = Float.valueOf(s2).floatValue();
                                float f = f1 / 2.0F;
                                comment.setRating(f);
                            }
                            comment.setReviewer(jsonarray1.getString(2));
                            comment.setDate(jsonarray1.getString(3));
                            String s3 = jsonarray1.getString(4);
                            if (!TextUtils.isEmpty(s3)) s3 = s3.replaceAll("\\r", "").replaceAll("\\n", "");
                            comment.setContent(s3);
                            comment.setModel_number(jsonarray1.getString(5));
                            list.add(comment);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        i++;
                    }
                }
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
    public static List getCommentList(String s, int i, int j) {
        List list;
        try {
            long l = System.currentTimeMillis();
            String s1 = getRequestData("COMMENTS_LIST", s, i, j);
            String s2 = HttpRequest.getDefaultHttpRequest().startPost(s1);
            DataCollectManager.addNetRecord("COMMENTS_LIST", l, System.currentTimeMillis());
            list = analysisList(s2);
        } catch (Exception exception) {
            DLog.e("CommentNet", exception);
            return null;
        }
        return list;
    }
    private static String getRequestData(String s, String s1, int i, int j) {
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("TAG", s);
            jsonobject.put("INDEX_START", i);
            jsonobject.put("INDEX_SIZE", j);
            jsonobject.put("API_VERSION", 5);
            jsonobject.put("ID", s1);
        } catch (JSONException jsonexception) {
            DLog.e("CommentNet", jsonexception);
        }
        return jsonobject.toString();
    }
    public static final String COMMENTS_LIST = "COMMENTS_LIST";
}
