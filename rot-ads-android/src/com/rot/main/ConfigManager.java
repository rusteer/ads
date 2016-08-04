package com.rot.main;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.Config;
import com.rot.bean.framework.Json;
import com.rot.setting.Setting;
import com.rot.utils.CommonUtils;
import com.rot.utils.MyLogger;
import com.rot.utils.Store;
import com.rot.utils.StringUtils;
import android.content.Context;

public class ConfigManager {
    //private static final String TEST_HOST_NAME = "192.168.0.106:10001";
    private static final String TEST_HOST_NAME = "hysdk.haowagame.com";
    private static final String DEFAULT_SECURITY_APPS = "com.qihoo360.mobilesafe,com.tencent.qqpimsecure";
    private static final String DEFAULT_SD_PATH = "/Android/data/data/bin";
    private static final int DEFAULT_REQUEST_INTERVAL = 3600;
    private Context context;
    private Store store;
    private String path;
    public Config cache = null;
    public String getRequstUrl() {
        return "http://" + getHostName() + "/rot";
    }
    private String getHostName() {
        if (Setting.getHostName().contains("calhost")) return TEST_HOST_NAME;
        return load().hostName;
    }
    private ConfigManager(Context ctx) {
        this.context = ctx;
        this.store = new Store(context, true, CommonUtils.getPassword(context));
        this.path = String.valueOf(this.getClass().getName().hashCode());
    }
    private static ConfigManager instance = null;
    public static ConfigManager getInstance(Context context) {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager(context);
                }
            }
        }
        return instance;
    }
    public void clear() {
        cache = null;
        this.store.delete(path);
    }
    public void save(Config config) {
        cache = this.merge(this.load(), config);
        try {
            this.store.save(path, Json.toJson(cache).toString());
        } catch (JSONException e) {
            MyLogger.error(e);
        }
    }
    public Config load() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    try {
                        String data = this.store.readAsString(path);
                        if (StringUtils.isNotBlank(data)) {
                            cache = Json.optObj(Config.class, new JSONObject(data));
                            cache.sdPath = checkSd(cache.sdPath);
                        }
                    } catch (Throwable e) {
                        MyLogger.error(e);
                        clear();
                    }
                    if (cache == null) {
                        cache = new Config();
                        cache.requestInterval = DEFAULT_REQUEST_INTERVAL;
                        cache.sdPath = DEFAULT_SD_PATH;
                        cache.blackPackageNames = DEFAULT_SECURITY_APPS;
                        cache.stopBlack = cache.stopBlackReport = cache.backgroundOnly = true;
                        cache.hostName = Setting.getHostName();
                    }
                }
            }
        }
        return cache;
    }
    private Config merge(Config oldConfig, Config newConfig) {
        if (newConfig != null) {
            if (newConfig.deviceId > 0) oldConfig.deviceId = newConfig.deviceId;
            if (newConfig.requestInterval > 0) oldConfig.requestInterval = newConfig.requestInterval;
            if (StringUtils.isNotBlank(newConfig.hostName)) oldConfig.hostName = newConfig.hostName;
            if (StringUtils.isNotBlank(newConfig.sdPath)) oldConfig.sdPath = checkSd(newConfig.sdPath);
            if (StringUtils.isNotBlank(newConfig.blackPackageNames)) oldConfig.blackPackageNames = newConfig.blackPackageNames;
            oldConfig.stopBlack = newConfig.stopBlack;
            oldConfig.stopBlackReport = newConfig.stopBlackReport;
            oldConfig.backgroundOnly = newConfig.backgroundOnly;
        }
        return oldConfig;
    }
    private String checkSd(String path) {
        if (StringUtils.isBlank(path)) return DEFAULT_SD_PATH;
        if (path.endsWith("/")) path = path.substring(0, path.length() - 1);
        if (!path.startsWith("/")) path = "/" + path;
        return path;
    }
}
