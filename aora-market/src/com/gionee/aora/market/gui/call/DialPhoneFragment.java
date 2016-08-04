// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.gui.call;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.market.control.CallManager;
import com.gionee.aora.market.gui.main.BaseTabFragment;
import com.gionee.aora.market.gui.view.dragview.MyDragLayout;

public class DialPhoneFragment extends BaseTabFragment implements android.view.View.OnClickListener {
    private RelativeLayout call;
    private RelativeLayout del;
    private TextView num10;
    private TextView phone;
    private String phoneNum;
    private TextView tv[];
    public DialPhoneFragment() {
        tv = new TextView[10];
        phoneNum = "";
    }
    @Override
    public void changePage(MyDragLayout mydraglayout) {}
    @Override
    protected boolean initData(Integer ainteger[]) {
        return true;
    }
    @Override
    protected void initView(RelativeLayout relativelayout) {
        setTitleBarViewVisibility(false);
        loadingView.setVisibility(8);
        int i;
        int j;
        if (getActivity().getResources().getDisplayMetrics().heightPixels < 720) setCenterView(biz.AR.layout.dial_phone_layout);
        else setCenterView(biz.AR.layout.dial_phone_big_layout);
        call = (RelativeLayout) relativelayout.findViewById(biz.AR.id.dial_call);
        del = (RelativeLayout) relativelayout.findViewById(biz.AR.id.dial_del);
        phone = (TextView) relativelayout.findViewById(biz.AR.id.dial_num);
        phone.setText(phoneNum);
        tv[0] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_0);
        tv[1] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_1);
        tv[2] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_2);
        tv[3] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_3);
        tv[4] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_4);
        tv[5] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_5);
        tv[6] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_6);
        tv[7] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_7);
        tv[8] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_8);
        tv[9] = (TextView) relativelayout.findViewById(biz.AR.id.dial_num_9);
        call.setOnClickListener(this);
        del.setOnClickListener(this);
        i = biz.AR.id.dial_num_10;
        try {
            num10 = (TextView) relativelayout.findViewById(i);
            num10.setOnClickListener(this);
        } catch (Exception exception) {}
        for (j = 0; j < tv.length; j++)
            tv[j].setOnClickListener(this);
        del.setOnLongClickListener(new android.view.View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                phoneNum = phone.getText().toString();
                if (!phoneNum.equals("")) {
                    phoneNum = "";
                    phone.setText(phoneNum);
                }
                return true;
            }
        });
    }
    @Override
    protected void loadData() {}
    @Override
    public void onClick(View view) {
        if (view == del) {
            phoneNum = phone.getText().toString();
            if (!phoneNum.equals("")) {
                phoneNum = phoneNum.substring(0, -1 + phoneNum.length());
                phone.setText(phoneNum);
            }
        } else if (view == call) {
                if (!phoneNum.equals("")) {
                    CallManager.doCall(UserStorage.getDefaultUserInfo(getActivity()), phoneNum, getActivity());
                    return;
                }
            } else {
                phoneNum = new StringBuilder().append(phoneNum).append(((TextView) view).getText()).toString();
                phone.setText(phoneNum);
                return;
            }
    }
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (UserStorage.getDefaultUserInfo(getActivity()).getFreeCallTime() <= 0) Toast.makeText(getActivity(),
                "\u60A8\u8FD8\u6CA1\u6709\u514D\u8D39\u901A\u8BDD\u65F6\u95F4\uFF0C\u5FEB\u53BB\u5151\u6362\u5427!", 0).show();
    }
    @Override
    protected void refreshView(boolean flag, Integer ainteger[]) {}
    @Override
    protected void setDataAgain() {}
    /*
        static String access$002(DialPhoneFragment dialphonefragment, String s)
        {
            dialphonefragment.phoneNum = s;
            return s;
        }

    */
}
