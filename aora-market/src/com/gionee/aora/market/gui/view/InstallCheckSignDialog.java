// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.control.SoftwareManager;

public class InstallCheckSignDialog extends MarketFloatAcitivityBase
{

    public InstallCheckSignDialog()
    {
        info = null;
    }

    private void loadView()
    {
        setCenterView(View.inflate(this, biz.AR.layout.dialog_gprs, null));
        TextView textview = (TextView)findViewById(biz.AR.id.message);
        ((CheckBox)findViewById(biz.AR.id.gprs_checkbox)).setVisibility(8);
        textview.setText((new StringBuilder()).append("\u4F60\u539F\u6709\u7684\u3010").append(info.getName()).append("\u3011\u65B0\u7248\u672C\u4E0D\u517C\u5BB9\uFF0C\u9700\u5378\u8F7D\u540E\u624D\u80FD\u5B89\u88C5\uFF0C\u662F\u5426\u7EE7\u7EED\u5B89\u88C5\uFF1F").toString());
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[2];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                finish();
            }

            
        }
;
        aonclicklistener[1] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                SoftwareManager.getInstace().uninstallApk(info.getPackageName());
                finish();
            }

             
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u53D6\u6D88\u5B89\u88DD", "\u7EE7\u7EED\u5B89\u88C5"
        });
    }

    public String getDialogTitle()
    {
        return null;
    }

    public String getMessage()
    {
        return null;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        info = (DownloadInfo)getIntent().getSerializableExtra("DOWNLOADINFO");
        loadView();
    }

    private DownloadInfo info;

}
