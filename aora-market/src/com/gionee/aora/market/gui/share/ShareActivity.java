// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.share;

import android.app.Activity;
import android.content.*;
import android.content.pm.*;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.util.*;

// Referenced classes of package com.gionee.aora.market.gui.share:
//            ShareAdapter

public class ShareActivity extends Activity
{

    public ShareActivity()
    {
        animatTime = 300L;
        shareContent = "";
        shareUrl = "";
        shareDec = "";
        finishRunnable = null;
    }

    private void onClose()
    {
        final View dialog = findViewById(biz.AR.id.share_lay);
        if(finishRunnable == null)
        {
            dialog.clearAnimation();
            TranslateAnimation translateanimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 0.0F, 1, 1.0F);
            translateanimation.setDuration(animatTime);
            translateanimation.setInterpolator(this, 0x10a0006);
            translateanimation.setFillAfter(true);
            dialog.startAnimation(translateanimation);
            finishRunnable = new Runnable() {

                public void run()
                {
                    dialog.removeCallbacks(finishRunnable);
                    finishRunnable = null;
                    finish();
                }

                
            }
;
            dialog.postDelayed(finishRunnable, animatTime);
        }
    }

    private void onOpen()
    {
        View view = findViewById(biz.AR.id.share_lay);
        TranslateAnimation translateanimation = new TranslateAnimation(1, 0.0F, 1, 0.0F, 1, 1.0F, 1, 0.0F);
        translateanimation.setDuration(animatTime);
        translateanimation.setInterpolator(this, 0x10a0006);
        view.startAnimation(translateanimation);
    }

    public static void share(Context context, String s, String s1, String s2)
    {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra("SHARE_CONTENT", s);
        intent.putExtra("SHARE_URL", s1);
        intent.putExtra("SHARE_DEC", s2);
        context.startActivity(intent);
    }

    private void wechatShare(int i)
    {
        WXWebpageObject wxwebpageobject = new WXWebpageObject();
        wxwebpageobject.webpageUrl = shareUrl;
        WXMediaMessage wxmediamessage = new WXMediaMessage(wxwebpageobject);
        com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req req;
        int j;
        if(i == 0)
            wxmediamessage.title = "\u6613\u7528\u6C47\u5206\u4EAB";
        else
            wxmediamessage.title = shareDec;
        wxmediamessage.description = shareDec;
        req = new com.tencent.mm.sdk.modelmsg.SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxmediamessage;
        if(i == 0)
            j = 0;
        else
            j = 1;
        req.scene = j;
        wxApi.sendReq(req);
    }

    public List getShareApps(Context context)
    {
        ArrayList arraylist = new ArrayList();
        Intent intent = new Intent("android.intent.action.SEND", null);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("*/*");
        Iterator iterator = context.getPackageManager().queryIntentActivities(intent, 0).iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            ResolveInfo resolveinfo = (ResolveInfo)iterator.next();
            if(resolveinfo.activityInfo.packageName.equals("com.android.mms") && !resolveinfo.activityInfo.name.equals("com.android.mms.ui.ShareVCardViaMMSActivity"))
                arraylist.add(resolveinfo);
            else
            if(resolveinfo.activityInfo.packageName.equals("com.tencent.mm"))
                arraylist.add(resolveinfo);
            else
            if(resolveinfo.activityInfo.packageName.equals("com.tencent.mobileqq") && resolveinfo.activityInfo.name.equals("com.tencent.mobileqq.activity.JumpActivity"))
                arraylist.add(resolveinfo);
            else
            if(resolveinfo.activityInfo.packageName.equals("com.qzone"))
                arraylist.add(resolveinfo);
            else
            if(resolveinfo.activityInfo.packageName.equals("com.sina.weibo"))
                arraylist.add(resolveinfo);
        } while(true);
        return arraylist;
    }

    public void onAttachedToWindow()
    {
        onAttachedToWindow();
        onOpen();
    }

    public void onBackPressed()
    {
        onClose();
    }

    protected void onCreate(Bundle bundle)
    {
        onCreate(bundle);
        requestWindowFeature(1);
        setTheme(biz.AR.style.Float_Dialog);
        setContentView(biz.AR.layout.share_layout);
        wxApi = WXAPIFactory.createWXAPI(this, "wxf1d0430e137a7ebe");
        wxApi.registerApp("wxf1d0430e137a7ebe");
        shareContent = getIntent().getStringExtra("SHARE_CONTENT");
        shareUrl = getIntent().getStringExtra("SHARE_URL");
        shareDec = getIntent().getStringExtra("SHARE_DEC");
        sharrgv = (GridView)findViewById(biz.AR.id.share_gv);
        rinfos = getShareApps(this);
        adapter = new ShareAdapter(rinfos, this);
        sharrgv.setAdapter(adapter);
        sharrgv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView adapterview, View view, int i, long l)
            {
                ResolveInfo resolveinfo = (ResolveInfo)rinfos.get(i);
                if(resolveinfo.activityInfo.name.equals("com.tencent.mm.ui.tools.ShareImgUI"))
                    wechatShare(0);
                else
                if(resolveinfo.activityInfo.name.equals("com.tencent.mm.ui.tools.ShareToTimeLineUI"))
                    wechatShare(1);
                else
                if(resolveinfo.activityInfo.name.equals("com.tencent.mobileqq.activity.JumpActivity"))
                {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setComponent(new ComponentName(resolveinfo.activityInfo.packageName, resolveinfo.activityInfo.name));
                    intent.setType("text/plain");
                    intent.putExtra("sms_body", shareContent);
                    intent.putExtra("android.intent.extra.TEXT", shareContent);
                    startActivity(intent);
                } else
                {
                    Intent intent1 = new Intent("android.intent.action.SEND");
                    intent1.setComponent(new ComponentName(resolveinfo.activityInfo.packageName, resolveinfo.activityInfo.name));
                    intent1.setType("text/plain");
                    intent1.putExtra("sms_body", shareContent);
                    intent1.putExtra("android.intent.extra.TEXT", shareContent);
                    intent1.setFlags(0x10000000);
                    startActivity(intent1);
                }
                finish();
            }

            
        }
);
    }

    public void onQuit(View view)
    {
        finish();
    }

    public static final String WX_APPID = "wxf1d0430e137a7ebe";
    private ShareAdapter adapter;
    private long animatTime;
    Runnable finishRunnable;
    private List rinfos;
    private String shareContent;
    private String shareDec;
    private String shareUrl;
    private GridView sharrgv;
    private IWXAPI wxApi;



}
