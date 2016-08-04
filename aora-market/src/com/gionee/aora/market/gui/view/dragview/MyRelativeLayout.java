// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

// Referenced classes of package com.gionee.aora.market.gui.view.dragview:
//            DragLayout

public class MyRelativeLayout extends RelativeLayout
{

    public MyRelativeLayout(Context context)
    {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public MyRelativeLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        dl.canScroll = true;
        if(dl.getStatus() != DragLayout.Status.Close && motionevent.getAction() == 1)
            dl.close();
        if(dl.getStatus() != DragLayout.Status.Close)
            return true;
        else
            return super.dispatchTouchEvent(motionevent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        return super.onInterceptTouchEvent(motionevent);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        return super.onTouchEvent(motionevent);
    }

    public void setDragLayout(DragLayout draglayout)
    {
        dl = draglayout;
    }

    private DragLayout dl;
}
