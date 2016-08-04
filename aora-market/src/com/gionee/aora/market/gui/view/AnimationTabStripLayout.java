// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.*;

public class AnimationTabStripLayout extends RelativeLayout
{

    public AnimationTabStripLayout(Context context)
    {
        super(context);
        params = null;
        now = 0;
        last = 0;
        dm = null;
        tabStrip = null;
        colum = 3;
        initLayout(context);
    }

    public AnimationTabStripLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        params = null;
        now = 0;
        last = 0;
        dm = null;
        tabStrip = null;
        colum = 3;
        initLayout(context);
    }

    public AnimationTabStripLayout(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        params = null;
        now = 0;
        last = 0;
        dm = null;
        tabStrip = null;
        colum = 3;
        initLayout(context);
    }

    private void initLayout(Context context)
    {
        mScroller = new Scroller(context);
        dm = context.getResources().getDisplayMetrics();
        params = new android.widget.RelativeLayout.LayoutParams(dm.widthPixels / colum, 20);
        tabStrip = new ImageView(context);
        tabStrip.setBackgroundColor(Color.parseColor("#ff7e00"));
        tabStrip.setLayoutParams(params);
        tabStrip.setVisibility(8);
        addView(tabStrip);
        setBackgroundColor(Color.argb(120, 0, 0, 0));
    }

    private void startAnimation()
    {
        if(last == now)
        {
            return;
        } else
        {
            smoothScrollTo((-now * dm.widthPixels) / colum, 0);
            last = now;
            return;
        }
    }

    public void computeScroll()
    {
        if(mScroller.computeScrollOffset())
        {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    public int getColum()
    {
        return colum;
    }

    public void refreshTabStrip(int i)
    {
        now = i;
        startAnimation();
    }

    public void setColum(int i)
    {
        if(i == 0)
        {
            return;
        } else
        {
            colum = i;
            params = new android.widget.RelativeLayout.LayoutParams(dm.widthPixels / i, 20);
            tabStrip.setLayoutParams(params);
            tabStrip.setVisibility(0);
            return;
        }
    }

    public void smoothScrollBy(int i, int j)
    {
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), i, j, 1000);
        invalidate();
    }

    public void smoothScrollTo(int i, int j)
    {
        smoothScrollBy(i - mScroller.getFinalX(), j - mScroller.getFinalY());
    }

    private int colum;
    private DisplayMetrics dm;
    private int last;
    private Scroller mScroller;
    private int now;
    private android.widget.RelativeLayout.LayoutParams params;
    private ImageView tabStrip;
}
