package com.ads.android.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import android.content.Context;
import android.content.res.AssetManager;

public class FileUtils {
    public static String read(String filePath) {
        File file = new File(filePath);
        String result = read(file);
        return result;
    }
    private static String read(File file) {
        String result = null;
        if (file.exists()) {
            BufferedReader bufReader = null;
            try {
                bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
        }
        return result;
    }
    public static String aesRead(Context context, File file) {
        if (file.exists()) {
            String encodedContent = read(file);
            String password = CommonUtils.getPassword(context);
            return AES.decode(encodedContent, password);
        }
        return null;
    }
    public static boolean aesWrite(Context context, File file, String content) {
        String password = CommonUtils.getPassword(context);
        String encodedContent = AES.encode(content, password);
        return write(file, encodedContent);
    }
    public static boolean write(String fileName, String content) {
        File file = new File(fileName);
        boolean success = write(file, content);
        return success;
    }
    private static boolean write(File file, String content) {
        boolean success = false;
        File folder = file.getParentFile();
        if (!folder.exists()) folder.mkdirs();
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            writer.write(content);
            writer.flush();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }
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
