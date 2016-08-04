// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;

public class IntegralLoadingView extends LinearLayout
{

    public IntegralLoadingView(Context context)
    {
        super(context);
        init(context);
    }

    public IntegralLoadingView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init(context);
    }

    private RotateAnimation getScrollAnimation()
    {
        RotateAnimation rotateanimation = new RotateAnimation(0.0F, 359F, 1, 0.5F, 1, 0.5F);
        rotateanimation.setRepeatCount(-1);
        rotateanimation.setDuration(1000L);
        rotateanimation.setInterpolator(new LinearInterpolator());
        return rotateanimation;
    }

    private void init(Context context)
    {
        View.inflate(context, biz.AR.layout.integral_loading_view, this);
        loadingBar = (LinearLayout)findViewById(biz.AR.id.loadingbar);
        scrollImg = (ImageView)findViewById(biz.AR.id.scrollImg);
        retryBtn = (Button)findViewById(biz.AR.id.retryBtn);
        showStateLoading();
    }

    public void showStateFail()
    {
        loadingBar.setVisibility(8);
        retryBtn.setVisibility(0);
    }

    public void showStateLoading()
    {
        loadingBar.setVisibility(0);
        retryBtn.setVisibility(8);
        scrollImg.startAnimation(getScrollAnimation());
    }

    LinearLayout loadingBar;
    Button retryBtn;
    ImageView scrollImg;
}
