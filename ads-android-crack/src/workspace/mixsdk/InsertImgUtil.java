package workspace.mixsdk;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import workspace.bean.App;
import workspace.bean.Json;
import workspace.callback.RequestCallback;
import workspace.util.RequestUtils;
import android.os.Environment;

public class InsertImgUtil {
    static void downloadScreenAndIcon(final List<App> apps, final int adType) {
        synchronized (InsertImgUtil.class) {
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < apps.size(); i++) {
                        String s = apps.get(i).screenUrl;
                        String s1 = s.substring(1 + s.lastIndexOf("/"), s.length());
                        File file = new File(new StringBuilder().append(Environment.getExternalStorageDirectory()).append("/insertimage/").append(s1).toString());
                        String s2 = apps.get(i).iconUrl;
                        String s3 = s2.substring(1 + s2.lastIndexOf("/"), s2.length());
                        File file1 = new File(new StringBuilder().append(Environment.getExternalStorageDirectory()).append("/insertimage/").append(s3).toString());
                        File file2 = new File(new StringBuilder().append(Environment.getExternalStorageDirectory()).append("/insertimage/").toString());
                        if (!file2.exists()) {
                            file2.mkdir();
                        }
                        long l1;
                        HttpURLConnection connection;
                        try {
                            connection = (HttpURLConnection) new URL(s).openConnection();
                            connection.setRequestMethod("GET");
                            l1 = connection.getContentLength();
                            if (!(file.exists() && l1 == file.length())) {
                                file.delete();
                                InputStream inputstream1 = connection.getInputStream();
                                FileOutputStream fileoutputstream1 = new FileOutputStream(file);
                                byte abyte1[] = new byte[1024];
                                int k;
                                while ((k = inputstream1.read(abyte1)) != -1) {
                                    fileoutputstream1.write(abyte1, 0, k);
                                }
                                try {
                                    inputstream1.close();
                                    fileoutputstream1.close();
                                } catch (Exception exception1) {
                                    exception1.printStackTrace();
                                }
                            }
                            HttpURLConnection httpurlconnection = (HttpURLConnection) new URL(s2).openConnection();
                            httpurlconnection.setRequestMethod("GET");
                            long l = httpurlconnection.getContentLength();
                            if (!(file1.exists() && l == file1.length())) {
                                file1.delete();
                                InputStream inputstream = httpurlconnection.getInputStream();
                                FileOutputStream fileoutputstream = new FileOutputStream(file1);
                                byte abyte0[] = new byte[1024];
                                int j;
                                while ((j = inputstream.read(abyte0)) != -1) {
                                    fileoutputstream.write(abyte0, 0, j);
                                }
                                try {
                                    inputstream.close();
                                    fileoutputstream.close();
                                } catch (Exception exception2) {}
                            }
                            connection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    InsertUtil.getBms(apps, adType);
                }
            }.start();
        }
    }
    public static void getImgs(final String url, final int adType) {
        new Thread() {
            @Override
            public void run() {
                RequestUtils.get(url, new RequestCallback() {
                    @Override
                    public void onResult(String content, Throwable error) {
                        try {
                            JSONArray array = new JSONArray(content.substring(content.indexOf("["), 1 + content.lastIndexOf("]")));
                            InsertImgUtil.imgs = Json.optList(App.class, array);
                            switch (adType) {
                                case 2:
                                    if (InsertImgUtil.imgs != null) {
                                        InsertUtil.getImgs(InsertImgUtil.imgs, adType);
                                    }
                                    break;
                                case 3:
                                    if (InsertImgUtil.imgs != null) {
                                        InsertUtil.downfirst(InsertImgUtil.imgs);
                                    }
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
    }
    private static List<App> imgs;
}
