// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
package com.gionee.aora.market.gui.integral;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aora.base.datacollect.DataCollectInfo;
import com.gionee.aora.integral.control.OnUserChangeListener;
import com.gionee.aora.integral.control.UserManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.rank.IntegralRankActivity;
import com.gionee.aora.integral.gui.strategy.IntegralStrategyActivity;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.integral.util.Util;
import com.gionee.aora.market.Constants;
import com.gionee.aora.market.control.DataCollectManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.gui.lenjoy.LenjoyActivity;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.gui.manager.SoftwareUpdateActivity;
import com.gionee.aora.market.gui.question.QuestionActivity;
import com.gionee.aora.market.gui.view.IntegralSignCalendarView;
import com.gionee.aora.market.net.QuestionNet;

// Referenced classes of package com.gionee.aora.market.gui.integral:
//            IntegralAppListActivity, IntegralGetMoneyPlayGame
public class IntegralGetMoney extends MarketBaseActivity implements android.view.View.OnClickListener, OnUserChangeListener {
    private DataCollectInfo action;
    View buttons[];
    IntegralSignCalendarView calendarView;
    TextView detailTipTv[];
    private boolean loading;
    ImageView que_new;
    MarketPreferences sp;
    TextView todayTotalTv;
    private String url;
    public IntegralGetMoney() {
        buttons = new View[5];
        detailTipTv = new TextView[5];
        action = new DataCollectInfo("", "8", "3", "", "");
        url = "";
        loading = false;
    }
    @Override
    protected void initCenterView() {
        DataCollectManager.addRecord(action, new String[0]);
        View view = View.inflate(this, biz.AR.layout.integral_get_money_title_layout, null);
        view.findViewById(biz.AR.id.get_money_note_btn).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent intent = new Intent(IntegralGetMoney.this, IntegralStrategyActivity.class);
                intent.putExtra("STRATGY_URL", Constants.getProperty("INTEGRAL_STRATGY_URL"));
                startActivity(intent);
            }
        });
        view.findViewById(biz.AR.id.get_money_rank_btn).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                UserInfo userinfo = UserStorage.getDefaultUserInfo(IntegralGetMoney.this);
                if (userinfo == null || userinfo.getLOGIN_STATE() == 3) UserManager.getInstance(IntegralGetMoney.this).doLogin(userinfo, "0", "", "");
                else if (userinfo.getLOGIN_STATE() != 1) {
                    Intent intent = new Intent(IntegralGetMoney.this, IntegralRankActivity.class);
                    intent.putExtra("DATACOLLECT_INFO", action);
                    startActivity(intent);
                    return;
                }
            }
        });
        titleBarView.setRightView(view);
        titleBarView.setTitle("\u8D5A\u53D6\u91D1\u5E01");
        setCenterView(biz.AR.layout.integral_get_money_activity_6100);
        calendarView = (IntegralSignCalendarView) findViewById(biz.AR.id.calendar_view);
        buttons[0] = findViewById(biz.AR.id.install_btn);
        buttons[1] = findViewById(biz.AR.id.playgame_btn);
        buttons[2] = findViewById(biz.AR.id.update_app_btn);
        buttons[3] = findViewById(biz.AR.id.qa_btn);
        buttons[4] = findViewById(biz.AR.id.lejoy_btn);
        todayTotalTv = (TextView) findViewById(biz.AR.id.today_total);
        que_new = (ImageView) findViewById(biz.AR.id.question_new);
        buttons[0].setOnClickListener(this);
        buttons[1].setOnClickListener(this);
        buttons[2].setOnClickListener(this);
        buttons[3].setOnClickListener(this);
        buttons[4].setOnClickListener(this);
        detailTipTv[0] = (TextView) findViewById(biz.AR.id.detail_1);
        detailTipTv[1] = (TextView) findViewById(biz.AR.id.detail_2);
        detailTipTv[2] = (TextView) findViewById(biz.AR.id.detail_3);
        detailTipTv[3] = (TextView) findViewById(biz.AR.id.detail_4);
        detailTipTv[4] = (TextView) findViewById(biz.AR.id.detail_5);
        refreshIntegralUi(UserStorage.getDefaultUserInfo(this));
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        loading = true;
        url = QuestionNet.getQuestion(UserStorage.getDefaultUserInfo(this));
        return !url.equals("");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case biz.AR.id.install_btn://L2_L2:
                if (Util.isOfficialUserInfo(this)) {
                    Intent intent1 = new Intent(this, IntegralAppListActivity.class);
                    intent1.putExtra("DATACOLLECT_INFO", action.clone());
                    startActivity(intent1);
                    return;
                }
                break;
            case biz.AR.id.update_app_btn://L3_L3:
                if (Util.isOfficialUserInfo(this)) {
                    SoftwareUpdateActivity.startSoftwareUpdateActivity(this, new DataCollectInfo("", "8", "3", "2", "11"));
                    return;
                }
                break;
            case biz.AR.id.playgame_btn://L4_L4:
                if (Util.isOfficialUserInfo(this)) {
                    Intent intent = new Intent(this, IntegralGetMoneyPlayGame.class);
                    intent.putExtra("DATACOLLECT_INFO", action.clone());
                    startActivity(intent);
                    return;
                }
                break;
            case biz.AR.id.qa_btn://L5_L5:
                if (sp.getShowQuestion().booleanValue() && !loading) {
                    if (Util.isOfficialUserInfo(this)) {
                        startActivity(new Intent(this, QuestionActivity.class));
                        return;
                    }
                } else {
                    Toast.makeText(this, "\u5F53\u524D\u6CA1\u6709\u95EE\u5377", 0).show();
                    return;
                }
                break;
            case biz.AR.id.lejoy_btn://L6_L6:
                DataCollectInfo datacollectinfo = action.clone();
                datacollectinfo.setPosition("4");
                datacollectinfo.setType("8");
                LenjoyActivity.showEditLenjoy(this, datacollectinfo, false);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle bundle) {
        UserManager.getInstance(this).addOnIntegralChangeListener(this);
        onCreate(bundle);
        sp = MarketPreferences.getInstance(this);
        if (sp.getShowQuestion().booleanValue()) que_new.setVisibility(0);
        else que_new.setVisibility(8);
        doLoadData(new Integer[0]);
    }
    @Override
    protected void onDestroy() {
        UserManager.getInstance(this).removeOnIntegralChangeListener(this);
        onDestroy();
    }
    @Override
    protected void onResume() {
        onResume();
        if (UserStorage.getDefaultUserInfo(this).getUSER_TYPE_FLAG() != 1) doLoadData(new Integer[0]);
        if (sp.getShowQuestion().booleanValue()) {
            que_new.setVisibility(0);
            return;
        } else {
            que_new.setVisibility(8);
            return;
        }
    }
    @Override
    public void onUserChange(UserInfo userinfo, int i) {
        refreshIntegralUi(userinfo);
    }
    private void refreshIntegralUi(UserInfo userinfo) {
        if (userinfo != null) {
            String s = userinfo.getToDayTotal();
            todayTotalTv.setText(s);
            calendarView.setCalendarInfo(userinfo);
        }
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        loading = false;
        sp.setShowQuestion(Boolean.valueOf(flag));
        if (flag) {
            sp.setQuestionUrl(url);
            que_new.setVisibility(0);
            return;
        } else {
            que_new.setVisibility(8);
            return;
        }
    }
}
