// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.market.control.CacheDataManager;
import com.gionee.aora.market.control.CallManager;
import com.gionee.aora.market.gui.call.view.ClearEditText;
import com.gionee.aora.market.gui.call.view.SideBar;
import com.gionee.aora.market.gui.main.BaseTabFragment;
import com.gionee.aora.market.gui.view.dragview.MyDragLayout;
import com.gionee.aora.market.module.ContactsInfo;
import com.gionee.aora.market.util.PinyinUtil;

// Referenced classes of package com.gionee.aora.market.gui.call:
//            SortGroupMemberAdapter, CharacterParser, PinyinComparator, PhoneAdapter
public class ContactsFragment extends BaseTabFragment {
    private static final String CONTACTS_CACHE_DATA = "cache_data_contacts_";
    private static final int LOAD_CACHE_DATA = 0;
    private static final int LOAD_DATA = 1;
    private SortGroupMemberAdapter adapter;
    private MarketFloateDialogBuilder builder;
    private CacheDataManager cacheDataManager;
    private List cachecontacts;
    private CharacterParser characterParser;
    private List contacts;
    private Dialog dialog;
    private boolean hasloadingcache;
    private int lastFirstVisibleItem;
    private ClearEditText mClearEditText;
    private View moreview;
    private PhoneAdapter padapter;
    private ListView phoneLv;
    private ImageView phoneicon;
    private TextView phonename;
    private TextView phoneno;
    private PinyinComparator pinyinComparator;
    private SideBar sideBar;
    private ListView sortListView;
    private TextView title;
    private LinearLayout titleLayout;
    private TextView tvNofriends;
    public ContactsFragment() {
        lastFirstVisibleItem = -1;
        hasloadingcache = false;
    }
    @Override
    public void changePage(MyDragLayout mydraglayout) {}
    private List filledData(List list) {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            ContactsInfo contactsinfo = (ContactsInfo) iterator.next();
            char c = Character.toUpperCase(PinyinUtil.cn2py(contactsinfo.getName().substring(0, 1)).charAt(0));
            if (c < 'A' || c > 'Z') contactsinfo.setSortLetters("#");
            else contactsinfo.setSortLetters(String.valueOf(c));
            arraylist.add(contactsinfo);
        }
        return arraylist;
    }
    private void filterData(String s) {
        ArrayList arraylist = new ArrayList();
        Object obj;
        if (TextUtils.isEmpty(s)) {
            obj = adapter.getContactsInfos();
            tvNofriends.setVisibility(8);
            titleLayout.setVisibility(0);
        } else {
            arraylist.clear();
            label0: do {
                    ContactsInfo contactsinfo;
                    List list;
                label1: do {
                            for (Iterator iterator = adapter.getContactsInfos().iterator(); iterator.hasNext(); arraylist.add(contactsinfo)) {
                                contactsinfo = (ContactsInfo) iterator.next();
                                String s1 = contactsinfo.getName();
                                list = contactsinfo.getPhone();
                                if (s1.indexOf(s.toString()) == -1 && !characterParser.getSelling(s1).startsWith(s.toString())) continue label1;
                            }
                    break label0;
                        } while (list == null || list.size() <= 0);
                    int i = 0;
                    while (i < list.size()) {
                        if (((String) list.get(i)).contains(s)) arraylist.add(contactsinfo);
                        i++;
                    }
                } while (true);
            obj = arraylist;
        }
        Collections.sort((List) obj, pinyinComparator);
        adapter.updateListView((List) obj);
        if (((List) obj).size() == 0) {
            tvNofriends.setVisibility(0);
            return;
        } else {
            tvNofriends.setVisibility(8);
            return;
        }
    }
    public int getPositionForSection(int i) {
        for (int j = 0; j < adapter.getContactsInfos().size(); j++)
            if (((ContactsInfo) adapter.getContactsInfos().get(j)).getSortLetters().toUpperCase().charAt(0) == i) return j;
        return -1;
    }
    public int getSectionForPosition(int i) {
        return ((ContactsInfo) adapter.getContactsInfos().get(i)).getSortLetters().charAt(0);
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            case 0://L2_L2:
                cachecontacts = (List) cacheDataManager.getCacheDataToFile2(getActivity(), "cache_data_contacts_");
                if (cachecontacts == null) return false;
                if (cachecontacts.size() == 0) return false;
                break;
            case 1://L3_L3:
                contacts = ContactsInfo.getContacts(getActivity());
                if (contacts == null) return false;
                if (contacts.size() == 0) return false;
                break;
        }
        return true;
    }
    @Override
    protected void initView(RelativeLayout relativelayout) {
        setCenterView(biz.AR.layout.contacts_layout);
        setTitleBarViewVisibility(false);
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        contacts = new ArrayList();
        titleLayout = (LinearLayout) relativelayout.findViewById(biz.AR.id.contacts_title_layout);
        title = (TextView) relativelayout.findViewById(biz.AR.id.contacts_title_layout_catalog);
        tvNofriends = (TextView) relativelayout.findViewById(biz.AR.id.contacts_no);
        sideBar = (SideBar) relativelayout.findViewById(biz.AR.id.contacts_sidrbar);
        mClearEditText = (ClearEditText) relativelayout.findViewById(biz.AR.id.contacts_search);
        sortListView = (ListView) relativelayout.findViewById(biz.AR.id.contacts_);
        adapter = new SortGroupMemberAdapter(getActivity(), contacts);
        builder = new MarketFloateDialogBuilder(getActivity());
        moreview = LayoutInflater.from(getActivity()).inflate(biz.AR.layout.phone_more_layout, null);
        phoneLv = (ListView) moreview.findViewById(biz.AR.id.phone_more_);
        phoneicon = (ImageView) moreview.findViewById(biz.AR.id.phone_more_icon);
        phonename = (TextView) moreview.findViewById(biz.AR.id.phone_more_name);
        phoneno = (TextView) moreview.findViewById(biz.AR.id.phone_more_no);
        builder.setCenterView(moreview, null);
        builder.setTitleVisibility(false);
        builder.setCancelable(true);
        dialog = builder.crteate();
        if (getActivity().getWindowManager().getDefaultDisplay().getHeight() < 720) sideBar.setVisibility(8);
        sideBar.setOnTouchingLetterChangedListener(new com.gionee.aora.market.gui.call.view.SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int i = adapter.getPositionForSection(s.charAt(0));
                if (i != -1) sortListView.setSelection(i);
            }
        });
        sortListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterview, View view, int i, long l) {
                ContactsInfo contactsinfo = (ContactsInfo) adapter.getItem(i);
                if (dialog != null) dialog.cancel();
                phonename.setText(contactsinfo.getName());
                if (contactsinfo.getPhoto() != null) {
                    java.io.InputStream inputstream = android.provider.ContactsContract.Contacts.openContactPhotoInputStream(getActivity().getContentResolver(),
                            Uri.parse(contactsinfo.getPhoto()));
                    phoneicon.setImageBitmap(BitmapFactory.decodeStream(inputstream));
                } else {
                    phoneicon.setImageResource(biz.AR.drawable.contacts_defalut);
                }
                if (contactsinfo.getPhone() != null && contactsinfo.getPhone().size() == 1) {
                    CallManager.doCall(UserStorage.getDefaultUserInfo(getActivity()), (String)contactsinfo.getPhone().get(0), getActivity());
                    return;
                }
                if (contactsinfo.getPhone() != null && contactsinfo.getPhone().size() > 1) {
                    phoneLv.setVisibility(0);
                    phoneno.setVisibility(8);
                    padapter = new PhoneAdapter(getActivity(), contactsinfo.getPhone());
                    phoneLv.setAdapter(padapter);
                    dialog.show();
                    return;
                } else {
                    phoneLv.setVisibility(8);
                    phoneno.setVisibility(0);
                    dialog.show();
                    return;
                }
            }
        });
        sortListView.setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView abslistview, int i, int j, int k) {
                if (!(adapter.getContactsInfos() == null || adapter.getContactsInfos().size() == 0)) {
                    //L1_L1:
                    int l = getSectionForPosition(i);
                    int i1;
                    int j1;
                    View view;
                    int k1;
                    int l1;
                    android.view.ViewGroup.MarginLayoutParams marginlayoutparams;
                    if (k > 1) i1 = getSectionForPosition(i + 1);
                    else i1 = l;
                    j1 = getPositionForSection(i1);
                    if (i != lastFirstVisibleItem) {
                        android.view.ViewGroup.MarginLayoutParams marginlayoutparams1 = (android.view.ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                        marginlayoutparams1.topMargin = 0;
                        titleLayout.setLayoutParams(marginlayoutparams1);
                        title.setText(((ContactsInfo) adapter.getContactsInfos().get(getPositionForSection(l))).getSortLetters());
                    }
                    if (j1 == i + 1) {
                        //L3_L3:
                        view = abslistview.getChildAt(0);
                        if (view != null) {
                            //L5_L5:
                            k1 = titleLayout.getHeight();
                            l1 = view.getBottom();
                            marginlayoutparams = (android.view.ViewGroup.MarginLayoutParams) titleLayout.getLayoutParams();
                            if (l1 < k1) {
                                //L6_L6:
                                marginlayoutparams.topMargin = (int) (l1 - k1);
                                titleLayout.setLayoutParams(marginlayoutparams);
                            } else {
                                //L7_L7:
                                if (marginlayoutparams.topMargin != 0) {
                                    marginlayoutparams.topMargin = 0;
                                    titleLayout.setLayoutParams(marginlayoutparams);
                                }
                            }
                        }
                    }
                    lastFirstVisibleItem = i;
                }
            }
            @Override
            public void onScrollStateChanged(AbsListView abslistview, int i) {}
        });
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {}
            @Override
            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {}
            @Override
            public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
                titleLayout.setVisibility(8);
                filterData(charsequence.toString());
            }
        });
    }
    @Override
    protected void loadData() {}
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            cacheDataManager = CacheDataManager.getInstance();
            setLoadDataOnce(false);
        }
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            case 0://L2_L2:
                if (flag) {
                    DLog.i("denglihua", "\u663E\u793A\u7F13\u5B58\u8054\u7CFB\u4EBA\u6570\u636E");
                    hasloadingcache = true;
                    loadingView.setVisibility(8);
                    cachecontacts = filledData(cachecontacts);
                    Collections.sort(cachecontacts, pinyinComparator);
                    adapter.updateListView(cachecontacts);
                    sortListView.setAdapter(adapter);
                }
                Integer ainteger1[] = new Integer[1];
                ainteger1[0] = Integer.valueOf(1);
                doLoadData(ainteger1);
                break;
            case 1://L3_L3:
                if (flag) {
                    loadingView.setVisibility(8);
                    boolean flag1 = cacheDataManager.saveCachDataToFile2(getActivity(), "cache_data_contacts_", contacts);
                    DLog.i("denglihua", new StringBuilder().append("\u7F13\u5B58\u8054\u7CFB\u4EBA\u6570\u636E").append(flag1).toString());
                    contacts = filledData(contacts);
                    Collections.sort(contacts, pinyinComparator);
                    if (hasloadingcache) {
                        adapter.updateListView(contacts);
                        return;
                    } else {
                        adapter.updateListView(contacts);
                        sortListView.setAdapter(adapter);
                        return;
                    }
                }
                if (!hasloadingcache) {
                    loadingView.setVisibility(8);
                    showErrorView(biz.AR.drawable.no_update_apps, "\u60A8\u6CA1\u6709\u8054\u7CFB\u4EBA", false);
                    return;
                }
                break;
        }
    }
    @Override
    protected void setDataAgain() {}
    @Override
    protected void tryAgain() {
        super.tryAgain();
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(1);
        doLoadData(ainteger);
    }
    /*
        static PhoneAdapter access$702(ContactsFragment contactsfragment, PhoneAdapter phoneadapter)
        {
            contactsfragment.padapter = phoneadapter;
            return phoneadapter;
        }

    */
    /*
        static int access$802(ContactsFragment contactsfragment, int i)
        {
            contactsfragment.lastFirstVisibleItem = i;
            return i;
        }

    */
}
