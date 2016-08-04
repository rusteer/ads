// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details.view;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ScreenShotsImageView extends ImageView {
    private ViewPager viewPager;
    public ScreenShotsImageView(Context context) {
        super(context);
        viewPager = null;
    }
    public ScreenShotsImageView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        viewPager = null;
    }
    public ScreenShotsImageView(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        viewPager = null;
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        if (viewPager != null) viewPager.requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(motionevent);
    }
    public void setViewPager(ViewPager viewpager) {
        viewPager = viewpager;
    }
}
