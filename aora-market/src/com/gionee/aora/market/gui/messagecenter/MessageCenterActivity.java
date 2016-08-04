// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.messagecenter;

import android.app.Dialog;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.database.MessageCenterDBHelper;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.gui.view.MarketListView;
import com.gionee.aora.market.net.MessageCenterNet;
import java.util.ArrayList;

// Referenced classes of package com.gionee.aora.market.gui.messagecenter:
//            MessageCenterAdapter

public class MessageCenterActivity extends MarketBaseActivity
{

    public MessageCenterActivity()
    {
        action = new DataCollectInfo("", "8", "6", "", "");
    }

    protected void initCenterView()
    {
        titleBarView.setTitle(getString(biz.AR.string.message_center_title));
        TextView textview = new TextView(this);
        textview.setPadding(0, 0, getResources().getDimensionPixelSize(biz.AR.dimen.dip14), 0);
        textview.setTextSize(1, 16F);
        textview.setTextColor(0xff999999);
        textview.setText(getString(biz.AR.string.message_center_clear));
        textview.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(MessageCenterActivity.this);
                marketfloatedialogbuilder.setTitle(getString(biz.AR.string.t_gprs_title));
                marketfloatedialogbuilder.setMessage(getString(biz.AR.string.message_center_clear_tip));
                marketfloatedialogbuilder.setCancelable(true);
                marketfloatedialogbuilder.setLeftButton("\u53D6\u6D88", new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                    }

                    
                }
);
                marketfloatedialogbuilder.setMidButton("\u786E\u5B9A", new android.view.View.OnClickListener() {

                    public void onClick(View view)
                    {
                        (new MessageCenterDBHelper(MessageCenterActivity.this)).deleteAllMessage(UserStorage.getDefaultUserInfo(MessageCenterActivity.this).getUSER_NAME());
                        if(data != null)
                            data.clear();
                        Integer ainteger[] = new Integer[1];
                        ainteger[0] = Integer.valueOf(0);
                        MessageCenterActivity.this.refreshView(true, ainteger);
                    }

                   
                }
);
                marketfloatedialogbuilder.crteate().show();
            }

            
        }
);
        titleBarView.setRightView(textview);
        titleBarView.setRightViewVisibility(false);
        listview = new MarketListView(this);
        listview.setBackgroundColor(getResources().getColor(biz.AR.color.main_bg1));
        setCenterView(listview);
        doLoadData(new Integer[0]);
    }

    protected   boolean initData(Integer ainteger[])
    {
        Boolean boolean1 = MessageCenterNet.getNewMessage(this);
        data = (new MessageCenterDBHelper(this)).selectAll(UserStorage.getDefaultUserInfo(this).getUSER_NAME());
        if(boolean1 == null)
            return false;
        else
            return boolean1.booleanValue();
    }

    protected   void refreshView(boolean flag, Integer ainteger[])
    {
        if(data == null || data.isEmpty())
        {
            titleBarView.setRightViewVisibility(false);
            showErrorView(biz.AR.drawable.no_download_task, "\u6CA1\u6709\u6D88\u606F", false);
        } else
        {
            titleBarView.setRightViewVisibility(true);
        }
        adapter = new MessageCenterAdapter(this, data, action);
        listview.setAdapter(adapter);
    }

    DataCollectInfo action;
    MessageCenterAdapter adapter;
    ArrayList data;
    MarketListView listview;
}
