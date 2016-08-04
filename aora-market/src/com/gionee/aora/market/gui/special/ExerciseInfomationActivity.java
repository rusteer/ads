// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import android.widget.ImageView;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.share.ShareActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.util.MarketAsyncTask;

public class ExerciseInfomationActivity extends MarketBaseActivity
{
    private class MyWebChromeClient extends WebChromeClient
    {

        public boolean onJsAlert(WebView webview, String s, String s1, JsResult jsresult)
        {
            return super.onJsAlert(webview, s, s1, jsresult);
        }


        private MyWebChromeClient()
        {
            super();
        }

    }

    private class MyWebViewClient extends WebViewClient
    {

        public void onPageFinished(WebView webview, String s)
        {
            super.onPageFinished(webview, s);
            DLog.i("lilijun", (new StringBuilder()).append("\u52A0\u8F7D\u7F51\u9875\u5B8C\u6210\uFF01\uFF01 url----->>>").append(s).toString());
            redirectUrl = s;
            if(startLoadUrl.equals(redirectUrl) && !isTwiceLoadStared && !isLoadFinished)
            {
                DLog.i("lilijun", "\u9690\u85CF\u52A0\u8F7D\u89C6\u56FE\uFF01\uFF01\uFF01");
                doLoadData(new Integer[0]);
            }
            isLoadFinished = true;
            UserInfo userinfo = UserStorage.getDefaultUserInfo(ExerciseInfomationActivity.this);
            String s1 = (new StringBuilder()).append(userinfo.getUSER_TYPE_FLAG()).append("|").append(userinfo.getUSER_NAME()).append("|").append(DataCollectUtil.getImei()).append("|").append(Build.MODEL).append("|").append(userinfo.getSURNAME()).toString();
            webView.loadUrl((new StringBuilder()).append("javascript:c_uinfo('").append(s1).append("')").toString());
        }

        public void onPageStarted(WebView webview, String s, Bitmap bitmap)
        {
            super.onPageStarted(webview, s, bitmap);
            DLog.i("lilijun", (new StringBuilder()).append("\u5F00\u59CB\u52A0\u8F7D\u7F51\u9875\uFF01url------>>>").append(s).toString());
            if(!"".equals(startLoadUrl))
                if(!startLoadUrl.equals(s) && !isLoadFinished)
                {
                    isTwiceLoadStared = true;
                } else
                {
                    isTwiceLoadStared = false;
                    isLoadFinished = false;
                }
            startLoadUrl = s;
        }

        public void onReceivedError(WebView webview, int i, String s, String s1)
        {
            super.onReceivedError(webview, i, s, s1);
            DLog.i("lilijun", "\u52A0\u8F7D\u51FA\u9519\uFF01\uFF01\uFF01");
        }

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            webview.loadUrl(s);
            return true;
        }

         

    }

    private class WebViewDownloadListener
        implements DownloadListener
    {

        public void onDownloadStart(String s, String s1, String s2, String s3, long l)
        {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(s));
            startActivity(intent);
        }

        

    }


    public ExerciseInfomationActivity()
    {
        datainfo = null;
        skipUrl = "";
        startLoadUrl = "";
        redirectUrl = "";
        isTwiceLoadStared = false;
        isLoadFinished = false;
    }

    protected void initCenterView()
    {
        DLog.i("lilijun", "ExerciseInfomationActivity,initCenterView()");
        vid = getIntent().getStringExtra("vid");
        skipUrl = getIntent().getStringExtra("skipUrl");
        DataCollectInfo datacollectinfo;
        String as[];
        ImageView imageview;
        if(getIntent().hasExtra("NAME"))
            name = getIntent().getStringExtra("NAME");
        else
            name = getString(biz.AR.string.exercise_infomation);
        datainfo = DataCollectManager.getCollectInfo(this);
        datainfo.setAction("9");
        datacollectinfo = datainfo;
        as = new String[2];
        as[0] = "vid";
        as[1] = vid;
        DataCollectManager.addRecord(datacollectinfo, as);
        setCenterView(biz.AR.layout.exercise_infomation_activity);
        webView = (WebView)findViewById(biz.AR.id.exercise_infomation_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        mWebViewDownloadListener = new WebViewDownloadListener();
        mWebViewClient = new MyWebViewClient();
        mWebChromeClient = new MyWebChromeClient();
        webView.setDownloadListener(mWebViewDownloadListener);
        webView.setWebViewClient(mWebViewClient);
        webView.setWebChromeClient(mWebChromeClient);
        webView.loadUrl(skipUrl.trim());
        setTitleBarViewVisibility(true);
        titleBarView.setTitle("\u8BDD\u9898");
        imageview = new ImageView(this);
        imageview.setImageResource(biz.AR.drawable.title_share);
        imageview.setPadding(0, 0, getResources().getDimensionPixelSize(biz.AR.dimen.dip10), 0);
        imageview.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                StringBuffer stringbuffer = new StringBuffer();
                stringbuffer.append(name);
                stringbuffer.append(skipUrl);
                ShareActivity.share(ExerciseInfomationActivity.this, stringbuffer.toString(), skipUrl, name);
            }

            
        }
);
        titleBarView.setRightView(imageview);
    }

    protected   boolean initData(Integer ainteger[])
    {
        return false;
    }

    public boolean onKeyDown(int i, KeyEvent keyevent)
    {
        if(webView.canGoBack() && keyevent.getKeyCode() == 4 && keyevent.getRepeatCount() == 0)
        {
            webView.goBack();
            return true;
        } else
        {
            return super.onKeyDown(i, keyevent);
        }
    }

    protected   void refreshView(boolean flag, Integer ainteger[])
    {
    }

    protected void tryAgain()
    {
        super.tryAgain();
        webView.loadUrl(skipUrl.trim());
    }

    private DataCollectInfo datainfo;
    private boolean isLoadFinished;
    private boolean isTwiceLoadStared;
    private MyWebChromeClient mWebChromeClient;
    private MyWebViewClient mWebViewClient;
    private WebViewDownloadListener mWebViewDownloadListener;
    private String name;
    private String redirectUrl;
    private String skipUrl;
    private String startLoadUrl;
    private String vid;
    private WebView webView;






/*
    static String access$502(ExerciseInfomationActivity exerciseinfomationactivity, String s)
    {
        exerciseinfomationactivity.startLoadUrl = s;
        return s;
    }

*/



/*
    static boolean access$602(ExerciseInfomationActivity exerciseinfomationactivity, boolean flag)
    {
        exerciseinfomationactivity.isLoadFinished = flag;
        return flag;
    }

*/



/*
    static boolean access$702(ExerciseInfomationActivity exerciseinfomationactivity, boolean flag)
    {
        exerciseinfomationactivity.isTwiceLoadStared = flag;
        return flag;
    }

*/



/*
    static String access$802(ExerciseInfomationActivity exerciseinfomationactivity, String s)
    {
        exerciseinfomationactivity.redirectUrl = s;
        return s;
    }

*/

}
