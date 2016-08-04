// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.market.util.Util;

public class LoadMoreView extends RelativeLayout
{

    public LoadMoreView(Context context)
    {
        super(context);
        tryAgainButton = null;
        loadingView = null;
        progressbarImageView = null;
        isShowTryAgain = false;
        onLayout(context);
    }

    public LoadMoreView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        tryAgainButton = null;
        loadingView = null;
        progressbarImageView = null;
        isShowTryAgain = false;
        onLayout(context);
    }

    public LoadMoreView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        tryAgainButton = null;
        loadingView = null;
        progressbarImageView = null;
        isShowTryAgain = false;
        onLayout(context);
    }

    private void onLayout(final Context context)
    {
        view = View.inflate(context, biz.AR.layout.load_more_layout, null);
        tryAgainButton = (Button)view.findViewById(biz.AR.id.tryAgagin);
        loadmsg = (TextView)view.findViewById(biz.AR.id.textView1);
        loadingView = (LinearLayout)view.findViewById(biz.AR.id.loadingMore);
        progressbarImageView = (ImageView)view.findViewById(biz.AR.id.progressBarImg);
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, biz.AR.anim.loadmore_animate);
        progressbarImageView.startAnimation(animation);
        addView(view, new android.widget.AbsListView.LayoutParams(-1, (int)context.getResources().getDimension(biz.AR.dimen.dip60)));
        setGravity(17);
        showTryAgainButton(false);
        tryAgainButton.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view1)
            {
                if(!DownloadManager.shareInstance().isbNetworkValid())
                {
                    Util.showNetErrorDialog(context);
                    return;
                } else
                {
                    showTryAgainButton(false);
                    tryAgain();
                    return;
                }
            }

             
        }
);
    }

    public boolean isShowTryAgain()
    {
        return isShowTryAgain;
    }

    public void setMoerViewColor(int i)
    {
        view.setBackgroundColor(i);
    }

    public void setMoerViewText(String s)
    {
        loadmsg.setText(s);
    }

    public void setShowTryAgain(boolean flag)
    {
        isShowTryAgain = flag;
    }

    public void showTryAgainButton(boolean flag)
    {
        if(flag)
        {
            loadingView.setVisibility(8);
            tryAgainButton.setVisibility(0);
            isShowTryAgain = true;
            return;
        } else
        {
            loadingView.setVisibility(0);
            tryAgainButton.setVisibility(8);
            isShowTryAgain = false;
            return;
        }
    }

    public void tryAgain()
    {
    }

    private boolean isShowTryAgain;
    private LinearLayout loadingView;
    private TextView loadmsg;
    private ImageView progressbarImageView;
    private Button tryAgainButton;
    private View view;
}
