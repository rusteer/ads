// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.TextView;

public class TestFragment extends Fragment
{

    public TestFragment()
    {
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle)
    {
        contentView = new TextView(getActivity());
        contentView.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -1));
        contentView.setGravity(17);
        contentView.setText("\u6807\u7B7E");
        return contentView;
    }

    TextView contentView;
}
