package workspace.push;
import workspace.util.CommonUtils;
import workspace.util.Constants;
import workspace.util.ReportUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PLoader {
    public void init(Context context, String s) {
        Constants.context = context;
        Editor editor = context.getSharedPreferences("PushVersion", 0).edit();
        editor.remove("version");
        editor.putString("version", Constants.version);
        editor.commit();
        SharedPreferences pref = context.getSharedPreferences("pushShow", 0);
        Editor editor1 = pref.edit();
        if (pref.getBoolean("isFirst", true)) {
            //Tools.sendDataToService(new StringBuilder(CS.newIMEI + "&app_id=").append(s).append("uuid=").append(Tools.getIMEI(context)).append("&ad_type=3").toString());
            ReportUtils.newIMEIReport();
            editor1.putLong("preTime", System.currentTimeMillis());
            editor1.putBoolean("isFirst", false);
            editor1.putInt("times", 0);
            editor1.putString("appid", s);
            editor1.putInt("installMonth", 1 + CommonUtils.getMonth());
            editor1.putInt("installDay", CommonUtils.getDate());
            editor1.commit();
        }
        MyDownload.init(context);
        new PushShow().onStart(context);
    }
}
