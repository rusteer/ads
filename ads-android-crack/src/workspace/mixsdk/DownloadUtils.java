package workspace.mixsdk;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import workspace.bean.App;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.ReportUtils;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@SuppressWarnings("deprecation")
public class DownloadUtils {
    private class DownLoadApk extends Thread {
        private int adType;
        private List<App> imgsListThread;
        private int indexThread;
        private DownLoadApk(List<App> list, int indexThread, int adType) {
            imgsListThread = list;
            this.indexThread = indexThread;
            this.adType = adType;
        }
        @Override
        public void run() {
            String str1 = imgsListThread.get(indexThread).appUrl;
            String str2 = str1.substring(1 + str1.lastIndexOf("/"), str1.length());
            File localFile = new File("sdcard/" + str2);
            try {
                HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(str1).openConnection();
                localHttpURLConnection.connect();
                if (localFile.exists() && localFile.length() == localHttpURLConnection.getContentLength()) {
                    handleDownloaded(localFile, indexThread, imgsListThread, adType, 1);
                    return;
                }
                localFile.delete();
                ((NotificationManager) Constants.context.getSystemService("notification")).cancel(Integer.parseInt(imgsListThread.get(indexThread).id));
                localFile.createNewFile();
                createNotifaction(imgsListThread, indexThread);
                InputStream localInputStream = localHttpURLConnection.getInputStream();
                FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
                byte[] arrayOfByte = new byte[1024];
                int i = localHttpURLConnection.getContentLength();
                int m;
                for (int j = 0;; j = m) {
                    do {
                        int k = localInputStream.read(arrayOfByte);
                        if (k == -1) {
                            localFileOutputStream.flush();
                            localInputStream.close();
                            localFileOutputStream.close();
                            localHttpURLConnection.disconnect();
                            downloaded(localFile, indexThread, imgsListThread, adType);
                            return;
                        }
                        localFileOutputStream.write(arrayOfByte, 0, k);
                        m = (int) (100L * localFile.length() / i);
                    } while (j == m || m >= 100);
                    changeNotification(imgsListThread, m, indexThread);
                }
            } catch (Exception localException) {}
        }
    }
    public static void getIcon(App imageinfo, Bitmap bitmap) {
        Message message = Message.obtain();
        message.what = 6;
        message.obj = bitmap;
        iconImageInfo = imageinfo;
        handler.sendMessage(message);
    }
    public static void reDownLoading() {
        if (handler != null) {
            handler.sendEmptyMessage(5);
        }
    }
    static void setImages(List<App> apps, int index, int j, String s) {
        if (handler != null && apps != null) {
            Message message = Message.obtain();
            message.what = 1;
            message.arg1 = index;
            message.arg2 = j;
            message.obj = apps;
            handler.sendMessage(message);
        }
    }
    static void setImages2(List<App> apps, int i, int j, String s) {
        if (handler != null && apps != null) {
            Message message = Message.obtain();
            message.what = 7;
            message.arg1 = i;
            message.arg2 = j;
            message.obj = apps;
            handler.sendMessage(message);
        }
    }
    public static void StopDownLoading() {
        if (handler != null) {
            handler.sendEmptyMessage(4);
        }
    }
    private static Handler handler;
    private static App iconImageInfo;
    private PendingIntent contentIntent;
    private List<App> imgsList;
    public DownloadUtils() {}
    private void changeNotification(List<App> arraylist, int i, int j) {
        String s = arraylist.get(j).name;
        String s1 = new StringBuilder(String.valueOf(i)).append("%").toString();
        Notification notification = new Notification(0x1080081, arraylist.get(j).description, System.currentTimeMillis());
        notification.flags = 2;
        notification.flags = 16;
        notification.setLatestEventInfo(Constants.context, s, new StringBuilder(String.valueOf("已下载")).append(s1).toString(), contentIntent);
    }
    private void changeNtcDone(App imageinfo, Bitmap bitmap) {
        Notification notification;
        if (bitmap != null) {
            String s = iconImageInfo.appUrl;
            String s1 = s.substring(1 + s.lastIndexOf("/"), s.length());
            File file = new File(new StringBuilder("sdcard/").append(s1).toString());
            String s2 = new StringBuilder(String.valueOf(imageinfo.name)).append("下载完毕,点击安装").toString();
            String s3 = imageinfo.description;
            notification = new Notification(0x108008f, imageinfo.description, System.currentTimeMillis());
            notification.flags = 2;
            notification.flags = 32;
            if (!file.exists() || notification == null) {
                CommonUtils.getIcon(imageinfo);
            } else {
                Intent intent = new Intent();
                intent.addFlags(0x10000000);
                intent.setAction("android.intent.action.VIEW");
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                PendingIntent pendingintent = PendingIntent.getActivity(Constants.context, 0, intent, 0);
                notification.setLatestEventInfo(Constants.context, s2, s3, pendingintent);
                LinearLayout linearlayout = new LinearLayout(Constants.context.getApplicationContext());
                int i = CommonUtils.getNFid((ViewGroup) notification.contentView.apply(Constants.context.getApplicationContext(), linearlayout));
                notification.contentView.setImageViewBitmap(i, bitmap);
                ((NotificationManager) Constants.context.getSystemService("notification")).notify(Integer.parseInt(imageinfo.id), notification);
            }
        }
        return;
    }
    private boolean checkdownloadingSp(int i) {
        Iterator iterator = ((HashMap) getPref().getAll()).entrySet().iterator();
        String s;
        do {
            java.util.Map.Entry entry;
            do {
                if (!iterator.hasNext()) { return false; }
                entry = (java.util.Map.Entry) iterator.next();
                s = (String) entry.getKey();
            } while (s.equals("times") || s.equals("isFirst"));
        } while (Integer.parseInt(s) != i);
        return true;
    }
    private void cleanAllDownLoading() {
        Map<String, ?> hashmap = getPref().getAll();
        Iterator<?> iterator = hashmap.entrySet().iterator();
        do {
            java.util.Map.Entry entry;
            String s;
            do {
                if (!iterator.hasNext()) { return; }
                entry = (java.util.Map.Entry) iterator.next();
                s = (String) entry.getKey();
            } while (hashmap.size() <= 2 || s.equals("times") || s.equals("isFirst"));
            int i = Integer.parseInt(s);
            try {
                ((NotificationManager) Constants.context.getSystemService("notification")).cancel(Integer.parseInt(imgsList.get(i).id));
            } catch (Exception exception) {}
        } while (true);
    }
    private void createHandler() {
        handler = new Handler() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleMessage(Message message) {
                int indexThread = message.arg1;
                int adType = message.arg2;
                switch (message.what) {
                    case 1:
                        imgsList = (List<App>) message.obj;
                        if (!checkdownloadingSp(indexThread)) {
                            ResponseClickApp(imgsList, indexThread, adType);
                            dwSPAddIndex(indexThread, adType);
                            new DownLoadApk(imgsList, indexThread, adType).start();
                            return;
                        }
                        break;
                    case 4:
                        cleanAllDownLoading();
                        break;
                    case 5:
                        if (hasDownloading()) {
                            reDownLoadAll();
                        }
                        break;
                    case 6:
                        changeNtcDone(DownloadUtils.iconImageInfo, (Bitmap) message.obj);
                        break;
                    case 7:
                        imgsList = (List<App>) message.obj;
                        if (!checkdownloadingSp(indexThread)) {
                            dwSPAddIndex(indexThread, adType);
                            new DownLoadApk((List<App>) message.obj, indexThread, adType).start();
                            return;
                        }
                        break;
                }
            }
        };
    }
    private void createNotifaction(List<App> list, int i) {
        String name = list.get(i).name;
        String intro = list.get(i).description;
        Intent intent = new Intent(Constants.context, Constants.context.getClass());
        intent.addFlags(0x20000000);
        contentIntent = PendingIntent.getActivity(Constants.context, 0, intent, 0);
        Notification notification = new Notification(0x1080081, intro, System.currentTimeMillis());
        notification.flags = 2;
        notification.flags = 16;
        notification.setLatestEventInfo(Constants.context, name, "已下载0%", contentIntent);
    }
    private void downloaded(File file, int i, List<App> list, int j) {
        if (checkdownloadingSp(i)) {
            //Tools.sendDataToService(new StringBuilder(CS.apkDownLoaded + "app_id=").append(appid).append("&uuid=").append(s).append("&ad_id=") .append(((ImageInfo) arraylist.get(i)).getId()).append("&ad_type=").append(j).toString());
            ReportUtils.apkDownLoadedReport();
        }
        removeDownLoadingSp(new StringBuilder(String.valueOf(i)).toString());
        android.content.SharedPreferences.Editor editor = Constants.context.getSharedPreferences("downLoadApk", 0).edit();
        editor.putInt(list.get(i).id, j);
        editor.commit();
        android.content.SharedPreferences.Editor editor1 = Constants.context.getSharedPreferences("downLoadApkPackageName", 0).edit();
        editor1.putString(list.get(i).id, list.get(i).packageName);
        editor1.commit();
    }
    private void dwSPAddIndex(int i, int j) {
        android.content.SharedPreferences.Editor editor = getPref().edit();
        editor.putString(new StringBuilder(String.valueOf(i)).toString(), new StringBuilder(String.valueOf(j)).toString());
        editor.commit();
    }
    private SharedPreferences getPref() {
        return Constants.context.getSharedPreferences("downLoadingApk", 0);
    }
    private void handleDownloaded(File file, int i, List<App> list, int j, int k) {
        removeDownLoadingSp(new StringBuilder(String.valueOf(i)).toString());
        android.content.SharedPreferences.Editor editor = Constants.context.getSharedPreferences("downLoadApk", 0).edit();
        editor.putInt(list.get(i).id, j);
        editor.commit();
        android.content.SharedPreferences.Editor editor1 = Constants.context.getSharedPreferences("downLoadApkPackageName", 0).edit();
        editor1.putString(list.get(i).id, list.get(i).packageName);
        editor1.commit();
    }
    private boolean hasDownloading() {
        Map<String, ?> map = getPref().getAll();
        return map != null && map.size() > 2;
    }
    public void onCreate() {
        Constants.context.getSharedPreferences("CheckInit", 0).getString("appid", "");
        createHandler();
    }
    private void reDownLoadAll() {
        Map<String, ?> map = getPref().getAll();
        Iterator<?> iterator = map.entrySet().iterator();
        do {
            java.util.Map.Entry entry;
            String s;
            do {
                if (!iterator.hasNext()) { return; }
                entry = (java.util.Map.Entry) iterator.next();
                s = (String) entry.getKey();
            } while (s.equals("times") || s.equals("isFirst"));
            String s1 = (String) entry.getValue();
            new DownLoadApk(imgsList, Integer.parseInt(s), Integer.parseInt(s1)).start();
        } while (true);
    }
    private void removeDownLoadingSp(String s) {
        android.content.SharedPreferences.Editor editor = getPref().edit();
        editor.remove(s);
        editor.commit();
    }
    private void ResponseClickApp(List<App> apps, int i, int j) {
        //String s = Tools.getIMEI(CS.context);
        //Tools.sendDataToService(new StringBuilder(CS.appClicked + "app_id=").append(appid).append("&uuid=").append(s).append("&ad_id=").append(((ImageInfo) arraylist.get(i)).getId()).append("&ad_type=").append(j).append(flag).toString());
        ReportUtils.appClickedReport();
    }
}
