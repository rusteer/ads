// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.*;

public class WXEntryActivity extends Activity
    implements IWXAPIEventHandler
{

    public WXEntryActivity()
    {
    }

    protected void onCreate(Bundle bundle)
    {
        api = WXAPIFactory.createWXAPI(this, "wxf1d0430e137a7ebe", false);
        api.handleIntent(getIntent(), this);
        super.onCreate(bundle);
    }

    public void onReq(BaseReq basereq)
    {
    }

    public void onResp(BaseResp baseresp)
    {
        if(baseresp.errCode == 0)
            Toast.makeText(this, "\u5FAE\u4FE1\u5206\u4EAB\u6210\u529F\uFF01\uFF01\uFF01", 0).show();
        finish();
    }

    private IWXAPI api;
}
