// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Context;
import android.view.*;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.module.RecommendAdInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.List;

public class SpecialBannerAdapter extends BaseAdapter
{
    class BannerHolder
    {

        public ImageView img;

        BannerHolder()
        {
            super();
        }
    }


    public SpecialBannerAdapter(Context context1, List list, int i)
    {
        imageLoader = null;
        context = context1;
        infos = list;
        imageLoader = ImageLoaderManager.getInstance();
        params = new android.widget.AbsListView.LayoutParams(-1, i);
        imgparams = new android.widget.RelativeLayout.LayoutParams(-1, i);
    }

    public int getCount()
    {
        return infos.size();
    }

    protected DisplayImageOptions getImageLoaderOptions(int i)
    {
        return (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
    }

    public List getInfos()
    {
        return infos;
    }

    public Object getItem(int i)
    {
        return infos.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        BannerHolder bannerholder;
        RecommendAdInfo recommendadinfo;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(biz.AR.layout.special_banner_item, null);
            bannerholder = new BannerHolder();
            bannerholder.img = (ImageView)view.findViewById(biz.AR.id.special_ad_icon);
            bannerholder.img.setLayoutParams(imgparams);
            view.setLayoutParams(params);
            view.setTag(bannerholder);
        } else
        {
            bannerholder = (BannerHolder)view.getTag();
        }
        recommendadinfo = (RecommendAdInfo)infos.get(i);
        imageLoader.displayImage(recommendadinfo.getUrl(), bannerholder.img, getImageLoaderOptions(biz.AR.drawable.ad_default_banner));
        return view;
    }

    public void setInfos(List list)
    {
        infos = list;
    }

    private Context context;
    private ImageLoaderManager imageLoader;
    private android.widget.RelativeLayout.LayoutParams imgparams;
    private List infos;
    private android.widget.AbsListView.LayoutParams params;
}
