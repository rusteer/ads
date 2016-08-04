// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.List;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.gionee.aora.market.module.ContactsInfo;

public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    static final class ViewHolder {
        ImageView icon;
        TextView name;
        TextView sort;
        ViewHolder() {}
    }
    private List list;
    private Context mContext;
    public SortGroupMemberAdapter(Context context, List list1) {
        list = null;
        mContext = context;
        list = list1;
    }
    private String getAlpha(String s) {
        String s1 = s.trim().substring(0, 1).toUpperCase();
        if (s1.matches("[A-Z]")) return s1;
        else return "#";
    }
    public List getContactsInfos() {
        return list;
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
    public int getPositionForSection(int i) {
        for (int j = 0; j < getCount(); j++)
            if (((ContactsInfo) list.get(j)).getSortLetters().toUpperCase().charAt(0) == i) return j;
        return -1;
    }
    @Override
    public int getSectionForPosition(int i) {
        return ((ContactsInfo) list.get(i)).getSortLetters().charAt(0);
    }
    @Override
    public Object[] getSections() {
        return null;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        ContactsInfo contactsinfo = (ContactsInfo) list.get(i);
        ViewHolder viewholder1;
        if (view == null) {
            ViewHolder viewholder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(biz.AR.layout.contacts_child_item, null);
            viewholder.sort = (TextView) view.findViewById(biz.AR.id.contacts_sort);
            viewholder.name = (TextView) view.findViewById(biz.AR.id.contacts_name);
            viewholder.icon = (ImageView) view.findViewById(biz.AR.id.contacts_icon);
            view.setTag(viewholder);
            viewholder1 = viewholder;
        } else {
            viewholder1 = (ViewHolder) view.getTag();
        }
        if (i == getPositionForSection(getSectionForPosition(i))) {
            viewholder1.sort.setVisibility(0);
            viewholder1.sort.setText(contactsinfo.getSortLetters());
        } else {
            viewholder1.sort.setVisibility(8);
        }
        viewholder1.name.setText(contactsinfo.getName());
        if (contactsinfo.getPhoto() != null) {
            java.io.InputStream inputstream = android.provider.ContactsContract.Contacts.openContactPhotoInputStream(mContext.getContentResolver(),
                    Uri.parse(contactsinfo.getPhoto()));
            viewholder1.icon.setImageBitmap(BitmapFactory.decodeStream(inputstream));
            return view;
        } else {
            viewholder1.icon.setImageResource(biz.AR.drawable.contacts_defalut);
            return view;
        }
    }
    public void updateListView(List list1) {
        list = list1;
        notifyDataSetChanged();
    }
}
