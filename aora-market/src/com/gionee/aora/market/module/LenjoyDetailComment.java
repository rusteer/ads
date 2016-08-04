// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;


public class LenjoyDetailComment
{

    public LenjoyDetailComment()
    {
        isBlankItem = true;
        isBlankItem = true;
    }

    public LenjoyDetailComment(String s, String s1, String s2, String s3, String s4, String s5, String s6, 
            String s7)
    {
        isBlankItem = true;
        isBlankItem = false;
        id = s;
        content = s1;
        reviewer = s2;
        reviewerID = s3;
        reply_name = s4;
        date = s5;
        userIcon = s6;
        model_number = s7;
    }

    public String getContent()
    {
        return content;
    }

    public String getDate()
    {
        return date;
    }

    public String getId()
    {
        return id;
    }

    public String getModel_number()
    {
        return model_number;
    }

    public String getReply_name()
    {
        return reply_name;
    }

    public String getReviewer()
    {
        return reviewer;
    }

    public String getReviewerID()
    {
        return reviewerID;
    }

    public String getUserIcon()
    {
        return userIcon;
    }

    public boolean isBlankItem()
    {
        return isBlankItem;
    }

    public void release()
    {
        id = null;
        reviewer = null;
        reply_name = null;
        content = null;
        date = null;
        model_number = null;
    }

    public void setBlankItem(boolean flag)
    {
        isBlankItem = flag;
    }

    public void setContent(String s)
    {
        content = s;
    }

    public void setDate(String s)
    {
        date = s;
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setModel_number(String s)
    {
        model_number = s;
    }

    public void setReply_name(String s)
    {
        reply_name = s;
    }

    public void setReviewer(String s)
    {
        reviewer = s;
    }

    public void setReviewerID(String s)
    {
        reviewerID = s;
    }

    public void setUserIcon(String s)
    {
        userIcon = s;
    }

    private String content;
    private String date;
    private String id;
    private boolean isBlankItem;
    private String model_number;
    private String reply_name;
    private String reviewer;
    private String reviewerID;
    private String userIcon;
}
