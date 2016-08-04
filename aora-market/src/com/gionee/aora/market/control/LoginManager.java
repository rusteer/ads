// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import android.content.Context;
import android.os.Handler;
import com.aora.base.datacollect.BaseCollectManager;
import com.aora.base.datacollect.DataCollectUtil;
import com.gionee.aora.market.net.MarketLoginNet;
import com.gionee.aora.market.util.MarketAsyncTask;

// Referenced classes of package com.gionee.aora.market.control:
//            MarketPreferences
public class LoginManager {
    public static final int MSG_WHAT_FAILD_LOGIN = 1;
    public static final int MSG_WHAT_FAILD_SPLASH = 3;
    public static final int MSG_WHAT_SUCCESS_LOGIN = 0;
    public static final int MSG_WHAT_SUCCESS_SPLASH = 2;
    private Context mContext;
    private Handler mHandler;
    public LoginManager(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }
    public MarketAsyncTask doLogin() {
        MarketAsyncTask marketasynctask = new MarketAsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void avoid[]) {
                com.gionee.aora.market.module.LoginInfo logininfo = MarketLoginNet.getLogin();
                if (logininfo != null) {
                    //L1_L1:
                    if (!isCancelled()) mHandler.sendMessage(mHandler.obtainMessage(0, logininfo));
                } else {
                    //L2_L2:
                    if (!isCancelled()) mHandler.sendEmptyMessage(1);
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void void1) {
                super.onPostExecute(void1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BaseCollectManager.initIp("http://www.baidu.com", "http://www.baidu.com", "http://www.baidu.com");
                    }
                }).start();
            }
        };
        marketasynctask.doExecutor(new Void[0]);
        return marketasynctask;
    }
    public MarketAsyncTask getSplashUrl() {
        MarketAsyncTask marketasynctask = new MarketAsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void avoid[]) {
                com.gionee.aora.market.module.RecommendAdInfo recommendadinfo = MarketLoginNet.getSplash();
                if (recommendadinfo != null) {
                    if (!isCancelled()) mHandler.sendMessage(mHandler.obtainMessage(2, recommendadinfo));
                } else {
                    if (!isCancelled()) mHandler.sendEmptyMessage(3);
                }
                return null;
            }
        };
        marketasynctask.doExecutor(new Void[0]);
        return marketasynctask;
    }
    public void setSessionID() {
        DataCollectUtil.setSession_id(DataCollectUtil.createSessionID(mContext));
        MarketPreferences.getInstance(mContext).setSession(DataCollectUtil.getSession_id());
    }
}
