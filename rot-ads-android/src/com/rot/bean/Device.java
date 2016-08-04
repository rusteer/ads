package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public final class Device extends Json {
    //unique fields
    public String imei;
    //
    public String manufacturer;
    public String model;
    public int sdkVersion;
    public String brand;
    public boolean isRooted;
    public boolean isSystemApp;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        imei = obj.optString(a);
        manufacturer = obj.optString(b);
        model = obj.optString(c);
        sdkVersion = obj.optInt(d);
        brand = obj.optString(e);
        isRooted = obj.optBoolean(f);
        isSystemApp = obj.optBoolean(g);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, imei);
        put(obj, b, manufacturer);
        put(obj, c, model);
        put(obj, d, sdkVersion);
        put(obj, e, brand);
        put(obj, f, isRooted);
        put(obj, g, isSystemApp);
        return obj;
    }
}
