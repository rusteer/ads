// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.Comparator;
import com.gionee.aora.market.module.ContactsInfo;

public class PinyinComparator implements Comparator {
    public PinyinComparator() {}
    public int compare(ContactsInfo contactsinfo, ContactsInfo contactsinfo1) {
        if (contactsinfo.getSortLetters().equals("@") || contactsinfo1.getSortLetters().equals("#")) return -1;
        if (contactsinfo.getSortLetters().equals("#") || contactsinfo1.getSortLetters().equals("@")) return 1;
        else return contactsinfo.getSortLetters().compareTo(contactsinfo1.getSortLetters());
    }
    @Override
    public int compare(Object obj, Object obj1) {
        return compare((ContactsInfo) obj, (ContactsInfo) obj1);
    }
}
