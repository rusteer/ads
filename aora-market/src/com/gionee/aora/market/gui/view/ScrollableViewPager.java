// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollableViewPager extends ViewPager
{

    public ScrollableViewPager(Context context)
    {
        super(context);
        enabled = false;
    }

    public ScrollableViewPager(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        enabled = false;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        if(enabled)
            return super.onInterceptTouchEvent(motionevent);
        else
            return false;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(enabled)
            return super.onTouchEvent(motionevent);
        else
            return false;
    }

    public void setPagingEnabled(boolean flag)
    {
        enabled = flag;
    }

    private boolean enabled;
}
