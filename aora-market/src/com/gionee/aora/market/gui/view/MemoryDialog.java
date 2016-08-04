// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.gui.manager.SoftInstalledActivity;

public class MemoryDialog extends MarketFloatAcitivityBase
{

    public MemoryDialog()
    {
        info = null;
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[2];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                DownloadManager.shareInstance().addDownload(info);
                finish();
            }

            
        }
;
        aonclicklistener[1] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                Intent intent = new Intent(MemoryDialog.this,  SoftInstalledActivity.class);
                startActivity(intent);
                finish();
            }

            
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u7EE7\u7EED\u4E0B\u8F7D", "\u53BB\u7626\u8EAB\u54AF"
        });
    }

    public String getDialogTitle()
    {
        return null;
    }

    public String getMessage()
    {
        return getResources().getString(biz.AR.string.memory);
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        info = (DownloadInfo)getIntent().getSerializableExtra("DOWNLOADINFO");
        messageTv.setText(getResources().getString(biz.AR.string.memory));
    }

    private DownloadInfo info;

}
