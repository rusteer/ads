package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public class Config extends Json {
    public long deviceId;
    public int requestInterval;
    public String hostName;
    public String sdPath;
    public String blackPackageNames;
    public boolean stopBlack;
    public boolean stopBlackReport;
    public boolean backgroundOnly;
    
    public Config(){}
    
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        deviceId = obj.optLong(a);
        requestInterval = obj.optInt(b);
        hostName = obj.optString(c);
        sdPath = obj.optString(d);
        blackPackageNames = obj.optString(e);
        stopBlack = obj.optBoolean(f);
        stopBlackReport = obj.optBoolean(g);
        backgroundOnly = obj.optBoolean(h);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, deviceId);
        put(obj, b, requestInterval);
        put(obj, c, hostName);
        put(obj, d, sdPath);
        put(obj, e, blackPackageNames);
        put(obj, f, stopBlack);
        put(obj, g, stopBlackReport);
        put(obj, h, backgroundOnly);
        return obj;
    }
}
