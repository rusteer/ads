// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import java.util.ArrayList;

// Referenced classes of package com.gionee.aora.market.module:
//            LenjoyInfo

public class LenjoyDetailInfo extends LenjoyInfo
{

    public LenjoyDetailInfo()
    {
        shortCutImgs = new ArrayList();
    }

    public ArrayList getShortCutImgs()
    {
        return shortCutImgs;
    }

    public void setShortCutImgs(ArrayList arraylist)
    {
        shortCutImgs = arraylist;
    }

    private ArrayList shortCutImgs;
}
