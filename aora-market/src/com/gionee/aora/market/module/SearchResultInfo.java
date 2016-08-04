// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.util.ArrayList;

public class SearchResultInfo
{

    public SearchResultInfo()
    {
        isDataEnd = false;
        resultApps = new ArrayList();
        otherApps = new ArrayList();
        contextTags = new ArrayList();
    }

    public ArrayList getContextTags()
    {
        return contextTags;
    }

    public ArrayList getOtherApps()
    {
        return otherApps;
    }

    public ArrayList getResultApps()
    {
        return resultApps;
    }

    public boolean isDataEnd()
    {
        return isDataEnd;
    }

    public void setContextTags(ArrayList arraylist)
    {
        contextTags = arraylist;
    }

    public void setDataEnd(boolean flag)
    {
        isDataEnd = flag;
    }

    public void setOtherApps(ArrayList arraylist)
    {
        otherApps = arraylist;
    }

    public void setResultApps(ArrayList arraylist)
    {
        resultApps = arraylist;
    }

    private ArrayList contextTags;
    private boolean isDataEnd;
    private ArrayList otherApps;
    private ArrayList resultApps;
}
