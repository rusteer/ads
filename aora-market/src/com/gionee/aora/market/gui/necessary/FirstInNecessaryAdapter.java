// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.necessary;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.module.AppInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import java.util.*;

// Referenced classes of package com.gionee.aora.market.gui.necessary:
//            FirstInNecessaryCheckedAppManager

public class FirstInNecessaryAdapter extends BaseExpandableListAdapter
{
    public class GroupNecessarysoftListViewHolder
    {

        CheckBox themeCheckBox;
        RelativeLayout themeLay;
        TextView themeName;

        public GroupNecessarysoftListViewHolder()
        {
            super();
        }
    }

    public class NecessarysoftListViewHolder
    {

        CheckBox leftCheckBox;
        ImageView leftIcon;
        TextView leftInstalled;
        RelativeLayout leftLay;
        TextView leftName;
        TextView leftSize;
        CheckBox rightCheckBox;
        ImageView rightIcon;
        TextView rightInstalled;
        RelativeLayout rightLay;
        TextView rightName;
        TextView rightSize;

        public NecessarysoftListViewHolder()
        {
            super();
        }
    }


    public FirstInNecessaryAdapter(Context context1, List list, List list1, Handler handler)
    {
        groupList = null;
        childrenList = null;
        imageLoader = null;
        options = null;
        mHandler = null;
        itemOnClickListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                AppInfo appinfo = (AppInfo)view.getTag();
                if(appinfo != null) {
                    //L1_L1:
                    DLog.i("lilijun", (new StringBuilder()).append("appInfo.getName()------>>>").append(appinfo.getName()).toString());
                    if( checkedAppManager.isHaveAppInfo(appinfo.getPackageName())) {
                        //L3_L3:
                        checkedAppManager.removeAppInfo(appinfo.getPackageName());
                    }else{
                        //L4_L4:
                        if(!softwareManager.getSoftwaresMap().containsKey(appinfo.getPackageName()))
                            checkedAppManager.addAppInfo(appinfo);
                    }

                    mHandler.sendEmptyMessage(1);
                    notifyDataSetChanged();
                } 
            }

             
        }
;
        groupCheckBoxOnClickListener = new android.view.View.OnClickListener() {

            public void onClick(View view)
            {
                int i = ((Integer)view.getTag()).intValue();
                if(((Boolean)view.getTag(biz.AR.id.necessary_new_group_title_lay)).booleanValue())
                {
                    AppInfo appinfo1;
                    for(Iterator iterator1 = ((List)childrenList.get(i)).iterator(); iterator1.hasNext(); checkedAppManager.removeAppInfo(appinfo1.getPackageName()))
                        appinfo1 = (AppInfo)iterator1.next();

                } else
                {
                    Iterator iterator = ((List)childrenList.get(i)).iterator();
                    do
                    {
                        if(!iterator.hasNext())
                            break;
                        AppInfo appinfo = (AppInfo)iterator.next();
                        if(!softwareManager.getSoftwaresMap().containsKey(appinfo.getPackageName()))
                            checkedAppManager.addAppInfo(appinfo);
                    } while(true);
                }
                mHandler.sendEmptyMessage(1);
                notifyDataSetChanged();
            }

             
        }
