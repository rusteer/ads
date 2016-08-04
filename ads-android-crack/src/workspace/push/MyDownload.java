package workspace.push;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.util.CommonUtils;
import workspace.util.MyLogger;
import workspace.util.ReportUtils;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyDownload {
    private static class DownLoadApk extends Thread {
        private int adType;
        private App imageInfo;
        private DownLoadApk(App imageinfo, int i) {
            imageInfo = imageinfo;
            adType = i;
        }
        @Override
        public void run() {
            int i = 0;
            String appUrl = imageInfo.appUrl;
            String s1 = appUrl.substring(1 + appUrl.lastIndexOf("/"), appUrl.length());
            MyLogger.info("info", new StringBuilder("下载：").append(appUrl).toString());
            File file = new File(new StringBuilder("sdcard/").append(s1).toString());
            try {
                HttpURLConnection httpurlconnection;
                httpurlconnection = (HttpURLConnection) new URL(appUrl).openConnection();
                httpurlconnection.connect();
                if (file.exists() && file.length() == httpurlconnection.getContentLength()) {
                    downloaded(file, adType);
                    return;
                }
                InputStream inputstream;
                FileOutputStream fileoutputstream;
                byte abyte0[];
                int j;
                file.delete();
                MyLogger.info("info", "file.exists()_nm.cancel");
                file.createNewFile();
                MyLogger.info("info", "=====下载APK");
                inputstream = httpurlconnection.getInputStream();
                fileoutputstream = new FileOutputStream(file);
                abyte0 = new byte[1024];
                j = httpurlconnection.getContentLength();
                int k;
                while ((k = inputstream.read(abyte0)) != -1) {
                    int l;
                    fileoutputstream.write(abyte0, 0, k);
                    l = (int) (100L * file.length() / j);
                    if (!(i == l || l >= 100)) {
                        changeNtf(imageInfo, l);
                        i = l;
                    }
                }
                fileoutputstream.flush();
                inputstream.close();
                fileoutputstream.close();
                httpurlconnection.disconnect();
                downloaded(file, adType);
                return;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return;
        }
    }
    private static void changeNtf(App imageinfo, int i) {
        String s = imageinfo.name;
        String s1 = new StringBuilder(String.valueOf(i)).append("%").toString();
        Notification notification = new Notification(0x1080081, imageinfo.description, System.currentTimeMillis());
        notification.flags = 2;
        notification.flags = 16;
        Intent intent = new Intent();
        intent.addFlags(0x20000000);
        PendingIntent pendingintent = PendingIntent.getActivity(CONTEXT, 0, intent, 0);
        notification.setLatestEventInfo(CONTEXT, s, new StringBuilder(String.valueOf("已下载")).append(s1).toString(), pendingintent);
        ((NotificationManager) CONTEXT.getSystemService("notification")).notify(Integer.parseInt(imageinfo.id), notification);
    }
    private static void ChangeNtfDone(App imageinfo, Bitmap bitmap) {
        String s = imageinfo.appUrl;
        String s1 = s.substring(1 + s.lastIndexOf("/"), s.length());
        MyLogger.info("info", new StringBuilder("下载：").append(s).toString());
        File file = new File(new StringBuilder("sdcard/").append(s1).toString());
        MyLogger.info("info", "changeNtcDone");
        String s2 = new StringBuilder(String.valueOf(imageinfo.name)).append("下载完毕,点击安装").toString();
        String s3 = imageinfo.description;
        Notification notification = new Notification(0x108008f, imageinfo.description, System.currentTimeMillis());
        notification.flags = 2;
        notification.flags = 32;
        if (file.exists() && notification != null) {
            Intent intent = new Intent();
            intent.addFlags(0x10000000);
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            PendingIntent pendingintent = PendingIntent.getActivity(CONTEXT, 0, intent, 0);
            notification.setLatestEventInfo(CONTEXT, s2, s3, pendingintent);
            LinearLayout linearlayout = new LinearLayout(CONTEXT.getApplicationContext());
            if (bitmap != null) {
                int i = CommonUtils.getNFid((ViewGroup) notification.contentView.apply(CONTEXT.getApplicationContext(), linearlayout));
                notification.contentView.setImageViewBitmap(i, bitmap);
            } else {
                ImgUtil.getIcon(imageInfo);
            }
            ((NotificationManager) CONTEXT.getSystemService("notification")).notify(Integer.parseInt(imageinfo.id), notification);
        }
    }
    public static void download(Context cONTEXT2, String id) {
        downloadAD(cONTEXT2, id);
    }
    private static void downloadAD(Context context1, String s) {
        Iterator iterator;
        CONTEXT = context1;
        iterator = getPushList().iterator();
        while (iterator.hasNext()) {
            App imageinfo = (App) iterator.next();
            if (s.equals(imageinfo.id)) {
                //L5_L5:
                MyLogger.info("info", new StringBuilder("getImg:").append(imageinfo.id).toString());
                imageInfo = imageinfo;
                break;
            }
        }
        if (imageInfo == null) {
            ((NotificationManager) CONTEXT.getSystemService("notification")).cancel(Integer.parseInt(s));
        } else if (!downloading.contains(imageInfo.id)) {
            MyLogger.info("info", new StringBuilder("imgInfo:").append(imageInfo.id).toString());
            downloading.add(imageInfo.id);
            //int i = Tools.getSurvivalDay(context);
            //String s1 = new StringBuilder(String.valueOf(Build.MODEL)).toString().toString().replace(" ", "");
            //String s2 = context.getApplicationContext().getSharedPreferences("pushShow", 0).getString("appid", "");
            //String s3 = Tools.getIMEI(context);
            //String s4 = new StringBuilder(CS.appClicked + "app_id=").append(s2).append("&uuid=").append(s3).append("&ad_id=").append(imageInfo.getId()).append("&ad_type=").append(3).append("&model=").append(s1).append("&survivaltime=").append(i).append("&apiVersion=").append(android.os.Build.VERSION.RELEASE).toString();
            //Tools.sendDataToService(s4);
            ReportUtils.appClickedReport();
            changeNtf(imageInfo, 0);
            downLoadAPK(imageInfo);
        }
    }
    private static void downLoadAPK(App imageinfo) {
        SharedPreferences sharedpreferences = CONTEXT.getSharedPreferences("pushDownLoadingSp", 0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        SharedPreferences sharedpreferences1 = CONTEXT.getSharedPreferences("pushDLPakNameSp", 0);
        android.content.SharedPreferences.Editor editor1 = sharedpreferences1.edit();
        if (sharedpreferences1.getString(imageinfo.id, "").equals("")) {
            editor1.putString(imageinfo.id, imageinfo.packageName);
            editor1.commit();
        }
        if (!Boolean.valueOf(sharedpreferences.getBoolean(imageinfo.id, false)).booleanValue()) {
            editor.putBoolean(imageinfo.id, true);
            editor.commit();
            new DownLoadApk(imageinfo, 3).start();
        }
    }
    private static void downloaded(File file, int i) {
        SharedPreferences sharedpreferences = CONTEXT.getSharedPreferences("pushDownLoadingSp", 0);
        if (sharedpreferences.getBoolean(imageInfo.id, false)) {
            //String s = context.getSharedPreferences("pushShow", 0).getString("appid", "");
            //String s1 = Tools.getIMEI(context);
            //Tools.sendDataToService(new StringBuilder(CS.apkDownLoaded + "app_id=").append(s).append("&uuid=").append(s1).append("&ad_id=").append(imageInfo.getId()) .append("&ad_type=").append(i).toString());
            ReportUtils.apkDownLoadedReport();
            ChangeNtfDone(imageInfo, null);
        }
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(imageInfo.id);
        editor.commit();
        downloading.remove(imageInfo.id);
        CommonUtils.openFile(CONTEXT, file, imageInfo.packageName);
    }
    public static void getIcon(App imageinfo, Bitmap bitmap) {
        Message message = Message.obtain();
        message.obj = bitmap;
        iconImginfo = imageinfo;
        message.what = 3;
        handler.sendMessage(message);
    }
    private static List<App> getPushList() {
        MyLogger.info("info", "getPushList");
        String s = CONTEXT.getSharedPreferences("pushShow", 0).getString("ImgJson", "");
        try {
            JSONArray jsonarray = new JSONArray(s);
            return Json.optList(App.class, jsonarray);
        } catch (JSONException jsonexception) {
            jsonexception.printStackTrace();
        }
        return new ArrayList<App>();
    }
    public static void init(Context context2) {
        CONTEXT = context2;
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        SharedPreferences pref = CONTEXT.getSharedPreferences("pushDownLoadingSp", 0);
                        android.content.SharedPreferences.Editor editor = pref.edit();
                        if (pref.getAll().size() > 0 && imageInfo != null) {
                            editor.putBoolean(imageInfo.id, true);
                            editor.commit();
                            new DownLoadApk(imageInfo, 3).start();
                            return;
                        }
                        break;
                    case 2:
                        String id = (String) message.obj;
                        downloadAD(CONTEXT, id);
                        break;
                    case 3:
                        Bitmap bitmap = (Bitmap) message.obj;
                        ChangeNtfDone(iconImginfo, bitmap);
                        break;
                }
            }
        };
    }
    public static void reDownLoading(Context context) {
        if (handler != null) {
            handler.sendEmptyMessage(1);
        }
    }
    public static void stopDownLoading(Context cONTEXT2) {
        if (handler != null) {
            handler.sendEmptyMessage(2);
        }
    }
    private static Context CONTEXT;
    private static Handler handler;
    private static App iconImginfo;
    private static List<String> downloading = new ArrayList<String>();
    private static App imageInfo;
}
