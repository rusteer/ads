// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageIndicateLayout extends LinearLayout
{

    public ImageIndicateLayout(Context context)
    {
        super(context);
        count = 0;
        curPosition = -1;
        init(context);
    }

    public ImageIndicateLayout(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        count = 0;
        curPosition = -1;
        init(context);
    }

    private void init(Context context)
    {
        setOrientation(0);
        mContext = context;
    }

    public void setCurView(final int position)
    {
        postDelayed(new Runnable() {

            public void run()
            {
                if(position < count)
                {
                    if(curPosition != -1 && getChildAt(curPosition) != null)
                        ((ImageView)getChildAt(curPosition)).setImageResource(biz.AR.drawable.banner_point_);
                    ((ImageView)getChildAt(position)).setImageResource(biz.AR.drawable.banner_point);
                    curPosition = position;
                }
            }

            
        }
, 300L);
    }

    public void setViewCount(int i)
    {
        removeAllViews();
        count = i;
        for(int j = 0; j < i; j++)
        {
            ImageView imageview = new ImageView(mContext);
            imageview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-2, -2));
            imageview.setImageResource(biz.AR.drawable.banner_point_);
            imageview.setPadding(15, 0, 0, 0);
            addView(imageview);
        }

    }

    private int count;
    private int curPosition;
    private Context mContext;




/*
    static int access$102(ImageIndicateLayout imageindicatelayout, int i)
    {
        imageindicatelayout.curPosition = i;
        return i;
    }

*/
}
