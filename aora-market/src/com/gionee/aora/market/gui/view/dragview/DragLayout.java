// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view.dragview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import com.aora.base.util.DLog;
import com.nineoldandroids.view.ViewHelper;

// Referenced classes of package com.gionee.aora.market.gui.view.dragview:
//            MyRelativeLayout

public class DragLayout extends FrameLayout
{
    public static interface DragListener
    {

        public abstract void onClose();

        public abstract void onDrag(float f);

        public abstract void onOpen();
    }
    
    public enum Status{
        Drag,Open,Close
    }
    

   

    class YScrollDetector extends android.view.GestureDetector.SimpleOnGestureListener
    {

        public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
        {
            return Math.abs(f1) <= Math.abs(f) && canScroll && motionevent1.getX() - motionevent.getX() > 100F && Math.abs(f1) > 5F;
        }


       
    }


    public DragLayout(Context context)
    {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attributeset)
    {
        this(context, attributeset, 0);
    }

    public DragLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        status = Status.Close;
        canScroll = false;
        isopening = false;
        clickopen = false;
        gestureDetector = new GestureDetectorCompat(context, new YScrollDetector());
        dragHelper = ViewDragHelper.create(this, dragHelperCallback);
    }

    private void animateView(float f)
    {
        float f1 = 1.0F - f * 0.2F;
        ViewHelper.setScaleX(vg_main, f1);
        float _tmp = 1.0F - f * 0.2F;
        ViewHelper.setScaleY(vg_main, f1);
        ViewHelper.setTranslationX(vg_left, (float)(-vg_left.getWidth()) / 2.3F + f * ((float)vg_left.getWidth() / 2.3F));
        ViewHelper.setScaleX(vg_left, 0.5F + 0.5F * f);
        ViewHelper.setScaleY(vg_left, 0.5F + 0.5F * f);
        ViewHelper.setAlpha(vg_left, f);
        getBackground().setColorFilter(evaluate(f, Integer.valueOf(0xff000000), Integer.valueOf(0)).intValue(), android.graphics.PorterDuff.Mode.SRC_OVER);
    }

    private void dispatchDragEvent(int i)
    {
        if(dragListener != null)
        {
            float f = (float)i / (float)range;
            animateView(f);
            dragListener.onDrag(f);
            Status status1 = status;
            if(status1 != getStatus() && status == Status.Close)
            {
                dragListener.onClose();
                return;
            }
            if(status1 != getStatus() && status == Status.Open)
            {
                dragListener.onOpen();
                return;
            }
        }
    }

    private Integer evaluate(float f, Object obj, Integer integer)
    {
        int i = ((Integer)obj).intValue();
        int j = 0xff & i >> 24;
        int k = 0xff & i >> 16;
        int l = 0xff & i >> 8;
        int i1 = i & 0xff;
        int j1 = integer.intValue();
        int k1 = 0xff & j1 >> 24;
        int l1 = 0xff & j1 >> 16;
        int i2 = 0xff & j1 >> 8;
        int j2 = j1 & 0xff;
        return Integer.valueOf(j + (int)(f * (float)(k1 - j)) << 24 | k + (int)(f * (float)(l1 - k)) << 16 | l + (int)(f * (float)(i2 - l)) << 8 | i1 + (int)(f * (float)(j2 - i1)));
    }

    public void close()
    {
        close(true);
    }

    public void close(boolean flag)
    {
        if(flag)
        {
            if(dragHelper.smoothSlideViewTo(vg_main, 0, 0))
                ViewCompat.postInvalidateOnAnimation(this);
            return;
        } else
        {
            vg_main.layout(0, 0, width, height);
            dispatchDragEvent(0);
            return;
        }
    }

    public void computeScroll()
    {
        if(dragHelper.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        if(isopening)
        {
            isopening = false;
            return true;
        } else
        {
            return super.dispatchTouchEvent(motionevent);
        }
    }

    public Status getStatus()
    {
        if(mainLeft == 0)
            status = Status.Close;
        else
        if(mainLeft == range)
            status = Status.Open;
        else
            status = Status.Drag;
        return status;
    }

    public ViewGroup getVg_left()
    {
        return vg_left;
    }

    public ViewGroup getVg_main()
    {
        return vg_main;
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
        vg_left = (ScrollView)getChildAt(0);
        vg_main = (MyRelativeLayout)getChildAt(1);
        vg_main.setDragLayout(this);
        vg_left.setClickable(true);
        vg_main.setClickable(true);
    }
    public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
    {
      try
      {
        boolean bool1 = this.dragHelper.shouldInterceptTouchEvent(paramMotionEvent);
        boolean bool2 = false;
        if (bool1)
        {
          boolean bool3 = this.gestureDetector.onTouchEvent(paramMotionEvent);
          bool2 = false;
          if (bool3) {
            bool2 = true;
          }
        }
        return bool2;
      }
      catch (Exception localException)
      {
        DLog.e("DragLayout", "onInterceptTouchEvent##", localException);
      }
      return false;
    }
     

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        vg_left.layout(0, 0, width, height);
        vg_main.layout(mainLeft, 0, mainLeft + width, height);
    }

    protected void onSizeChanged(int i, int j, int k, int l)
    {
        super.onSizeChanged(i, j, k, l);
        width = vg_left.getMeasuredWidth();
        height = vg_left.getMeasuredHeight();
        range = (int)(0.7F * (float)width);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        try
        {
            dragHelper.processTouchEvent(motionevent);
        }
        catch(Exception exception)
        {
            DLog.e("DragLayout", "onTouchEvent##", exception);
        }
        return false;
    }

    public void open()
    {
        open(true);
    }

    public void open(boolean flag)
    {
        if(flag)
        {
            if(clickopen)
            {
                isopening = true;
                clickopen = false;
            }
            if(dragHelper.smoothSlideViewTo(vg_main, range, 0))
                ViewCompat.postInvalidateOnAnimation(this);
            return;
        } else
        {
            vg_main.layout(range, 0, 2 * range, height);
            dispatchDragEvent(range);
            return;
        }
    }

    public void setCanScroll(boolean flag)
    {
        canScroll = flag;
    }

    public void setClickOpen(boolean flag)
    {
        clickopen = flag;
    }

    public void setDragListener(DragListener draglistener)
    {
        dragListener = draglistener;
    }

    public void setOpening(boolean flag)
    {
        isopening = flag;
    }

    public boolean canScroll;
    public boolean clickopen;
    private ViewDragHelper dragHelper;
    private android.support.v4.widget.ViewDragHelper.Callback dragHelperCallback = new android.support.v4.widget.ViewDragHelper.Callback() {

        public int clampViewPositionHorizontal(View view, int j, int k)
        {
            if(k + mainLeft < 0)
                j = 0;
            else
            if(k + mainLeft > range)
                return range;
            return j;
        }

        public int getViewHorizontalDragRange(View view)
        {
            return width;
        }

        public void onViewPositionChanged(View view, int j, int k, int l, int i1)
        {
            if(view == vg_main)
                mainLeft = j;
            else
                mainLeft = j + mainLeft;
            if(mainLeft < 0) {
                //L1_L1:
                mainLeft = 0;
            }else{
                //L2_L2:
                if(mainLeft > range)
                    mainLeft = range;
            }

            if(view == vg_left)
            {
                vg_left.layout(0, 0, width, height);
                vg_main.layout(mainLeft, 0, mainLeft + width, height);
            }
            dispatchDragEvent(mainLeft);
            return;
 
        }

        public void onViewReleased(View view, float f, float f1)
        {
            super.onViewReleased(view, f, f1);
            if(f > 0.0F)
            {
                open();
                return;
            }
            if(f < 0.0F)
            {
                close();
                return;
            }
            if(view == vg_main && (double)mainLeft > 0.29999999999999999D * (double)range)
            {
                open();
                return;
            }
            if(view == vg_left && (double)mainLeft > 0.69999999999999996D * (double)range)
            {
                open();
                return;
            } else
            {
                close();
                return;
            }
        }

        public boolean tryCaptureView(View view, int j)
        {
            return true;
        }

        
    }
;
    private DragListener dragListener;
    private GestureDetectorCompat gestureDetector;
    private int height;
    public boolean isopening;
    private int mainLeft;
    private int range;
    public Status status;
    private ScrollView vg_left;
    private MyRelativeLayout vg_main;
    private int width;



/*
    static int access$002(DragLayout draglayout, int i)
    {
        draglayout.mainLeft = i;
        return i;
    }

*/






}
