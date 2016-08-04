// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;

public class Comment
    implements Serializable
{

    public Comment()
    {
    }

    public Comment(String s, String s1, String s2, float f, String s3)
    {
        content = s;
        reviewer = s1;
        date = s2;
        rating = f;
        model_number = s3;
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

    public float getRating()
    {
        return rating;
    }

    public String getReviewer()
    {
        return reviewer;
    }

    public void release()
    {
        reviewer = null;
        content = null;
        date = null;
        model_number = null;
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

    public void setRating(float f)
    {
        rating = f;
    }

    public void setReviewer(String s)
    {
        reviewer = s;
    }

    private static final long serialVersionUID = 1L;
    private String content;
    private String date;
    private String id;
    private String model_number;
    private float rating;
    private String reviewer;
}
