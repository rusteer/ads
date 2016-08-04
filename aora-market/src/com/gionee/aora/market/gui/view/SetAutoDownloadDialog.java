// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.gionee.aora.integral.gui.view.MarketFloatAcitivityBase;
import com.gionee.aora.market.ProjectConfig;
import com.gionee.aora.market.control.MarketPreferences;

public class SetAutoDownloadDialog extends MarketFloatAcitivityBase
{

    public SetAutoDownloadDialog()
    {
    }

    public android.view.View.OnClickListener[] getButtonListener()
    {
        android.view.View.OnClickListener aonclicklistener[] = new android.view.View.OnClickListener[1];
        aonclicklistener[0] = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                if(checkBox.isChecked())
                    MarketPreferences.getInstance(SetAutoDownloadDialog.this).setAutoUpdateType(2);
                finish();
            }
 
        }
;
        return aonclicklistener;
    }

    public String[] getButtonText()
    {
        return (new String[] {
            "\u786E\u5B9A"
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
        View view = View.inflate(this, biz.AR.layout.set_wifi_update_dialog, null);
        checkBox = (CheckBox)view.findViewById(biz.AR.id.update_dialog_checkbox);
        if(ProjectConfig.noAutoUpdate.booleanValue())
            checkBox.setChecked(false);
        else
            checkBox.setChecked(true);
        titleTv.setText("\u6613\u7528\u6C47 \u201C\u5F00\u542F\u81EA\u52A8\u66F4\u65B0\u201D");
        setCenterView(view);
    }

    private CheckBox checkBox;

}
