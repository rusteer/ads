package com.ads.android.module;
import org.json.JSONException;
import org.json.JSONObject;
import com.ads.android.bean.Config;
import com.ads.android.bean.framework.Json;
import com.ads.android.util.MyLogger;
import com.ads.android.util.Store;
import com.ads.android.util.StringUtils;
import android.content.Context;

public class ConfigHelper {
    private static Config CONFIG = null;
    private static String key = ConfigHelper.class.getName();
    public static void saveConfig(Context context, Config config) {
        synchronized (key) {
            if (config != null) {
                CONFIG = config;
                try {
                    new Store(context).save(key, config.toJson().toString());
                } catch (JSONException e) {
                    MyLogger.error(e);
                }
            }
        }
    }
    public static Config getConfig(Context context) {
        synchronized (key) {
            if (CONFIG == null) {
                String content = new Store(context).readAsString(key);
                if (StringUtils.isNotBlank(content)) {
                    try {
                        CONFIG = Json.optObj(Config.class, new JSONObject(content));
                    } catch (JSONException e) {
                        MyLogger.error(e);
                    }
                }
            }
            if (CONFIG == null) {
                CONFIG = getDefaultConfig();
            }
            return CONFIG;
        }
    }
    private static Config getDefaultConfig() {
        Config config = new Config();
        config.pushCount = config.shortcutCount = 3;
        config.requestInterval = config.popInterval = config.pushInterval = config.shortcutInterval = config.bannerInterval = 3600;
        return config;
    }
}
