// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.details;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.aora.base.util.DLog;
import com.gionee.aora.market.gui.view.ChildTitleView;
import com.gionee.aora.market.net.CommitNet;
import com.gionee.aora.market.util.MarketAsyncTask;

public class IntroductionProblemActivity extends Activity {
    private static final int RadioGroup_AGAINST_REASON5 = 5;
    private static final int RadioGroup_HATE3 = 3;
    private static final int RadioGroup_HAZARDOUS4 = 4;
    private static final int RadioGroup_SEX1 = 1;
    private static final int RadioGroup_VIOLENT2 = 2;
    private static final String TAG = "IntroductionProblemActivity";
    private Button commitReferBtn;
    private int illNum;
    private EditText inputMsg_et;
    private String problemMsg;
    private String soft_id;
    private ChildTitleView titleBarView;
    public IntroductionProblemActivity() {
        titleBarView = null;
        illNum = 1;
    }
    private void hideInputMethodManagerKeyStore(Context context) {
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
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(biz.AR.layout.introduction_soft_problem_onekey);
        titleBarView = (ChildTitleView) findViewById(biz.AR.id.problemTitleBar);
        titleBarView.setTitle(getResources().getString(biz.AR.string.soft_problem_onekey));
        inputMsg_et = (EditText) findViewById(biz.AR.id.against_reason_et);
        ((RadioGroup) findViewById(biz.AR.id.soft_problem_menu)).setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radiogroup, int i) {
                switch (i) {
                    default:
                        return;
                    case biz.AR.id.sex_content:
                        hideInputMethodManagerKeyStore(IntroductionProblemActivity.this);
                        illNum = 1;
                        inputMsg_et.setVisibility(8);
                        return;
                    case biz.AR.id.violent_images:
                        hideInputMethodManagerKeyStore(IntroductionProblemActivity.this);
                        illNum = 2;
                        inputMsg_et.setVisibility(8);
                        return;
                    case biz.AR.id.hate_content:
                        hideInputMethodManagerKeyStore(IntroductionProblemActivity.this);
                        illNum = 3;
                        inputMsg_et.setVisibility(8);
                        return;
                    case biz.AR.id.mobile_hazardous:
                        hideInputMethodManagerKeyStore(IntroductionProblemActivity.this);
                        illNum = 4;
                        inputMsg_et.setVisibility(8);
                        return;
                    case biz.AR.id.against_reason:
                        illNum = 5;
                        break;
                }
                inputMsg_et.setVisibility(0);
            }
        });
        soft_id = getIntent().getStringExtra("APPINFO");
        commitReferBtn = (Button) findViewById(biz.AR.id.soft_problem_refer);
        commitReferBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                problemRefer();
            }
        });
    }
    private void problemRefer() {
        if (illNum == 5) {
            problemMsg = inputMsg_et.getEditableText().toString();
            if (inputMsg_et.getEditableText().toString().replaceAll(" ", "").replaceAll("\\s", "").equals("")) {
                Toast.makeText(this, biz.AR.string.soft_problem_input_content, 0).show();
                return;
            }
        }
        commitReferBtn.setText("\u63D0\u4EA4\u4E2D");
        commitReferBtn.setBackgroundResource(biz.AR.drawable.introduction_uninstall);
        commitReferBtn.setClickable(false);
        new MarketAsyncTask<Void, Boolean, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... avoid) {
                DLog.i("IntroductionProblemActivity",
                        new StringBuilder().append("soft_id = ").append(soft_id).append("illNum = ").append(illNum).append("problemMsg = ").append(problemMsg).toString());
                return Boolean.valueOf(CommitNet.getCommit(soft_id, illNum, problemMsg));
            }
            @Override
            protected void onPostExecute(Boolean boolean1) {
                super.onPostExecute(boolean1);
                if (boolean1.booleanValue()) {
                    DLog.i("IntroductionProblemActivity", new StringBuilder().append("IntroductionProblemActivity.problemRefer.\u4E3E\u62A5\u6210\u529F").append(boolean1)
                            .toString());
                    Toast.makeText(getBaseContext(), "\u4E3E\u62A5\u6210\u529F", 1).show();
                    finish();
                    return;
                } else {
                    DLog.i("IntroductionProblemActivity", new StringBuilder().append("IntroductionProblemActivity.problemRefer.\u4E3E\u62A5\u5931\u8D25").append(boolean1)
                            .toString());
                    Toast.makeText(getBaseContext(), "\u4E3E\u62A5\u5931\u8D25", 1).show();
                    commitReferBtn.setText("\u63D0\u4EA4");
                    commitReferBtn.setBackgroundResource(biz.AR.drawable.soft_introduction_download_selector);
                    commitReferBtn.setClickable(true);
                    return;
                }
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }.doExecutor(new Void[0]);
    }
    /*
        static int access$102(IntroductionProblemActivity introductionproblemactivity, int i)
        {
            introductionproblemactivity.illNum = i;
            return i;
        }

    */
}
