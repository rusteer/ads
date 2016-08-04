// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.manager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.util.Linkify;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.view.ChildTitleView;

public class MarketAboutActivity extends Activity {
    public static void copy(String s, Context context) {
        ((ClipboardManager) context.getSystemService("clipboard")).setText(s.trim());
    }
    private static void shareWeibo(TextView textview) {
        Linkify.addLinks(textview, Pattern.compile("@(.+)"), "http://t.sina.com.cn/", null, new android.text.util.Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher matcher, String s) {
                return "aoraestore";
            }
        });
    }
    public static final String SERVICE_TELEPHONE = "4007705518";
    private static final String TAG = "MarketAboutActivity";
    private LinearLayout about_mail;
    private TextView about_qq;
    private TextView about_tel;
    private TextView about_version;
    private TextView about_weixin;
    private String app_version;
    private ChildTitleView titleBarView;
    public MarketAboutActivity() {
        titleBarView = null;
        app_version = "";
    }
    private void dialServiceTel(String s) {
        if (s == null || s.equals("")) {
            Toast.makeText(this, "\u53F7\u7801\u4E3A\u7A7A\uFF0C\u65E0\u6CD5\u62E8\u53F7\uFF01", 0).show();
            return;
        } else {
            startActivity(new Intent("android.intent.action.DIAL", Uri.parse(new StringBuilder().append("tel:").append(s).toString())));
            return;
        }
    }
    private void getAppVersion(Context context) {
        try {
            app_version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return;
        } catch (Exception exception) {
            DLog.e("MarketAboutActivity", "getAppVersion()#Exception=", exception);
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.about);
        titleBarView = (ChildTitleView) findViewById(biz.AR.id.aboutTitleBar);
        titleBarView.setTitle(getResources().getString(biz.AR.string.about));
        titleBarView.setRightViewVisibility();
        about_tel = (TextView) findViewById(biz.AR.id.about_tel);
        about_tel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialServiceTel("4007705518");
            }
        });
        about_tel.requestFocus();
        about_mail = (LinearLayout) findViewById(biz.AR.id.about_mail);
        about_mail.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:support@aoratec.com"));
                startActivity(intent);
            }
        });
        about_qq = (TextView) findViewById(biz.AR.id.about_qq);
        about_qq.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketAboutActivity.copy("326889619", getBaseContext());
                Toast.makeText(getBaseContext(), "\u6587\u672C\u5DF2\u590D\u5236\u5230\u7C98\u8D34\u677F", 0).show();
            }
        });
        about_weixin = (TextView) findViewById(biz.AR.id.about_weixin);
        about_weixin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketAboutActivity.copy("\u91D1\u7ACB\u6613\u7528\u6C47", getBaseContext());
                Toast.makeText(getBaseContext(), "\u6587\u672C\u5DF2\u590D\u5236\u5230\u7C98\u8D34\u677F", 0).show();
            }
        });
        getAppVersion(this);
        about_version = (TextView) findViewById(biz.AR.id.about_versions);
        about_version.setText(new StringBuilder().append("V").append(app_version).toString());
        shareWeibo((TextView) findViewById(biz.AR.id.about_weibo_text));
    }
}
