// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectInfo;
import com.aora.base.datacollect.DataCollectUtil;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.module.AppInfo;
import com.gionee.aora.market.module.Comment;
import com.gionee.aora.market.net.CommentSumbitNet;

public class IntroductionCommentActivy extends MarketBaseActivity {
    public static final String COMMENT_ACTION = "IntroductionCommentActivy_ADD_COMMENT";
    private final int INITVIEW = 0;
    private final int SENT_COMMNET = 2;
    private DataCollectInfo action;
    private AppInfo appInfo;
    private Comment comment;
    private RadioGroup commentRadioGroup;
    private String commentString;
    private Button comment_submbit_btn_quit;
    private Button comment_submbit_button;
    private RatingBar comment_sumbitBar;
    private EditText comment_sumbit_EditText;
    private RelativeLayout openCheckBoxRelativeLayout;
    private ImageView openCommend;
    private LinearLayout whyLinearLayout;
    public IntroductionCommentActivy() {
        comment_submbit_button = null;
        comment_submbit_btn_quit = null;
        comment_sumbit_EditText = null;
        comment_sumbitBar = null;
        commentString = "";
        openCommend = null;
        action = null;
    }
    public void hideInputMethodManagerKeyStore(Context context) {
        InputMethodManager inputmethodmanager = (InputMethodManager) context.getSystemService("input_method");
        if (inputmethodmanager != null) {
            View view = ((Activity) context).getCurrentFocus();
            if (view != null) {
                android.os.IBinder ibinder = view.getWindowToken();
                if (ibinder != null) inputmethodmanager.hideSoftInputFromWindow(ibinder, 2);
            }
        }
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.comment_submit_layout);
        titleBarView.setTitle(getResources().getString(biz.AR.string.commnet));
        comment_submbit_button = (Button) findViewById(biz.AR.id.comment_submbit_button);
        comment_submbit_btn_quit = (Button) findViewById(biz.AR.id.comment_submbit_btn_quit);
        comment_sumbit_EditText = (EditText) findViewById(biz.AR.id.comment_submbit_editText);
        comment_sumbitBar = (RatingBar) findViewById(biz.AR.id.comment_submbit_RatingBar);
        comment_submbit_button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideInputMethodManagerKeyStore(IntroductionCommentActivy.this);
                if (comment_sumbit_EditText.getText().toString().replaceAll(" ", "").replaceAll("\\s", "").equals("")) {
                    Toast.makeText(IntroductionCommentActivy.this, "\u8BC4\u8BBA\u4E0D\u80FD\u4E3A\u7A7A", 0).show();
                    return;
                }
                if (comment_sumbitBar.getRating() == 0.0F) {
                    Toast.makeText(IntroductionCommentActivy.this, "\u8BC4\u8BBA\u661F\u7EA7\u4E0D\u80FD\u4E3A0", 0).show();
                    return;
                } else {
                    comment_submbit_button.setClickable(false);
                    comment_submbit_button.setBackgroundDrawable(getResources().getDrawable(biz.AR.drawable.introduction_uninstall));
                    comment_submbit_button.setText("\u63D0 \u4EA4 \u4E2D...");
                    comment_submbit_button.setTextColor(Color.parseColor("#909090"));
                    IntroductionCommentActivy introductioncommentactivy = IntroductionCommentActivy.this;
                    Integer ainteger[] = new Integer[1];
                    ainteger[0] = Integer.valueOf(2);
                    introductioncommentactivy.doLoadData(ainteger);
                    return;
                }
            }
        });
        comment_submbit_btn_quit.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        openCheckBoxRelativeLayout = (RelativeLayout) findViewById(biz.AR.id.whyLayout);
        whyLinearLayout = (LinearLayout) findViewById(biz.AR.id.commentLinearLayout);
        whyLinearLayout.setVisibility(8);
        comment_sumbitBar.setOnRatingBarChangeListener(new android.widget.RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingbar, float f, boolean flag) {
                if (f <= 2.0F) {
                    whyLinearLayout.setVisibility(0);
                    return;
                } else {
                    whyLinearLayout.setVisibility(8);
                    commentString = "";
                    return;
                }
            }
        });
        openCheckBoxRelativeLayout.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });
        commentRadioGroup = (RadioGroup) findViewById(biz.AR.id.commentRadioGroup);
        commentRadioGroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radiogroup, int i) {
                int j = biz.AR.id.otherRadioButton;
                commentString = ((RadioButton) findViewById(i)).getText().toString();
                if (i != j) ((RadioButton) findViewById(j)).setChecked(false);
            }
        });
        openCommend = (ImageView) findViewById(biz.AR.id.openCommend);
        openCommend.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whyLinearLayout.getVisibility() == 0) {
                    whyLinearLayout.setVisibility(8);
                    return;
                } else {
                    whyLinearLayout.setVisibility(0);
                    return;
                }
            }
        });
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            case 0: // '\0'
            case 1: // '\001'
            default:
                return true;
            case 2: // '\002'
                comment = new Comment();
                break;
        }
        comment.setContent(comment_sumbit_EditText.getText().toString().replaceAll("\\r", "").replaceAll("\\n", "").replaceAll(" ", ""));
        comment.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        comment.setRating(comment_sumbitBar.getRating());
        comment.setModel_number(DataCollectUtil.getValue("m"));
        UserInfo userinfo = UserStorage.getDefaultUserInfo(this);
        comment.setReviewer(new StringBuilder().append("\u7F51\u53CB: ").append(userinfo.getSURNAME()).toString());
        return CommentSumbitNet.getCommentSumbit(appInfo.getSoftId(), new StringBuilder().append(userinfo.getID()).append("").toString(),
                new StringBuilder().append(2.0F * comment_sumbitBar.getRating()).append("").toString(), comment.getContent(), DataCollectUtil.getValue("m"), commentString);
    }
    @Override
    public void onBackPressed() {
        hideInputMethodManagerKeyStore(this);
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle bundle) {
        action = DataCollectManager.getCollectInfo(this);
        super.onCreate(bundle);
        appInfo = (AppInfo) getIntent().getSerializableExtra("APPINFO");
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(0);
        doLoadData(ainteger);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            default:
                return;
            case 2: // '\002'
                comment_submbit_button.setClickable(true);
                break;
        }
        comment_submbit_button.setBackgroundDrawable(getResources().getDrawable(biz.AR.drawable.soft_introduction_install_selector));
        comment_submbit_button.setTextColor(-1);
        comment_submbit_button.setText("\u63D0 \u4EA4");
        if (flag) {
            Toast.makeText(this, "\u8BC4\u8BBA\u6210\u529F", 0).show();
            DataCollectInfo datacollectinfo = action.clone();
            datacollectinfo.setAction("4");
            datacollectinfo.setType("10");
            String as[] = new String[4];
            as[0] = "app_id";
            as[1] = appInfo.getSoftId();
            as[2] = "cpversion";
            as[3] = new StringBuilder().append(appInfo.getUpdateVersionName()).append("").toString();
            DataCollectManager.addRecord(datacollectinfo, as);
            Intent intent = new Intent();
            intent.setAction("IntroductionCommentActivy_ADD_COMMENT");
            intent.putExtra("COMMENTS", comment);
            sendBroadcast(intent);
            finish();
            return;
        } else {
            Toast.makeText(this, "\u63D0\u4EA4\u8BC4\u8BBA\u5931\u8D25", 0).show();
            comment_sumbitBar.setRating(5F);
            comment_sumbit_EditText.setText("");
            return;
        }
    }
    /*
        static String access$502(IntroductionCommentActivy introductioncommentactivy, String s)
        {
            introductioncommentactivy.commentString = s;
            return s;
        }

    */
}
