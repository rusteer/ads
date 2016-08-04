// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.login.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.*;
import android.widget.Scroller;

public class VerticalSlidingView extends ViewGroup
{
    public static interface OnPageScrollListener
    {

        public abstract void onPageChanged(int i);
    }


    public VerticalSlidingView(Context context)
    {
        super(context);
        mCurrentPage = -1;
        mIsScrolling = false;
        paramsInit(context);
    }

    public VerticalSlidingView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mCurrentPage = -1;
        mIsScrolling = false;
        paramsInit(context);
    }

    private void paramsInit(Context context)
    {
        mScroller = new Scroller(context);
    }

    public void computeScroll()
    {
        if( mScroller.computeScrollOffset()) {
            //L1_L1:
            int j = mScroller.getCurrY();
            if(j > 0 && j + mEndYPosition > mTotalHeight) {
                //L3_L3:
                j = mTotalHeight - mEndYPosition;
            }else{
                //L4_L4:
                if(j < 0 && j + mEndYPosition < 0)
                    j = -mEndYPosition;
            }

    //_L8:
            scrollTo(0, j + mEndYPosition);
            mIsScrolling = true;
            postInvalidate();
        }else{
            //L2_L2:
            if( mIsScrolling) {
                //L5_L5:
                if(mPageScrollListener != null)
                {
                    int i = getScrollY() / getMeasuredHeight();
                    if(i != mCurrentPage)
                    {
                        mCurrentPage = i;
                        mPageScrollListener.onPageChanged(mCurrentPage);
                    }
                }
                mIsScrolling = false;
            } 

        }

        return;

       
    }

    protected void onLayout(boolean flag, int i, int j, int k, int l)
    {
        int i1 = 0;
        int j1 = getChildCount();
        int k1 = getMeasuredHeight();
        for(int l1 = 0; l1 < j1; l1++)
        {
            View view = getChildAt(l1);
            if(view.getVisibility() != 8)
            {
                view.layout(i, i1, k, i1 + k1);
                i1 += k1;
            }
        }

        mTotalHeight = k1 * (j1 - 1);
        mTolerance = k1 / 2;
    }

    protected void onMeasure(int i, int j)
    {
        super.onMeasure(i, j);
        int k = getChildCount();
        for(int l = 0; l < k; l++)
            measureChild(getChildAt(l), i, j);

    }

    public boolean onTouchEvent(MotionEvent motionevent)
    {
        if(mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.addMovement(motionevent);
        switch(motionevent.getAction()){
            case 0://L2_L2:
                if(!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = (int)motionevent.getY();
                mStartYPosition = getScrollY();
                break;
            case 1://L3_L3:
                mEndYPosition = getScrollY();
                int i = mEndYPosition - mStartYPosition;
                mVelocityTracker.computeCurrentVelocity(1000);
                int j = (int)mVelocityTracker.getYVelocity();
                mVelocityTracker.recycle();
                mVelocityTracker = null;
                if(Math.abs(j) >= 600 || Math.abs(i) > mTolerance)
                {
                    int k;
                    if(i > 0)
                        k = getMeasuredHeight() - i;
                    else
                    if(i < 0)
                        k = -(i + getMeasuredHeight());
                    else
                        k = 0;
                    mScroller.startScroll(0, 0, 0, k);
                } else
                {
                    mScroller.startScroll(0, 0, 0, -i);
                }
                postInvalidate();
                break;
            case 2://L4_L4:
                int l;
                int i1;
                int j1;
                l = (int)motionevent.getY();
                i1 = mLastY - l;
                j1 = getScrollY();
                if(!(i1 >= 0 || j1 + i1 >= 0)){
                    //L5_L5:
                    i1 = 0 - j1;
                }else{
                    //L6_L6:
                    if(i1 > 0 && j1 + i1 > mTotalHeight)
                        i1 = mTotalHeight - j1;
                }

                scrollBy(0, i1);
                mLastY = l;
                break;
        }
        
        return true;

        

        
    }

    public void setOnPageScrollListener(OnPageScrollListener onpagescrolllistener)
    {
        mPageScrollListener = onpagescrolllistener;
    }

    private int mCurrentPage;
    private int mEndYPosition;
    private boolean mIsScrolling;
    private int mLastY;
    private OnPageScrollListener mPageScrollListener;
    private Scroller mScroller;
    private int mStartYPosition;
    private int mTolerance;
    private int mTotalHeight;
    private VelocityTracker mVelocityTracker;
}
