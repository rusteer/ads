// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.List;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gionee.aora.market.module.CallRecordsInfo;

public class CallRecordsAdapter extends BaseAdapter {
    class Holder {
        ImageView icon;
        TextView name;
        TextView sale;
        TextView time;
        Holder() {
            super();
        }
    }
    private Context mcontext;
    private List records;
    public CallRecordsAdapter(Context context, List list) {
        records = list;
        mcontext = context;
    }
    @Override
    public int getCount() {
        return records.size();
    }
    @Override
    public Object getItem(int i) {
        return records.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public List getRecords() {
        return records;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        Holder holder;
        CallRecordsInfo callrecordsinfo;
        if (view == null) {
            view = LayoutInflater.from(mcontext).inflate(biz.AR.layout.callrecords_child_layout, null);
            holder = new Holder();
            holder.icon = (ImageView) view.findViewById(biz.AR.id.call_records_icon);
            holder.name = (TextView) view.findViewById(biz.AR.id.call_records_name);
            holder.sale = (TextView) view.findViewById(biz.AR.id.call_records_hold);
            holder.time = (TextView) view.findViewById(biz.AR.id.call_records_time);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        callrecordsinfo = (CallRecordsInfo) records.get(i);
        if (callrecordsinfo.getCallname() != null && !callrecordsinfo.getCallname().equals("")) holder.name.setText(callrecordsinfo.getCallname());
        else holder.name.setText(callrecordsinfo.getCallee());
        holder.sale.setText(callrecordsinfo.getSale());
        holder.time.setText(callrecordsinfo.getTime());
        if (callrecordsinfo.getPhotouri() != null) {
            java.io.InputStream inputstream = android.provider.ContactsContract.Contacts.openContactPhotoInputStream(mcontext.getContentResolver(), callrecordsinfo.getPhotouri());
            holder.icon.setImageBitmap(BitmapFactory.decodeStream(inputstream));
            return view;
        } else {
            holder.icon.setImageResource(biz.AR.drawable.contacts_defalut);
            return view;
        }
    }
    public void setRecords(List list) {
        records = list;
    }
}
