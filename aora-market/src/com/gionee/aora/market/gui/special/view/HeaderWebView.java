// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class HeaderWebView extends WebView
{

    public HeaderWebView(Context context)
    {
        super(context);
        init();
    }

    public HeaderWebView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init();
    }

    public HeaderWebView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init();
    }

    public HeaderWebView(Context context, AttributeSet attributeset, int i, boolean flag)
    {
        super(context, attributeset, i, flag);
        init();
    }

    private void init()
    {
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollBarEnabled(false);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        return true;
    }
}
