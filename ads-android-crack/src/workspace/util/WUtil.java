package workspace.util;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.callback.DownloadCallback;
import workspace.shortcut.ShortcutUtil;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bangbang.ads.android.WmfActivity;
import com.bangbang.ads.android.WmfService;

public class WUtil {
    private static void a(App bean) {
        SharedPreferences pref = mContext.getSharedPreferences(Constants.lockscreenADCount, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putInt(bean.id, 1 + pref.getInt(bean.id, 0));
        editor.commit();
        if (pref.getBoolean("isFirstRun", true)) {
            editor.putBoolean("isFirstRun", false);
            editor.putLong("preTime", System.currentTimeMillis());
            editor.commit();
        } else {
            long l = pref.getLong("preTime", 0L);
            if (Math.abs((System.currentTimeMillis() - l) / 60000L) >= 10L) {
                editor.putLong("preTime", System.currentTimeMillis());
                editor.commit();
                j();
                return;
            }
        }
    }
    private static void a(App class1000, int l) {
        b(class1000, l);
    }
    private static void a(App class1000, String s) {
        class1000.pathName = s;
        Message message = Message.obtain();
        message.what = 4;
        message.obj = class1000;
        mHandler.sendMessage(message);
    }
    public static void a(Bitmap bitmap) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = bitmap;
        mHandler.sendMessage(message);
    }
    public static void a(Context context) {
        context.startService(new Intent(context, WmfService.class));
        mContext = context;
        Constants.context = context;
        SharedPreferences sharedpreferences = mContext.getSharedPreferences("lockShow", 0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        if (sharedpreferences.getBoolean("isFirst", true)) {
            MyLogger.info(tag, "lockShow_FIRST");
            editor.putBoolean("isFirst", false);
            editor.putLong("preTime", System.currentTimeMillis());
            editor.putInt("times", 0);
            editor.remove("appid");
            editor.putString("appid", Constants.appId);
            editor.putInt(Constants.installMonth, 1 + CommonUtils.getMonth());
            editor.putInt(Constants.installDay, CommonUtils.getDate());
            editor.commit();
            requestAdList3();
        }
        int l = sharedpreferences.getInt("times", 0);
        long l1 = (System.currentTimeMillis() - sharedpreferences.getLong("preTime", 0L)) / 60000L;
        if (l == 0 || Math.abs(l1) >= 10L) {
            editor.putInt("times", l + 1);
            editor.commit();
            editor.putLong("preTime", System.currentTimeMillis());
            editor.commit();
            MyLogger.info("info", new StringBuilder("times：").append(l).append("或者间隔小于").append(l1).append("分钟").toString());
            ShortcutUtil.configRequest(Constants.appconfig, Constants.appId);
            return;
        } else {
            parseConfigResult(sharedpreferences.getString("appconfig", ""));
            return;
        }
    }
    public static void a(Context context, RelativeLayout relativelayout) {
        if (context != null) {
            Constants.context = context;
            mContext = context;
            mRelateLayout = relativelayout;
            initHandler();
            mHandler.sendEmptyMessage(1);
        }
    }
    @SuppressWarnings("deprecation")
    private static void b(App class1000) {
        File file1;
        Notification notification;
        String pathName = class1000.pathName;
        String s1 = class1000.appUrl;
        String s2 = s1.substring(1 + s1.lastIndexOf("/"), s1.length());
        MyLogger.info("info", new StringBuilder("下载：").append(s1).toString());
        try {
            String s3 = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).packageName;
            File file = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(s3).toString());
            file1 = new File(new StringBuilder().append(file).append("/").append(s2).toString());
            MyLogger.info("info", "changeNtcDone");
            contentTitle = new StringBuilder(String.valueOf(class1000.name)).append("下载完毕,点击安装").toString();
            contentText = class1000.description;
            notification = new Notification(0x108008f, class1000.description, System.currentTimeMillis());
            notification.flags = 2;
            if (!mContext.getSharedPreferences("lockShow", 0).getString(Constants.ah, "").equals("0")) {
                notification.flags = 16;
            } else {
                notification.flags = 32;
            }
            notification.flags = 2;
            Intent intent = new Intent();
            intent.addFlags(0x10000000);
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(file1), "application/vnd.android.package-archive");
            PendingIntent pendingintent = PendingIntent.getActivity(mContext, 0, intent, 0);
            notification.setLatestEventInfo(mContext, contentTitle, contentText, pendingintent);
            LinearLayout linearlayout = new LinearLayout(mContext.getApplicationContext());
            int l = ShortcutUtil.methodD((ViewGroup) notification.contentView.apply(mContext.getApplicationContext(), linearlayout));
            notification.contentView.setImageViewBitmap(l, BitmapFactory.decodeFile(pathName));
            ((NotificationManager) mContext.getSystemService("notification")).notify(Integer.parseInt(class1000.id), notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    private static void b(App class1000, int adType) {
        //String s = ShortcutUtil.encodeInfo(CS.context);
        //ShortcutUtil.httpGet(new StringBuilder(String.valueOf(CS.appClicked)).append("app_id=").append(CS.appId).append("&uuid=").append(s).append("&ad_id=").append(class1000.getAdId()).append("&ad_type=").append(adType).toString());
        ReportUtils.apkInstalledReport();
    }
    public static void b(Context context) {
        Intent intent = new Intent(context, WmfActivity.class);
        intent.setFlags(0x10000000);
        context.startActivity(intent);
    }
    public static void downloadIconUrl(final App bean) {
        try {
            String packageName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).packageName;
            MyLogger.info(tag, packageName);
            final File param2 = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(packageName).toString());
            if (!param2.exists()) {
                param2.mkdirs();
            }
            new Thread() {
                @Override
                public void run() {
                    String url = bean.iconUrl;
                    String s1 = url.substring(1 + url.lastIndexOf("/"), url.length());
                    File file = new File(new StringBuilder(String.valueOf(param2.getAbsolutePath())).append("/").append(s1).toString());
                    RequestUtils.download(url, file, new DownloadCallback() {
                        @Override
                        public void onResult(File file, Throwable error) {
                            if (file != null) {
                                WUtil.a(bean, file.getAbsolutePath());
                            }
                        }
                    });
                    return;
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void downloadScreenUrl(final List<App> list) {
        try {
            String packageName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).packageName;
            final File folder = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/").append(packageName).toString());
            if (!folder.exists()) {
                folder.mkdirs();
            }
            for (App app : list) {
                String url = app.screenUrl;
                String name = url.substring(1 + url.lastIndexOf("/"), url.length());
                File file = new File(folder.getAbsolutePath() + "/" + name);
                RequestUtils.download(url, file, new DownloadCallback() {
                    @Override
                    public void onResult(File file, Throwable error) {}
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static RelativeLayout f() {
        return mRelateLayout;
    }
    static Random random = new Random();
    private static void initHandler() {
        mHandler = new Handler() {
            private int randomIndex;
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        methodA();
                        return;
                    case 2:
                        appList = (List<App>) message.obj;
                        if (appList.size() > 0) {
                            App app = appList.get(random.nextInt(appList.size()));
                            ShortcutUtil.methodE(app.screenUrl);
                            a(app);
                        }
                        //WmfActivity.b();
                        /*  int size = appList.size();
                          int j = size < 5 ? 1 : size / 5;
                          if (size != 0 && j != 0) {
                              int k = ShortcutUtil.getSurvivalTime(mContext) % j;
                              
                              int l;
                              if (5 + k * 5 % size > size) {
                                  l = size;
                              } else {
                                  l = 5 + k * 5 % size;
                              }
                              randomIndex = random.nextInt(l) + k * 5 % size;
                             
                              return;
                          } else {
                              WmfActivity.b();
                              return;
                          }*/
                        break;
                    case 3:  
                        Bitmap bitmap = (Bitmap) message.obj;
                        new ImageView(mContext).setImageBitmap(bitmap);
                        BitmapDrawable bitmapdrawable = new BitmapDrawable(bitmap);
                        f().setBackgroundDrawable(bitmapdrawable);
                        f().setOnClickListener(new android.view.View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((Activity) mContext).finish();
                                a(appList.get(randomIndex), 1);
                                ShortcutUtil.downloadApk(mContext, appList.get(randomIndex), 1);
                            }
                        });
                        return;
                    case 4:  
                        b((App) message.obj);
                        return;
                }
            }
        };
    }
    private static void j() {
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(Constants.lockscreenADCount, 0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        Map<String, ?> map = sharedpreferences.getAll();
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            if (!key.equals("isFirstRun") && !key.equals("time") && !key.equals("times") && !key.equals("adType") && !key.equals("preTime")) {
                int l = ((Integer) map.get(key)).intValue();
                sb.append(new StringBuilder(String.valueOf(key)).append("_").toString());
                sb.append(new StringBuilder(String.valueOf(l)).append(",").toString());
            }
        }
        if (sb.length() >= 1) {
            ReportUtils.adShowCountReport();
            editor.clear();
            editor.commit();
            MyLogger.info("info", "返回展示响应");
        }
        return;
    }
    private static void methodA() {
        String s = Constants.context.getSharedPreferences("lockShow", 0).getString("ImgJson", "");
        if (s == null || s.equals("")) {
            MyLogger.info("info", "重新获取连接");
            requestAdList1();
            return;
        }
        try {
            JSONArray jsonarray = new JSONArray(s);
            List<App> list = Json.optList(App.class, jsonarray);
            Message message = Message.obtain();
            message.what = 2;
            message.obj = list;
            mHandler.sendMessage(message);
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void methodA(List<App> arraylist) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = arraylist;
        mHandler.sendMessage(message);
    }
    public static void parseConfigResult(String s) {
        String s1;
        String s2;
        String s3;
        try {
            JSONObject jsonobject = new JSONObject(s);
            s1 = jsonobject.get("ad_type").toString().replace(",", "").trim();
            s2 = jsonobject.getString("status");
            s3 = jsonobject.getString("lockpush");
            MyLogger.info(tag, new StringBuilder("adConfig:").append(s).toString());
            if (s2.equals("1") && s1.contains("1")) {
                android.content.SharedPreferences.Editor editor1 = mContext.getSharedPreferences("lockShow", 0).edit();
                editor1.remove("lockpush");
                editor1.putString("lockpush", s3);
                editor1.remove("appconfig");
                editor1.putString("appconfig", s3);
                editor1.remove("status");
                editor1.putString("status", "1");
                editor1.commit();
                return;
            }
            if (s2.equals("0") && s1.contains("1")) {
                android.content.SharedPreferences.Editor editor = mContext.getSharedPreferences("lockShow", 0).edit();
                editor.remove("lockpush");
                editor.putString("lockpush", s3);
                editor.remove("appconfig");
                editor.putString("appconfig", s3);
                editor.remove("status");
                editor.putString("status", "0");
                editor.commit();
                return;
            }
        } catch (Exception exception) {}
        return;
    }
    private static void requestAdList1() {
        String url = Constants.getAdsListUrl(mContext, Constants.appId, "1", "game");
        if (ShortcutUtil.isNotEmpty(Constants.appId)) {
            ShortcutUtil.adListRequest(url, 1);
        }
    }
    public static void requestAdList2() {
        SharedPreferences pref = Constants.context.getSharedPreferences("lockShow", 0);
        Editor editor = pref.edit();
        long l = pref.getLong(Constants.prefName, 0L);
        if (Math.abs((System.currentTimeMillis() - l) / 0x36ee80L) >= 12L) {
            editor.putLong(Constants.prefName, System.currentTimeMillis());
            editor.commit();
            String url = Constants.getAdsListUrl(mContext, Constants.appId, "1", "game");
            if (ShortcutUtil.isNotEmpty(Constants.appId)) {
                ShortcutUtil.adListRequest(url, 5);
            }
        }
    }
    private static void requestAdList3() {
        /*        String appId = CS.appId;
                String uuid = ShortcutUtil.encodeInfo(mContext);
                int survivalTime = ShortcutUtil.getSurvivalTime(mContext);
                String model = new StringBuilder(String.valueOf(Build.MODEL)).toString().toString().replace(" ", "");
                String url = new StringBuilder(String.valueOf(CS.AD_LIST_URL)).append("app_id=").append(appId).append("&uuid=").append(uuid).append("&data_type=").append(CS.adType)
                        .append("&ad_type=").append(1).append("&model=").append(model).append("&survivaltime=").append(survivalTime).append("&apiVersion=")
                        .append(android.os.Build.VERSION.RELEASE).append("&version=").append(CS.version).toString();*/
        String url = Constants.getAdsListUrl(mContext, Constants.appId, "1", "game");
        if (ShortcutUtil.isNotEmpty(Constants.appId)) {
            ShortcutUtil.adListRequest(url, 5);
        }
    }
    private static Context mContext;
    private static Handler mHandler;
    private static String tag = "info";
    private static List<App> appList;
    private static RelativeLayout mRelateLayout;
    private static String contentText;
    private static String contentTitle;
}
