// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.aora.base.util.DLog;
import com.aora.base.util.NetUtil;
import com.gionee.aora.integral.main.MarketBaseActivity;

public class CallHelp extends MarketBaseActivity {
    private class MyWebChromeClient extends WebChromeClient {
        private MyWebChromeClient() {
            super();
        }
        @Override
        public boolean onJsAlert(WebView webview, String s, String s1, JsResult jsresult) {
            return super.onJsAlert(webview, s, s1, jsresult);
        }
    }
    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
            super();
        }
        @Override
        public void onPageFinished(WebView webview, String s) {
            DLog.i("denglh", "\u52A0\u8F7D\u6DD8\u91D1\u653B\u7565\u7F51\u9875\u5B8C\u6210\uFF01\uFF01");
            redirectUrl = s;
            if (startLoadUrl.equals(redirectUrl) && !isTwiceLoadStared && !isLoadFinished) {
                DLog.i("lilijun", "\u9690\u85CF\u52A0\u8F7D\u89C6\u56FE\uFF01\uFF01\uFF01");
                doLoadData(new Integer[0]);
            }
            isLoadFinished = true;
        }
        @Override
        public void onPageStarted(WebView webview, String s, Bitmap bitmap) {
            DLog.i("denglh", "\u5F00\u59CB\u52A0\u8F7D\u6DD8\u91D1\u653B\u7565\u7F51\u9875\uFF01");
            if (!"".equals(startLoadUrl)) if (!startLoadUrl.equals(s) && !isLoadFinished) {
                isTwiceLoadStared = true;
            } else {
                isTwiceLoadStared = false;
                isLoadFinished = false;
            }
            startLoadUrl = s;
        }
        @Override
        public void onReceivedError(WebView webview, int i, String s, String s1) {
            DLog.i("denglh", "\u52A0\u8F7D\u6DD8\u91D1\u653B\u7565\u7F51\u9875\u51FA\u9519\uFF01\uFF01\uFF01");
            super.onReceivedError(webview, i, s, s1);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String s) {
            webview.loadUrl(s);
            return true;
        }
    }
    private class WebViewDownloadListener implements DownloadListener {
        private WebViewDownloadListener() {
            super();
        }
        @Override
        public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(s));
            startActivity(intent);
        }
    }
    private String STRATGY_URL;
    private String TAG;
    private boolean isLoadFinished;
    private boolean isTwiceLoadStared;
    private String redirectUrl;
    private String startLoadUrl;
    private WebSettings webSettings;
    private WebView webView;
    public CallHelp() {
        TAG = "IntegralStrategyActivity";
        STRATGY_URL = "http://test.myaora.net:81/rechange/taobao.php";
        startLoadUrl = "";
        redirectUrl = "";
        isTwiceLoadStared = false;
        isLoadFinished = false;
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.integral_strategy);
        titleBarView.setTitle("\u7535\u8BDD\u5E2E\u52A9");
        LinearLayout linearlayout = (LinearLayout) findViewById(biz.AR.id.strategy_webview);
        webView = new WebView(this);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setDownloadListener(new WebViewDownloadListener());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        linearlayout.addView(webView);
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        return true;
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        STRATGY_URL = getIntent().getStringExtra("STRATGY_URL");
        if (NetUtil.isNetworkAvailable(this)) {
            webView.loadUrl(STRATGY_URL.trim());
            return;
        } else {
            showErrorView();
            return;
        }
    }
    @Override
    public boolean onKeyDown(int i, KeyEvent keyevent) {
        if (webView.canGoBack() && keyevent.getKeyCode() == 4 && keyevent.getRepeatCount() == 0) {
            webView.goBack();
            return true;
        } else {
            return super.onKeyDown(i, keyevent);
        }
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {}
    @Override
    protected void tryAgain() {
        DLog.v(TAG, "tryAgain");
        super.tryAgain();
        webView.loadUrl(STRATGY_URL.trim());
    }
    /*
        static String access$202(CallHelp callhelp, String s)
        {
            callhelp.startLoadUrl = s;
            return s;
        }

    */
    /*
        static boolean access$302(CallHelp callhelp, boolean flag)
        {
            callhelp.isLoadFinished = flag;
            return flag;
        }

    */
    /*
        static boolean access$402(CallHelp callhelp, boolean flag)
        {
            callhelp.isTwiceLoadStared = flag;
            return flag;
        }

    */
    /*
        static String access$502(CallHelp callhelp, String s)
        {
            callhelp.redirectUrl = s;
            return s;
        }

    */
}
