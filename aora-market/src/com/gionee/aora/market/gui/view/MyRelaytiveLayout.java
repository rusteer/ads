// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            MainInterceptListener

public class MyRelaytiveLayout extends RelativeLayout
{

    public MyRelaytiveLayout(Context context)
    {
        super(context);
        isCanListen = true;
    }

    public MyRelaytiveLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        isCanListen = true;
    }

    public MyRelaytiveLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        isCanListen = true;
    }

    public boolean isSubViewCanListen()
    {
        return isCanListen;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        if(!isCanListen)
        {
            if(l != null)
                l.onClick();
            return true;
        } else
        {
            return super.onInterceptTouchEvent(motionevent);
        }
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(!isCanListen)
            return true;
        else
            return super.onTouchEvent(motionevent);
    }

    public void setInterceptListener(MainInterceptListener maininterceptlistener)
    {
        l = maininterceptlistener;
    }

    public void setSubViewCanListen(boolean flag)
    {
        isCanListen = flag;
    }

    private boolean isCanListen;
    MainInterceptListener l;
}
