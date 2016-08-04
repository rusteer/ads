// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.gionee.aora.market.gui.view.MyViewPager;

// Referenced classes of package com.gionee.aora.market.gui.view.dragview:
//            DragLayout

public class MyDragLayout extends DragLayout
{

    public MyDragLayout(Context context)
    {
        super(context);
        viewPager = null;
    }

    public MyDragLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        viewPager = null;
    }

    public MyDragLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        viewPager = null;
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        if(viewPager == null)
        {
            if(status != DragLayout.Status.Open)
                setCanScroll(true);
            return super.dispatchTouchEvent(motionevent);
        }
        if(viewPager.getCurrentItem() == 0 && status != DragLayout.Status.Open){
        setCanScroll(true);
        }else{
            if(viewPager.getCurrentItem() != 0)
                setCanScroll(false); 
        }
        return super.dispatchTouchEvent(motionevent);
       
        
    }

    public void setViewPager(MyViewPager myviewpager)
    {
        viewPager = myviewpager;
    }

    private MyViewPager viewPager;
}
