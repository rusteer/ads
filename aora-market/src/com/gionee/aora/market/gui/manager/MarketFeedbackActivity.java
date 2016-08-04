// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.manager;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.module.FeedbackInfo;
import com.gionee.aora.market.net.FeedbackNet;
import com.gionee.aora.market.util.MarketAsyncTask;

// Referenced classes of package com.gionee.aora.market.gui.manager:
//            MarketFeedbackAdapter
public class MarketFeedbackActivity extends Activity implements android.view.View.OnClickListener {
    private static final String TAG = "MarketFeedback";
    private MarketFeedbackAdapter adapter;
    private Button feedbackRefer;
    private EditText feedback_Edit;
    private String feedback_text;
    private ArrayList listdata;
    private ListView listview;
    private ChildTitleView titleBarView;
    public MarketFeedbackActivity() {
        titleBarView = null;
        feedback_text = "";
    }
    private void feedbackRefer() {
        feedback_text = feedback_Edit.getText().toString();
        if (feedback_text == null || feedback_text.equals("")) {
            return;
        } else {
            feedbackRefer.setText("\u63D0\u4EA4\u4E2D");
            feedbackRefer.setClickable(false);
            listdata.add(new FeedbackInfo(feedback_text, 1));
            adapter.notifyDataSetChanged();
            listview.setSelection(-1 + listdata.size());
            new MarketAsyncTask<Void, Boolean, Boolean>() {
                @Override
                protected Boolean doInBackground(Void avoid[]) {
                    DLog.i("MarketFeedback",
                            new StringBuilder().append("feedback_text = ").append(feedback_text).append("IMEI = ").append(DataCollectUtil.getImei()).append("\u673A\u578B  = ")
                                    .append(DataCollectUtil.getValue("m")).append("\u7248\u672C\u53F7  = ").append(DataCollectUtil.getVersionCode()).toString());
                    return Boolean.valueOf(FeedbackNet.getFeedback(feedback_text, DataCollectUtil.getImei(), DataCollectUtil.getValue("m"), DataCollectUtil.getVersionCode()));
                }
                @Override
                protected void onPostExecute(Boolean boolean1) {
                    super.onPostExecute(boolean1);
                    feedbackRefer.setClickable(true);
                    feedback_Edit.setEnabled(true);
                    if (boolean1.booleanValue()) {
                        DLog.i("MarketFeedback", new StringBuilder().append("MarketFeedbackActivity.feedbackRefer.\u53CD\u9988\u6210\u529F").append(boolean1).toString());
                        listdata.add(new FeedbackInfo(
                                "\u60A8\u7684\u5B9D\u8D35\u610F\u89C1\u6211\u4EEC\u5DF2\u7ECF\u63A5\u6536\u5230\uFF0C\u8C22\u8C22\u5173\u6CE8\u6613\u7528\u6C47!", 0));
                        adapter.notifyDataSetChanged();
                        listview.setSelection(-1 + listdata.size());
                        feedback_Edit.setText("");
                        feedbackRefer.setText("\u63D0\u4EA4");
                        return;
                    } else {
                        DLog.i("MarketFeedback", new StringBuilder().append("MarketFeedbackActivity.feedbackRefer.\u53CD\u9988\u5931\u8D25").append(boolean1).toString());
                        listdata.add(new FeedbackInfo("\u7F51\u7EDC\u9519\u8BEF\uFF0C\u610F\u89C1\u63D0\u4EA4\u5931\u8D25\uFF0C\u8BF7\u7A0D\u5019\u518D\u8BD5", 0));
                        adapter.notifyDataSetChanged();
                        listview.setSelection(-1 + listdata.size());
                        feedbackRefer.setText("\u63D0\u4EA4");
                        return;
                    }
                }
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    feedback_Edit.setEnabled(false);
                }
            }.doExecutor(new Void[0]);
            return;
        }
    }
    private void feedbackReferTest() {
        feedback_text = feedback_Edit.getText().toString();
        listdata.add(new FeedbackInfo(feedback_text, 1));
        adapter.notifyDataSetChanged();
        listview.setSelection(-1 + listdata.size());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                return;
            case biz.AR.id.feedback_refer:
                feedbackRefer();
                break;
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.feedback);
        titleBarView = (ChildTitleView) findViewById(biz.AR.id.feedbackTitleBar);
        titleBarView.setTitle(getResources().getString(biz.AR.string.feedback));
        titleBarView.setRightViewVisibility();
        feedback_Edit = (EditText) findViewById(biz.AR.id.feedback_edit);
        feedbackRefer = (Button) findViewById(biz.AR.id.feedback_refer);
        feedbackRefer.setOnClickListener(this);
        listview = (ListView) findViewById(biz.AR.id.listview);
        int i = getResources().getDimensionPixelSize(biz.AR.dimen.dip8);
        View view = new View(this);
        view.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, i));
        listview.addHeaderView(view, null, false);
        View view1 = new View(this);
        view1.setLayoutParams(new android.widget.AbsListView.LayoutParams(-1, i));
        listview.addFooterView(view1, null, false);
        listdata = new ArrayList();
        FeedbackInfo feedbackinfo = new FeedbackInfo(getString(biz.AR.string.feedback_first_message), 0);
        listdata.add(feedbackinfo);
        adapter = new MarketFeedbackAdapter(this, listdata);
        listview.setAdapter(adapter);
    }
    public void radioButtonOnClick(View view) {
        showFeedbackSelectView();
    }
    private void showFeedbackSelectView() {}
}
