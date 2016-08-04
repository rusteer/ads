// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view.tabview;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;

// Referenced classes of package com.gionee.aora.market.gui.view.tabview:
//            UnderlinePageIndicator

public class ClassifyTabView extends HorizontalScrollView
    implements android.support.v4.view.ViewPager.OnPageChangeListener
{

    public ClassifyTabView(Context context1)
    {
        super(context1);
        itemSumPerScreen = 5;
        itemWidth = 1;
        context = context1;
        initView(context1);
    }

    public ClassifyTabView(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        itemSumPerScreen = 5;
        itemWidth = 1;
        context = context1;
        initView(context1);
    }

    public ClassifyTabView(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        itemSumPerScreen = 5;
        itemWidth = 1;
        context = context1;
        initView(context1);
    }

    private void init()
    {
        int i = context.getResources().getDimensionPixelSize(biz.AR.dimen.dip44);
        itemWidth = (int)(0.5D + (double)(sw / 5));
        if(pager != null && pager.getAdapter() != null)
        {
            int j = pager.getAdapter().getCount() * itemWidth;
            android.widget.FrameLayout.LayoutParams layoutparams = (android.widget.FrameLayout.LayoutParams)contentOutSide.getLayoutParams();
            layoutparams.width = j;
            contentOutSide.setLayoutParams(layoutparams);
            android.widget.LinearLayout.LayoutParams layoutparams1 = (android.widget.LinearLayout.LayoutParams)content.getLayoutParams();
            layoutparams1.width = j;
            content.setLayoutParams(layoutparams1);
            android.widget.LinearLayout.LayoutParams layoutparams2 = (android.widget.LinearLayout.LayoutParams)underLine.getLayoutParams();
            layoutparams2.width = j;
            underLine.setLayoutParams(layoutparams2);
              int index = 0;
            while(index < pager.getAdapter().getCount()) 
            {
                TextView textview = (TextView)View.inflate(context, biz.AR.layout.classify_pager_tab_tv, null);
                textview.setText(titles[index]);
                textview.setFocusable(true);
                android.content.res.XmlResourceParser xmlresourceparser = getResources().getXml(biz.AR.drawable.tab_botton_textcolor_lv2);
                android.widget.LinearLayout.LayoutParams layoutparams3;
                try
                {
                    textview.setTextColor(ColorStateList.createFromXml(getResources(), xmlresourceparser));
                }
                catch(Exception exception) { }
                final int index1=index;
                textview.setOnClickListener(new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        pager.setCurrentItem(index1);
                    }

                    
                }
);
                if(index == pager.getCurrentItem())
                    textview.setSelected(true);
                layoutparams3 = new android.widget.LinearLayout.LayoutParams(itemWidth, i);
                content.addView(textview, layoutparams3);
                index++;
            }
        }
    }

    private void initView(Context context1)
    {
        sw = ((Activity)context1).getWindowManager().getDefaultDisplay().getWidth();
        setHorizontalFadingEdgeEnabled(false);
        setHorizontalScrollBarEnabled(false);
        View.inflate(context1, biz.AR.layout.classify_pager_tab, this);
        contentOutSide = (LinearLayout)findViewById(biz.AR.id.classify_pager_tab_content);
        content = (LinearLayout)findViewById(biz.AR.id.classify_pager_tab_content2);
        underLine = (UnderlinePageIndicator)findViewById(biz.AR.id.under_line_block);
        underLine.setSelectedColor(0xff8ac873);
        underLine.setFades(false);
    }

    protected void onMeasure(int i, int j)
    {
        super.onMeasure(i, j);
        init();
    }

    public void onPageScrollStateChanged(int i)
    {
        mScrollState = i;
        if(mListener != null)
            mListener.onPageScrollStateChanged(i);
    }

    public void onPageScrolled(int i, float f, int j)
    {
        int k;
        Log.e("test", (new StringBuilder()).append("index=").append(i).toString());
        Log.e("test", (new StringBuilder()).append("offset=").append(f).toString());
        Log.e("test", (new StringBuilder()).append("offsetPx=").append(j).toString());
        k = (int)((f + (float)(i + 1)) * (float)itemWidth - (float)(getScrollX() + sw));
        if(k > 0) {
        scrollBy(k, 0);
        }else{
            int l = (int)((f + (float)i) * (float)itemWidth - (float)getScrollX());
            if(l < 0)
                scrollBy(l, 0);
        }
        if(mListener != null)
            mListener.onPageScrolled(i, f, j);
        
    }

    public void onPageSelected(int i)
    {
        int j = 0;
        while(j < content.getChildCount()) 
        {
            View view = content.getChildAt(j);
            boolean flag;
            if(i == j)
                flag = true;
            else
                flag = false;
            view.setSelected(flag);
            j++;
        }
        if(mListener != null)
            mListener.onPageSelected(i);
    }

    public void setDefaultIndex(int i)
    {
        int j = 0;
        while(j < content.getChildCount()) 
        {
            View view = content.getChildAt(j);
            boolean flag;
            if(i == j)
                flag = true;
            else
                flag = false;
            view.setSelected(flag);
            j++;
        }
    }

    public void setOnPageChangeListener(android.support.v4.view.ViewPager.OnPageChangeListener onpagechangelistener)
    {
        mListener = onpagechangelistener;
    }

    public void setTitles(String as[])
    {
        titles = as;
    }

    public void setViewPager(ViewPager viewpager)
    {
        pager = viewpager;
        underLine.setViewPager(viewpager);
        underLine.setOnPageChangeListener(this);
    }

    LinearLayout content;
    LinearLayout contentOutSide;
    Context context;
    private final int itemSumPerScreen;
    private int itemWidth;
    private android.support.v4.view.ViewPager.OnPageChangeListener mListener;
    private int mScrollState;
    ViewPager pager;
    int sw;
    String titles[];
    UnderlinePageIndicator underLine;
}
