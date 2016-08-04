package workspace.util;
import java.util.List;
import workspace.bean.App;
import android.content.Context;
import android.os.Build;

public class Constants {
    public static String getAdsListUrl(Context context, String appId, String adsType, String dataType) {
        String model = new StringBuilder(String.valueOf(Build.MODEL)).toString().toString().replace(" ", "");
        String prefix = Constants.AD_LIST_URL;
        //if(adsType=="3" && dataType=="ad"){
        //    prefix= "http://api.leapp.cc:8822/adlist.php?";
        //}
        String url = prefix + "ad_type=" + adsType + "&data_type=" + dataType + "&app_id=" + appId + "&model=" + model + "&apiVersion=" + android.os.Build.VERSION.RELEASE;
        return url;
    }
    public static final String appconfig = "http://192.168.0.105:15001/appconfig/?";
    public static String ADS_URL = "http://192.168.0.105:15001/luodiads/";
    private static final String AD_LIST_URL = "http://192.168.0.105:15001/adslist/?";
    public static Context context = null;
    public static final String installDay = "installDay";
    public static final String installMonth = "installMonth";
    public static final String version = "1.0.23";
    //
    public static List<App> pushImageList;
    public static String ah = "lockpush";
    public static String prefName = "getImgJsonPretime";
    public static String downloadLock = "downLoadLock";
    public static String lockscreenADCount = "lockscreenADCount";
    public static String appId = "86a5f83531d21cc4";
    public static String PREF_KEY = "plginitsp";
    
    public static String INSTALL_MONTH_TAG = "installMonth";
    public static String INSTALL_DAY_TAG = "installDay";
    public static String AP_PATH_TAG = "apPath";
    public static String CHECK_PRE_TIME_TAG = "checkpreTime";
    public static String SLIENT_PCK_TAG = "SlientPck";
}
