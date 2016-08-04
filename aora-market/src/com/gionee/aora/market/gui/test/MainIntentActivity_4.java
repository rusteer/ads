// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.test;

import android.os.Bundle;
import android.widget.TextView;

// Referenced classes of package com.gionee.aora.market.gui.test:
//            MainBaseActivity

public class MainIntentActivity_4 extends MainBaseActivity
{

    public MainIntentActivity_4()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.test_activity);
        content = (TextView)findViewById(biz.AR.id.content);
        setText("\u754C\u97624");
    }

    protected void setText(String s)
    {
        content.setText(s);
    }

    protected TextView content;
}
