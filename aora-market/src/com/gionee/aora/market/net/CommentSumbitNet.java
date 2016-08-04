// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.net;

import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentSumbitNet
{

    public CommentSumbitNet()
    {
    }

    public static boolean getCommentSumbit(String s, String s1, String s2, String s3, String s4, String s5)
    {
        boolean flag;
        try
        {
            JSONObject jsonobject = new JSONObject();
            jsonobject.put("TAG", "COMMENT");
            jsonobject.put("ID", s);
            jsonobject.put("USER_ID", s1);
            jsonobject.put("STAR", s2);
            jsonobject.put("COMMENT_CONTENT", s3);
            jsonobject.put("MODEL_NO", s4);
            jsonobject.put("API_VERSION", 5);
            jsonobject.put("BAD_REASON", s5);
            long l = System.currentTimeMillis();
            String s6 = HttpRequest.getDefaultHttpRequest().startPost(jsonobject.toString());
            DataCollectManager.addNetRecord("COMMENT", l, System.currentTimeMillis());
            flag = parseCommentSumbit(s6);
        }
        catch(Exception exception)
        {
            DLog.i("CommentSumbitNet", (new StringBuilder()).append("FeedbackNet#getFeedback").append(exception).toString());
            return false;
        }
        return flag;
    }

    private static boolean parseCommentSumbit(String s)
        throws JSONException
    {
        return "COMMENT".equals((new JSONObject(s)).getString("TAG"));
    }

    private static final String COMMENT = "COMMENT";
    private static final String TAG = "CommentSumbitNet";
}
