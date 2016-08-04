// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import com.aora.base.util.StringUtil;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.gionee.aora.market.module:
//            EvaluatInfo

public class NotificationPushInfo
    implements Serializable
{

    public NotificationPushInfo()
    {
        id = "";
        type = 0;
        icon = "";
        date = "";
        title = "";
        text = "";
        url = "";
        softId = "";
        topicId = "";
        evaluatInfo = null;
    }

    public NotificationPushInfo(JSONObject jsonobject)
        throws JSONException
    {
        id = "";
        type = 0;
        icon = "";
        date = "";
        title = "";
        text = "";
        url = "";
        softId = "";
        topicId = "";
        evaluatInfo = null;
        setId(jsonobject.getString("ID"));
        setType(jsonobject.getInt("TYPE"));
        setIcon(jsonobject.getString("ICON"));
        setDate(jsonobject.getString("DATE"));
        if(jsonobject.has("TITLE"))
            setTitle(jsonobject.getString("TITLE"));
        if(jsonobject.has("TEXT"))
            setText(jsonobject.getString("TEXT"));
        String s = jsonobject.getString("JUMP");
        switch(getType())
        {
        case 3: // '\003'
        case 4: // '\004'
        default:
            return;

        case 1: // '\001'
            if(s.contains("http"))
            {
                setUrl(s);
                return;
            } else
            {
                setTopicId(s);
                return;
            }

        case 2: // '\002'
            JSONObject jsonobject1 = jsonobject.getJSONObject("ACT_INFO");
            EvaluatInfo evaluatinfo = new EvaluatInfo();
            evaluatinfo.setAppDownloadCount(jsonobject1.getString("DOWNLOAD_COUNT"));
            evaluatinfo.setAppVersionName(jsonobject1.getString("VERSION_NAME"));
            evaluatinfo.setAppDownloadCount(StringUtil.getDownloadNumber(jsonobject1.getString("HITCOUNT")));
            evaluatinfo.setAppDownloadUrl(jsonobject1.getString("APK_URL"));
            evaluatinfo.setAppSize(StringUtil.getFormatSize(jsonobject1.getLong("SIZE")));
            evaluatinfo.setId(jsonobject1.getInt("ID"));
            evaluatinfo.setAppPackageName(jsonobject1.getString("PACKAGE_NAME"));
            evaluatinfo.setAppId(jsonobject1.getInt("SOFT_ID"));
            evaluatinfo.setAppIconUrl(jsonobject1.getString("ICON_URL"));
            evaluatinfo.setAppName(jsonobject1.getString("SOFT_NAME"));
            evaluatinfo.setSkipUrl(s);
            if(jsonobject.has("TITLE"))
                evaluatinfo.setName(jsonobject.getString("TITLE"));
            setEvaluatInfo(evaluatinfo);
            return;

        case 5: // '\005'
        case 6: // '\006'
            if(s.contains("http"))
            {
                setUrl(s);
                return;
            } else
            {
                setSoftId(s);
                return;
            }

        case 7: // '\007'
            setUrl(s);
            return;

        case 8: // '\b'
            setUrl(s);
            return;

        case 9: // '\t'
            setUrl(s);
            return;
        }
    }

    public String getDate()
    {
        return date;
    }

    public EvaluatInfo getEvaluatInfo()
    {
        return evaluatInfo;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getId()
    {
        return id;
    }

    public String getSoftId()
    {
        return softId;
    }

    public String getText()
    {
        return text;
    }

    public String getTitle()
    {
        return title;
    }

    public String getTopicId()
    {
        return topicId;
    }

    public int getType()
    {
        return type;
    }

    public String getUrl()
    {
        return url;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setEvaluatInfo(EvaluatInfo evaluatinfo)
    {
        evaluatInfo = evaluatinfo;
    }

    public void setIcon(String s)
    {
        icon = s;
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setSoftId(String s)
    {
        softId = s;
    }

    public void setText(String s)
    {
        text = s;
    }

    public void setTitle(String s)
    {
        title = s;
    }

    public void setTopicId(String s)
    {
        topicId = s;
    }

    public void setType(int i)
    {
        type = i;
    }

    public void setUrl(String s)
    {
        url = s;
    }

    private static final long serialVersionUID = 0xa1298103446a11eL;
    private String date;
    private EvaluatInfo evaluatInfo;
    private String icon;
    private String id;
    private String softId;
    private String text;
    private String title;
    private String topicId;
    private int type;
    private String url;
}
