// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LenjoyInfo
    implements Serializable
{

    public LenjoyInfo()
    {
        shareAppInfos = new ArrayList();
        praiseUserInfos = new ArrayList();
    }

    public int getAppRotateAngle()
    {
        return appRotateAngle;
    }

    public String getAppScreenShortUrl()
    {
        return appScreenShortUrl;
    }

    public int getBadCount()
    {
        return badCount;
    }

    public int getCommentCount()
    {
        return commentCount;
    }

    public String getDate()
    {
        return date;
    }

    public int getId()
    {
        return id;
    }

    public String getListTitle()
    {
        return listTitle;
    }

    public String getPraiseCount()
    {
        return praiseCount;
    }

    public int getPraiseCountInt()
    {
        return praiseCountInt;
    }

    public List getPraiseUserInfos()
    {
        return praiseUserInfos;
    }

    public List getShareAppInfos()
    {
        return shareAppInfos;
    }

    public String getStartId()
    {
        return startId;
    }

    public int getState()
    {
        return state;
    }

    public String getStateExplain()
    {
        return stateExplain;
    }

    public String getTheme()
    {
        return theme;
    }

    public int getType()
    {
        return type;
    }

    public String getUserAccountID()
    {
        return accountID;
    }

    public String getUserIconUrl()
    {
        return userIconUrl;
    }

    public String getUserImei()
    {
        return userImei;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getUserSurname()
    {
        return userSurname;
    }

    public boolean isBadPraise()
    {
        return isBadPraise;
    }

    public boolean isBoutique()
    {
        return isBoutique;
    }

    public boolean isPraise()
    {
        return isPraise;
    }

    public void setAppRotateAngle(int i)
    {
        appRotateAngle = i;
    }

    public void setAppScreenShortUrl(String s)
    {
        appScreenShortUrl = s;
    }

    public void setBadCount(int i)
    {
        badCount = i;
    }

    public void setBadPraise(boolean flag)
    {
        isBadPraise = flag;
    }

    public void setBoutique(boolean flag)
    {
        isBoutique = flag;
    }

    public void setCommentCount(int i)
    {
        commentCount = i;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setId(int i)
    {
        id = i;
    }

    public void setListTitle(String s)
    {
        listTitle = s;
    }

    public void setPraise(boolean flag)
    {
        isPraise = flag;
    }

    public void setPraiseCount(String s)
    {
        praiseCount = s;
    }

    public void setPraiseCountInt(int i)
    {
        praiseCountInt = i;
    }

    public void setPraiseUserInfos(List list)
    {
        praiseUserInfos = list;
    }

    public void setShareAppInfos(List list)
    {
        shareAppInfos = list;
    }

    public void setStartId(String s)
    {
        startId = s;
    }

    public void setState(int i)
    {
        state = i;
    }

    public void setStateExplain(String s)
    {
        stateExplain = s;
    }

    public void setTheme(String s)
    {
        theme = s;
    }

    public void setType(int i)
    {
        type = i;
    }

    public void setUserAccountID(String s)
    {
        accountID = s;
    }

    public void setUserIconUrl(String s)
    {
        userIconUrl = s;
    }

    public void setUserImei(String s)
    {
        userImei = s;
    }

    public void setUserName(String s)
    {
        userName = s;
    }

    public void setUserSurname(String s)
    {
        userSurname = s;
    }

    private static final long serialVersionUID = 1L;
    private String accountID;
    private int appRotateAngle;
    private String appScreenShortUrl;
    private int badCount;
    private int commentCount;
    private String date;
    private int id;
    private boolean isBadPraise;
    private boolean isBoutique;
    private boolean isPraise;
    private String listTitle;
    private String praiseCount;
    private int praiseCountInt;
    private List praiseUserInfos;
    private List shareAppInfos;
    private String startId;
    private int state;
    private String stateExplain;
    private String theme;
    private int type;
    private String userIconUrl;
    private String userImei;
    private String userName;
    private String userSurname;
}
