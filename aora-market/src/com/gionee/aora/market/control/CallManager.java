// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Toast;
import com.aora.base.util.DLog;
import com.gionee.aora.integral.gui.view.MarketFloateDialogBuilder;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.net.CallNet;
import com.gionee.aora.market.util.MarketAsyncTask;

public class CallManager {
    private static class CallTask extends MarketAsyncTask<String, Boolean, Boolean> {
        private CallTask() {}
        @Override
        protected Boolean doInBackground(String as[]) {
            Object aobj[] = CallNet.getCall(as[0], CallManager.info);
            if (aobj != null) {
                CallManager.errorStr = (String) aobj[1];
                return (Boolean) aobj[0];
            } else {
                return Boolean.valueOf(false);
            }
        }
        @Override
        protected void onPostExecute(Boolean boolean1) {
            if (!boolean1.booleanValue()) {
                if (CallManager.dialog != null) CallManager.dialog.dismiss();
                Toast.makeText(CallManager.mcontext, CallManager.errorStr, 0).show();
                return;
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000L);
                            if (CallManager.dialog != null) CallManager.dialog.dismiss();
                            return;
                        } catch (InterruptedException interruptedexception) {
                            DLog.e("CallManager", "CallTask##onPostExecute", interruptedexception);
                        }
                    }
                }).start();
                return;
            }
        }
        @Override
        protected void onPreExecute() {
            CallManager.errorStr = CallManager.mcontext.getResources().getString(biz.AR.string.network_error);
            if (CallManager.dialog != null) CallManager.dialog.cancel();
            CallManager.builder = new MarketFloateDialogBuilder(CallManager.mcontext);
            android.view.View view = LayoutInflater.from(CallManager.mcontext).inflate(biz.AR.layout.call_wait_layout, null);
            android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(-1, -1);
            layoutparams.addRule(13);
            CallManager.builder.setCenterView(view, layoutparams);
            CallManager.builder.setCancelable(true);
            CallManager.dialog = CallManager.builder.crteate();
            CallManager.builder.setViewCenter();
            CallManager.dialog.show();
        }
    }
    public static void doCall(UserInfo userinfo, String s, Context context) {
        info = userinfo;
        mcontext = context;
        new CallTask().doExecutor(new String[] { s });
    }
    private static MarketFloateDialogBuilder builder;
    private static Dialog dialog;
    private static String errorStr;
    private static UserInfo info;
    private static Context mcontext;
    public CallManager() {}
    /*
        static String access$102(String s)
        {
            errorStr = s;
            return s;
        }

    */
    /*
        static Dialog access$302(Dialog dialog1)
        {
            dialog = dialog1;
            return dialog1;
        }

    */
    /*
        static MarketFloateDialogBuilder access$402(MarketFloateDialogBuilder marketfloatedialogbuilder)
        {
            builder = marketfloatedialogbuilder;
            return marketfloatedialogbuilder;
        }

    */
}
