// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package com.gionee.aora.market.control;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

// Referenced classes of package com.gionee.aora.market.control:
//            MarketPreferences, DataCollectManager
public class WifiCollectManager {
    public static boolean sameDay(Context context) {
        MarketPreferences marketpreferences = MarketPreferences.getInstance(context);
        long l = marketpreferences.getWifiTime();
        if (l == 0L) return false;
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-mm-dd");
        long l1 = System.currentTimeMillis();
        if (simpledateformat.format(Long.valueOf(l)).equals(simpledateformat.format(Long.valueOf(l1)))) {
            return true;
        } else {
            marketpreferences.setWifiTime(l1);
            return false;
        }
    }
    public static void wificollect(Context context) {
        if (!sameDay(context)) {
            List list = ((WifiManager) context.getSystemService("wifi")).getScanResults();
            if (list != null && !list.isEmpty()) {
                String as[];
                for (Iterator iterator = list.iterator(); iterator.hasNext(); DataCollectManager.addWifiRecord("9998", as)) {
                    ScanResult scanresult = (ScanResult) iterator.next();
                    as = new String[8];
                    as[0] = "wifi_mac";
                    as[1] = scanresult.BSSID;
                    as[2] = "wifi_name";
                    as[3] = scanresult.SSID;
                    as[4] = "wifi_priority";
                    as[5] = new StringBuilder().append(WifiManager.calculateSignalLevel(scanresult.level, 5)).append("").toString();
                    as[6] = "wifi_encrypt_method";
                    as[7] = scanresult.capabilities;
                }
            }
        }
    }
    public WifiCollectManager() {}
}
