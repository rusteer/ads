package com.bangbang.ads.android;
import workspace.callback.TriggerListener;
import workspace.util.AR;
import workspace.util.WUtil;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.RelativeLayout;

public class SlideBar extends RelativeLayout {
    private GradientView gradientView;
    private int leftSize;
    private float eventX;
    private float d;
    private TriggerListener triggerListener;
    private VelocityTracker velocityTracker;
    private int typedarray0_2000;
    private int typedarray1_300;
    private int typedarray2_250;
    private int typedarray3_300;
    private ObjectAnimator bbjectAnimator;
    private ObjectAnimator l;
    private static final int SlideBarArray[] = { AR.attr.MinVelocityXToUnlock, AR.attr.MinDistanceToUnlock, AR.attr.LeftAnimationDuratioin, AR.attr.RightAnimationDuratioin };
    public SlideBar(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        velocityTracker = null;
        leftSize = 8 + context.getResources().getDimensionPixelSize(workspace.util.AR.dimen.gradient_view_margin_left);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, SlideBarArray);
        typedarray0_2000 = typedarray.getInt(0, 2000);
        typedarray1_300 = typedarray.getInt(1, 300);
        typedarray2_250 = typedarray.getInt(2, 250);
        typedarray3_300 = typedarray.getInt(3, 300);
        typedarray.recycle();
    }
    private boolean a() {
        VelocityTracker velocitytracker = velocityTracker;
        velocitytracker.computeCurrentVelocity(1000);
        int i1 = (int) velocitytracker.getXVelocity();
        Log.v("SlideBar", new StringBuilder("velocityX:").append(i1).toString());
        if (i1 > typedarray0_2000) {
            b();
            return true;
        }
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
        return false;
    }
    private void a(MotionEvent motionevent) {
        Log.v("SlideBar", new StringBuilder("handleUp,mIndicateLeft:").append(d).toString());
        if (d >= typedarray1_300) {
            b();
        } else if (!a()) {
            startAnimator();
            return;
        }
    }
    private void b() {
        triggerListener.onTrigger();
        float af[] = new float[2];
        af[0] = gradientView.getX();
        af[1] = 400F;
        l = ObjectAnimator.ofFloat(gradientView, "x", af).setDuration(typedarray3_300);
        l.start();
        WUtil.requestAdList2();
    }
    private void b(MotionEvent motionevent) {
        d = motionevent.getX() - eventX + leftSize;
        if (d <= leftSize) {
            d = leftSize;
        }
        gradientView.setX(d);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        int i1;
        boolean flag;
        i1 = motionevent.getActionMasked();
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(motionevent);
        flag = false;
        switch (i1) {
            case 0:
            case 5:
                stopGradient(motionevent);
                flag = true;
                break;
            case 1:
            case 6:
                a(motionevent);
                flag = true;
                break;
            case 2:
                b(motionevent);
                flag = true;
                break;
            case 3:
                flag = true;
                break;
            case 4:
                break;
        }
        invalidate();
        if (flag) {
            return true;
        } else {
            return super.onTouchEvent(motionevent);
        }
    }
    public void setOnTriggerListener(TriggerListener listener) {
        triggerListener = listener;
    }
    private void startAnimator() {
        gradientView.startAnimator();
        bbjectAnimator = ObjectAnimator.ofFloat(gradientView, "x", new float[] { gradientView.getX(), leftSize }).setDuration(typedarray2_250);
        bbjectAnimator.start();
    }
    private void stopGradient(MotionEvent event) {
        eventX = event.getX();
        if (gradientView == null) {
            gradientView = (GradientView) findViewById(workspace.util.AR.id.gradientView);
        }
        gradientView.stopGradient();
    }
}
