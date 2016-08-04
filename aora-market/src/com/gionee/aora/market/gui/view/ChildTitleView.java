// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.app.Activity;
import android.content.*;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import com.aora.base.util.DLog;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.market.gui.download.DownloadManagerActivity;
import com.gionee.aora.market.gui.search.SearchActivity;
import java.util.List;

public class ChildTitleView extends RelativeLayout
{
    class DownloadChanReceiver extends BroadcastReceiver
    {

        public void onReceive(Context context1, Intent intent)
        {
            DLog.d("denglh", "ChildTitleView.DownloadChanReceiver");
            List list = DownloadManager.shareInstance().getAllTaskInfo();
            if(list != null && list.size() != 0)
            {
                downloadTextView.setVisibility(0);
                return;
            } else
            {
                downloadTextView.setVisibility(8);
                return;
            }
        }

        
    }


    public ChildTitleView(Context context1)
    {
        super(context1);
        init(context1);
    }

    public ChildTitleView(Context context1, AttributeSet attributeset)
    {
        super(context1, attributeset);
        init(context1);
    }

    public ChildTitleView(Context context1, AttributeSet attributeset, int i)
    {
        super(context1, attributeset, i);
        init(context1);
    }

    private void init(Context context1)
    {
        context = context1;
        contentView = View.inflate(context, biz.AR.layout.child_title_lay, this);
        childTitleLayout = (RelativeLayout)contentView.findViewById(biz.AR.id.child_title_lay_layout);
        titleTextView = (TextView)contentView.findViewById(biz.AR.id.child_title_textview);
        line = contentView.findViewById(biz.AR.id.child_title_line);
        rightView = (RelativeLayout)contentView.findViewById(biz.AR.id.child_title_right_lay);
        backImg = (RelativeLayout)contentView.findViewById(biz.AR.id.child_title_back);
        backArrowImg = (ImageView)contentView.findViewById(biz.AR.id.child_title_back_img);
        backImg.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ((Activity)context).finish();
            }

            
        }
);
        searchImg = (ImageView)contentView.findViewById(biz.AR.id.child_title_search_img);
        searchImg.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(context,  SearchActivity.class);
                intent.setFlags(0x200000);
                context.startActivity(intent);
            }

            
        }
);
        downloadImg = (ImageView)contentView.findViewById(biz.AR.id.child_title_download_img);
        downloadImg.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(context, DownloadManagerActivity.class);
                context.startActivity(intent);
            }

           
        }
);
        downloadTextView = contentView.findViewById(biz.AR.id.child_title_download_count);
        List list = DownloadManager.shareInstance().getAllTaskInfo();
        if(list != null && list.size() != 0)
            downloadTextView.setVisibility(0);
        reciver = new DownloadChanReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("com.gionee.aora.market.download.count.change");
        context.registerReceiver(reciver, intentfilter);
    }

    public RelativeLayout getRightView()
    {
        return (RelativeLayout)contentView.findViewById(biz.AR.id.child_title_right_lay);
    }

    protected void onDetachedFromWindow()
    {
        if(reciver != null)
            context.unregisterReceiver(reciver);
        super.onDetachedFromWindow();
    }

    public void reSetRightSearchImg(int i, android.view.View.OnClickListener onclicklistener)
    {
        if(searchImg == null)
        {
            return;
        } else
        {
            searchImg.setOnClickListener(null);
            searchImg.setBackgroundResource(i);
            searchImg.setOnClickListener(onclicklistener);
            return;
        }
    }

    public void setAllBackgroundColor(int i)
    {
        childTitleLayout.setBackgroundColor(i);
    }

    public void setAllHeight(int i)
    {
        android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(-1, i);
        childTitleLayout.setLayoutParams(layoutparams);
    }

    public void setBackArrow(int i)
    {
        backArrowImg.setBackgroundResource(i);
    }

    public void setLineVisibility(int i)
    {
        line.setVisibility(i);
    }

    public void setOnBackListener(android.view.View.OnClickListener onclicklistener)
    {
        backImg.setOnClickListener(null);
        backImg.setOnClickListener(onclicklistener);
    }

    public void setRightView(View view)
    {
        android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(-2, -2);
        layoutparams.addRule(15);
        view.setLayoutParams(layoutparams);
        rightView.removeAllViews();
        rightView.addView(view);
    }

    public void setRightViewVisibility()
    {
        rightView.setVisibility(8);
    }

    public void setRightViewVisibility(boolean flag)
    {
        if(flag)
        {
            rightView.setVisibility(0);
            return;
        } else
        {
            rightView.setVisibility(8);
            return;
        }
    }

    public void setTitle(String s)
    {
        titleTextView.setText(s);
    }

    public void setTitleColor(int i)
    {
        titleTextView.setTextColor(i);
    }

    public ImageView backArrowImg;
    public RelativeLayout backImg;
    private RelativeLayout childTitleLayout;
    private View contentView;
    private Context context;
    public ImageView downloadImg;
    private View downloadTextView;
    public View line;
    private DownloadChanReceiver reciver;
    public RelativeLayout rightView;
    public ImageView searchImg;
    private TextView titleTextView;


}
