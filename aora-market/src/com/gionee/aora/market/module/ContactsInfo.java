// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.gionee.aora.market.module;

import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import com.aora.base.util.DLog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactsInfo
    implements Serializable
{

    public ContactsInfo()
    {
    }

    public static List getContacts(Context context)
    {
        if(context == null)
            return null;
        ArrayList arraylist = new ArrayList();
        DLog.d("denglihua", (new StringBuilder()).append("\u5F00\u59CB\u65F6\u95F4\uFF1A").append(System.currentTimeMillis()).toString());
        ContentResolver contentresolver = context.getContentResolver();
        Cursor cursor;
        ContactsInfo contactsinfo;
        for(cursor = contentresolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI, null, null, null, null); cursor.moveToNext(); arraylist.add(contactsinfo))
        {
            contactsinfo = new ContactsInfo();
            contactsinfo.setName(cursor.getString(cursor.getColumnIndex("display_name")));
            long l = cursor.getLong(cursor.getColumnIndex("_id"));
            contactsinfo.setId((new StringBuilder()).append(l).append("").toString());
            if(cursor.getLong(cursor.getColumnIndex("photo_id")) > 0L)
                contactsinfo.setPhoto(ContentUris.withAppendedId(android.provider.ContactsContract.Contacts.CONTENT_URI, l).toString());
            Cursor cursor1 = contentresolver.query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, (new StringBuilder()).append("contact_id = ").append(l).toString(), null, null);
            ArrayList arraylist1 = new ArrayList();
            for(; cursor1.moveToNext(); arraylist1.add(cursor1.getString(cursor1.getColumnIndex("data1")).replaceAll("\\s*", "")));
            contactsinfo.setPhone(arraylist1);
            cursor1.close();
        }

        cursor.close();
        DLog.d("denglihua", (new StringBuilder()).append("\u7ED3\u675F\u65F6\u95F4\uFF1A").append(System.currentTimeMillis()).toString());
        return arraylist;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public List getPhone()
    {
        return phone;
    }

    public String getPhoto()
    {
        return photourl;
    }

    public String getSortLetters()
    {
        return sortLetters;
    }

    public void setId(String s)
    {
        id = s;
    }

    public void setName(String s)
    {
        name = s;
    }

    public void setPhone(List list)
    {
        phone = list;
    }

    public void setPhoto(String s)
    {
        photourl = s;
    }

    public void setSortLetters(String s)
    {
        sortLetters = s;
    }

    private static final long serialVersionUID = 0x1cfbbe4cb2c2a4cfL;
    private String id;
    private String name;
    private List phone;
    private String photourl;
    private String sortLetters;
}
