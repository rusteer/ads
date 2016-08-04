// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.view.View;

public abstract class TabFrameItem
{

    public TabFrameItem(Context context1)
    {
        context = context1;
        contentView = initLayout();
    }

    public View getContentView()
    {
        return contentView;
    }

    public abstract void initData();

    public abstract View initLayout();

    protected View contentView;
    Context context;
}
