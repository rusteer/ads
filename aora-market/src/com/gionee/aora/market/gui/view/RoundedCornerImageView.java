// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedCornerImageView extends ImageView
{

    public RoundedCornerImageView(Context context)
    {
        super(context);
        init(context);
    }

    public RoundedCornerImageView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        init(context);
    }

    public RoundedCornerImageView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        init(context);
    }

    private void init(Context context)
    {
        setBackgroundColor(0);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Math.max(canvas.getWidth() / 2, canvas.getHeight() / 2);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xff000000);
        paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_OVER));
        canvas.drawRoundRect(new RectF(0.0F, 0.0F, getWidth(), getHeight()), 50F, 50F, paint);
    }

    Bitmap b;
}
