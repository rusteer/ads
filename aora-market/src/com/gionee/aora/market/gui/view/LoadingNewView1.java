// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aora.base.util.DLog;
import com.nineoldandroids.animation.*;
import com.nineoldandroids.view.ViewHelper;

public class LoadingNewView1 extends RelativeLayout
{
    private static class ViewWrapper
    {

        public void clearAnimations()
        {
            mTarget.clearAnimation();
        }

        public int getHeight()
        {
            return mTarget.getLayoutParams().width;
        }

        public float getRoate()
        {
            return ViewHelper.getRotation(mTarget);
        }

        public float getScaleX()
        {
            return ViewHelper.getScaleX(mTarget);
        }

        public float getScaleY()
        {
            return ViewHelper.getScaleY(mTarget);
        }

        public float getTranslationX()
        {
            return ViewHelper.getTranslationX(mTarget);
        }

        public float getTranslationY()
        {
            return ViewHelper.getTranslationY(mTarget);
        }

        public int getWidth()
        {
            return mTarget.getLayoutParams().width;
        }

        public void setHeight(int i)
        {
            mTarget.getLayoutParams().height = i;
            mTarget.requestLayout();
        }

        public void setRoate(float f)
        {
            ViewHelper.setRotation(mTarget, f);
        }

        public void setScaleX(float f)
        {
            ViewHelper.setScaleX(mTarget, f);
        }

        public void setScaleY(float f)
        {
            ViewHelper.setScaleY(mTarget, f);
        }

        public void setTranslationX(float f)
        {
            ViewHelper.setTranslationX(mTarget, f);
        }

        public void setTranslationY(float f)
        {
            ViewHelper.setTranslationY(mTarget, f);
        }

        public void setWidth(int i)
        {
            mTarget.getLayoutParams().width = i;
            mTarget.requestLayout();
        }

        public void setmTarget(View view)
        {
            mTarget = view;
        }

        private View mTarget;

        public ViewWrapper(View view)
        {
            mTarget = view;
        }
    }


    public LoadingNewView1(Context context)
    {
        super(context);
        count = 0;
        isGo = true;
        isStart = false;
        scaleValue = 0.2F;
        duration = 800L;
        isLoading = true;
        initRoate = -45F;
        initScaleX = 1.0F;
        initScaleY = 1.0F;
        initTranslationX = 0.0F;
        initTranslationY = 0.0F;
        init(context);
    }

    public LoadingNewView1(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        count = 0;
        isGo = true;
        isStart = false;
        scaleValue = 0.2F;
        duration = 800L;
        isLoading = true;
        initRoate = -45F;
        initScaleX = 1.0F;
        initScaleY = 1.0F;
        initTranslationX = 0.0F;
        initTranslationY = 0.0F;
        init(context);
    }

    public LoadingNewView1(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        count = 0;
        isGo = true;
        isStart = false;
        scaleValue = 0.2F;
        duration = 800L;
        isLoading = true;
        initRoate = -45F;
        initScaleX = 1.0F;
        initScaleY = 1.0F;
        initTranslationX = 0.0F;
        initTranslationY = 0.0F;
        init(context);
    }

    private void init(Context context)
    {
        View view = View.inflate(context, biz.AR.layout.loading_big_view, null);
        roateView = (TextView)view.findViewById(biz.AR.id.loading_big_view_img);
        addView(view);
        wrapper = new ViewWrapper(roateView);
    }

