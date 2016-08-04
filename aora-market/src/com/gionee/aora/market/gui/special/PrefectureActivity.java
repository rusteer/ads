// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.os.Bundle;
import android.widget.RelativeLayout;
import com.gionee.aora.market.gui.main.MarketBaseFragmentActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;
import java.util.ArrayList;
import java.util.List;

public class PrefectureActivity extends MarketBaseFragmentActivity
{

    public PrefectureActivity()
    {
    }

    public List getItems()
    {
        return new ArrayList();
    }

    public String[] getTitleText()
    {
        String as[] = new String[2];
        as[0] = getString(biz.AR.string.topic);
        as[1] = getString(biz.AR.string.exercise);
        return as;
    }

    public void onBackPressed()
    {
        finish();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setActivityGroup(true);
        titleBar = new ChildTitleView(this);
        headerViewLayout.addView(titleBar);
        titleBar.setTitle(getString(biz.AR.string.prefeture));
    }

    private ChildTitleView titleBar;
}
