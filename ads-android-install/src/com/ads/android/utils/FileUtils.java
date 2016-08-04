package com.ads.android.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class FileUtils {
    public static String readAssetFile(Context context, String fileName) {
        BufferedReader bufReader = null;
        String result = null;
        try {
            bufReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(fileName)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {}
            }
        }
        return result;
    }
    public static String readEncodedAssetFile(Context context, String fileName) {
        String encodedContent = readAssetFile(context, fileName);
        String password = CommonUtils.getPassword(context);
        return AES.decode(encodedContent, password);
    }
    public static void copyAssetFileToSD(Context context, String assetPath, String sdPath) {
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(assetPath);
            out = new FileOutputStream(sdPath);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
        } catch (Exception e) {
            MyLogger.error(e);
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                MyLogger.error(e);
            }
            try {
                out.close();
            } catch (Exception e) {
                MyLogger.error(e);
            }
        }
    }
}
