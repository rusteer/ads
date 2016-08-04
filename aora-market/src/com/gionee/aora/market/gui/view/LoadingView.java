// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class LoadingView extends FrameLayout
{

    public LoadingView(Context context)
    {
        super(context);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init(context);
    }

    public LoadingView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init(context);
    }

    private void init(Context context)
    {
        animation_rotate = new RotateAnimation(0.0F, 359F, 1, 0.5F, 1, 0.5F);
        animation_rotate.setRepeatCount(-1);
        animation_rotate.setDuration(1000L);
        animation_rotate.setInterpolator(new LinearInterpolator());
        refresh_turn_img = new ImageView(context);
        refresh_turn_img.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-2, -2));
        refresh_turn_img.setImageResource(biz.AR.drawable.refresh_turn);
        refresh_logo_img = new ImageView(context);
        refresh_logo_img.setLayoutParams(new android.widget.FrameLayout.LayoutParams(-2, -2));
        refresh_logo_img.setImageResource(biz.AR.drawable.refresh_logo);
        addView(refresh_logo_img);
        addView(refresh_turn_img);
    }

    private void start()
    {
        refresh_turn_img.startAnimation(animation_rotate);
    }

    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        start();
    }

    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stop();
    }

    public void stop()
    {
        refresh_turn_img.clearAnimation();
    }

    private RotateAnimation animation_rotate;
    private ImageView refresh_logo_img;
    private ImageView refresh_turn_img;
}
