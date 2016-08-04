package com.ads.android.util;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import com.ads.android.bean.Ads;
import com.ads.android.bean.framework.Json;
import android.content.Context;

public class AdsCache {
    File file;
    private List<Ads> buffer = null;
    private Context context;
    public AdsCache(Context context) {
        File folder = context.getCacheDir();
        this.context = context;
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file = new File(folder.getAbsoluteFile() + "/cbefg.dat");
    }
    public List<Ads> load() {
        synchronized (this) {
            if (buffer == null) {
                List<Ads> temp = null;
                if (file.exists()) {
                    try {
                        String content = FileUtils.aesRead(context, file);
                        if (content != null) {
                            temp = Json.optList(Ads.class, new JSONArray(content));
                        }
                    } catch (Exception e) {
                        MyLogger.error(e);
                    }
                }
                buffer = temp;
            }
            return buffer == null ? new ArrayList<Ads>() : buffer;
        }
    }
    public void save(Ads ads) {
        List<Ads> list = load();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == ads.id) {
                list.set(i, ads);
                break;
            }
        }
        save(list);
    }
    public boolean save(List<Ads> list) {
        boolean result = false;
        if (list != null) {
            synchronized (this) {
                try {
                    FileUtils.aesWrite(context, file, Json.toJson(list).toString());
                    result = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
