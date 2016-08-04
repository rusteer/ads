package com.rot.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class Store {
    private static final String PRE_NAME = Store.class.getName();
    private String sdParentPath;
    private Context context;
    private String password;
    private boolean enableSD;
    public Store(Context context, boolean enableSD, String password) {
        this.context = context;
        this.enableSD = enableSD;
        this.password = password;
        this.sdParentPath = android.os.Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName().hashCode() + "/";
    }
    public void delete(String path) {
        if (path != null) {
            deleteFromPref(path);
            if (enableSD) deleteFromSdCard(path);
        }
    }
    public String readAsString(String path) {
        String data = readFromPref(path);
        if (TextUtils.isEmpty(data)) {
            if (enableSD) data = readFromSdCard(path);
        }
        return data;
    }
    public int readAsInt(String path, int defaultValue) {
        String data = this.readAsString(path);
        int result = defaultValue;
        if (StringUtils.isNotBlank(data)) {
            try {
                result = Integer.valueOf(data);
            } catch (Exception e) {
                MyLogger.error(e);
            }
        }
        return result;
    }
    public long readAsLong(String path, long defaultValue) {
        String data = this.readAsString(path);
        long result = defaultValue;
        if (StringUtils.isNotBlank(data)) {
            try {
                result = Long.valueOf(data);
            } catch (Exception e) {
                MyLogger.error(e);
            }
        }
        return result;
    }
    public void save(String path, String data) {
        if (data != null && path != null) {
            writeToPref(path, data);
            if (enableSD) writeToSdCard(path, data);
        }
    }
    public void save(String path, long data) {
        save(path, String.valueOf(data));
    }
    public void save(String path, int data) {
        save(path, String.valueOf(data));
    }
    public static void save(Context context, String path, String value, String password) {
        new Store(context, true, password).save(path, value);
    }
    private String encode(String data) {
        return StringUtils.isNotBlank(password) ? AES.encode(data, password) : data;
    }
    private String decode(String data) {
        return StringUtils.isNotBlank(password) ? AES.decode(data, password) : data;
    }
    private SharedPreferences getPref() {
        return context.getSharedPreferences(PRE_NAME, 0);
    }
    private String readFromPref(String path) {
        String result = null;
        try {
            String encodedValue = getPref().getString(path, null);
            if (encodedValue != null) {
                result = decode(encodedValue);
            }
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return result;
    }
    private String readFromSdCard(String path) {
        File file = new File(sdParentPath + path);
        String result = null;
        BufferedReader reader = null;
        try {
            if (file.exists() && file.isFile()) {
                StringBuilder sb = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                result = decode(sb.toString());
            }
        } catch (Throwable e) {
            MyLogger.error(e);
        } finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException e) {
                MyLogger.error(e);
            }
        }
        return result;
    }
    private boolean writeToPref(String path, String data) {
        boolean success = false;
        try {
            if (data != null) {
                Editor editor = getPref().edit();
                editor.putString(path, encode(data));
                editor.commit();
            }
            success = true;
        } catch (Throwable e) {
            MyLogger.error(e);
        }
        return success;
    }
    private boolean writeToSdCard(String path, String data) {
        boolean success = false;
        String encodedData = encode(data);
        FileOutputStream stream = null;
        try {
            File file = new File(sdParentPath + path);
            File dir = file.getParentFile();
            if (!dir.exists()) dir.mkdirs();
            stream = new FileOutputStream(file);
            stream.write(encodedData.getBytes("UTF-8"));
            stream.flush();
            success = true;
        } catch (Throwable e) {
            MyLogger.error(e);
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                MyLogger.error(e);
            }
        }
        return success;
    }
    private void deleteFromPref(String path) {
        try {
            Editor editor = getPref().edit();
            editor.remove(path);
            editor.commit();
        } catch (Exception e) {
            MyLogger.error(e);
        }
    }
    private void deleteFromSdCard(String path) {
        try {
            File file = new File(sdParentPath + path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            MyLogger.error(e);
        }
    }
}
