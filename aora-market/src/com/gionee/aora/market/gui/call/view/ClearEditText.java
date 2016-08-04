// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call.view;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

public class ClearEditText extends EditText implements android.view.View.OnFocusChangeListener, TextWatcher {
    public static Animation shakeAnimation(int i) {
        TranslateAnimation translateanimation = new TranslateAnimation(0.0F, 10F, 0.0F, 0.0F);
        translateanimation.setInterpolator(new CycleInterpolator(i));
        translateanimation.setDuration(1000L);
        return translateanimation;
    }
    private Drawable mClearDrawable;
    public ClearEditText(Context context) {
        this(context, null);
    }
    public ClearEditText(Context context, AttributeSet attributeset) {
        this(context, attributeset, 0x101006e);
    }
    public ClearEditText(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        init();
    }
    @Override
    public void afterTextChanged(Editable editable) {}
    @Override
    public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {}
    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) mClearDrawable = getResources().getDrawable(biz.AR.drawable.contacts_del);
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }
    @Override
    public void onFocusChange(View view, boolean flag) {
        if (flag) {
            int i = getText().length();
            boolean flag1 = false;
            if (i > 0) flag1 = true;
            setClearIconVisible(flag1);
            return;
        } else {
            setClearIconVisible(false);
            return;
        }
    }
    @Override
    public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
        boolean flag;
        if (charsequence.length() > 0) flag = true;
        else flag = false;
        setClearIconVisible(flag);
    }
    @Override
    public boolean onTouchEvent(MotionEvent motionevent) {
        int i = 1;
        if (getCompoundDrawables()[2] != null && motionevent.getAction() == i) {
            if (motionevent.getX() <= getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth() || motionevent.getX() >= getWidth() - getPaddingRight()) i = 0;
            if (i != 0) setText("");
        }
        return super.onTouchEvent(motionevent);
    }
    protected void setClearIconVisible(boolean flag) {
        Drawable drawable;
        if (flag) drawable = mClearDrawable;
        else drawable = null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], drawable, getCompoundDrawables()[3]);
    }
    public void setShakeAnimation() {
        setAnimation(shakeAnimation(5));
    }
}
