// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.control.CallManager;
import com.gionee.aora.market.gui.main.BaseTabFragment;
import com.gionee.aora.market.gui.view.LoadMoreScrollListener;
import com.gionee.aora.market.gui.view.LoadMoreView;
import com.gionee.aora.market.gui.view.MarketListView;
import com.gionee.aora.market.gui.view.dragview.MyDragLayout;
import com.gionee.aora.market.module.CallRecordsInfo;
import com.gionee.aora.market.module.ContactsInfo;
import com.gionee.aora.market.net.CallRecordsNet;
import com.nostra13.universalimageloader.core.ImageLoader;

// Referenced classes of package com.gionee.aora.market.gui.call:
//            CallRecordsAdapter
public class CallRecordsFragment extends BaseTabFragment {
    private final int LOAD_DATA = 0;
    private final int LOAD_DATA_COUNT = 20;
    private final int LOAD_MORE_DATA = 1;
    private List Morecallrecords;
    private CallRecordsAdapter adapter;
    private List callrecords;
    private UserInfo info;
    private MarketListView listView;
    private LoadMoreView loadMoreView;
    public CallRecordsFragment() {
        loadMoreView = null;
        info = null;
    }
    @Override
    public void changePage(MyDragLayout mydraglayout) {}
    private List contactsCall(List list) {
        List list1 = ContactsInfo.getContacts(getActivity());
        if (list1 != null && list1.size() != 0) {
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                CallRecordsInfo callrecordsinfo = (CallRecordsInfo) iterator.next();
                Iterator iterator1 = list1.iterator();
                while (iterator1.hasNext()) {
                    ContactsInfo contactsinfo = (ContactsInfo) iterator1.next();
                    if (contactsinfo.getPhone() != null && contactsinfo.getPhone().size() != 0) {
                        Iterator iterator2 = contactsinfo.getPhone().iterator();
                        while (iterator2.hasNext()) {
                            String s = (String) iterator2.next();
                            if (callrecordsinfo.getCallee().equals(s)) callrecordsinfo.setCallname(contactsinfo.getName());
                        }
                    }
                }
            }
        }
        return list;
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            default:
                return true;
            case 0: // '\0'
                callrecords = CallRecordsNet.getCallRecords(ainteger[1].intValue(), 20, info);
                if (callrecords != null && callrecords.size() != 0) {
                    callrecords = contactsCall(callrecords);
                    return true;
                } else {
                    return false;
                }
            case 1: // '\001'
                break;
        }
        if (Morecallrecords != null) {
            Morecallrecords.clear();
            Morecallrecords = null;
        }
        Morecallrecords = CallRecordsNet.getCallRecords(ainteger[1].intValue(), 20, info);
        if (Morecallrecords != null) {
            Morecallrecords = contactsCall(Morecallrecords);
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void initView(RelativeLayout relativelayout) {
        setCenterView(biz.AR.layout.call_records_layout);
        setTitleBarViewVisibility(false);
        callrecords = new ArrayList();
        listView = (MarketListView) relativelayout.findViewById(biz.AR.id.callrecordslv);
        adapter = new CallRecordsAdapter(getActivity(), callrecords);
        loadMoreView = new LoadMoreView(getActivity()) {
            @Override
            public void tryAgain() {
                loadMoreData();
            }
        };
        listView.setOnScrollListener(new LoadMoreScrollListener(ImageLoader.getInstance(), true, true,
                new com.gionee.aora.market.gui.view.LoadMoreScrollListener.setOnScrollToEndListener() {
                    @Override
                    public void loadMoreWhenScrollToEnd() {
                        loadMoreData();
                    }
                }));
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterview, View view, int i, long l) {
                CallManager.doCall(UserStorage.getDefaultUserInfo(getActivity()), ((CallRecordsInfo) callrecords.get(i)).getCallee(), getActivity());
            }
        });
    }
    @Override
    protected void loadData() {}
    private void loadMoreData() {
        if (callrecords == null || loadingData || loadingDataEnd || loadMoreView.isShowTryAgain()) {
            return;
        } else {
            loadingData = true;
            Integer ainteger[] = new Integer[2];
            ainteger[0] = Integer.valueOf(1);
            ainteger[1] = Integer.valueOf(callrecords.size());
            doLoadData(ainteger);
            return;
        }
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        info = UserStorage.getDefaultUserInfo(getActivity());
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            default:
                return;
            case 0: // '\0'
                if (flag) {
                    if (callrecords.size() < 20) loadingDataEnd = true;
                    else listView.addFooterView(loadMoreView, null, false);
                    adapter.setRecords(callrecords);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return;
                } else {
                    showErrorView(biz.AR.drawable.no_update_apps, "\u60A8\u8FD8\u6CA1\u6709\u901A\u8BDD\u8BB0\u5F55", false);
                    return;
                }
            case 1: // '\001'
                break;
        }
        if (flag) {
            if (Morecallrecords.size() != 0) {
                callrecords.addAll(Morecallrecords);
                adapter.notifyDataSetChanged();
            }
            if (Morecallrecords.size() < 20) {
                loadingDataEnd = true;
                listView.removeFooterView(loadMoreView);
                adapter.notifyDataSetChanged();
            }
        } else {
            loadMoreView.showTryAgainButton(true);
        }
        loadingData = false;
    }
    @Override
    protected void setDataAgain() {
        if (callrecords != null && callrecords.size() != 0) {
            listView.addFooterView(loadMoreView, null, false);
            if (callrecords.size() < 20) {
                loadingDataEnd = true;
                listView.removeFooterView(loadMoreView);
            }
            adapter.setRecords(callrecords);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return;
        } else {
            showErrorView(biz.AR.drawable.no_update_apps, "\u60A8\u8FD8\u6CA1\u6709\u901A\u8BDD\u8BB0\u5F55", false);
            return;
        }
    }
    @Override
    protected void tryAgain() {
        super.tryAgain();
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
}
