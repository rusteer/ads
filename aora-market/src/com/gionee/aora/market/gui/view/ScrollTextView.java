// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ScrollTextView extends TextView
{

    public ScrollTextView(Context context)
    {
        super(context);
    }

    public ScrollTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
    }

    public ScrollTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public boolean isFocused()
    {
        return true;
    }
}
