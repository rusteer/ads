// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.gui.special.view.PrefectureRecomendAppLayout;
import com.gionee.aora.market.module.PrefectureInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.special:
//            SpecialInfomationActivity

public class PrefectureRecomendAdapter extends BaseAdapter
{
    class ViewHolder
    {

        PrefectureRecomendAppLayout appLayout;
        TextView browse_count;
        TextView describle;
        ImageView icon;
        TextView name;
        TextView time;

        ViewHolder()
        {
            super();
        }
    }


    public PrefectureRecomendAdapter(Context context, List list, DataCollectInfo datacollectinfo)
    {
        datainfo = null;
        recomentTimeStr = "\u4E13\u9898\u65F6\u95F4\uFF1A";
        itemOnClickListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                PrefectureInfo prefectureinfo = (PrefectureInfo)view.getTag(biz.AR.id.prefecture_info_lay);
                Intent intent = new Intent();
                intent.putExtra("specialId", (new StringBuilder()).append(prefectureinfo.getId()).append("").toString());
                intent.setClass(mContext,  SpecialInfomationActivity.class);
                mContext.startActivity(intent);
            }

            
        }
;
        mContext = context;
        prefectureInfos = list;
        mInflater = LayoutInflater.from(mContext);
        options = getImageLoaderOptions();
        datainfo = datacollectinfo;
    }

    private DisplayImageOptions getImageLoaderOptions()
    {
        int i = biz.AR.drawable.ad_default_banner;
        return (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
    }

    public int getCount()
    {
        return prefectureInfos.size();
    }

    public Object getItem(int i)
    {
        return prefectureInfos.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public List getPrefectureInfos()
    {
        return prefectureInfos;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        PrefectureInfo prefectureinfo = (PrefectureInfo)getItem(i);
        if(view == null)
        {
            holder = new ViewHolder();
            view = mInflater.inflate(biz.AR.layout.prefecture_recomend_fragment_listview_adapter, null);
            view.setOnClickListener(itemOnClickListener);
            holder.icon = (ImageView)view.findViewById(biz.AR.id.prefecture_recoment_icon);
            holder.name = (TextView)view.findViewById(biz.AR.id.prefecture_recoment_name);
            holder.time = (TextView)view.findViewById(biz.AR.id.prefecture_recoment_time);
            holder.browse_count = (TextView)view.findViewById(biz.AR.id.prefecture_recoment_browse_count);
            holder.describle = (TextView)view.findViewById(biz.AR.id.prefecture_describle);
            holder.appLayout = (PrefectureRecomendAppLayout)view.findViewById(biz.AR.id.prefecture_app_layout);
            view.setTag(holder);
        } else
        {
            holder = (ViewHolder)view.getTag();
        }
        ImageLoaderManager.getInstance().displayImage(prefectureinfo.getIconUrl(), holder.icon, options);
        holder.name.setText(prefectureinfo.getName());
        holder.time.setText((new StringBuilder()).append(recomentTimeStr).append(prefectureinfo.getTime()).toString());
        holder.browse_count.setText(String.valueOf(prefectureinfo.getBrowseCount()));
        holder.describle.setText(prefectureinfo.getDescrible());
        holder.appLayout.setAppData(((PrefectureInfo)prefectureInfos.get(i)).getAppInfos(), null, null);
        view.setTag(biz.AR.id.prefecture_info_lay, prefectureinfo);
        return view;
    }

    public void onDestory()
    {
        if(holder != null && holder.appLayout != null)
            holder.appLayout.onDestroy();
    }

    public void setPrefectureInfos(List list)
    {
        prefectureInfos = list;
    }

    private DataCollectInfo datainfo;
    private ViewHolder holder;
    private android.view.View.OnClickListener itemOnClickListener;
    private Context mContext;
    private LayoutInflater mInflater;
    private DisplayImageOptions options;
    private List prefectureInfos;
    private String recomentTimeStr;

}
