// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.gui.share;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.*;
import android.widget.*;
import java.util.List;

public class ShareAdapter extends BaseAdapter
{
    class Holder
    {

        public ImageView icon;
        public TextView name;

        Holder()
        {
            super();
        }
    }


    public ShareAdapter(List list, Context context1)
    {
        context = context1;
        rinfos = list;
        pkgmanager = context1.getPackageManager();
    }

    public int getCount()
    {
        return rinfos.size();
    }

    public Object getItem(int i)
    {
        return rinfos.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    public View getView(int i, View view, ViewGroup viewgroup)
    {
        Holder holder;
        ResolveInfo resolveinfo;
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(biz.AR.layout.share_child_layout, null);
            holder = new Holder();
            holder.icon = (ImageView)view.findViewById(biz.AR.id.share_icon);
            holder.name = (TextView)view.findViewById(biz.AR.id.share_name);
            view.setTag(holder);
        } else
        {
            holder = (Holder)view.getTag();
        }
        resolveinfo = (ResolveInfo)rinfos.get(i);
        holder.icon.setImageDrawable(resolveinfo.loadIcon(pkgmanager));
        holder.name.setText(resolveinfo.loadLabel(pkgmanager).toString());
        return view;
    }

    private Context context;
    private PackageManager pkgmanager;
    private List rinfos;
}
