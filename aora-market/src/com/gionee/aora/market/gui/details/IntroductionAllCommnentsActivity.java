// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import java.util.ArrayList;
import java.util.List;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.util.DLog;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.gui.details.view.CommentGradeLayout;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.view.LoadMoreScrollListener;
import com.gionee.aora.market.gui.view.LoadMoreView;
import com.gionee.aora.market.gui.view.MarketListView;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.module.Comment;
import com.gionee.aora.market.net.CommentNet;
import com.nostra13.universalimageloader.core.ImageLoader;

// Referenced classes of package com.gionee.aora.market.gui.details:
//            IntroductionCommentAdapter, IntroductionCommentActivy
public class IntroductionAllCommnentsActivity extends MarketBaseActivity {
    class CommentBroadcastReceiver extends BroadcastReceiver {
        CommentBroadcastReceiver() {
            super();
        }
        @Override
        public void onReceive(Context context, Intent intent) {
            Comment comment1 = (Comment) intent.getSerializableExtra("COMMENTS");
            comments.add(0, comment1);
            adapter.setComments(comments);
            adapter.notifyDataSetChanged();
        }
    }
    private final int LOAD_COMMENTS_DATA_COUNT = 10;
    private final int LOAD_DATA = 0;
    private final int LOAD_MORE_COMMENTS_DATA = 1;
    private String TAG;
    private DataCollectInfo action;
    private IntroductionCommentAdapter adapter;
    private AppInfo appInfo;
    private Button comment;
    private List comments;
    private CommentGradeLayout gradeLayout;
    private MarketListView listView;
    private List loadData;
    private LoadMoreView loadMoreView;
    private CommentBroadcastReceiver receiver;
    public IntroductionAllCommnentsActivity() {
        appInfo = null;
        listView = null;
        comment = null;
        adapter = null;
        loadMoreView = null;
        TAG = "IntroductionAllCommnentsActivity";
        receiver = null;
        action = null;
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.grade_layout);
        titleBarView.setTitle("\u6240\u6709\u8BC4\u8BBA");
        comment = (Button) findViewById(biz.AR.id.comment_btn);
        listView = (MarketListView) findViewById(biz.AR.id.comment_listView);
        listView.setDividerHeight(0);
        comments = new ArrayList();
        adapter = new IntroductionCommentAdapter(this, comments);
        loadMoreView = new LoadMoreView(this) {
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
        gradeLayout = new CommentGradeLayout(getApplicationContext());
        listView.addHeaderView(gradeLayout);
        listView.addFooterView(loadMoreView, null, false);
        listView.setAdapter(adapter);
        comment.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appInfo.isCommented()) {
                    Toast.makeText(IntroductionAllCommnentsActivity.this, "\u4F60\u5DF2\u7ECF\u8BC4\u8BBA\u8FC7\u4E86", 0).show();
                    return;
                }
                if (!SoftwareManager.getInstace().getSoftwaresMap().containsKey(appInfo.getPackageName())) {
                    Toast.makeText(IntroductionAllCommnentsActivity.this, "\u8981\u5148\u5B89\u88C5\u624D\u80FD\u8BC4\u8BBA\u54E6", 0).show();
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.setClass(IntroductionAllCommnentsActivity.this, IntroductionCommentActivy.class);
                    intent.putExtra("APPINFO", appInfo);
                    intent.putExtra("DATACOLLECT_INFO", action.clone());
                    startActivityForResult(intent, 100);
                    return;
                }
            }
        });
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        boolean flag = true;
    DLog.d(TAG, new StringBuilder().append("initData").append(ainteger[0]).toString());
    switch (ainteger[0].intValue()) {
        case 0://L2_L2:
            List list1;
            loadData = CommentNet.getCommentList(appInfo.getSoftId(), ainteger[1].intValue(), 10);
            list1 = loadData;
            flag = false;
            if (list1 == null) return flag;
            if (loadData.size() == 0) return false;
            break;
        case 1://L3_L3:
            List list;
            if (loadData != null) {
                loadData.clear();
                loadData = null;
            }
            loadData = CommentNet.getCommentList(appInfo.getSoftId(), ainteger[1].intValue(), 10);
            list = loadData;
            flag = false;
            if (list == null) return flag;
            if (loadData.size() == 0) return true;
            return true;
    }
        return flag;
    }
    private void loadMoreData() {
        if (comments == null || loadingData || loadingDataEnd || loadMoreView.isShowTryAgain()) {
            return;
        } else {
            loadingData = true;
            Integer ainteger[] = new Integer[2];
            ainteger[0] = Integer.valueOf(1);
            ainteger[1] = Integer.valueOf(comments.size());
            doLoadData(ainteger);
            return;
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        action = DataCollectManager.getCollectInfo(this);
        super.onCreate(bundle);
        appInfo = (AppInfo) getIntent().getSerializableExtra("APPINFO");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
        receiver = new CommentBroadcastReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("IntroductionCommentActivy_ADD_COMMENT");
        registerReceiver(receiver, intentfilter);
    }
    @Override
    protected void onDestroy() {
        if (receiver != null) unregisterReceiver(receiver);
        super.onDestroy();
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        DLog.d(TAG, new StringBuilder().append("refreshView").append(ainteger[0]).toString());
        switch (ainteger[0].intValue()) {
            case 0://L2_L2:
                if (flag) {
                    gradeLayout.setDataInfo(appInfo.getComment());
                    comments.addAll(loadData);
                    adapter.notifyDataSetChanged();
                    if (loadData.size() < 10) {
                        loadingDataEnd = true;
                        listView.removeFooterView(loadMoreView);
                        return;
                    }
                } else {
                    showErrorView();
                    return;
                }
                break;
            case 1://L3_L3:
                if (flag) {
                    if (loadData.size() != 0) {
                        comments.addAll(loadData);
                        adapter.notifyDataSetChanged();
                    }
                    if (loadData.size() < 10) {
                        loadingDataEnd = true;
                        listView.removeFooterView(loadMoreView);
                    }
                } else {
                    loadMoreView.showTryAgainButton(true);
                }
                loadingData = false;
                break;
        }
    }
    @Override
    protected void tryAgain() {
        super.tryAgain();
        DLog.v(TAG, "tryAgain");
        Integer ainteger[] = new Integer[2];
        ainteger[0] = Integer.valueOf(0);
        ainteger[1] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
}
