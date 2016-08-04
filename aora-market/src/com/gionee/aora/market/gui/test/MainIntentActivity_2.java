// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.test;

import android.os.Bundle;
import com.gionee.aora.market.gui.main.MarketBaseFragmentActivity;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.test:
//            TestFragment

public class MainIntentActivity_2 extends MarketBaseFragmentActivity
{

    public MainIntentActivity_2()
    {
    }

    public List getItems()
    {
        ArrayList arraylist = new ArrayList();
        for(int i = 0; i < 5; i++)
            arraylist.add(new TestFragment());

        return arraylist;
    }

    public String[] getTitleText()
    {
        return (new String[] {
            "\u6807\u7B7E1", "\u6807\u7B7E2", "\u6807\u7B7E3", "\u6807\u7B7E4", "\u6807\u7B7E5"
        });
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
    }

    List views;
}
