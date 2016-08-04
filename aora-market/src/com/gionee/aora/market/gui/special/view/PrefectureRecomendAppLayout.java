// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.gui.details.IntroductionDetailActivity;
import com.gionee.aora.market.module.AppInfo;
import java.util.List;

public class PrefectureRecomendAppLayout extends HorizontalScrollView
{

    public PrefectureRecomendAppLayout(Context context1)
    {
        super(context1);
        datainfo = null;
        context = context1;
        init(context1);
    }

    public PrefectureRecomendAppLayout(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        datainfo = null;
        context = context1;
        init(context1);
    }

    public PrefectureRecomendAppLayout(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        datainfo = null;
        context = context1;
        init(context1);
    }

    private void init(Context context1)
    {
        setHorizontalScrollBarEnabled(false);
        imageLoaderManager = ImageLoaderManager.getInstance();
        isInEditMode();
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

    public void onDestroy()
    {
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
        if(viewGroups != null)
        {
            for(int i = 0; i < viewGroups.length; i++)
                viewGroups[i].requestDisallowInterceptTouchEvent(true);

        }
        return super.onTouchEvent(motionevent);
    }

    public   void setAppData(List list, DataCollectInfo datacollectinfo, ViewGroup aviewgroup[])
    {
        for(datainfo = datacollectinfo; list == null || list.size() == 0;)
            return;

        viewGroups = aviewgroup;
        int i = list.size();
        LinearLayout linearlayout = new LinearLayout(context);
        linearlayout.setGravity(16);
        int j = (int)getResources().getDimension(biz.AR.dimen.dip10);
        int k = (int)getResources().getDimension(biz.AR.dimen.dip12);
        int l = 0;
        while(l < i) 
        {
            View view = View.inflate(context, biz.AR.layout.prefecture_app_view_layout, null);
            icon = (ImageView)view.findViewById(biz.AR.id.app_view_icon);
            appName = (TextView)view.findViewById(biz.AR.id.app_view_name);
            android.widget.LinearLayout.LayoutParams layoutparams = new android.widget.LinearLayout.LayoutParams(-1, -1);
            if(l == 0)
                layoutparams.leftMargin = j;
            else
                layoutparams.leftMargin = k;
            if(l == i - 1)
                layoutparams.rightMargin = j;
            view.setLayoutParams(layoutparams);
            linearlayout.addView(view);
            appName.setText((new StringBuilder()).append(((AppInfo)list.get(l)).getName()).append(" ").toString());
            view.setTag(list.get(l));
            view.setOnClickListener(itemOnClickListener);
            imageLoaderManager.displayImage(((AppInfo)list.get(l)).getIconUrl(), icon, imageLoaderManager.getImageLoaderOptions());
            l++;
        }
        removeAllViews();
        addView(linearlayout);
    }

    private TextView appName;
    private Context context;
    private DataCollectInfo datainfo;
    private ImageView icon;
    private ImageLoaderManager imageLoaderManager;
    private android.view.View.OnClickListener itemOnClickListener = new android.view.View.OnClickListener() {

        public void onClick(View view)
        {
            AppInfo appinfo = (AppInfo)view.getTag();
            IntroductionDetailActivity.startIntroductionActivity(context, appinfo, datainfo);
        }
 
    }
;
    private ViewGroup viewGroups[];


}
