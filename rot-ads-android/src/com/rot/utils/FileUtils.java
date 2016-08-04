package com.rot.utils;
import java.io.File;
import android.content.Context;
import android.os.Environment;
import com.rot.bean.Config;
import com.rot.main.ConfigManager;

public class FileUtils {
    public static File getUrlFile(Context context, String url) {
        Config config = ConfigManager.getInstance(context).load();
        String folderPath = Environment.getExternalStorageDirectory() + config.sdPath;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            try {
                folder.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (folder.exists()) { return new File(folderPath + "/1" + url.hashCode()); }
        return null;
    }
}
