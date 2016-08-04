// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.aora.base.datacollect.BaseCollectManager;
import com.aora.base.datacollect.DataCollectUtil;
import com.aora.base.net.HttpRequest;
import com.aora.base.util.DLog;
import com.aora.base.util.DefaultExceptionHandler;
import com.gionee.aora.download.DownloadInfo;
import com.gionee.aora.download.DownloadManager;
import com.gionee.aora.fihs.FihsApplication;
import com.gionee.aora.integral.net.IntegralBaseNet;
import com.gionee.aora.market.control.ImageLoaderManager;
import com.gionee.aora.market.control.MarketPreferences;
import com.gionee.aora.market.control.SoftWareUpdateManager;
import com.gionee.aora.market.control.SoftwareManager;
import com.gionee.aora.market.control.UpdateManager;

// Referenced classes of package com.gionee.aora.market:
//            Constants
public class MarketApplication extends FihsApplication {
    private static final String APPID = "01d13901d76b49d4a518a49e4bdcc6fe";
    private static final String CONTROLER_CONFIG_ID = "1";
    public static final String PROJECT_ITEM_ID = "17";
    public MarketApplication() {}
    @Override
    public com.gionee.aora.fihs.controler.Constants.Properties getPushProperties() {
        com.gionee.aora.fihs.controler.Constants.Properties properties = new com.gionee.aora.fihs.controler.Constants.Properties();
        properties.APPID = "01d13901d76b49d4a518a49e4bdcc6fe";
        properties.PROJECT_ITEM_ID = "17";
        properties.CONTROLER_CONFIG_ID = "1";
        return properties;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        String s = getPackageName();
        String s1 = getCurProcessName(this);
        if (s.equals(s1)) {
            Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));
            Constants.init(this);
            Constants.isGioneeVerison = false;
            Constants.isKeyboardVersion = false;
            DLog.init("aoramarket/logs", Boolean.parseBoolean(Constants.getProperty("MARKET_LOG")));
            DLog.setTAG("Aora_Market");
            DataCollectUtil.init(this, "17", Constants.getChannelId());
            DataCollectUtil.setSession_id(DataCollectUtil.createSessionID(getApplicationContext()));
            MarketPreferences.getInstance(getApplicationContext()).setSession(DataCollectUtil.getSession_id());
            ArrayList arraylist = new ArrayList();
            arraylist.add(Constants.getProperty("MAIN_URL_DEFAULT"));
            arraylist.add(Constants.getProperty("MAIN_URL_BACKUP"));
            HttpRequest.init(getApplicationContext(), arraylist);
            ArrayList arraylist1 = new ArrayList();
            arraylist1.add(Constants.getProperty("INTEGRAL_URL_DEFAULT"));
            arraylist1.add(Constants.getProperty("INTEGRAL_URL_BACKUP"));
            IntegralBaseNet.init(arraylist1);
            if (!MarketPreferences.getInstance(getApplicationContext()).checkVersionCodeAndName()) DLog.e("lung", new StringBuilder().append(deleteDatabase("downloadApk.db"))
                    .append("---").append(deleteDatabase("SoftwaresDB")).toString());
            MarketPreferences.getInstance(getApplicationContext()).setVersionCodeAndName(getApplicationContext());
            ImageLoaderManager.getInstance().init(getApplicationContext());
            DownloadManager.shareInstance().init(getApplicationContext());
            SoftwareManager.getInstace().init(getApplicationContext());
            UpdateManager.getInstance().init(getApplicationContext());
            SoftWareUpdateManager.getInstance().init(getApplicationContext());
            List list = SoftWareUpdateManager.getInstance().getAllDownloadTasks();
            if (list != null) {
                Iterator iterator = list.iterator();
                do {
                    if (!iterator.hasNext()) break;
                    DownloadInfo downloadinfo = (DownloadInfo) iterator.next();
                    if (downloadinfo.getPath() != null && !new File(downloadinfo.getPath()).exists()) SoftWareUpdateManager.getInstance().deleteDownloadInfo(
                            downloadinfo.getPackageName());
                } while (true);
            }
            BaseCollectManager.init(this);
            DLog.i("MarketApplication#onCreate - \u6613\u7528\u6C47\u8FDB\u7A0B\u542F\u52A8");
        }
        DLog.i("MarketApplication", new StringBuilder().append("MarketApplication#onCreate - getCurProcessName=").append(s1).toString());
    }
}