;
        groupList = list;
        childrenList = list1;
        context = context1;
        mHandler = handler;
        softwareManager = SoftwareManager.getInstace();
        imageLoader = ImageLoaderManager.getInstance();
        options = getImageLoaderOptions();
        checkedAppManager = FirstInNecessaryCheckedAppManager.getInstance();
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
label0:
        {
            if(view == null)
            {
                RelativeLayout relativelayout = (RelativeLayout)View.inflate(context, biz.AR.layout.first_in_necessary_new_adapter_layout, null);
                NecessarysoftListViewHolder necessarysoftlistviewholder1 = new NecessarysoftListViewHolder();
                necessarysoftlistviewholder1.leftLay = (RelativeLayout)relativelayout.findViewById(biz.AR.id.necessary_new_left_app_lay);
                necessarysoftlistviewholder1.rightLay = (RelativeLayout)relativelayout.findViewById(biz.AR.id.necessary_new_right_app_lay);
                necessarysoftlistviewholder1.leftLay.setOnClickListener(itemOnClickListener);
                necessarysoftlistviewholder1.rightLay.setOnClickListener(itemOnClickListener);
                necessarysoftlistviewholder1.leftCheckBox = (CheckBox)relativelayout.findViewById(biz.AR.id.necessary_new_left_checkbox);
                necessarysoftlistviewholder1.rightCheckBox = (CheckBox)relativelayout.findViewById(biz.AR.id.necessary_new_right_checkbox);
                necessarysoftlistviewholder1.leftIcon = (ImageView)relativelayout.findViewById(biz.AR.id.necessary_new_left_app_icon);
                necessarysoftlistviewholder1.rightIcon = (ImageView)relativelayout.findViewById(biz.AR.id.necessary_new_right_app_icon);
                necessarysoftlistviewholder1.leftInstalled = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_left_installed_icon);
                necessarysoftlistviewholder1.rightInstalled = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_right_installed_icon);
                necessarysoftlistviewholder1.leftName = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_left_app_name);
                necessarysoftlistviewholder1.rightName = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_right_app_name);
                necessarysoftlistviewholder1.leftSize = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_left_app_size);
                necessarysoftlistviewholder1.rightSize = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_right_app_size);
                relativelayout.setTag(necessarysoftlistviewholder1);
                necessarysoftlistviewholder = necessarysoftlistviewholder1;
                view = relativelayout;
            } else
            {
                necessarysoftlistviewholder = (NecessarysoftListViewHolder)view.getTag();
            }
            if(j * 2 < ((List)childrenList.get(i)).size())
            {
                AppInfo appinfo = (AppInfo)((List)childrenList.get(i)).get(j * 2);
                imageLoader.displayImage(appinfo.getIconUrl(), necessarysoftlistviewholder.leftIcon, options);
                necessarysoftlistviewholder.leftName.setText(appinfo.getName());
                necessarysoftlistviewholder.leftSize.setText(appinfo.getSize());
                necessarysoftlistviewholder.leftLay.setTag(appinfo);
                AppInfo appinfo1;
                if(softwareManager.getSoftwaresMap().containsKey(appinfo.getPackageName()))
                {
                    necessarysoftlistviewholder.leftCheckBox.setVisibility(8);
                    necessarysoftlistviewholder.leftInstalled.setVisibility(0);
                } else
                {
                    necessarysoftlistviewholder.leftCheckBox.setVisibility(0);
                    necessarysoftlistviewholder.leftCheckBox.setChecked(checkedAppManager.isHaveAppInfo(appinfo.getPackageName()));
                    necessarysoftlistviewholder.leftInstalled.setVisibility(8);
                }
                if(1 + j * 2 >= ((List)childrenList.get(i)).size())
                    break label0;
                appinfo1 = (AppInfo)((List)childrenList.get(i)).get(1 + j * 2);
                imageLoader.displayImage(appinfo1.getIconUrl(), necessarysoftlistviewholder.rightIcon, options);
                necessarysoftlistviewholder.rightName.setText(appinfo1.getName());
                necessarysoftlistviewholder.rightSize.setText(appinfo1.getSize());
                necessarysoftlistviewholder.rightLay.setTag(appinfo1);
                if(softwareManager.getSoftwaresMap().containsKey(appinfo1.getPackageName()))
                {
                    necessarysoftlistviewholder.rightCheckBox.setVisibility(8);
                    necessarysoftlistviewholder.rightInstalled.setVisibility(0);
                } else
                {
                    necessarysoftlistviewholder.rightCheckBox.setVisibility(0);
                    necessarysoftlistviewholder.rightCheckBox.setChecked(checkedAppManager.isHaveAppInfo(appinfo1.getPackageName()));
                    necessarysoftlistviewholder.rightInstalled.setVisibility(8);
                }
                necessarysoftlistviewholder.rightIcon.setVisibility(0);
                necessarysoftlistviewholder.rightName.setVisibility(0);
                necessarysoftlistviewholder.rightSize.setVisibility(0);
            }
            return view;
        }
        necessarysoftlistviewholder.rightIcon.setVisibility(8);
        necessarysoftlistviewholder.rightName.setVisibility(8);
        necessarysoftlistviewholder.rightSize.setVisibility(8);
        necessarysoftlistviewholder.rightCheckBox.setVisibility(8);
        necessarysoftlistviewholder.rightInstalled.setVisibility(8);
        return view;
    }

    public int getChildrenCount(int i)
    {
        if(childrenList == null)
            return 0;
        int j = ((List)childrenList.get(i)).size();
        if(j % 2 == 0)
            return j / 2;
        else
            return 1 + j / 2;
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
        boolean flag1=true;
        int j = biz.AR.id.necessary_new_group_title_lay;
        Iterator iterator;
        AppInfo appinfo;
        if(view == null)
        {
            RelativeLayout relativelayout = (RelativeLayout)View.inflate(context, biz.AR.layout.first_in_necessary_group_item, null);
            GroupNecessarysoftListViewHolder groupnecessarysoftlistviewholder1 = new GroupNecessarysoftListViewHolder();
            groupnecessarysoftlistviewholder1.themeLay = (RelativeLayout)relativelayout.findViewById(j);
            groupnecessarysoftlistviewholder1.themeName = (TextView)relativelayout.findViewById(biz.AR.id.necessary_new_theme_name);
            groupnecessarysoftlistviewholder1.themeCheckBox = (CheckBox)relativelayout.findViewById(biz.AR.id.necessary_new_theme_checkbox);
            groupnecessarysoftlistviewholder1.themeLay.setOnClickListener(groupCheckBoxOnClickListener);
            relativelayout.setTag(groupnecessarysoftlistviewholder1);
            groupnecessarysoftlistviewholder = groupnecessarysoftlistviewholder1;
            view = relativelayout;
        } else
        {
            groupnecessarysoftlistviewholder = (GroupNecessarysoftListViewHolder)view.getTag();
        }
        groupnecessarysoftlistviewholder.themeName.setText((CharSequence)groupList.get(i));
        groupnecessarysoftlistviewholder.themeLay.setTag(Integer.valueOf(i));
        groupnecessarysoftlistviewholder.themeLay.setTag(j, Boolean.valueOf(groupnecessarysoftlistviewholder.themeCheckBox.isChecked()));
        iterator = ((List)childrenList.get(i)).iterator();
        if( iterator.hasNext()){
        appinfo = (AppInfo)iterator.next();
        if(!checkedAppManager.isHaveAppInfo(appinfo.getPackageName()))
            
        flag1 = false;
        }
        
        if(flag1)
        {
            groupnecessarysoftlistviewholder.themeCheckBox.setChecked(true);
            
        } else
        {
            groupnecessarysoftlistviewholder.themeCheckBox.setChecked(false);
        }
        return view;
        
        
 
    }

    protected DisplayImageOptions getImageLoaderOptions()
    {
        int i = biz.AR.drawable.cp_defaulf;
        return (new com.nostra13.universalimageloader.core.DisplayImageOptions.Builder()).showStubImage(i).showImageForEmptyUri(i).showImageOnFail(i).cacheInMemory().cacheOnDisc().build();
    }

    public boolean hasStableIds()
    {
        return true;
    }

    public boolean isChildSelectable(int i, int j)
    {
        return true;
    }

    private FirstInNecessaryCheckedAppManager checkedAppManager;
    private List childrenList;
    private Context context;
    private android.view.View.OnClickListener groupCheckBoxOnClickListener;
    private List groupList;
    protected ImageLoaderManager imageLoader;
    private android.view.View.OnClickListener itemOnClickListener;
    private Handler mHandler;
    protected DisplayImageOptions options;
    private SoftwareManager softwareManager;




}
