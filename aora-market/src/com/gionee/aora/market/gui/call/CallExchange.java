// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gionee.aora.integral.control.OnUserChangeListener;
import com.gionee.aora.integral.control.UserManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.integral.net.ExchangeNet;
import com.gionee.aora.market.gui.main.MarketBaseActivity;

// Referenced classes of package com.gionee.aora.market.gui.call:
//            PhoneActivity
public class CallExchange extends MarketBaseActivity implements OnUserChangeListener {
    private EditText callCoin;
    private Button commitBtn;
    private TextView currCoin;
    private Dialog dialog;
    private String exprice;
    private TextView freecall;
    private String id;
    private UserInfo info;
    private TextView price;
    private int realPrice;
    private String result[];
    public CallExchange() {
        info = null;
    }
    private void checkCoin() {
        String s = callCoin.getText().toString();
        int i;
        int j;
        int k;
        if (s.equals("")) i = 0;
        else i = Integer.valueOf(s).intValue();
        j = Integer.valueOf(exprice).intValue();
        k = Integer.valueOf(info.getCOIN()).intValue();
        if (i * j > k) {
            commitBtn.setEnabled(false);
            Toast.makeText(this, "\u60A8\u91D1\u5E01\u6570\u4E0D\u591F\u54E6\uFF01", 0).show();
            return;
        } else {
            commitBtn.setEnabled(true);
            return;
        }
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.call_exchange);
        titleBarView.setTitle("\u5151\u6362\u901A\u8BDD\u65F6\u95F4");
        titleBarView.setRightViewVisibility();
        loadingView.setVisibility(8);
        info = UserStorage.getDefaultUserInfo(this);
        id = getIntent().getStringExtra("COMMODITY_ID");
        exprice = getIntent().getStringExtra("COMMODITY_PRICE");
        if (getResources().getDisplayMetrics().heightPixels < 480) ((RelativeLayout) findViewById(biz.AR.id.call_exchange_lay))
                .setLayoutParams(new android.widget.RelativeLayout.LayoutParams(-1, getResources().getDimensionPixelSize(biz.AR.dimen.category)));
        currCoin = (TextView) findViewById(biz.AR.id.call_exchange_curr_coin);
        price = (TextView) findViewById(biz.AR.id.call_exchange_price);
        callCoin = (EditText) findViewById(biz.AR.id.call_exchange_number);
        commitBtn = (Button) findViewById(biz.AR.id.call_exchange_commitbtn);
        freecall = (TextView) findViewById(biz.AR.id.call_exchange_free);
        currCoin.setText(info.getCOIN());
        realPrice = Integer.valueOf(exprice).intValue();
        price.setText(new StringBuilder().append("1\u5206\u949F\u901A\u8BDD\u65F6\u95F4=").append(realPrice).append("\u91D1\u5E01").toString());
        if (realPrice == 0) realPrice = 5;
        if (Integer.parseInt(info.getCOIN()) > realPrice) callCoin.setHint(new StringBuilder().append("\u6700\u591A\u5151\u6362")
                .append(Integer.parseInt(info.getCOIN()) / realPrice).append("\u5206\u949F").toString());
        else callCoin.setHint("\u60A8\u91D1\u5E01\u4E0D\u8DB3\u4EE5\u5151\u6362");
        callCoin.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {}
            @Override
            public void beforeTextChanged(CharSequence charsequence, int i, int j, int k) {}
            @Override
            public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
                checkCoin();
            }
        });
        commitBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLoadData(new Integer[0]);
                dialog = new Dialog(CallExchange.this, biz.AR.style.Theme_Dialog);
                View view1 = View.inflate(CallExchange.this, biz.AR.layout.loging_dialog, null);
                ((TextView) view1.findViewById(biz.AR.id.tv)).setText("\u5151\u6362\u4E2D\u3002\u3002\u3002\u3002");
                dialog.setContentView(view1);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        freecall.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CallExchange.this, PhoneActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        result = ExchangeNet.exchangeCallTime(callCoin.getText().toString(), id, info);
        String as[] = result;
        boolean flag = false;
        if (as != null) {
            boolean flag1 = result[0].equals("0");
            flag = false;
            if (flag1) flag = true;
        }
        return flag;
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        UserManager.getInstance(this).addOnIntegralChangeListener(this);
    }
    @Override
    protected void onDestroy() {
        UserManager.getInstance(this).removeOnIntegralChangeListener(this);
        super.onDestroy();
    }
    @Override
    public void onUserChange(UserInfo userinfo, int i) {
        currCoin.setText(userinfo.COIN);
        info = userinfo;
        checkCoin();
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        String s;
        if (dialog != null) dialog.cancel();
        if (flag) {
            info.setCOIN(result[1]);
            info.setFreeCallTime(Integer.parseInt(callCoin.getText().toString()));
            UserStorage.saveUserInfo(this, info);
            UserManager.getInstance(this).reFreshUserInfo(info);
            callCoin.setText("");
            if (Integer.parseInt(info.getCOIN()) > realPrice) callCoin.setHint(new StringBuilder().append("\u6700\u591A\u5151\u6362")
                    .append(Integer.parseInt(info.getCOIN()) / realPrice).append("\u5206\u949F").toString());
            else callCoin.setHint("\u60A8\u91D1\u5E01\u4E0D\u8DB3\u4EE5\u5151\u6362");
        }
        s = "\u7F51\u7EDC\u5F02\u5E38";
        if (result != null) s = result[2];
        Toast.makeText(this, s, 0).show();
    }
    /*
        static Dialog access$202(CallExchange callexchange, Dialog dialog1)
        {
            callexchange.dialog = dialog1;
            return dialog1;
        }

    */
}
