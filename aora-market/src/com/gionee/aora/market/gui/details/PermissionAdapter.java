// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gionee.aora.market.module.PermissionInfo;

public class PermissionAdapter extends BaseAdapter {
    class Holder {
        public TextView describle;
        public TextView title;
        Holder() {
            super();
        }
    }
    private Context context;
    private List permission;
    public PermissionAdapter(List list, Context context1) {
        permission = list;
        context = context1;
    }
    @Override
    public int getCount() {
        return permission.size();
    }
    @Override
    public Object getItem(int i) {
        return permission.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public List getPermission() {
        return permission;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(biz.AR.layout.permission_child, null);
            holder = new Holder();
            holder.title = (TextView) view.findViewById(biz.AR.id.permission_title);
            holder.describle = (TextView) view.findViewById(biz.AR.id.permission_describle);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.title.setText(((PermissionInfo) permission.get(i)).getPermissionTitle());
        holder.describle.setText(((PermissionInfo) permission.get(i)).getPermissionDescrible());
        return view;
    }
    public void setPermission(List list) {
        permission = list;
    }
}
