// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.util.StringTokenizer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.gionee.aora.integral.control.UserManager;
import com.gionee.aora.integral.control.UserStorage;
import com.gionee.aora.integral.module.UserInfo;
import com.gionee.aora.market.net.IntegralNet;
import com.gionee.aora.market.util.MarketAsyncTask;

// Referenced classes of package com.gionee.aora.market.control:
//            MarketPreferences
public class IntegralReceiver extends BroadcastReceiver {
    class IntegralTask extends MarketAsyncTask<String, Object, Object> {
        Context context;
        UserInfo info;
        String respon[];
        String softId;
        int type;
        public IntegralTask(Context context1, UserInfo userinfo, String s, int i) {
            super();
            softId = "";
            type = 1;
            context = context1;
            info = userinfo;
            softId = s;
            type = i;
        }
        @Override
        protected Object[] doInBackground(String as[]) {
            if (type == 1) return IntegralNet.integralFirstOpen(context, info, softId);
            if (type == 2) return IntegralNet.integralUpdate(context, info, softId);
            else return IntegralNet.integralPlayGames(context, info, softId);
        }
        protected void onPostExecute(Object aobj[]) {
            MarketPreferences marketpreferences;
            super.onPostExecute(aobj);
            marketpreferences = MarketPreferences.getInstance(context);
            if (aobj != null) {
                Integer integer;
                UserInfo userinfo;
                integer = (Integer) aobj[0];
                userinfo = (UserInfo) aobj[1];
                String s1 = (String) aobj[2];
                Toast.makeText(context, s1, 1).show();
                if (integer.intValue() == 0) {
                    //L3_L3:
                    UserStorage.saveUserInfo(context, userinfo);
                    UserManager.getInstance(context).reFreshUserInfo(userinfo);
                    if (type == 1) {
                        Intent intent1 = new Intent("com.gionee.aora.market.gui.integral.IntegralAppListActivity.installedFinish");
                        intent1.putExtra("softid", softId);
                        intent1.putExtra("isgetcoin", true);
                        context.sendBroadcast(intent1);
                    }
                    return;
                } else {
                    //L4_L4:
                    if (integer.intValue() == 204 && type == 1) {
                        Intent intent = new Intent("com.gionee.aora.market.gui.integral.IntegralAppListActivity.installedFinish");
                        intent.putExtra("softid", softId);
                        intent.putExtra("isgetcoin", false);
                        context.sendBroadcast(intent);
                        return;
                    }
                }
            } else {
                    if (type == 1) {
                        String s = marketpreferences.getIntegralFail();
                        marketpreferences.setIntegralFail(addRecord(s, softId));
                        return;
                    }
            }
        }
        protected void onProgressUpdate(Integer ainteger[]) {
            super.onProgressUpdate(ainteger);
        }
    }
    public static String[] stringAnalytical(String s, String s1) {
        String s2 = "";
        if (s.endsWith(s1)) s2 = s.substring(0, -1 + s.length());
        StringTokenizer stringtokenizer = new StringTokenizer(s2, s1);
        String as[] = new String[stringtokenizer.countTokens()];
        for (int i = 0; stringtokenizer.hasMoreTokens(); i++) {
            as[i] = new String();
            as[i] = stringtokenizer.nextToken();
        }
        return as;
    }
    public static final String KEY_SOFT_ID = "softId";
    public static final String TAG = "IntegralReceiver";
    public IntegralReceiver() {}
    private String addRecord(String s, String s1) {
        String as[] = stringAnalytical(s, ",");
        int i = as.length;
        for (int j = 0; j < i; j++)
            if (as[j].equals(s1)) return s;
        return new StringBuilder().append(s).append(s1).append(",").toString();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String s;
        label0: {
            if (intent.getAction().equals("com.gionee.aora.market.control.IntegralReceiver.appinstalled")) {
                s = intent.getStringExtra("softId");
                if (!"".equals(s) && s != null) break label0;
            }
            return;
        }
        if (intent.hasExtra("IS_UPDATE") && intent.getBooleanExtra("IS_UPDATE", false)) {
            UserInfo userinfo1 = UserStorage.getDefaultUserInfo(context.getApplicationContext());
            new IntegralTask(context.getApplicationContext(), userinfo1, s, 2).doExecutor(new String[0]);
            return;
        } else {
            UserInfo userinfo = UserStorage.getDefaultUserInfo(context.getApplicationContext());
            new IntegralTask(context.getApplicationContext(), userinfo, s, 1).doExecutor(new String[0]);
            return;
        }
    }
}
