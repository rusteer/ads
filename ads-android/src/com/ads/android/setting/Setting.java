package com.ads.android.setting;
public class Setting {
    public static final String TEST_URL = "http://192.168.1.108:15001/ads/";
    //public static final String TEST_URL = "http://ads.yousdk.com/mmm";
    private static String hostName = "localhost";
    private static String SERVER_URL = "http://" + hostName + "/mmm";
    public static String getServerUrl() {
        String url = hostName.contains("host") ? TEST_URL : SERVER_URL;
        return url;
    }
    public static final boolean DEBUG = true;
}
