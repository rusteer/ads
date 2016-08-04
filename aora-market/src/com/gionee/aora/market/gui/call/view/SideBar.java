// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call.view;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class SideBar extends View {
    public static interface OnTouchingLetterChangedListener {
        public abstract void onTouchingLetterChanged(String s);
    }
    public static String b[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#" };
    private int choose;
    private TextView mTextDialog;
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    private Paint paint;
    public SideBar(Context context) {
        super(context);
        choose = -1;
        paint = new Paint();
    }
    public SideBar(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        choose = -1;
        paint = new Paint();
    }
    public SideBar(Context context, AttributeSet attributeset, int i) {
        super(context, attributeset, i);
        choose = -1;
        paint = new Paint();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent motionevent) {
        int i;
        int j;
        OnTouchingLetterChangedListener ontouchingletterchangedlistener;
        int k;
        i = motionevent.getAction();
        float f = motionevent.getY();
        j = choose;
        ontouchingletterchangedlistener = onTouchingLetterChangedListener;
        k = (int) (f / getHeight() * b.length);
        switch (i) {
            case 1://L2_L2:
                setBackgroundDrawable(new ColorDrawable(0));
                choose = -1;
                invalidate();
                if (mTextDialog != null) mTextDialog.setVisibility(4);
                break;
            default://_L1:
                setBackgroundResource(biz.AR.drawable.sidebar_background);
                if (j != k && k >= 0 && k < b.length) {
                    if (ontouchingletterchangedlistener != null) ontouchingletterchangedlistener.onTouchingLetterChanged(b[k]);
                    if (mTextDialog != null) {
                        mTextDialog.setText(b[k]);
                        mTextDialog.setVisibility(0);
                    }
                    choose = k;
                    invalidate();
                }
        }
        return true;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = getHeight();
        int j = getWidth();
        int k = i / b.length;
        for (int l = 0; l < b.length; l++) {
            paint.setColor(Color.rgb(33, 65, 98));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20F);
            if (l == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float f = j / 2 - paint.measureText(b[l]) / 2.0F;
            float f1 = k + k * l;
            canvas.drawText(b[l], f, f1, paint);
            paint.reset();
        }
    }
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener ontouchingletterchangedlistener) {
        onTouchingLetterChangedListener = ontouchingletterchangedlistener;
    }
    public void setTextView(TextView textview) {
        mTextDialog = textview;
    }
}
