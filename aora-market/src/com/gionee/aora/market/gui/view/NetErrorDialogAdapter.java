// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aora.base.util.DLog;
import com.aora.base.util.StringUtil;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.List;

public class NetErrorDialogAdapter extends BaseAdapter
{
    private class HolderView
    {

        ImageView icon;
        TextView name;
        TextView size;
        Button upgreageBtn;

         

    }


    public NetErrorDialogAdapter(Context context, List list)
    {
        imageLoader = null;
        options = null;
        upgreadeBtnClickListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                SoftwareManager.getInstace().installApp(DownloadManager.shareInstance(), (DownloadInfo)view.getTag());
            }

            
        }
;
        mContext = context;
        downloadInfos = list;
        imageLoader = ImageLoaderManager.getInstance();
        options = getImageLoaderOptions();
    }

    private DisplayImageOptions getImageLoaderOptions()
    {
        int i = biz.AR.drawable.cp_defaulf;
        return (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
    }

    public int getCount()
    {
        return downloadInfos.size();
    }

    public Object getItem(int i)
    {
        return downloadInfos.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        DownloadInfo downloadinfo = (DownloadInfo)getItem(i);
        HolderView holderview;
        if(view == null)
        {
            view = View.inflate(mContext, biz.AR.layout.net_error_dialog_adapter, null);
            HolderView holderview1 = new HolderView();
            holderview1.icon = (ImageView)view.findViewById(biz.AR.id.net_error_dialog_icon);
            holderview1.upgreageBtn = (Button)view.findViewById(biz.AR.id.net_error_dialog_upgreade_btn);
            holderview1.name = (TextView)view.findViewById(biz.AR.id.net_error_dialog_name);
            holderview1.size = (TextView)view.findViewById(biz.AR.id.net_error_dialog_size);
            view.setTag(holderview1);
            holderview1.upgreageBtn.setOnClickListener(upgreadeBtnClickListener);
            holderview = holderview1;
        } else
        {
            holderview = (HolderView)view.getTag();
        }
        DLog.i("lilijun", (new StringBuilder()).append("downloadInfo.getIconUrl()------->>>").append(downloadinfo.getIconUrl()).toString());
        holderview.upgreageBtn.setTag(downloadinfo);
        imageLoader.displayImage(downloadinfo.getIconUrl(), holderview.icon, options);
        holderview.name.setText(downloadinfo.getName());
        holderview.size.setText(StringUtil.getFormatSize(downloadinfo.getSize()));
        return view;
    }

    private List downloadInfos;
    private ImageLoaderManager imageLoader;
    private Context mContext;
    private DisplayImageOptions options;
    private android.view.View.OnClickListener upgreadeBtnClickListener;
}
