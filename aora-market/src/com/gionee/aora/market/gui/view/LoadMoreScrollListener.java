// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.widget.AbsListView;
import com.aora.base.util.DLog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

public class LoadMoreScrollListener extends PauseOnScrollListener
{
    public static interface setOnScrollToEndListener
    {

        public abstract void loadMoreWhenScrollToEnd();
    }


    public LoadMoreScrollListener(ImageLoader imageloader, boolean flag, boolean flag1, setOnScrollToEndListener setonscrolltoendlistener)
    {
        super(imageloader, flag, flag1);
        listener = setonscrolltoendlistener;
    }

    public void onScroll(AbsListView abslistview, int i, int j, int k)
    {
    }

    public void onScrollStateChanged(AbsListView abslistview, int i)
    {
        super.onScrollStateChanged(abslistview, i);
        if(i == 0 && abslistview.getCount() - abslistview.getLastVisiblePosition() < 10)
        {
            DLog.d("lung", "\u6ED1\u52A8\u5230\u5E95");
            listener.loadMoreWhenScrollToEnd();
        }
    }

    private setOnScrollToEndListener listener;
}
