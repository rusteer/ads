// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details.view;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

public class ScreenShotHorizontalScrollView extends HorizontalScrollView {
    private ScrollView scrollView;
    private long touchFirstTime;
    private float touchFirstY;
    private ViewPager viewPager;
    public ScreenShotHorizontalScrollView(Context context) {
        super(context);
        touchFirstTime = 0L;
        touchFirstY = 0.0F;
    }
    public ScreenShotHorizontalScrollView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        touchFirstTime = 0L;
        touchFirstY = 0.0F;
    }
    public ScreenShotHorizontalScrollView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        touchFirstTime = 0L;
        touchFirstY = 0.0F;
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        switch (motionevent.getAction()) {
            case 0://L2_L2:
                touchFirstTime = System.currentTimeMillis();
                touchFirstY = motionevent.getY();
                break;
            case 1://L1
                break;
            case 2://L3_L3:
                if (System.currentTimeMillis() - touchFirstTime <= 200L) if (Math.abs(motionevent.getY() - touchFirstY) > 30F) {
                    if (viewPager != null) viewPager.requestDisallowInterceptTouchEvent(true);
                    if (scrollView != null) scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    if (viewPager != null) viewPager.requestDisallowInterceptTouchEvent(true);
                    if (scrollView != null) scrollView.requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onTouchEvent(motionevent);
    }
    public void setViewGroup(ViewPager viewpager, ScrollView scrollview) {
        viewPager = viewpager;
        scrollView = scrollview;
    }
}
