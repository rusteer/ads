// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.test;

import android.app.Activity;

public class MainBaseActivity extends Activity
{

    public MainBaseActivity()
    {
    }

    public void onBackPressed()
    {
        getParent().onBackPressed();
    }
}
