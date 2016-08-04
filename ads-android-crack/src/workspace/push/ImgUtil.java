package workspace.push;
import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.callback.RequestCallback;
import workspace.util.Constants;
import workspace.util.MyLogger;
import workspace.util.RequestUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImgUtil {
    public static Bitmap getBms(App imageinfo) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream bufferedinputstream = new BufferedInputStream(new URL(imageinfo.iconUrl).openConnection().getInputStream());
            bitmap = BitmapFactory.decodeStream(bufferedinputstream);
            bufferedinputstream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static void getIcon(final App imageInfo) {
        new Thread() {
            @Override
            public void run() {
                try {
                    MyLogger.info("info", "下载ICON========================== ");
                    HttpURLConnection httpurlconnection = (HttpURLConnection) new URL(imageInfo.iconUrl).openConnection();
                    httpurlconnection.connect();
                    Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(httpurlconnection.getInputStream()));
                    httpurlconnection.disconnect();
                    MyDownload.getIcon(imageInfo, bitmap);
                    return;
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }.start();
    }
    public static void getImgs(final String url, final int adType) {
        new Thread() {
            @Override
            public void run() {
                RequestUtils.get(url, new RequestCallback() {
                    @Override
                    public void onResult(String content, Throwable error) {
                        String s2 = content.substring(content.indexOf("["), 1 + content.lastIndexOf("]"));
                        android.content.SharedPreferences.Editor editor = Constants.context.getSharedPreferences("pushShow", 0).edit();
                        editor.remove("ImgJson");
                        editor.putString("ImgJson", s2);
                        editor.commit();
                        try {
                            JSONArray jsonarray = new JSONArray(s2);
                            List<App> arraylist = Json.optList(App.class, jsonarray);
                            PushShow.getImgs(arraylist);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
    public ImgUtil() {}
}