    private void startAnimationBack()
    {
        isGo = false;
        count = -1 + count;
        ViewWrapper viewwrapper = wrapper;
        float af[] = new float[2];
        af[0] = 90 * count;
        af[1] = -90 + 90 * count;
        roateAnimator = ObjectAnimator.ofFloat(viewwrapper, "roate", af).setDuration(duration);
        roateAnimator.addListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {

            public void onAnimationCancel(Animator animator)
            {
            }

            public void onAnimationEnd(Animator animator)
            {
label0:
                {
                    if(isStart)
                    {
                        if(count <= 0)
                            break label0;
                        startAnimationBack();
                    }
                    return;
                }
                startAnimationGo();
            }

            public void onAnimationRepeat(Animator animator)
            {
            }

            public void onAnimationStart(Animator animator)
            {
            }

             
        }
);
        ViewWrapper viewwrapper1 = wrapper;
        float af1[] = new float[2];
        af1[0] = wrapper.getScaleX();
        af1[1] = wrapper.getScaleX() + scaleValue;
        scaleWidthAnimator = ObjectAnimator.ofFloat(viewwrapper1, "scaleX", af1).setDuration(duration);
        ViewWrapper viewwrapper2 = wrapper;
        float af2[] = new float[2];
        af2[0] = wrapper.getScaleY();
        af2[1] = wrapper.getScaleY() + scaleValue;
        scaleHeightAnimator = ObjectAnimator.ofFloat(viewwrapper2, "scaleY", af2).setDuration(duration);
        ViewWrapper viewwrapper3 = wrapper;
        float af3[] = new float[2];
        af3[0] = wrapper.getTranslationX();
        af3[1] = wrapper.getTranslationX() - (float)wrapper.getWidth() * wrapper.getScaleX();
        transXAnimator = ObjectAnimator.ofFloat(viewwrapper3, "translationX", af3).setDuration(duration);
        ViewWrapper viewwrapper4 = wrapper;
        float af4[] = new float[2];
        af4[0] = wrapper.getTranslationY();
        af4[1] = wrapper.getTranslationY() - (float)(wrapper.getHeight() / 4) * wrapper.getScaleY();
        transYAnimator = ObjectAnimator.ofFloat(viewwrapper4, "translationY", af4).setDuration(duration);
        animatorSet = new AnimatorSet();
        AnimatorSet animatorset = animatorSet;
        Animator aanimator[] = new Animator[5];
        aanimator[0] = roateAnimator;
        aanimator[1] = scaleWidthAnimator;
        aanimator[2] = scaleHeightAnimator;
        aanimator[3] = transXAnimator;
        aanimator[4] = transYAnimator;
        animatorset.playTogether(aanimator);
        animatorSet.start();
    }

    private void startAnimationGo()
    {
        isGo = true;
        count = 1 + count;
        ViewWrapper viewwrapper = wrapper;
        float af[] = new float[2];
        af[0] = -90 + 90 * count;
        af[1] = 90 * count;
        roateAnimator = ObjectAnimator.ofFloat(viewwrapper, "roate", af).setDuration(duration);
        roateAnimator.addListener(new com.nineoldandroids.animation.Animator.AnimatorListener() {

            public void onAnimationCancel(Animator animator)
            {
            }

            public void onAnimationEnd(Animator animator)
            {
label0:
                {
                    if(isStart)
                    {
                        if(count >= 4)
                            break label0;
                        startAnimationGo();
                    }
                    return;
                }
                startAnimationBack();
            }

            public void onAnimationRepeat(Animator animator)
            {
            }

            public void onAnimationStart(Animator animator)
            {
            }

             
        }
);
        ViewWrapper viewwrapper1 = wrapper;
        float af1[] = new float[2];
        af1[0] = wrapper.getScaleX();
        af1[1] = wrapper.getScaleX() - scaleValue;
        scaleWidthAnimator = ObjectAnimator.ofFloat(viewwrapper1, "scaleX", af1).setDuration(duration);
        ViewWrapper viewwrapper2 = wrapper;
        float af2[] = new float[2];
        af2[0] = wrapper.getScaleY();
        af2[1] = wrapper.getScaleY() - scaleValue;
        scaleHeightAnimator = ObjectAnimator.ofFloat(viewwrapper2, "scaleY", af2).setDuration(duration);
        ViewWrapper viewwrapper3 = wrapper;
        float af3[] = new float[2];
        af3[0] = wrapper.getTranslationX();
        af3[1] = wrapper.getTranslationX() + (float)wrapper.getWidth() * (wrapper.getScaleX() - scaleValue);
        transXAnimator = ObjectAnimator.ofFloat(viewwrapper3, "translationX", af3).setDuration(duration);
        ViewWrapper viewwrapper4 = wrapper;
        float af4[] = new float[2];
        af4[0] = wrapper.getTranslationY();
        af4[1] = wrapper.getTranslationY() + (float)(wrapper.getHeight() / 4) * (wrapper.getScaleY() - scaleValue);
        transYAnimator = ObjectAnimator.ofFloat(viewwrapper4, "translationY", af4).setDuration(duration);
        animatorSet = new AnimatorSet();
        AnimatorSet animatorset = animatorSet;
        Animator aanimator[] = new Animator[5];
        aanimator[0] = roateAnimator;
        aanimator[1] = scaleWidthAnimator;
        aanimator[2] = scaleHeightAnimator;
        aanimator[3] = transXAnimator;
        aanimator[4] = transYAnimator;
        animatorset.playTogether(aanimator);
        animatorSet.start();
    }

    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if(isLoading)
            start();
    }

    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        stopAllAnimations();
    }

    public void setLoading(boolean flag)
    {
        isLoading = flag;
    }

    public void start()
    {
label0:
        {
            if(!isStart)
            {
                isStart = true;
                ViewHelper.setRotation(roateView, initRoate);
                ViewHelper.setScaleX(roateView, initScaleX);
                ViewHelper.setScaleY(roateView, initScaleY);
                ViewHelper.setTranslationX(roateView, initTranslationX);
                ViewHelper.setTranslationY(roateView, initTranslationY);
                if(!isGo || count >= 4)
                    break label0;
                startAnimationGo();
            }
            return;
        }
        startAnimationBack();
    }

    public void stopAllAnimations()
    {
        DLog.i("lilijun", "\u505C\u6B62\u6240\u6709\u52A8\u753B\uFF0C\u5E76\u6062\u590D\u6210\u521D\u59CB\u503C");
        if(roateAnimator != null)
            roateAnimator.removeAllListeners();
        if(animatorSet != null)
            animatorSet.removeAllListeners();
        isStart = false;
        count = 0;
        isGo = true;
        isStart = false;
    }

    private AnimatorSet animatorSet;
    private int count;
    private long duration;
    private float initRoate;
    private float initScaleX;
    private float initScaleY;
    private float initTranslationX;
    private float initTranslationY;
    private boolean isGo;
    private boolean isLoading;
    private boolean isStart;
    private ObjectAnimator roateAnimator;
    private TextView roateView;
    private ObjectAnimator scaleHeightAnimator;
    private float scaleValue;
    private ObjectAnimator scaleWidthAnimator;
    private ObjectAnimator transXAnimator;
    private ObjectAnimator transYAnimator;
    private ViewWrapper wrapper;




}
