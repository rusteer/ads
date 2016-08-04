// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.recommend;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.StringUtil;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.gui.download.DownloadOnClickListener;
import com.gionee.aora.market.gui.download.NewDownloadBaseAdapter;
import com.gionee.aora.market.gui.download.view.DownloadProgress;
import com.gionee.aora.market.module.AppInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.ArrayList;

public class PersonalRecommandAdapter extends NewDownloadBaseAdapter
{
    private class ViewHolder
    {

        public TextView appInfoTv;
        public TextView appNameTv;
        public View divider;
        public DownloadProgress downloadStateButton;
        public ImageView icon;
        public DownloadOnClickListener listener;

        public ViewHolder(View view)
        {
            super();
            icon = (ImageView)view.findViewById(biz.AR.id.icon);
            downloadStateButton = (DownloadProgress)view.findViewById(biz.AR.id.down_btn);
            appNameTv = (TextView)view.findViewById(biz.AR.id.app_name);
            appInfoTv = (TextView)view.findViewById(biz.AR.id.app_info);
            divider = view.findViewById(biz.AR.id.divider);
            listener = new DownloadOnClickListener(context);
        }
    }


    public PersonalRecommandAdapter(Context context1, ListView listview, ArrayList arraylist, DataCollectInfo datacollectinfo)
    {
        super(context1, listview, arraylist);
        int i = biz.AR.drawable.refresh_logo;
        action = null;
        context = context1;
        data = arraylist;
        action = datacollectinfo;
        ops = (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
    }

    public int getCount()
    {
        if(data == null)
            return 0;
        else
            return data.size();
    }

    public AppInfo getItem(int i)
    {
        return (AppInfo)data.get(i);
    }

    

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        AppInfo appinfo = getItem(i);
        ViewHolder viewholder;
        if(view == null)
        {
            view = View.inflate(context, biz.AR.layout.personal_dialog_list_item, null);
            viewholder = new ViewHolder(view);
            view.setTag(viewholder);
            viewholder.downloadStateButton.downloadbtn.setOnClickListener(viewholder.listener);
        } else
        {
            viewholder = (ViewHolder)view.getTag();
        }
        ImageLoaderManager.getInstance().displayImage(appinfo.getIconUrl(), viewholder.icon, ops);
        viewholder.downloadStateButton.setInfo(appinfo.getPackageName());
        viewholder.listener.setDownloadListenerInfo(appinfo, action);
        viewholder.appNameTv.setText(appinfo.getName());
        viewholder.appInfoTv.setText((new StringBuilder()).append(appinfo.getSize()).append("|").append(StringUtil.getDownloadNumber(appinfo.getDownload_region())).append("\u6B21\u4E0B\u8F7D").toString());
        if(i == 0)
        {
            viewholder.divider.setVisibility(8);
            return view;
        } else
        {
            viewholder.divider.setVisibility(0);
            return view;
        }
    }

    protected void refreshData(DownloadInfo downloadinfo, View view)
    {
        ViewHolder viewholder = (ViewHolder)view.getTag();
        if(viewholder != null)
        {
            viewholder.downloadStateButton.setInfo(downloadinfo.getPackageName());
            viewholder.listener.setDownloadListenerInfo(downloadinfo);
        }
    }

    DataCollectInfo action;
    Context context;
    ArrayList data;
    DisplayImageOptions ops;
}
