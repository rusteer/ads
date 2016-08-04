package workspace.mixsdk;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.callback.DownloadCallback;
import workspace.callback.RequestCallback;
import workspace.util.RequestUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PopImgUtil {
    public static void clear() {
        bms.clear();
    }
    public static void downloadScreen(final List<App> imageInfos, final int adType) {
        new Thread() {
            @Override
            public void run() {
                PopImgUtil.bms = new ArrayList<Bitmap>();
                String screenUrl = imageInfos.get(0).screenUrl;
                File file = new File(new StringBuilder("sdcard/popimage/").append(screenUrl.substring(1 + screenUrl.lastIndexOf("/"), screenUrl.length())).toString());
                File folder = new File("sdcard/popimage/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                RequestUtils.download(screenUrl, file, new DownloadCallback() {
                    @Override
                    public void onResult(File file, Throwable error) {
                        if (file != null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                            PopImgUtil.bms.add(bitmap);
                            PopUI.getBms(PopImgUtil.bms, adType);
                        }
                    }
                });
            }
        }.start();
    }
    public static void getImgs(final String imgPath, final int adType) {
        new Thread() {
            @Override
            public void run() {
                RequestUtils.get(imgPath, new RequestCallback() {
                    @Override
                    public void onResult(String content, Throwable error) {
                        try {
                            JSONArray array = new JSONArray(content.substring(content.indexOf("["), 1 + content.lastIndexOf("]")));
                            PopImgUtil.imgs = Json.optList(App.class, array);
                            PopUI.getImgs(PopImgUtil.imgs, adType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
    private static List<Bitmap> bms = null;
    private static List<App> imgs = null;
    public PopImgUtil() {}
}
