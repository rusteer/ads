// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.control.MarketPreferences;

public class GprsLimtDialog extends MarketFloatAcitivityBase
{

    public GprsLimtDialog()
    {
        preferences = null;
        info = null;
        isshow_gprslimt = Boolean.valueOf(true);
    }

    private void loadView()
    {
        setCenterView(View.inflate(this, biz.AR.layout.dialog_gprs, null));
        TextView textview = (TextView)findViewById(biz.AR.id.message);
        ((CheckBox)findViewById(biz.AR.id.gprs_checkbox)).setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton compoundbutton, boolean flag)
            {
                if(flag)
                {
                    isshow_gprslimt = Boolean.valueOf(true);
                    return;
                } else
                {
                    isshow_gprslimt = Boolean.valueOf(false);
                    return;
                }
            }

           
        }
);
        Resources resources = getResources();
        int i = biz.AR.string.download_gprs_limt;
        Object aobj[] = new Object[1];
        aobj[0] = Integer.valueOf(preferences.getDownloadMaxSize());
        textview.setText(resources.getString(i, aobj));
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
                preferences.setDownloadGprslimt(isshow_gprslimt);
                DownloadManager.shareInstance().addDownload(info);
                finish();
            }

            
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u9000\u51FA", "\u9A6C\u4E0A\u4E0B\u8F7D"
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
        preferences = MarketPreferences.getInstance(this);
        info = (DownloadInfo)getIntent().getSerializableExtra("DOWNLOADINFO");
        loadView();
    }

    private DownloadInfo info;
    private Boolean isshow_gprslimt;
    private MarketPreferences preferences;



/*
    static Boolean access$002(GprsLimtDialog gprslimtdialog, Boolean boolean1)
    {
        gprslimtdialog.isshow_gprslimt = boolean1;
        return boolean1;
    }

*/


}
