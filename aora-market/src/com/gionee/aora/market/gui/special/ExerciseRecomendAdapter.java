// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.special;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.*;
import android.widget.*;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.module.PrefectureInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.List;

// Referenced classes of package com.gionee.aora.market.gui.special:
//            ExerciseInfomationActivity

public class ExerciseRecomendAdapter extends BaseAdapter
{
    private class ConvertViewOnClickListener
        implements android.view.View.OnClickListener
    {

        public void onClick(View view)
        {
            PrefectureInfo prefectureinfo = (PrefectureInfo)getItem(position);
            Intent intent = new Intent();
            intent.putExtra("skipUrl", prefectureinfo.getSkipUrl());
            intent.putExtra("vid", prefectureinfo.getId());
            intent.putExtra("DATACOLLECT_INFO", datainfo);
            intent.setClass(mContext,  ExerciseInfomationActivity.class);
            mContext.startActivity(intent);
        }

        int position;

        private ConvertViewOnClickListener()
        {
            super();
            position = 0;
        }

    }

    class ViewHolder
    {

        TextView browse_count;
        ImageView icon;
        TextView name;
        TextView time;

        ViewHolder()
        {
            super();
        }
    }


    public ExerciseRecomendAdapter(Context context, List list, DataCollectInfo datacollectinfo)
    {
        datainfo = null;
        mContext = context;
        prefectureInfos = list;
        datainfo = datacollectinfo;
        mInflater = LayoutInflater.from(mContext);
        options = getImageLoaderOptions();
        res = mContext.getResources();
        exerciseTimeStr = res.getString(biz.AR.string.exercise_time);
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
        ConvertViewOnClickListener convertviewonclicklistener;
        ViewHolder viewholder1;
        if(view == null)
        {
            ViewHolder viewholder = new ViewHolder();
            convertviewonclicklistener = new ConvertViewOnClickListener();
            view = mInflater.inflate(biz.AR.layout.prefecture_recomend_fragment_listview_adapter, null);
            view.setOnClickListener(convertviewonclicklistener);
            viewholder.icon = (ImageView)view.findViewById(biz.AR.id.prefecture_recoment_icon);
            view.setTag(viewholder);
            view.setTag(view.getId(), convertviewonclicklistener);
            viewholder1 = viewholder;
        } else
        {
            viewholder1 = (ViewHolder)view.getTag();
            convertviewonclicklistener = (ConvertViewOnClickListener)view.getTag(view.getId());
        }
        ImageLoaderManager.getInstance().displayImage(prefectureinfo.getIconUrl(), viewholder1.icon, options);
        viewholder1.name.setText(prefectureinfo.getName());
        if(prefectureinfo.isFinish())
        {
            viewholder1.time.setText(res.getString(biz.AR.string.exercise_over));
            viewholder1.time.setTextColor(res.getColor(biz.AR.color.exercise_over_colore));
        } else
        {
            viewholder1.time.setText((new StringBuilder()).append(exerciseTimeStr).append(prefectureinfo.getTime()).toString());
        }
        viewholder1.browse_count.setText(String.valueOf(prefectureinfo.getBrowseCount()));
        convertviewonclicklistener.position = i;
        return view;
    }

    public void setPrefectureInfos(List list)
    {
        prefectureInfos = list;
    }

    private DataCollectInfo datainfo;
    private String exerciseTimeStr;
    private Context mContext;
    private LayoutInflater mInflater;
    private DisplayImageOptions options;
    private List prefectureInfos;
    private Resources res;


}
