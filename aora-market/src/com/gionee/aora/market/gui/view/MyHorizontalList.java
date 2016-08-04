// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

// Referenced classes of package com.gionee.aora.market.gui.view:
//            BaseHorizontalScrollView

public class MyHorizontalList extends BaseHorizontalScrollView
    implements android.support.v4.view.ViewPager.OnPageChangeListener
{

    public MyHorizontalList(Context context1)
    {
        super(context1);
        mSelectedIndex = 0;
        context = context1;
    }

    public MyHorizontalList(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        mSelectedIndex = 0;
        context = context1;
        setHorizontalScrollBarEnabled(false);
    }

    private void animateToIcon(int i)
    {
        final View iconView = ((LinearLayout)getChildAt(0)).getChildAt(i);
        if(mIconSelector != null)
            removeCallbacks(mIconSelector);
        mIconSelector = new Runnable() {

            public void run()
            {
                int j = iconView.getLeft() - (getWidth() - iconView.getWidth()) / 2;
                smoothScrollTo(j, 0);
                mIconSelector = null;
            }

            
        }
;
        post(mIconSelector);
    }

    private void refreshUISelect()
    {
        ViewGroup viewgroup = (ViewGroup)getChildAt(0);
        if(viewgroup != null)
        {
            int i = 0;
            while(i < viewgroup.getChildCount()) 
            {
                View view = viewgroup.getChildAt(i);
                if(view != null)
                {
                    view.setTag(Integer.valueOf(i));
                    boolean flag;
                    if(i == mSelectedIndex)
                        flag = true;
                    else
                        flag = false;
                    view.setSelected(flag);
                    view.setOnClickListener(new android.view.View.OnClickListener() {

                        public void onClick(View view1)
                        {
                            int j;
                            if(view1.getTag() == null)
                                j = 0;
                            else
                                j = ((Integer)view1.getTag()).intValue();
                            setCurrentItem(j);
                        }

                        
                    }
);
                }
                i++;
            }
        }
    }

    public void addTab(View view)
    {
        ((LinearLayout)getChildAt(0)).addView(view);
    }

    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if(mIconSelector != null)
            post(mIconSelector);
    }

    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if(mIconSelector != null)
            removeCallbacks(mIconSelector);
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        refreshUISelect();
        super.onLayout(flag, i, j, k, l);
    }

    public void onPageScrollStateChanged(int i)
    {
        if(listener != null)
            listener.onPageScrollStateChanged(i);
    }

    public void onPageScrolled(int i, float f, int j)
    {
        if(listener != null)
            listener.onPageScrolled(i, f, j);
    }

    public void onPageSelected(int i)
    {
        mSelectedIndex = i;
        if(listener != null)
            listener.onPageSelected(mSelectedIndex);
        refreshUISelect();
        animateToIcon(mSelectedIndex);
    }

    public void setCurrentItem(int i)
    {
        if(viewpager == null)
            throw new IllegalStateException("ViewPager has not been bound.");
        mSelectedIndex = i;
        viewpager.setCurrentItem(mSelectedIndex);
        LinearLayout linearlayout = (LinearLayout)getChildAt(0);
        int j = linearlayout.getChildCount();
        int k = 0;
        while(k < j) 
        {
            View view = linearlayout.getChildAt(k);
            boolean flag;
            if(k == mSelectedIndex)
                flag = true;
            else
                flag = false;
            view.setSelected(flag);
            if(flag)
                animateToIcon(mSelectedIndex);
            k++;
        }
    }

    public void setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener onpagechangelistener)
    {
        listener = onpagechangelistener;
    }

    public void setViewPager(ViewPager viewpager1)
    {
        if(viewpager1 == null)
        {
            return;
        } else
        {
            viewpager = viewpager1;
            viewpager.setOnPageChangeListener(this);
            return;
        }
    }

    Context context;
    android.support.v4.view.ViewPager.OnPageChangeListener listener;
    private Runnable mIconSelector;
    private int mSelectedIndex;
    private ViewPager viewpager;


/*
    static Runnable access$002(MyHorizontalList myhorizontallist, Runnable runnable)
    {
        myhorizontallist.mIconSelector = runnable;
        return runnable;
    }

*/
}
