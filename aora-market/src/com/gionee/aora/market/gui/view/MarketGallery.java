// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Gallery;
import com.aora.base.util.DLog;
import com.aora.base.util.ReflectUtil;
import java.lang.reflect.Method;

public class MarketGallery extends Gallery
{
    public static interface OnGalleryItemClickLsr
    {

        public abstract void onGalleryItemClickLsr(int i);
    }


    public MarketGallery(Context context)
    {
        super(context);
        viewGroups = null;
        cancle = false;
        isOntouch = false;
        x = 0.0F;
        init();
    }

    public MarketGallery(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        viewGroups = null;
        cancle = false;
        isOntouch = false;
        x = 0.0F;
        init();
    }

    public MarketGallery(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        viewGroups = null;
        cancle = false;
        isOntouch = false;
        x = 0.0F;
        init();
    }

    private void init()
    {
        setSpacing(0);
        setUnselectedAlpha(255F);
        setFadingEdgeLength(0);
    }

    public void autoRefresh()
    {
        cancle = false;
        handler.sendEmptyMessageDelayed(0, 5000L);
    }

    public void dispatchOnItemClickListener(int i)
    {
        if(mOnGalleryItemClickLsr != null)
            mOnGalleryItemClickLsr.onGalleryItemClickLsr(i);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        if(viewGroups != null)
        {
            for(int i = 0; i < viewGroups.length; i++)
                viewGroups[i].requestDisallowInterceptTouchEvent(true);

        }
        return super.dispatchTouchEvent(motionevent);
    }

    public ViewGroup[] getViewGroups()
    {
        return viewGroups;
    }

    public boolean isCancle()
    {
        return cancle;
    }

    public boolean moveBefore()
    {
        boolean flag;
        try
        {
            Class class1 = getClass();
            Class aclass[] = new Class[1];
            aclass[0] = Integer.TYPE;
            Method method = ReflectUtil.getMethod(class1, "trackMotionScroll", aclass);
            method.setAccessible(true);
            Object aobj[] = new Object[1];
            aobj[0] = new Integer(-1);
            method.invoke(this, aobj);
            Method method1 = ReflectUtil.getMethod(getClass(), "movePrevious", new Class[0]);
            method1.setAccessible(true);
            flag = ((Boolean)method1.invoke(this, new Object[0])).booleanValue();
        }
        catch(Exception exception)
        {
            DLog.e("MarketGallery", "moveBefore()#Exception=", exception);
            return false;
        }
        return flag;
    }

    public boolean moveN()
    {
        boolean flag;
        try
        {
            Class class1 = getClass();
            Class aclass[] = new Class[1];
            aclass[0] = Integer.TYPE;
            Method method = ReflectUtil.getMethod(class1, "trackMotionScroll", aclass);
            method.setAccessible(true);
            Object aobj[] = new Object[1];
            aobj[0] = new Integer(-1);
            method.invoke(this, aobj);
            Method method1 = ReflectUtil.getMethod(getClass(), "moveNext", new Class[0]);
            method1.setAccessible(true);
            flag = ((Boolean)method1.invoke(this, new Object[0])).booleanValue();
        }
        catch(Exception exception)
        {
            DLog.e("MarketGallery", "moveN()#Exception=", exception);
            return false;
        }
        return flag;
    }

    public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1, float f, float f1)
    {
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionevent)
    {
        if(viewGroups != null)
        {
            for(int i = 0; i < viewGroups.length; i++)
                viewGroups[i].requestDisallowInterceptTouchEvent(true);

        }
        return super.onInterceptTouchEvent(motionevent);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        ViewGroup aviewgroup[];
        int i;
        if(motionevent.getAction() == 0)
        {
            x = motionevent.getX();
            isOntouch = true;
            handler.removeMessages(0);
        } else
        if(motionevent.getAction() == 1 || motionevent.getAction() == 3)
        {
            isOntouch = false;
            if(x - motionevent.getX() > 2.0F)
                moveN();
            else
            if(x - motionevent.getX() < -2F)
                moveBefore();
            autoRefresh();
        }
        aviewgroup = viewGroups;
        i = 0;
        if(aviewgroup != null)
            for(; i < viewGroups.length; i++)
                viewGroups[i].requestDisallowInterceptTouchEvent(true);

        return super.onTouchEvent(motionevent);
    }

    public void setCancle(boolean flag)
    {
        cancle = flag;
    }

    public void setOnGalleryItemClickLsr(OnGalleryItemClickLsr ongalleryitemclicklsr)
    {
        mOnGalleryItemClickLsr = ongalleryitemclicklsr;
    }

    public   void setViewGroups(ViewGroup aviewgroup[])
    {
        viewGroups = aviewgroup;
    }

    private static final String TAG = "MarketGallery";
    private boolean cancle;
    private Handler handler = new Handler() {

        public void handleMessage(Message message)
        {
            super.handleMessage(message);
            while(cancle || isOntouch) 
                return;
            if(!moveN())
            {
                setSelection(0);
                return;
            } else
            {
                handler.sendEmptyMessageDelayed(0, 3000L);
                return;
            }
        }

        
    }
;
    private boolean isOntouch;
    private OnGalleryItemClickLsr mOnGalleryItemClickLsr;
    private ViewGroup viewGroups[];
    private float x;



}
