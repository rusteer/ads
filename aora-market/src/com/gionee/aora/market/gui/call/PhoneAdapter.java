// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PhoneAdapter extends BaseAdapter {
    class Holder {
        TextView phone;
    }
    private List list;
    private Context mContext;
    public PhoneAdapter(Context context, List list1) {
        list = null;
        mContext = context;
        list = list1;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public Object getItem(int i) {
        return list.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(biz.AR.layout.phone_child_layout, null);
            Holder holder1 = new Holder();
            holder1.phone = (TextView) view.findViewById(biz.AR.id.phone_name);
            view.setTag(holder1);
            holder = holder1;
        } else {
            holder = (Holder) view.getTag();
        }
        holder.phone.setText((CharSequence) list.get(i));
        return view;
    }
}
