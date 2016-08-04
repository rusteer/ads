// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.necessary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.gui.download.*;
import com.gionee.aora.market.gui.download.view.DownloadProgress;
import com.gionee.aora.market.module.AppInfo;
import java.util.ArrayList;
import java.util.List;

public class NecessaryNewAdapter extends DownloadExpandableBaseAdapter
{
    public class GroupNecessarysoftListViewHolder
    {

        public void init(View view)
        {
            groupTitle = (TextView)view.findViewById(biz.AR.id.necessary_new_theme_name);
        }

        public TextView groupTitle;

        public GroupNecessarysoftListViewHolder(View view)
        {
            super();
            init(view);
        }
    }

    public class NecessarysoftListViewHolder extends DownloadViewHolder
    {

        public void init()
        {
            icon = (ImageView)baseView.findViewById(biz.AR.id.rank_list_icon);
            appName = (TextView)baseView.findViewById(biz.AR.id.rank_list_appName);
            size = (TextView)baseView.findViewById(biz.AR.id.rank_list_packageSize);
            downloadStateButton = (DownloadProgress)baseView.findViewById(biz.AR.id.rank_list_button);
            downloadNumber = (TextView)baseView.findViewById(biz.AR.id.rank_list_downloadRegion);
            topSplitLine = baseView.findViewById(biz.AR.id.necessary_new_item_top_split_line);
            listener = new DownloadOnClickListener(NecessaryNewAdapter.context);
        }

        public TextView appName;
        public TextView downloadNumber;
        public DownloadProgress downloadStateButton;
        public ImageView icon;
        public DownloadOnClickListener listener;
        public TextView size;
        public View topSplitLine;

        public NecessarysoftListViewHolder(View view)
        {
            super(view);
            init();
        }
    }


    public NecessaryNewAdapter(Context context, List list, List list1, DataCollectInfo datacollectinfo)
    {
        super(context);
        groupList = null;
        childrenList = null;
        datainfo = null;
        groupList = list;
        childrenList = list1;
        datainfo = datacollectinfo;
        datacollectinfo.setType("1");
        setUpdateProgress(true);
    }

    public Object getChild(int i, int j)
    {
        return ((List)childrenList.get(i)).get(j);
    }

    public long getChildId(int i, int j)
    {
        return (long)j;
    }

    public View getChildView(int i, int j, boolean flag, View view, ViewGroup viewgroup)
    {
        NecessarysoftListViewHolder necessarysoftlistviewholder;
        AppInfo appinfo;
        if(view == null)
        {
            RelativeLayout relativelayout = (RelativeLayout)View.inflate(context, biz.AR.layout.necessary_new_item2, null);
            necessarysoftlistviewholder = new NecessarysoftListViewHolder(relativelayout);
            relativelayout.setTag(necessarysoftlistviewholder);
            necessarysoftlistviewholder.downloadStateButton.downloadbtn.setOnClickListener(necessarysoftlistviewholder.listener);
            view = relativelayout;
        } else
        {
            necessarysoftlistviewholder = (NecessarysoftListViewHolder)view.getTag();
        }
        addToViewHolder(((AppInfo)((List)childrenList.get(i)).get(j)).getPackageName(), necessarysoftlistviewholder);
        if(j == 0)
            necessarysoftlistviewholder.topSplitLine.setVisibility(8);
        else
            necessarysoftlistviewholder.topSplitLine.setVisibility(0);
        appinfo = (AppInfo)((List)childrenList.get(i)).get(j);
        necessarysoftlistviewholder.appName.setText(appinfo.getName());
        necessarysoftlistviewholder.size.setText(appinfo.getSize());
        necessarysoftlistviewholder.downloadStateButton.setInfo(appinfo.getPackageName());
        necessarysoftlistviewholder.listener.setDownloadListenerInfo(appinfo, datainfo);
        necessarysoftlistviewholder.downloadNumber.setText((new StringBuilder()).append(appinfo.getDownload_region()).append("\u6B21\u4E0B\u8F7D").toString());
        imageLoader.displayImage(appinfo.getIconUrl(), necessarysoftlistviewholder.icon, options);
        necessarysoftlistviewholder.listener.setAnimationViewInfo(appinfo.getIconUrl(), necessarysoftlistviewholder.icon);
        return view;
    }

    public int getChildrenCount(int i)
    {
        if(childrenList == null)
            return 0;
        else
            return ((List)childrenList.get(i)).size();
    }

    public Object getGroup(int i)
    {
        return groupList.get(i);
    }

    public int getGroupCount()
    {
        if(groupList == null)
            return 0;
        else
            return groupList.size();
    }

    public long getGroupId(int i)
    {
        return (long)i;
    }

    public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
    {
        GroupNecessarysoftListViewHolder groupnecessarysoftlistviewholder;
        if(view == null)
        {
            LinearLayout linearlayout = (LinearLayout)View.inflate(context, biz.AR.layout.necessary_new_group_item2, null);
            GroupNecessarysoftListViewHolder groupnecessarysoftlistviewholder1 = new GroupNecessarysoftListViewHolder(linearlayout);
            linearlayout.setTag(groupnecessarysoftlistviewholder1);
            view = linearlayout;
            groupnecessarysoftlistviewholder = groupnecessarysoftlistviewholder1;
        } else
        {
            groupnecessarysoftlistviewholder = (GroupNecessarysoftListViewHolder)view.getTag();
        }
        groupnecessarysoftlistviewholder.groupTitle.setText((CharSequence)groupList.get(i));
        return view;
    }

    protected List getHoldersList()
    {
        necessaryHolderList = new ArrayList();
        return necessaryHolderList;
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public boolean isChildSelectable(int i, int j)
    {
        return true;
    }

    protected   void setDownloadData(DownloadViewHolder downloadviewholder, DownloadInfo downloadinfo)
    {
        setDownloadData((NecessarysoftListViewHolder)downloadviewholder, downloadinfo);
    }

    protected void setDownloadData(NecessarysoftListViewHolder necessarysoftlistviewholder, DownloadInfo downloadinfo)
    {
        necessarysoftlistviewholder.downloadStateButton.setInfo(downloadinfo.getPackageName());
        necessarysoftlistviewholder.listener.setDownloadListenerInfo(downloadinfo);
    }

    private List childrenList;
    private DataCollectInfo datainfo;
    private List groupList;
    private List necessaryHolderList;

}
