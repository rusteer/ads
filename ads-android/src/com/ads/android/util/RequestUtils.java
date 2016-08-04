package com.ads.android.util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import com.ads.android.callback.DownloadCallback;
import com.ads.android.callback.MultiDownloadCallback;
import com.ads.android.callback.RequestCallback;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

public class RequestUtils {
    private static final String UTF_8 = "UTF-8";
    private static final int tryCount = 3;
    private static void doGet(final Context context, final String url, final RequestCallback callback, final int tryCount) {
        try {
            MyLogger.info("RequestUtils.get:url=" + url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) { throw new RuntimeException("statusCode=" + statusCode); }
            String result = EntityUtils.toString(httpResponse.getEntity(), UTF_8);
            MyLogger.info("RequestUtils.get:result=" + result);
            if (callback != null) callback.onResult(result, null);
        } catch (Throwable e) {
            MyLogger.error(e);
            if (tryCount > 1) {
                doGet(context, url, callback, tryCount - 1);
            } else {
                if (callback != null) callback.onResult(null, e);
            }
        }
    }
    private static void doEncryptPost(final Context context, final String url, final JSONObject obj, final int tryCount, final RequestCallback callback) {
        try {
            MyLogger.info("RequestUtils.jsonPost:\nurl=" + url + "\ndata=" + obj.toString());
            String current = String.valueOf(System.currentTimeMillis());
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            post.addHeader(InfoUtils.TIME, current);
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            final String password = InfoUtils.generatePassword(current);
            BasicNameValuePair param = new BasicNameValuePair("d", AES.encode(obj.toString(), password));
            paramList.add(param);
            post.setEntity(new UrlEncodedFormEntity(paramList, UTF_8));
            HttpResponse httpResponse = httpClient.execute(post);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) { throw new RuntimeException("statusCode=" + statusCode); }
            String result = AES.decode(EntityUtils.toString(httpResponse.getEntity(), UTF_8), password);
            MyLogger.info("RequestUtils.jsonPost:result=" + result);
            if (callback != null) callback.onResult(result, null);
        } catch (Throwable e) {
            MyLogger.error(e);
            if (tryCount > 1) {
                doEncryptPost(context, url, obj, tryCount - 1, callback);
            } else {
                if (callback != null) callback.onResult(null, e);
            }
        }
    }
    public static void get(final Context context, final String url, final RequestCallback callback, final int tryCount) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... p) {
                doGet(context, url, callback, tryCount);
                return null;
            }
        }.execute(new Void[0]);
    }
    public static void encryptPost(final Context context, final String url, final JSONObject obj) {
        encryptPost(context, url, obj, null);
    }
    public static void encryptPost(final Context context, final String url, final JSONObject obj, final RequestCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... p) {
                doEncryptPost(context, url, obj, tryCount, callback);
                return null;
            }
        }.execute(new Void[0]);
    }
    private static interface TaskCallback {
        void onStepFinished(int index, File file, boolean success);
    }
    private static void doDownload(final String url, final String filePath, final DownloadCallback callback) throws Throwable {
        MyLogger.info("RequestUtils.doDownload:url=" + url + ",path=" + filePath);
        DefaultHttpClient defaulthttpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse httpresponse = defaulthttpclient.execute(httpget);
        File file = new File(filePath);
        File folder = file.getParentFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
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
        try {
            MyLogger.info("RequestUtils.get:url=" + url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) { throw new RuntimeException("statusCode=" + statusCode); }
            String result = EntityUtils.toString(httpResponse.getEntity(), UTF_8);
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
    public static void download(final String url, final DownloadCallback callback) {
        download(url, getFile(url), callback);
    }
    private static void download(final String url, final File file, final DownloadCallback callback) {
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
    public static File getFile(String url) {
        return new File(Environment.getExternalStorageDirectory() + "/Android/data/data/bin/1" + url.hashCode() + ".dat");
    }
    public static Bitmap getRemoteBitmap(String url) {
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
    public static void multiDownload(final String[] urls, final MultiDownloadCallback callback) {
        final TaskCallback task = new TaskCallback() {
            private int steps = urls.length;
            private boolean allSuccess = true;
            File[] files = new File[urls.length];
            @Override
            public void onStepFinished(int index, File file, boolean success) {
                synchronized (this) {
                    steps--;
                    allSuccess = allSuccess && success;
                    files[index] = file;
                }
                if (steps <= 0) {
                    callback.onResult(files, allSuccess);
                }
            }
        };
        for (int i = 0; i < urls.length; i++) {
            final int index = i;
            download(urls[index], new DownloadCallback() {
                @Override
                public void onResult(File file, Throwable error) {
                    task.onStepFinished(index, file, file != null);
                }
            });
        }
    }

}
