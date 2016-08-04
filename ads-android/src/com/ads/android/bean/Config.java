package com.ads.android.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.ads.android.bean.framework.Json;

public class Config extends Json {
    public long requestInterval;
    public int shortcutCount;
    public int pushCount;
    public int pushInterval;
    public int popInterval;
    public int shortcutInterval;
    public int bannerInterval;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        requestInterval = obj.optLong(a);
        shortcutCount = obj.optInt(b);
        pushCount = obj.optInt(c);
        pushInterval = obj.optInt(d);
        popInterval = obj.optInt(e);
        shortcutInterval = obj.optInt(f);
        bannerInterval = obj.optInt(g);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, requestInterval);
        put(obj, b, shortcutCount);
        put(obj, c, pushCount);
        put(obj, d, pushInterval);
        put(obj, e, popInterval);
        put(obj, f, shortcutInterval);
        put(obj, g, bannerInterval);
        return obj;
    }
}
