package workspace.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import workspace.callback.DownloadCallback;
import workspace.callback.RequestCallback;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

public class RequestUtils {
    private static void doDownload(final String url, final String filePath, final DownloadCallback callback) throws Throwable {
        MyLogger.info("RequestUtils.doDownload:url=" + url + ",path=" + filePath);
        DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse httpresponse = defaulthttpclient.execute(httpget);
        File file = new File(filePath);
        if (file.exists() && file.length() == httpresponse.getEntity().getContentLength()) {
            defaulthttpclient.getConnectionManager().shutdown();
            callback.onResult(file, null);
        } else {
            file.delete();
            File tempFile = new File(filePath + ".tmp");
            InputStream inputStream = httpresponse.getEntity().getContent();
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] abyte0 = new byte[1024];
            int read;
            while ((read = inputStream.read(abyte0)) != -1) {
                outputStream.write(abyte0, 0, read);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
            if (httpget != null) {
                httpget.abort();
            }
            tempFile.renameTo(file);
            defaulthttpclient.getConnectionManager().shutdown();
            callback.onResult(file, null);
        }
    }
    private static void doGet(final String url, final RequestCallback callback, final int tryCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("url:").append(url).append("\n");
        try {
            MyLogger.info("RequestUtils.get:url=" + url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) { throw new RuntimeException("statusCode=" + statusCode); }
            String result = EntityUtils.toString(httpResponse.getEntity(), UTF_8);
            sb.append("result:\n").append(result).append("\n");
            String substring = url.contains("php") ? url.substring(url.indexOf("8822") + 5, url.indexOf("php") - 1) : "";
            log(Environment.getExternalStorageDirectory() + "/request-" + substring + "-" + System.currentTimeMillis() + ".txt", sb.toString());
            MyLogger.info("RequestUtils.get:result=" + result);
            if (callback != null) {
                callback.onResult(result, null);
            }
        } catch (Throwable e) {
            MyLogger.error(e);
            if (tryCount > 1) {
                doGet(url, callback, tryCount - 1);
            } else {
                if (callback != null) {
                    callback.onResult(null, e);
                }
            }
        }
    }
    public static void download(final String url, final File file, final DownloadCallback callback) {
        download(url, file.getAbsolutePath(), callback);
    }
    private static void download(final String url, final String filePath, final DownloadCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... p) {
                try {
                    doDownload(url, filePath, callback);
                } catch (Throwable e) {
                    callback.onResult(null, e);
                }
                return null;
            }
        }.execute(new Void[0]);
    }
    public static void get(final String url) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... p) {
                try {
                    new DefaultHttpClient().execute(new HttpGet(url));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(new Void[0]);
    }
    public static void get(final String url, final RequestCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... p) {
                doGet(url, callback, 1);
                return null;
            }
        }.execute(new Void[0]);
    }
    public static Bitmap getRemoteBitmpa(String url) {
        MyLogger.info("RequestUtils.getRemoteBitmpa:url=" + url);
        Bitmap bitmap = null;
        HttpURLConnection httpurlconnection = null;
        InputStream inputstream = null;
        try {
            httpurlconnection = (HttpURLConnection) new URL(url).openConnection();
            httpurlconnection.connect();
            inputstream = httpurlconnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputstream);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                inputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpurlconnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }
    private static void log(String filePath, String content) {
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(filePath));
            outputStream.write(content.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final String UTF_8 = "UTF-8";
}
