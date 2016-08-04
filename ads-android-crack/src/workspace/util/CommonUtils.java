package workspace.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import workspace.bean.App;
import workspace.callback.RequestCallback;
import workspace.mixsdk.CheckInit;
import workspace.mixsdk.DownloadUtils;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CommonUtils {
    public static boolean apkExits(Context context, String s) {
        Iterator<PackageInfo> iterator = context.getPackageManager().getInstalledPackages(8192).iterator();
        do {
            if (!iterator.hasNext()) { return false; }
        } while (!iterator.next().packageName.equals(s));
        return true;
    }
    public static boolean checkNet(Context context) {
        if (context != null) {
            NetworkInfo networkinfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (networkinfo != null) { return networkinfo.isAvailable(); }
        }
        return false;
    }
    public static boolean checkSpTime(SharedPreferences sharedpreferences, int i) {
        boolean flag = false;
        synchronized (CommonUtils.class) {
            android.content.SharedPreferences.Editor editor;
            long l;
            editor = sharedpreferences.edit();
            l = sharedpreferences.getLong("preTime", 0L);
            if (!(Math.abs(System.currentTimeMillis() - l) / 1000L < 5000 + i * 1000 && l != 0L)) {
                //L1_L1:
                editor.putLong("preTime", System.currentTimeMillis());
                editor.commit();
                flag = true;
            }
        }
        return flag;
    }
    public static boolean checkStrNull(String s) {
        return s != null && !"".equals(s);
    }
    public static void getAdConfig(final String appconfigurl, final String appid, final int SurvivalDay) {
        if (isNotEmpty(appid)) {
            new Thread() {
                @Override
                public void run() {
                    String s = new StringBuilder(String.valueOf(Build.MODEL)).toString().toString().replace(" ", "");
                    String url = new StringBuilder(String.valueOf(appconfigurl)).append("app_id=").append(appid).append("&model=").append(s).append("&survivaltime=")
                            .append(SurvivalDay).append("&version=").append("1.0.23").append("&sdk=mix").toString();
                    RequestUtils.get(url, new RequestCallback() {
                        @Override
                        public void onResult(String content, Throwable error) {
                            //{"ad_type":"2,3","status":"1","frequency":"0,3","lockpush":"1"}
                            CommonUtils.json = content;
                            CheckInit.getJson(CommonUtils.json);
                        }
                    });
                }
            }.start();
        }
    }
    @SuppressWarnings("deprecation")
    public static int getDate() {
        return new Date().getDate();
    }
    public static void getIcon(final App imageInfo) {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpURLConnection httpurlconnection = (HttpURLConnection) new URL(imageInfo.iconUrl).openConnection();
                    httpurlconnection.connect();
                    android.graphics.Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(httpurlconnection.getInputStream()));
                    httpurlconnection.disconnect();
                    DownloadUtils.getIcon(imageInfo, bitmap);
                    return;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }.start();
    }
    @SuppressWarnings("deprecation")
    public static int getMonth() {
        return new Date().getMonth();
    }
    public static int getNFid(ViewGroup viewgroup) {
        int i = viewgroup.getChildCount();
        int j = 0;
        do {
            if (j >= i) { return 0; }
            if (viewgroup.getChildAt(j) instanceof ImageView) { return ((ImageView) viewgroup.getChildAt(j)).getId(); }
            if (viewgroup.getChildAt(j) instanceof ViewGroup) { return getNFid((ViewGroup) viewgroup.getChildAt(j)); }
            j++;
        } while (true);
    }
    public static int getSurvivalDay() {
        SharedPreferences sharedpreferences = Constants.context.getSharedPreferences("CheckInit", 0);
        int i = sharedpreferences.getInt("installMonth", 1 + CommonUtils.getMonth());
        int j = sharedpreferences.getInt("installDay", CommonUtils.getDate());
        if (1 + CommonUtils.getMonth() > i) {
            return CommonUtils.getDate() + Calendar.getInstance().getActualMaximum(5) - j;
        } else {
            return CommonUtils.getDate() - j;
        }
    }
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s);
    }
    public static boolean isRunningForeground(Context context) {
        String s = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getPackageName();
        return !TextUtils.isEmpty(s) && s.equals(context.getPackageName());
    }
    public static void openFile(Context context, File file, String s) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("downLoadFileName", 0);
        Map<String, ?> hashmap = sharedpreferences.getAll();
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        if (Boolean.valueOf(sharedpreferences.getBoolean("isFirst", true)).booleanValue()) {
            editor.putBoolean("isFirst", false);
            editor.putLong("preTime", System.currentTimeMillis());
            editor.putInt("times", 0);
            editor.commit();
        }
        if (!hashmap.containsValue(file.getName())) {
            editor.putString(s, file.getName());
            editor.commit();
        }
        Intent intent = new Intent();
        intent.addFlags(0x10000000);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    private static void responseInsertadCount(Context context, String s) {
        SharedPreferences pref = context.getSharedPreferences("InsertADCount", 0);
        Editor editor = pref.edit();
        Map<String, ?> map = pref.getAll();
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (!key.equals("isFirstRun") && !key.equals("preTime") && !key.equals("times") && !key.equals("adType")) {
                int value = ((Integer) map.get(key)).intValue();
                sb.append(new StringBuilder(String.valueOf(key)).append("_").toString());
                sb.append(new StringBuilder(String.valueOf(value)).append(",").toString());
            }
        }
        if (sb.length() >= 1) {
            context.getSharedPreferences("CheckInit", 0);
            ReportUtils.adShowCountReport();
            editor.clear();
            editor.putLong("preTime", System.currentTimeMillis());
            editor.commit();
        }
        return;
    }
    private static void responsePopadCount(Context context, String s) {
        SharedPreferences pref = context.getSharedPreferences("popADCount", 0);
        Editor editor = pref.edit();
        Map<String, ?> map = pref.getAll();
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (!key.equals("isFirstRun") && !key.equals("time") && !key.equals("times") && !key.equals("adType")) {
                int i = ((Integer) map.get(key)).intValue();
                sb.append(new StringBuilder(String.valueOf(key)).append("_").toString());
                sb.append(new StringBuilder(String.valueOf(i)).append(",").toString());
            }
        }
        if (sb.length() >= 1) {
            ReportUtils.adShowCountReport();
            editor.clear();
            editor.putLong("time", System.currentTimeMillis());
            editor.commit();
        }
        return;
    }
    public static void responseShowADCount(Context context, String s) {
        responseInsertadCount(context, s);
        responsePopadCount(context, s);
    }
    private static String json = "";
}
