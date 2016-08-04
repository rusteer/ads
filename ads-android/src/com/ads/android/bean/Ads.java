package com.ads.android.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.ads.android.bean.framework.Json;

public class Ads extends Json {
    //
    public long id;
    public String name;
    public String packageName;
    public boolean installable;
    public String url;
    public String screenUrl;
    public String iconUrl;
    public String description;
    //
    public AdsBanner bannerConfig;
    public AdsPop popConfig;
    public AdsPush pushConfig;
    public AdsShortcut shortcutConfig;
    //================used in client only===================================
    public boolean donwloadFinished;
    public long iconSize;
    public long screenSize;
    public long apkSize;
    //================used in client only===================================
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        id = obj.optLong(a);
        name = obj.optString(b);
        packageName = obj.optString(c);
        installable = obj.optBoolean(d);
        url = obj.optString(e);
        iconUrl = obj.optString(f);
        screenUrl = obj.optString(g);
        description = obj.optString(h);
        bannerConfig = Json.optObj(AdsBanner.class, obj.optJSONObject(i));
        popConfig = Json.optObj(AdsPop.class, obj.optJSONObject(j));
        pushConfig = Json.optObj(AdsPush.class, obj.optJSONObject(k));
        shortcutConfig = Json.optObj(AdsShortcut.class, obj.optJSONObject(l));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, id);
        put(obj, b, name);
        put(obj, c, packageName);
        put(obj, d, installable);
        put(obj, e, url);
        put(obj, f, iconUrl);
        put(obj, g, screenUrl);
        put(obj, h, description);
        put(obj, i, toJson(bannerConfig));
        put(obj, j, toJson(popConfig));
        put(obj, k, toJson(pushConfig));
        put(obj, l, toJson(shortcutConfig));
        return obj;
    }
}
