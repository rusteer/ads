// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import android.database.Cursor;
import com.aora.base.util.DLog;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageInfo
{

    public MessageInfo(Cursor cursor)
    {
        super();
        boolean flag = true;
        msgId = "";
        type = ((flag) ? 1 : 0);
        opUser = "";
        userIcon = "";
        title = "";
        comment = "";
        appIcon = "";
        url = "";
        date = "";
        lejoyId = "";
        num = 0;
        isRead = false;
        commenetId = "";
        msgId = cursor.getString(cursor.getColumnIndex("message_id"));
        type = cursor.getInt(cursor.getColumnIndex("message_type"));
        opUser = cursor.getString(cursor.getColumnIndex("operater"));
        userIcon = cursor.getString(cursor.getColumnIndex("operater_icon"));
        title = cursor.getString(cursor.getColumnIndex("title"));
        comment = cursor.getString(cursor.getColumnIndex("comment"));
        commenetId = cursor.getString(cursor.getColumnIndex("comment_id"));
        appIcon = cursor.getString(cursor.getColumnIndex("lenjoy_app_icon"));
        url = cursor.getString(cursor.getColumnIndex("url"));
        date = cursor.getString(cursor.getColumnIndex("date"));
        lejoyId = cursor.getString(cursor.getColumnIndex("lejoy_id"));
        num = cursor.getInt(cursor.getColumnIndex("operate_count"));
        if(cursor.getInt(cursor.getColumnIndex("is_read")) != 1)
            flag = false;
        isRead = flag;
    }

    public MessageInfo(JSONObject jsonobject)
    {
        msgId = "";
        type = 1;
        opUser = "";
        userIcon = "";
        title = "";
        comment = "";
        appIcon = "";
        url = "";
        date = "";
        lejoyId = "";
        num = 0;
        isRead = false;
        commenetId = "";
        try
        {
            msgId = jsonobject.getString("ID");
            type = jsonobject.getInt("TYPE");
            opUser = jsonobject.getString("SURNAME");
            userIcon = jsonobject.getString("ICON_URL");
            title = jsonobject.getString("TITLE");
            comment = jsonobject.getString("COMMENT");
            appIcon = jsonobject.getString("APP_ICON");
            url = jsonobject.getString("SKIP_URL");
            date = jsonobject.getString("ADD_TIME");
            lejoyId = jsonobject.getString("DATA_ID");
            num = jsonobject.getInt("NUM");
            commenetId = jsonobject.getString("COMMENT_ID");
            isRead = false;
            return;
        }
        catch(JSONException jsonexception)
        {
            DLog.e("MessageInfo", jsonexception);
        }
    }

    public String getAppIcon()
    {
        return appIcon;
    }

    public String getCommenetId()
    {
        return commenetId;
    }

    public String getComment()
    {
        return comment;
    }

    public String getDate()
    {
        return date;
    }

    public String getLejoyId()
    {
        return lejoyId;
    }

    public String getMsgId()
    {
        return msgId;
    }

    public int getNum()
    {
        return num;
    }

    public String getOpUser()
    {
        return opUser;
    }

    public String getTitle()
    {
        return title;
    }

    public int getType()
    {
        return type;
    }

    public String getUrl()
    {
        return url;
    }

    public String getUserIcon()
    {
        return userIcon;
    }

    public boolean isRead()
    {
        return isRead;
    }

    public void setAppIcon(String s)
    {
        appIcon = s;
    }

    public void setCommenetId(String s)
    {
        commenetId = s;
    }

    public void setComment(String s)
    {
        comment = s;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setLejoyId(String s)
    {
        lejoyId = s;
    }

    public void setMsgId(String s)
    {
        msgId = s;
    }

    public void setNum(int i)
    {
        num = i;
    }

    public void setOpUser(String s)
    {
        opUser = s;
    }

    public void setRead(boolean flag)
    {
        isRead = flag;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    public void setType(int i)
    {
        type = i;
    }

    public void setUrl(String s)
    {
        url = s;
    }

    public void setUserIcon(String s)
    {
        userIcon = s;
    }

    private String appIcon;
    private String commenetId;
    private String comment;
    private String date;
    private boolean isRead;
    private String lejoyId;
    private String msgId;
    private int num;
    private String opUser;
    private String title;
    private int type;
    private String url;
    private String userIcon;
}
