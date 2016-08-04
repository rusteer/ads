// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.io.Serializable;
import java.util.ArrayList;

// Referenced classes of package com.gionee.aora.market.module:
//            PopuWindowsPushInfo

public class PopuWindowsPushInfoList
    implements Serializable
{

    public PopuWindowsPushInfoList()
    {
        currShowIndex = 0;
        resultCode = 0;
        currShowIndex = 0;
        pushInfos = new ArrayList();
        resultCode = 1;
    }

    public PopuWindowsPushInfoList(int i, ArrayList arraylist, int j)
    {
        currShowIndex = 0;
        resultCode = 0;
        currShowIndex = j;
        pushInfos = arraylist;
        resultCode = i;
    }

    public int getCurrShowIndex()
    {
        return currShowIndex;
    }

    public ArrayList getPushInfos()
    {
        return pushInfos;
    }

    public int getResultCode()
    {
        return resultCode;
    }

    public PopuWindowsPushInfo nextInfo()
    {
        if(pushInfos == null || pushInfos.isEmpty())
            return null;
        int i = getCurrShowIndex();
        int j;
        PopuWindowsPushInfo popuwindowspushinfo;
        int k;
        int l;
        int i1;
        if(i < 0)
            j = 0;
        else
        if(i >= pushInfos.size())
            j = -1 + pushInfos.size();
        else
            j = i;
        popuwindowspushinfo = (PopuWindowsPushInfo)pushInfos.get(j);
        k = j + 1;
        l = pushInfos.size();
        i1 = 0;
        if(k < l)
            i1 = j + 1;
        setCurrShowIndex(i1);
        return popuwindowspushinfo;
    }

    public void setCurrShowIndex(int i)
    {
        currShowIndex = i;
    }

    public void setPushInfos(ArrayList arraylist)
    {
        pushInfos = arraylist;
    }

    public void setResultCode(int i)
    {
        resultCode = i;
    }

    private static final long serialVersionUID = 0x92520f6da2d90a66L;
    private int currShowIndex;
    private ArrayList pushInfos;
    private int resultCode;
}
