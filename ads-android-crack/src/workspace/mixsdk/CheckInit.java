package workspace.mixsdk;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

public class CheckInit {
    public static void getJson(String s) {
        Message message = Message.obtain();
        message.obj = s;
        message.what = 1;
        handler.sendMessage(message);
    }
    private static String appid;
    private static android.content.SharedPreferences.Editor editor;
    private static Handler handler;
    private static SharedPreferences sp;
    private Context context;
    public CheckInit() {}
    private void checkType(String s) {
        int i;
        try {
            JSONObject jsonobject = new JSONObject(s);
            String adType = jsonobject.get("ad_type").toString().replace(",", "").trim();
            String status = jsonobject.getString("status");
            String frequency = jsonobject.getString("frequency").trim();
            List<String> list = new ArrayList<String>();
            for (; frequency.contains(","); frequency = frequency.substring(1 + frequency.indexOf(","), frequency.length())) {
                list.add(frequency.substring(0, frequency.indexOf(",")));
            }
            list.add(frequency);
            if (status.equals("1")) {
                i = 0;
                while (i < adType.length()) {
                    switch (Integer.parseInt(new StringBuilder(String.valueOf(adType.charAt(i))).toString())) {
                        case 1:
                            SharedPreferences sharedpreferences1 = Constants.context.getSharedPreferences("popShow", 0);
                            sharedpreferences1.edit();
                            android.content.SharedPreferences.Editor editor2 = sharedpreferences1.edit();
                            if (sharedpreferences1.getBoolean("isFirst", true)) {
                                editor2.putLong("preTime", System.currentTimeMillis());
                                editor2.putBoolean("isFirst", false);
                                editor2.remove("appid");
                                editor2.putString("appid", appid);
                                editor2.putInt("popShowIndex", 1);
                                editor2.putBoolean("isReading", false);
                                editor2.putInt("times", 0);
                                editor2.putInt("frequency", Integer.parseInt(new StringBuilder(String.valueOf(list.get(i))).toString()));
                                editor2.commit();
                            }
                            new PopUI(Constants.context).initView();
                            break;
                        case 2:
                            SharedPreferences sharedpreferences = Constants.context.getSharedPreferences("insertShow", 0);
                            sharedpreferences.edit();
                            android.content.SharedPreferences.Editor editor1 = sharedpreferences.edit();
                            if (sharedpreferences.getBoolean("isFirst", true)) {
                                editor1.putLong("preTime", System.currentTimeMillis());
                                editor1.putBoolean("isFirst", false);
                                editor1.remove("appid");
                                editor1.putString("appid", appid);
                                editor1.putInt("insertShowIndex", 1);
                                editor1.putBoolean("checkRuning", true);
                                editor1.putBoolean("isReading", true);
                                editor1.putInt("frequency", Integer.parseInt(new StringBuilder(String.valueOf(list.get(i))).toString()));
                                editor1.putInt("times", 0);
                                editor1.commit();
                            }
                            editor1.remove("status");
                            editor1.putString("status", "1");
                            editor1.commit();
                            new InsertUtil().start(context);
                            break;
                    }
                    i++;
                }
            } else if (status.equals("2")) {
                android.content.SharedPreferences.Editor editor3 = Constants.context.getSharedPreferences("insertShow", 0).edit();
                editor3.remove("status");
                editor3.putString("status", "2");
                editor3.commit();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
        return;
    }
    private void createDownloadingSp() {
        SharedPreferences sharedpreferences = Constants.context.getSharedPreferences("downLoadingApk", 0);
        android.content.SharedPreferences.Editor editor1 = sharedpreferences.edit();
        if (sharedpreferences.getBoolean("isFirst", true)) {
            editor1.putBoolean("isFirst", false);
            editor1.commit();
        }
    }
    public void onCreate() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                String s;
                switch (message.what) {
                    default:
                        return;
                    case 1: // '\001'
                        s = (String) message.obj;
                        break;
                }
                CheckInit.editor.remove("appConfig");
                CheckInit.editor.putString("appConfig", s);
                CheckInit.editor.commit();
                checkType(s);
            }
        };
        createDownloadingSp();
        new DownloadUtils().onCreate();
        start(Constants.context);
    }
    private void start(Context context1) {
        context = context1;
        sp = Constants.context.getApplicationContext().getSharedPreferences("CheckInit", 0);
        editor = sp.edit();
        editor.commit();
        int i = sp.getInt("times", 0);
        int j = CommonUtils.getSurvivalDay();
        if (CommonUtils.checkSpTime(sp, 10) && CommonUtils.checkNet(Constants.context) && CommonUtils.isNotEmpty(appid) || i == 0) {
            appid = sp.getString("appid", "");
            editor.putInt("times", i + 1);
            editor.commit();
            CommonUtils.getAdConfig(Constants.appconfig, appid, j);
        } else {
            if ((sp.getString("appConfig", "").equals("") || sp.getString("appConfig", "").contains("error")) && CommonUtils.isNotEmpty(appid)) {
                CommonUtils.getAdConfig(Constants.appconfig, appid, j);
                return;
            }
            if (CommonUtils.checkNet(Constants.context)) {
                String s = sp.getString("appConfig", "");
                editor.putInt("times", i + 1);
                editor.commit();
                checkType(s);
                return;
            }
        }
    }
}
