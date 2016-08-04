// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.login.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

// Referenced classes of package com.gionee.aora.market.gui.login.view:
//            NewbieScrollChanged

public class NewbieScrllView extends ScrollView
{

    public NewbieScrllView(Context context)
    {
        super(context);
        range = 0;
        isTouchScroll = true;
    }

    public NewbieScrllView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        range = 0;
        isTouchScroll = true;
    }

    public NewbieScrllView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        range = 0;
        isTouchScroll = true;
    }

    protected void onScrollChanged(int i, int j, int k, int l)
    {
        super.onScrollChanged(i, j, k, l);
        if(range == 0)
            range = computeVerticalScrollRange();
        if(newbieScrollChanged != null)
            newbieScrollChanged.onScrollChanged(this, i, j, k, l, range);
    }

    public void onScrollChangedListener(NewbieScrollChanged newbiescrollchanged)
    {
        newbieScrollChanged = newbiescrollchanged;
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(!isTouchScroll)
        {
            scrollTo(range, 0);
            return false;
        } else
        {
            return super.onTouchEvent(motionevent);
        }
    }

    public void scrollToLast()
    {
        smoothScrollTo(range, 0);
        postInvalidate();
    }

    public void setTouchScroll(boolean flag)
    {
        isTouchScroll = flag;
    }

    private boolean isTouchScroll;
    private NewbieScrollChanged newbieScrollChanged;
    private int range;
}
