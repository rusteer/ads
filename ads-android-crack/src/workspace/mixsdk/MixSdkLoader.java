package workspace.mixsdk;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import android.content.Context;
import android.content.SharedPreferences;

public class MixSdkLoader {
    private CheckInit checkInit;
    public MixSdkLoader() {
        checkInit = null;
    }
    public void init(Context context, String s) {
        Constants.context = context;
        android.content.SharedPreferences.Editor editor;
        SharedPreferences sharedpreferences;
        android.content.SharedPreferences.Editor editor1;
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        editor = context.getSharedPreferences("MixVersion", 0).edit();
        editor.remove("version");
        editor.putString("version", "1.0.23");
        editor.commit();
        sharedpreferences = context.getSharedPreferences("CheckInit", 0);
        editor1 = sharedpreferences.edit();
        if (sharedpreferences.getBoolean("isFirst", true)) {
            editor1.putLong("preTime", System.currentTimeMillis());
            editor1.putBoolean("isFirst", false);
            editor1.putInt("times", 0);
            editor1.remove("appid");
            editor1.putString("appid", s);
            editor1.putInt("installMonth", 1 + CommonUtils.getMonth());
            editor1.putInt("installDay", CommonUtils.getDate());
            editor1.commit();
        }
        if (checkInit == null) {
            checkInit = new CheckInit();
            checkInit.onCreate();
        }
    }
}
