// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import java.lang.reflect.InvocationTargetException;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;

public class IntroductionVideoActivity extends Activity {
    private class MyWebChromeClient extends WebChromeClient {
        private MyWebChromeClient() {
            super();
        }
        @Override
        public void onGeolocationPermissionsShowPrompt(String s, android.webkit.GeolocationPermissions.Callback callback) {
            callback.invoke(s, true, false);
            super.onGeolocationPermissionsShowPrompt(s, callback);
        }
        @Override
        public void onHideCustomView() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(IntroductionVideoActivity.this, "onHideCustomView", 0).show();
                }
            });
            if (myView != null && mycCallback != null) {
                mycCallback.onCustomViewHidden();
                mycCallback = null;
            }
            ViewGroup viewgroup = (ViewGroup) myView.getParent();
            viewgroup.removeView(myView);
            viewgroup.addView(webView);
            myView = null;
        }
        @Override
        public void onShowCustomView(View view, android.webkit.WebChromeClient.CustomViewCallback customviewcallback) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(IntroductionVideoActivity.this, "onShowCustomView", 0).show();
                }
            });
            if (mycCallback != null) {
                mycCallback.onCustomViewHidden();
                mycCallback = null;
                return;
            } else {
                ViewGroup viewgroup = (ViewGroup) webView.getParent();
                viewgroup.removeView(webView);
                viewgroup.addView(view);
                myView = view;
                mycCallback = customviewcallback;
                mWebChromeClient = this;
                return;
            }
        }
        @Override
        public void onShowCustomView(View view, int i, android.webkit.WebChromeClient.CustomViewCallback customviewcallback) {
            onShowCustomView(view, customviewcallback);
        }
    }
    private class MyWebViewClient extends WebViewClient {
        private MyWebViewClient() {
            super();
        }
        @Override
        public void onPageFinished(WebView webview, String s) {
            super.onPageFinished(webview, s);
            DLog.i("lilijun", new StringBuilder().append("\u52A0\u8F7D\u7F51\u9875\u5B8C\u6210\uFF01\uFF01 url----->>>").append(s).toString());
            redirectUrl = s;
            if (startLoadUrl.equals(redirectUrl) && !isTwiceLoadStared && !isLoadFinished) DLog.i("lilijun", "\u9690\u85CF\u52A0\u8F7D\u89C6\u56FE\uFF01\uFF01\uFF01");
            isLoadFinished = true;
        }
        @Override
        public void onPageStarted(WebView webview, String s, Bitmap bitmap) {
            super.onPageStarted(webview, s, bitmap);
            DLog.i("lilijun", new StringBuilder().append("\u5F00\u59CB\u52A0\u8F7D\u7F51\u9875\uFF01url------>>>").append(s).toString());
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
            super.onReceivedError(webview, i, s, s1);
            DLog.i("lilijun", "\u52A0\u8F7D\u51FA\u9519\uFF01\uFF01\uFF01");
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
    private DataCollectInfo datainfo;
    private boolean isLoadFinished;
    private boolean isTwiceLoadStared;
    private MyWebChromeClient mWebChromeClient;
    private MyWebViewClient mWebViewClient;
    private View myView;
    private android.webkit.WebChromeClient.CustomViewCallback mycCallback;
    private String redirectUrl;
    private String skipUrl;
    private String startLoadUrl;
    private int vid;
    private WebView webView;
    public IntroductionVideoActivity() {
        datainfo = null;
        skipUrl = "";
        startLoadUrl = "";
        redirectUrl = "";
        isTwiceLoadStared = false;
        isLoadFinished = false;
        myView = null;
    }
    @Override
    public void onBackPressed() {
        if (webView != null) {
            stopVideo();
            ((RelativeLayout) findViewById(biz.AR.id.webviewRelativeLayout)).removeAllViews();
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.exercise_infomation_activity);
        datainfo = DataCollectManager.getCollectInfo(this);
        vid = getIntent().getIntExtra("vid", 0);
        skipUrl = getIntent().getStringExtra("VIDEO_URL");
        DataCollectInfo datacollectinfo = datainfo;
        String as[] = new String[4];
        as[0] = "vid";
        as[1] = String.valueOf(vid);
        as[2] = "app_id";
        as[3] = getIntent().getStringExtra("APPID");
        DataCollectManager.addRecord(datacollectinfo, as);
        webView = (WebView) findViewById(biz.AR.id.exercise_infomation_webview);
        mWebViewClient = new MyWebViewClient();
        mWebChromeClient = new MyWebChromeClient();
        webView.setWebViewClient(mWebViewClient);
        webView.setWebChromeClient(mWebChromeClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginsEnabled(true);
        webView.getSettings().setPluginState(android.webkit.WebSettings.PluginState.ON);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(skipUrl.trim());
    }
    @Override
    protected void onPause() {
        stopVideo();
        super.onPause();
    }
    @Override
    protected void onResume() {
        if (webView != null) try {
            webView.getClass().getMethod("onResume", new Class[0]).invoke(webView, (Object[]) null);
        } catch (IllegalArgumentException illegalargumentexception) {
            DLog.e("IntroductionVideoActivity", new StringBuilder().append(" IllegalArgumentException ").append(illegalargumentexception).toString());
        } catch (IllegalAccessException illegalaccessexception) {
            DLog.e("IntroductionVideoActivity", new StringBuilder().append(" IllegalAccessException ").append(illegalaccessexception).toString());
        } catch (InvocationTargetException invocationtargetexception) {
            DLog.e("IntroductionVideoActivity", new StringBuilder().append(" InvocationTargetException ").append(invocationtargetexception).toString());
        } catch (NoSuchMethodException nosuchmethodexception) {
            DLog.e("IntroductionVideoActivity", new StringBuilder().append(" NoSuchMethodException ").append(nosuchmethodexception).toString());
        }
        super.onResume();
    }
    private void stopVideo() {
        if (webView != null) {
            try {
                webView.getClass().getMethod("onPause", new Class[0]).invoke(webView, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*
        static String access$202(IntroductionVideoActivity introductionvideoactivity, String s)
        {
            introductionvideoactivity.startLoadUrl = s;
            return s;
        }

    */
    /*
        static boolean access$302(IntroductionVideoActivity introductionvideoactivity, boolean flag)
        {
            introductionvideoactivity.isLoadFinished = flag;
            return flag;
        }

    */
    /*
        static boolean access$402(IntroductionVideoActivity introductionvideoactivity, boolean flag)
        {
            introductionvideoactivity.isTwiceLoadStared = flag;
            return flag;
        }

    */
    /*
        static String access$502(IntroductionVideoActivity introductionvideoactivity, String s)
        {
            introductionvideoactivity.redirectUrl = s;
            return s;
        }

    */
    /*
        static View access$602(IntroductionVideoActivity introductionvideoactivity, View view)
        {
            introductionvideoactivity.myView = view;
            return view;
        }

    */
    /*
        static android.webkit.WebChromeClient.CustomViewCallback access$702(IntroductionVideoActivity introductionvideoactivity, android.webkit.WebChromeClient.CustomViewCallback customviewcallback)
        {
            introductionvideoactivity.mycCallback = customviewcallback;
            return customviewcallback;
        }

    */
    /*
        static MyWebChromeClient access$902(IntroductionVideoActivity introductionvideoactivity, MyWebChromeClient mywebchromeclient)
        {
            introductionvideoactivity.mWebChromeClient = mywebchromeclient;
            return mywebchromeclient;
        }

    */
}
