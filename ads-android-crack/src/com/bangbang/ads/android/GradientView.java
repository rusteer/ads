package com.bangbang.ads.android;
import workspace.util.AR;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GradientView extends View {
    private int color1;
    private int color2;
    private float x1 = 0.0F;
    private Shader shader;
    private int dimension;
    private ValueAnimator animator;
    private int x;
    private int y;
    private String text;
    private Paint paint;
    private float ascent;
    private Drawable iconResource;
    private int minimumHeight;
    private static final int GradientViewArray[] = { AR.attr.StringToShow, AR.attr.TextSize, AR.attr.TextColor, AR.attr.SlideColor };
    public GradientView(Context context) {
        super(context);
    }
    public GradientView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        TypedArray typedarray = context.obtainStyledAttributes(attributeset, GradientViewArray);
        text = typedarray.getString(0);
        dimension = (int) typedarray.getDimension(1, 40F);
        color1 = typedarray.getColor(2, 0xff888888);
        color2 = typedarray.getColor(3, -1);
        iconResource = context.getResources().getDrawable(workspace.util.AR.drawable.slide_icon);
        minimumHeight = iconResource.getMinimumHeight();
        typedarray.recycle();
        animator = ValueAnimator.ofFloat(new float[] { 90F, 600F });
        animator.setDuration(1800L);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueanimator) {
                x1 = Float.parseFloat(valueanimator.getAnimatedValue().toString());
                shader = new LinearGradient(x1 - 300F, 100F, x1, 100F, new int[] { color1, color1, color1, color2, color2, color1, color1, color1 }, null,
                        android.graphics.Shader.TileMode.CLAMP);
                postInvalidate();
            }
        });
        animator.setRepeatCount(-1);
        animator.start();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color2);
        paint.setTextSize(dimension);
        paint.setTextAlign(android.graphics.Paint.Align.CENTER);
        ascent = paint.ascent();
        setFocusable(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        paint.setShader(shader);
        canvas.drawBitmap(((BitmapDrawable) iconResource).getBitmap(), 20F, 2 + y / 2 - minimumHeight / 2, null);
        canvas.drawText(text, x / 2, y / 2 - ascent / 2.0F - 2.0F, paint);
    }
    @Override
    protected void onMeasure(int i1, int j1) {
        super.onMeasure(i1, j1);
        x = android.view.View.MeasureSpec.getSize(i1);
        y = android.view.View.MeasureSpec.getSize(j1);
    }
    public void startAnimator() {
        animator.start();
    }
    public void stopGradient() {
        Log.w("GradientView", "stopGradient");
        animator.cancel();
        shader = new LinearGradient(0.0F, 100F, x1, 100F, new int[] { color2, color2 }, null, android.graphics.Shader.TileMode.CLAMP);
        invalidate();
    }
}
