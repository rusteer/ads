package com.rot.utils;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;

public class FileUtils {
    public static byte[] read(Context context, String name) {
        byte result[] = null;
        boolean exists = false;
        for (String f : context.fileList()) {
            if (name.equals(f)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            FileInputStream stream = null;
            try {
                stream = context.openFileInput(name);
                result = new byte[stream.available()];
                stream.read(result);
            } catch (Exception e) {
                MyLogger.error(e);
            } finally {
                if (stream != null) try {
                    stream.close();
                } catch (Exception e) {}
            }
        }
        return result;
    }
    public static boolean save(Context context, String name, byte abyte0[]) {
        boolean flag = false;
        if (abyte0 != null) {
            FileOutputStream fileoutputstream = null;
            try {
                fileoutputstream = context.openFileOutput(name, 0);
                fileoutputstream.write(abyte0);
                fileoutputstream.flush();
                flag = true;
            } catch (Exception e) {
                MyLogger.error(e);
            } finally {
                try {
                    fileoutputstream.close();
                } catch (IOException e) {
                    MyLogger.error(e);
                }
            }
        }
        return flag;
    }
}
