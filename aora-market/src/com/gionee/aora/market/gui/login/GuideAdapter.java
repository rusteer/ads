// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.login;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class GuideAdapter extends PagerAdapter
{

    public GuideAdapter(List list)
    {
        views = list;
    }

    public void destroyItem(ViewGroup viewgroup, int i, Object obj)
    {
        viewgroup.removeView((View)views.get(i));
    }

    public int getCount()
    {
        return views.size();
    }

    public Object instantiateItem(ViewGroup viewgroup, int i)
    {
        viewgroup.addView((View)views.get(i), 0);
        return views.get(i);
    }

    public boolean isViewFromObject(View view, Object obj)
    {
        return view == obj;
    }

    private List views;
}
