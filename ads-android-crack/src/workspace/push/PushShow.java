package workspace.push;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import workspace.bean.App;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.MyLogger;
import workspace.util.ReportUtils;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PushShow extends Service {
    public static void getImgs(List<App> arraylist) {
        Message message = Message.obtain();
        message.what = 4;
        message.obj = arraylist;
        handler.sendMessage(message);
    }
    public static void getJson(String s) {
        Message message = Message.obtain();
        message.what = 2;
        message.obj = s;
        MyLogger.info("info", new StringBuilder("appconfig:").append(s).toString());
        handler.sendMessage(message);
    }
    private static Context context;
    private static Handler handler;
    private static final int PUSHOW_INIT = 1;
    private static final int PUSHOW_GETCONFIG = 2;
    private static final int PUSHOW_SHOWIT = 3;
    private static final int PUSHOW_GETIMGS = 4;
    private static final int PUSHOW_OPENUNINSTALLFILE = 5;
    private void AddAdshowCount(App imageinfo) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("pushADCount", 0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(imageinfo.id, 1 + sharedpreferences.getInt(imageinfo.id, 0));
        editor.commit();
        ResponseADshowCount();
    }
    private void analyseUrl(String s) {
        String adType;
        String status;
        String frequency;
        String lockPush;
        List<String> list = new ArrayList<String>();
        try {
            JSONObject jsonobject = new JSONObject(s);
            adType = jsonobject.get("ad_type").toString().replace(",", "").trim();
            status = jsonobject.getString("status");
            frequency = jsonobject.getString("frequency").trim();
            lockPush = jsonobject.getString("lockpush");
        } catch (Exception exception) {
            return;
        }
        String s5 = frequency;
        while (s5.contains(",")) {
            list.add(s5.substring(0, s5.indexOf(",")));
            s5 = s5.substring(1 + s5.indexOf(","), s5.length());
        }
        list.add(s5);
        if (status.equals("1")) {
            for (int i = 0; i < adType.length(); i++) {
                switch (Integer.parseInt(new StringBuilder(String.valueOf(adType.charAt(i))).toString())) {
                    case 1:
                    case 2:
                        MyLogger.info("info", new StringBuilder("ADf:2").append(list.get(i)).toString());
                        break;
                    case 3://L6_L6:
                        SharedPreferences pref = context.getSharedPreferences("pushShow", 0);
                        Editor editor = pref.edit();
                        editor.remove(s5);
                        editor.putInt("frequency", Integer.parseInt(list.get(i)));
                        int j = pref.getInt("times", 0);
                        int k = pref.getInt("frequency", 0);
                        if (j == 0) {
                            editor.putLong("preTime", System.currentTimeMillis());
                            editor.putInt("times", j + 1);
                            editor.commit();
                            handler.sendEmptyMessage(3);
                        } else {
                            if (CommonUtils.checkSpTime(pref, k)) {
                                MyLogger.info("info", new StringBuilder("push时间间隔>:").append(k).toString());
                                editor.putLong("preTime", System.currentTimeMillis());
                                editor.putInt("times", j + 1);
                                editor.commit();
                                handler.sendEmptyMessage(3);
                            }
                        }
                        editor.remove("lockpush");
                        editor.putString("lockpush", lockPush);
                        editor.commit();
                        MyLogger.info("info", new StringBuilder("ADf:3").append(list.get(i)).toString());
                        break;
                }
            }
        }
    }
    private void checkShow() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("pushShow", 0);
        android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
        int i = sharedpreferences.getInt("times", 0);
        long l = (System.currentTimeMillis() - sharedpreferences.getLong("preTime", 0L)) / 60000L;
        if (Math.abs(l) <= 3L && i == 0) {
            MyLogger.info("info", new StringBuilder("times：").append(i).append("或者间隔小于").append(l).append("分钟").toString());
        } else {
            handler.sendEmptyMessage(1);
        }
        editor.commit();
    }
    private void checkShowed(List<App> list) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("pushADShowed", 0);
        Editor editor = sharedpreferences.edit();
        Map<String, ?> map = sharedpreferences.getAll();
        for (int i = 0; i < list.size(); i++) {
            App imageinfo = list.get(i);
            if (map.size() >= list.size()) {
                sharedpreferences.edit().clear().commit();
                map = sharedpreferences.getAll();
            }
            if (!(map.containsKey(imageinfo.id) || imageinfo == null)) {
                String appUrl = imageinfo.appUrl;
                new File(new StringBuilder("sdcard/").append(appUrl.substring(1 + appUrl.lastIndexOf("/"), appUrl.length())).toString());
                editor.putString(imageinfo.id, "3");
                editor.commit();
                createNotification(imageinfo);
                Constants.pushImageList = list;
                return;
            }
        }
        return;
    }
    private void createHandler() {
        handler = new Handler() {
            @SuppressWarnings({ "unchecked" })
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case PUSHOW_INIT:
                        SharedPreferences sharedpreferences1 = PushShow.context.getApplicationContext().getSharedPreferences("pushShow", 0);
                        String s1 = sharedpreferences1.getString("appid", "");
                        String s2 = sharedpreferences1.getString(Constants.appconfig, "");
                        long l = sharedpreferences1.getLong("preTime", 0L);
                        long l1 = (System.currentTimeMillis() - l) / 60000L;
                        int i = sharedpreferences1.getInt("installMonth", 1 + CommonUtils.getMonth());
                        int j = sharedpreferences1.getInt("installDay", CommonUtils.getDate());
                        int k;
                        if (1 + CommonUtils.getMonth() > i) {
                            k = CommonUtils.getDate() + Calendar.getInstance().getActualMaximum(5) - j;
                        } else {
                            k = CommonUtils.getDate() - j;
                        }
                        if ((Math.abs(l1) >= 10L || s2.equals("") || s2.contains("error")) && CommonUtils.checkStrNull(s1)) {
                            CommonUtils.getAdConfig(Constants.appconfig, s1, k);
                            MyLogger.info("info", "取服务器Appconfig");
                            return;
                        } else {
                            MyLogger.info("info", "取本地Appconfig");
                            analyseUrl(s2);
                            return;
                        }
                    case PUSHOW_GETCONFIG:
                        SharedPreferences sharedpreferences = PushShow.context.getSharedPreferences("pushShow", 0);
                        String s = (String) message.obj;
                        Editor editor = sharedpreferences.edit();
                        editor.remove(Constants.appconfig);
                        editor.putString(Constants.appconfig, s);
                        editor.commit();
                        analyseUrl(s);
                        return;
                    case PUSHOW_SHOWIT: // '\003'
                        getImgs();
                        return;
                    case PUSHOW_GETIMGS: // '\004'
                        showPush((List<App>) message.obj);
                        return;
                    case PUSHOW_OPENUNINSTALLFILE: // '\005'
                        openUninstallFile();
                        return;
                }
            }
        };
    }
    @SuppressWarnings("deprecation")
    private void createNotification(final App app) {
        String s = context.getSharedPreferences("pushShow", 0).getString("lockpush", "0");
        MyLogger.info("info", "createNotifaction");
        String s1 = app.name;
        String s2 = app.description;
        String s3 = app.description;
        Intent intent = new Intent("com.android.psoho.init.StartDLReceiver");
        intent.putExtra("imageId", app.id);
        PendingIntent pendingintent = PendingIntent.getBroadcast(context, Integer.parseInt(app.id), intent, 0x8000000);
        final Notification nfCreate = new Notification(0x108008f, s3, System.currentTimeMillis());
        nfCreate.flags = 2;
        if (s.equals("0")) {
            nfCreate.flags = 16;
        } else {
            nfCreate.flags = 32;
        }
        nfCreate.setLatestEventInfo(context, s1, s2, pendingintent);
        new Thread() {
            @Override
            public void run() {
                final NotificationManager notificationmanager = (NotificationManager) context.getSystemService("notification");
                android.graphics.Bitmap bitmap = ImgUtil.getBms(app);
                try {
                    Object object1 = View.inflate(context, nfCreate.contentView.getLayoutId(), null);
                    Object object2 = recurseGroup((View) object1);
                    //nfCreate.contentView.setImageViewBitmap(imgID, bitmap);
                    nfCreate.contentView.setImageViewBitmap(((ImageView) object2).getId(), bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (nfCreate != null) {
                    notificationmanager.notify(Integer.parseInt(app.id), nfCreate);
                }
                AddAdshowCount(app);
            }
        }.start();
    }
    private void getImgs() {
        String appId = context.getApplicationContext().getSharedPreferences("pushShow", 0).getString("appid", "");
        String url = Constants.getAdsListUrl(context, appId, "3", "ad");
        if (CommonUtils.checkStrNull(appId)) {
            ImgUtil.getImgs(url, 3);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onStart(Context context1) {
        context = context1;
        createHandler();
        new Thread() {
            @Override
            public void run() {
                do {
                    try {
                        checkShow();
                        Thread.sleep(0x493e0L);
                    } catch (InterruptedException interruptedexception) {
                        interruptedexception.printStackTrace();
                    }
                } while (true);
            }
        }.start();
        handler.sendEmptyMessage(5);
    }
    private void openUninstallFile() {
        MyLogger.info("info", "openUninstallFile");
        SharedPreferences sharedpreferences = context.getSharedPreferences("downLoadFileName", 0);
        Editor editor = sharedpreferences.edit();
        Map<String, ?> map = sharedpreferences.getAll();
        long preTime = sharedpreferences.getLong("preTime", 0L);
        long l1 = (System.currentTimeMillis() - preTime) / 0x36ee80L;
        int times = sharedpreferences.getInt("times", 0);
        if (times != 0 && l1 < 4L) { return; }
        if (map.size() <= 2) { return; }
        for (String key : map.keySet()) {
            if (!key.equals("isFirst") && !key.equals("times") && !key.equals("preTime")) {
                String value = (String) map.get(key);
                File file = new File(new StringBuilder("sdcard/").append(value).toString());
                if (!CommonUtils.apkExits(context, key) && file.exists()) {
                    editor.putLong("preTime", System.currentTimeMillis());
                    editor.putInt("times", times + 1);
                    editor.commit();
                    CommonUtils.openFile(context, file, key);
                }
            }
        }
        editor.putLong("preTime", System.currentTimeMillis());
        editor.commit();
        return;
    }
    private ImageView recurseGroup(View paramView) { // 获得remote中的ImageView
        try {
            if (paramView instanceof ViewGroup) {
                for (int i1 = ((ViewGroup) paramView).getChildCount(); i1 > 0; --i1) {
                    ImageView localImageView;
                    if ((localImageView = recurseGroup(((ViewGroup) paramView).getChildAt(i1 - 1))) != null) { return localImageView; }
                }
            }
            if (paramView instanceof ImageView) { return (ImageView) paramView; }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }
    private void ResponseADshowCount() {
        SharedPreferences sharedpreferences = context.getSharedPreferences("pushADCount", 0);
        Editor editor = sharedpreferences.edit();
        Map<String, ?> hashmap = sharedpreferences.getAll();
        StringBuilder sb = new StringBuilder();
        for (String key : hashmap.keySet()) {
            if (!key.equals("isFirstRun") && !key.equals("time") && !key.equals("times") && !key.equals("adType")) {
                int value = ((Integer) hashmap.get(key)).intValue();
                sb.append(key).append("_").append(value).append(",");
            }
        }
        if (sb.length() >= 1) {
            ReportUtils.adShowCountReport();
            editor.clear();
            editor.commit();
        }
        return;
    }
    private void showPush(List<App> list) {
        if (list != null) {
            checkShowed(list);
        }
    }
}
