package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public abstract class Request extends Json {
    public static final int TASK = 1;
    public static final int RETENTION_REPORT = 2;
    public static final int STOP_REPORT = 3;
    //
    public int method;
    public long deviceId;
    public Device device;
    public String packageName;
    public int versionCode;
    public Request() {}
    public Request(int method) {
        this.method = method;
    }
    @Override
    protected void init(JSONObject obj) {
        method = obj.optInt(A);
        deviceId = obj.optLong(B);
        device = optObj(Device.class, obj.optJSONObject(C));
        packageName = obj.optString(D);
        versionCode = obj.optInt(E);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, A, method);
        put(obj, B, deviceId);
        put(obj, C, toJson(device));
        put(obj, D, packageName);
        put(obj, E, versionCode);
        return obj;
    }
}
