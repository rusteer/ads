// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.aora.base.util.DLog;

public class LoadingNewView2 extends ImageView
{

    public LoadingNewView2(Context context)
    {
        super(context);
        init(context);
    }

    public LoadingNewView2(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init(context);
    }

    public LoadingNewView2(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init(context);
    }

    private void init(Context context)
    {
        setBackgroundResource(biz.AR.drawable.loading_view_shape);
        animation_rotate = new RotateAnimation(-45F, 314F, 1, 0.5F, 1, 0.5F);
        animation_rotate.setRepeatCount(-1);
        animation_rotate.setDuration(2000L);
        animation_rotate.setInterpolator(new LinearInterpolator());
        setLayoutParams(new android.widget.RelativeLayout.LayoutParams(-2, -2));
    }

    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        startAnimation(animation_rotate);
    }

    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        DLog.i("lilijun", "LoadingNewView2\u505C\u6B62\u52A8\u753B\uFF01\uFF01\uFF01");
        clearAnimation();
    }

    public void setVisibilyView(boolean flag)
    {
        if(flag)
        {
            setVisibility(0);
            if(getAnimation() == null)
                startAnimation(animation_rotate);
            return;
        } else
        {
            setVisibility(8);
            clearAnimation();
            return;
        }
    }

    private RotateAnimation animation_rotate;
}
