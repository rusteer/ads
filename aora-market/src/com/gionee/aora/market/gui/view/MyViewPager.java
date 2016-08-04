// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.integral.ManagerActivity;

public class MyViewPager extends ViewPager
{

    public MyViewPager(Context context)
    {
        super(context);
        isMainActivity = false;
        eventX = 0.0F;
        eventY = 0.0F;
        dip20 = 0.0F;
        activity = null;
        dispatch = false;
        dip20 = context.getResources().getDimension(biz.AR.dimen.dip20);
    }

    public MyViewPager(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        isMainActivity = false;
        eventX = 0.0F;
        eventY = 0.0F;
        dip20 = 0.0F;
        activity = null;
        dispatch = false;
        dip20 = context.getResources().getDimension(biz.AR.dimen.dip20);
    }

    public MyViewPager(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset);
        isMainActivity = false;
        eventX = 0.0F;
        eventY = 0.0F;
        dip20 = 0.0F;
        activity = null;
        dispatch = false;
        dip20 = context.getResources().getDimension(biz.AR.dimen.dip20);
    }

    public boolean dispatchTouchEvent(MotionEvent motionevent)
    {
        if(motionevent.getAction() == 0)
        {
            eventX = motionevent.getRawX();
            eventY = motionevent.getRawY();
        }
        if(motionevent.getAction() == 2 && isMainActivity && !dispatch && getCurrentItem() == 0 && motionevent.getRawX() - eventX > dip20 && Math.abs(motionevent.getRawY() - eventY) < dip20)
        {
            DLog.d("denglihua", "\u7B2C\u4E00\u4E2Aviewpage\u800C\u4E14\u5411\u53F3\u6ED1\u52A8  \u8DF3\u8F6C\u81F3\u7BA1\u7406\u4E2D\u5FC3");
            Intent intent = new Intent(activity,  ManagerActivity.class);
            activity.startActivity(intent);
            activity.overridePendingTransition(biz.AR.anim.login_right_out, biz.AR.anim.login_right_in);
        }
        dispatch = false;
        return super.dispatchTouchEvent(motionevent);
    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        return super.onTouchEvent(motionevent);
    }

    public void setActivity(Activity activity1)
    {
        activity = activity1;
    }

    public void setDispatch(boolean flag)
    {
        dispatch = flag;
    }

    public void setMainActivity(boolean flag)
    {
        isMainActivity = flag;
    }

    private Activity activity;
    private float dip20;
    private boolean dispatch;
    private float eventX;
    private float eventY;
    private boolean isMainActivity;
}
