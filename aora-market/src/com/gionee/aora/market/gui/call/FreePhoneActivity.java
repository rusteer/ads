// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.gui.integral.IntegralGetMoney;
import com.gionee.aora.market.gui.main.MarketBaseActivity;
import com.gionee.aora.market.net.CallTimeNet;
import com.gionee.aora.market.net.UnBoundNet;

// Referenced classes of package com.gionee.aora.market.gui.call:
//            BoundPhoneActivity, CallHelp, CallExchange, PhoneActivity
public class FreePhoneActivity extends MarketBaseActivity {
    private static final int DO_CALL_TIME = 0;
    private static final int DO_UNBOUND_PHONE = 1;
    private LinearLayout callbtn;
    private Dialog dialog;
    private Dialog dialog1;
    private TextView exchangebtn;
    private TextView freelimttime;
    private ImageView freelimttimeiv;
    private TextView freephone;
    private TextView freetime;
    private TextView getcoinbtn;
    private int id;
    private UserInfo info;
    private String msg;
    private int price;
    private TextView rephone;
    private int time;
    public FreePhoneActivity() {
        msg = "\u7F51\u7EDC\u9519\u8BEF\uFF0C\u8BF7\u68C0\u67E5\u7F51\u7EDC\u8BBE\u7F6E";
    }
    @Override
    protected void initCenterView() {
        setCenterView(biz.AR.layout.free_phone_layout);
        loadingView.setVisibility(8);
        titleBarView.setTitle("\u514D\u8D39\u7535\u8BDD");
        ImageView imageview = new ImageView(this);
        imageview.setImageResource(biz.AR.drawable.help);
        imageview.setPadding(0, 0, getResources().getDimensionPixelSize(biz.AR.dimen.dip10), 0);
        imageview.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreePhoneActivity.this, CallHelp.class);
                intent.putExtra("STRATGY_URL", "http://po.myaora.net:81/rechange/taobao.php?a=free_call");
                startActivity(intent);
            }
        });
        titleBarView.setRightView(imageview);
        info = UserStorage.getDefaultUserInfo(this);
        freephone = (TextView) findViewById(biz.AR.id.free_phone);
        rephone = (TextView) findViewById(biz.AR.id.free_re_phone);
        freelimttime = (TextView) findViewById(biz.AR.id.only_time_free_tv);
        freelimttimeiv = (ImageView) findViewById(biz.AR.id.only_time_free_iv);
        freetime = (TextView) findViewById(biz.AR.id.free_time);
        getcoinbtn = (TextView) findViewById(biz.AR.id.free_get_btn);
        exchangebtn = (TextView) findViewById(biz.AR.id.free_exchange_btn);
        callbtn = (LinearLayout) findViewById(biz.AR.id.free_call_btn);
        TextView textview = freephone;
        Resources resources = getResources();
        int i = biz.AR.string.free_phone_txt;
        Object aobj[] = new Object[1];
        aobj[0] = info.getFreePhone();
        textview.setText(resources.getString(i, aobj));
        freetime.setText(new StringBuilder().append(info.getFreeCallTime()).append("\u5206\u949F").toString());
        if (info.getFreeLimtTime() != null && !info.getFreeLimtTime().equals("")) {
            freelimttime.setVisibility(0);
            freelimttimeiv.setVisibility(0);
            freelimttime.setText(info.getFreeLimtTime());
        } else {
            freelimttime.setVisibility(4);
            freelimttimeiv.setVisibility(4);
        }
        exchangebtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreePhoneActivity.this, CallExchange.class);
                intent.putExtra("COMMODITY_ID", new StringBuilder().append(id).append("").toString());
                intent.putExtra("COMMODITY_PRICE", new StringBuilder().append(price).append("").toString());
                startActivity(intent);
            }
        });
        getcoinbtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreePhoneActivity.this, IntegralGetMoney.class);
                startActivity(intent);
            }
        });
        callbtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FreePhoneActivity.this, PhoneActivity.class);
                startActivity(intent);
            }
        });
        rephone.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MarketFloateDialogBuilder marketfloatedialogbuilder = new MarketFloateDialogBuilder(FreePhoneActivity.this);
                TextView textview1 = new TextView(FreePhoneActivity.this);
                textview1.setText(new StringBuilder().append("\u662F\u5426\u8981\u89E3\u7ED1\n").append(info.getFreePhone()).append("\u8FD9\u4E2A\u53F7\u7801").toString());
                textview1.setTextSize(24F);
                textview1.setTextColor(Color.parseColor("#333333"));
                textview1.setGravity(17);
                android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(-1, -1);
                layoutparams.addRule(13);
                textview1.setLayoutParams(layoutparams);
                marketfloatedialogbuilder.setCenterView(textview1, null);
                marketfloatedialogbuilder.setCancelable(false);
                marketfloatedialogbuilder.setLeftButton("\u53D6\u6D88", new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null) dialog.cancel();
                    }
                });
                marketfloatedialogbuilder.setRightButton("\u786E\u5B9A", new android.view.View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialog != null) dialog.cancel();
                        FreePhoneActivity freephoneactivity = FreePhoneActivity.this;
                        Integer ainteger[] = new Integer[1];
                        ainteger[0] = Integer.valueOf(1);
                        freephoneactivity.doLoadData(ainteger);
                        dialog1 = new Dialog(FreePhoneActivity.this, biz.AR.style.Theme_Dialog);
                        View view1 = View.inflate(FreePhoneActivity.this, biz.AR.layout.loging_dialog, null);
                        ((TextView) view1.findViewById(biz.AR.id.tv)).setText("\u89E3\u7ED1\u4E2D\u3002\u3002\u3002\u3002");
                        dialog1.setContentView(view1);
                        dialog1.setCancelable(false);
                        dialog1.setCanceledOnTouchOutside(false);
                        dialog1.show();
                    }
                });
                dialog = marketfloatedialogbuilder.crteate();
                dialog.show();
            }
        });
    }
    @Override
    protected boolean initData(Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            case 0://L2_L2:
                int ai[] = CallTimeNet.getCallTime(info);
                if (ai != null) {
                    time = ai[0];
                    id = ai[1];
                    price = ai[2];
                    return true;
                }
                break;
            case 1://L3_L3:
                Object aobj[] = UnBoundNet.getUnBound(info);
                if (aobj != null) {
                    if (((Boolean) aobj[0]).booleanValue()) return true;
                    msg = (String) aobj[1];
                }
                break;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Integer ainteger[] = new Integer[1];
        ainteger[0] = Integer.valueOf(0);
        doLoadData(ainteger);
        info = UserStorage.getDefaultUserInfo(this);
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {
        switch (ainteger[0].intValue()) {
            case 0://L2_L2:
                if (flag) {
                    info.setFreeCallTime(time);
                    freetime.setText(new StringBuilder().append(info.getFreeCallTime()).append("\u5206\u949F").toString());
                    UserStorage.saveUserInfo(this, info);
                    return;
                }
                break;
            case 1://L3_L3:
                if (dialog1 != null) dialog1.cancel();
                if (flag) {
                    info.setFreePhone("");
                    UserStorage.saveUserInfo(this, info);
                    startActivity(new Intent(this, BoundPhoneActivity.class));
                    finish();
                    return;
                } else {
                    showToast(msg);
                    return;
                }
        }
    }
    private void showToast(String s) {
        Toast.makeText(this, s, 0).show();
    }
    /*
        static Dialog access$302(FreePhoneActivity freephoneactivity, Dialog dialog2)
        {
            freephoneactivity.dialog = dialog2;
            return dialog2;
        }

    */
    /*
        static Dialog access$502(FreePhoneActivity freephoneactivity, Dialog dialog2)
        {
            freephoneactivity.dialog1 = dialog2;
            return dialog2;
        }

    */
}
