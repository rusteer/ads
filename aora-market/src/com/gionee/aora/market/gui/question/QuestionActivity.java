// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.question;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.*;
import android.widget.ImageView;
import android.widget.Toast;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.control.UserManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.view.*;
import com.gionee.aora.integral.main.MarketBaseActivity;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.integral.util.MarketAsyncTask;
import com.gionee.aora.market.control.MarketPreferences;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionActivity extends MarketBaseActivity
    implements WebViewChangeListener
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
            DLog.i("denglh", (new StringBuilder()).append("\u52A0\u8F7D\u7F51\u9875\u5B8C\u6210\uFF01\uFF01 url----->>>").append(s).toString());
            redirectUrl = s;
            if(startLoadUrl.equals(redirectUrl) && !isTwiceLoadStared && !isLoadFinished)
            {
                DLog.i("denglh", "\u9690\u85CF\u52A0\u8F7D\u89C6\u56FE\uFF01\uFF01\uFF01");
                doLoadData(new Integer[0]);
            }
            isLoadFinished = true;
        }

        public void onPageStarted(WebView webview, String s, Bitmap bitmap)
        {
            super.onPageStarted(webview, s, bitmap);
            DLog.i("denglh", (new StringBuilder()).append("\u5F00\u59CB\u52A0\u8F7D\u7F51\u9875\uFF01url------>>>").append(s).toString());
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
            DLog.i("denglh", "\u52A0\u8F7D\u51FA\u9519\uFF01\uFF01\uFF01");
        }

        public boolean shouldOverrideUrlLoading(WebView webview, String s)
        {
            webview.loadUrl(s);
            return true;
        }


        private MyWebViewClient()
        {
            super();
        }

    }

    final class QuextionJsInterface
    {

        public void doSubmit(String s)
        {
            DLog.d("denglh", (new StringBuilder()).append("\u63D0\u4EA4\u6709\u5956\u95EE\u5377\267\267\267\267\267\267\267\267").append(s).toString());
            MarketPreferences.getInstance(QuestionActivity.this).setShowQuestion(Boolean.valueOf(false));
            try
            {
                JSONObject jsonobject = new JSONObject(s);
                String s1 = jsonobject.getString("COIN");
                String s2 = jsonobject.getString("TODAY_GET_TOTAL");
                String s3 = jsonobject.getString("MSG");
                UserInfo userinfo = UserStorage.getDefaultUserInfo(QuestionActivity.this);
                userinfo.setCOIN(s1);
                userinfo.setToDayTotal(s2);
                UserStorage.saveUserInfo(QuestionActivity.this, userinfo);
                UserManager.getInstance(QuestionActivity.this).reFreshUserInfo(userinfo);
                Toast.makeText(QuestionActivity.this, s3, 0).show();
            }
            catch(JSONException jsonexception)
            {
                DLog.e("QuestionActivity", "doSubmit#Exception ", jsonexception);
            }
            finish();
        }


        QuextionJsInterface()
        {
            super();
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


        private WebViewDownloadListener()
        {
            super();
        }

    }


    public QuestionActivity()
    {
        skipUrl = "";
        startLoadUrl = "";
        redirectUrl = "";
        isTwiceLoadStared = false;
        isLoadFinished = false;
    }

    public void OnChange()
    {
        DLog.d("denglh", "WebView\u9AD8\u5EA6\u6539\u53D8");
        webView.postInvalidate();
    }

    protected void initCenterView()
    {
        DLog.i("denglh", "QuestionActivity,initCenterView()");
        sp = MarketPreferences.getInstance(this);
        if(sp.getShowQuestion().booleanValue())
            skipUrl = sp.getQuestionUrl();
        setCenterView(biz.AR.layout.question_layout);
        webView = (MyWebView)findViewById(biz.AR.id.question_wv);
        webView.setListener(this);
        webView.getSettings().setJavaScriptEnabled(true);
        mWebViewDownloadListener = new WebViewDownloadListener();
        mWebViewClient = new MyWebViewClient();
        mWebChromeClient = new MyWebChromeClient();
        webView.setDownloadListener(mWebViewDownloadListener);
        webView.setWebViewClient(mWebViewClient);
        webView.setWebChromeClient(mWebChromeClient);
        webView.addJavascriptInterface(new QuextionJsInterface(), "submit");
        webView.loadUrl(skipUrl.trim());
        setTitleBarViewVisibility(true);
        titleBarView.setTitle("\u6709\u5956\u95EE\u5377");
        titleBarView.backImg.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
                webView.postInvalidate();
                webView.postDelayed(new Runnable() {

                    public void run()
                    {
                        finish();
                    }

                    
                }
, 200L);
            }

            
        }
);
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

    private boolean isLoadFinished;
    private boolean isTwiceLoadStared;
    private MyWebChromeClient mWebChromeClient;
    private MyWebViewClient mWebViewClient;
    private WebViewDownloadListener mWebViewDownloadListener;
    private String redirectUrl;
    private String skipUrl;
    private MarketPreferences sp;
    private String startLoadUrl;
    private MyWebView webView;




/*
    static String access$402(QuestionActivity questionactivity, String s)
    {
        questionactivity.startLoadUrl = s;
        return s;
    }

*/



/*
    static boolean access$502(QuestionActivity questionactivity, boolean flag)
    {
        questionactivity.isLoadFinished = flag;
        return flag;
    }

*/



/*
    static boolean access$602(QuestionActivity questionactivity, boolean flag)
    {
        questionactivity.isTwiceLoadStared = flag;
        return flag;
    }

*/



/*
    static String access$702(QuestionActivity questionactivity, String s)
    {
        questionactivity.redirectUrl = s;
        return s;
    }

*/

}
