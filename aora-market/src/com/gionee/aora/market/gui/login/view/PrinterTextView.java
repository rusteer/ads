// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.login.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class PrinterTextView extends TextView
{
    public static interface OnTypeViewListener
    {

        public abstract void onTypeOver();

        public abstract void onTypeStart();
    }

    class TypeTimerTask extends TimerTask
    {

        public void run()
        {
            post(new Runnable() {

                public void run()
                {
                    if(getText().toString().length() < mShowTextString.length())
                    {
                        setText(mShowTextString.substring(0, 1 + getText().toString().length()));
                        startTypeTimer();
                    } else
                    {
                        stopTypeTimer();
                        if(mOnTypeViewListener != null)
                        {
                            mOnTypeViewListener.onTypeOver();
                            return;
                        }
                    }
                }

                
            }
);
        }

         

        TypeTimerTask()
        {
            super();
        }
    }


    public PrinterTextView(Context context)
    {
        super(context);
        mShowTextString = null;
        mTypeTimer = null;
        mOnTypeViewListener = null;
        mTypeTimeDelay = 80;
    }

    public PrinterTextView(Context context, AttributeSet attributeset)
    {
        super(context, attributeset);
        mShowTextString = null;
        mTypeTimer = null;
        mOnTypeViewListener = null;
        mTypeTimeDelay = 80;
    }

    public PrinterTextView(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
        mShowTextString = null;
        mTypeTimer = null;
        mOnTypeViewListener = null;
        mTypeTimeDelay = 80;
    }

    private void startTypeTimer()
    {
        stopTypeTimer();
        mTypeTimer = new Timer();
        mTypeTimer.schedule(new TypeTimerTask(), mTypeTimeDelay);
    }

    private void stopTypeTimer()
    {
        if(mTypeTimer != null)
        {
            mTypeTimer.cancel();
            mTypeTimer = null;
        }
    }

    public void setOnTypeViewListener(OnTypeViewListener ontypeviewlistener)
    {
        mOnTypeViewListener = ontypeviewlistener;
    }

    public void start(final String textString, final int typeTimeDelay)
    {
        if(TextUtils.isEmpty(textString) || typeTimeDelay < 0)
        {
            return;
        } else
        {
            post(new Runnable() {

                public void run()
                {
                    mShowTextString = textString;
                    mTypeTimeDelay = typeTimeDelay;
                    setText("");
                    startTypeTimer();
                    if(mOnTypeViewListener != null)
                        mOnTypeViewListener.onTypeStart();
                }

                
            }
);
            return;
        }
    }

    public void stop()
    {
        stopTypeTimer();
    }

    private static final int TYPE_TIME_DELAY = 80;
    private OnTypeViewListener mOnTypeViewListener;
    private String mShowTextString;
    private int mTypeTimeDelay;
    private Timer mTypeTimer;



/*
    static String access$002(PrinterTextView printertextview, String s)
    {
        printertextview.mShowTextString = s;
        return s;
    }

*/


/*
    static int access$102(PrinterTextView printertextview, int i)
    {
        printertextview.mTypeTimeDelay = i;
        return i;
    }

*/



}
