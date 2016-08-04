package com.ads.android.bean.form;
import org.json.JSONException;
import org.json.JSONObject;
import com.ads.android.bean.Device;
import com.ads.android.bean.framework.Json;

public abstract class AbstractForm extends Json {
    public int method;
    //unique instant id in server
    public long appInstanceId;
    //
    public long deviceId;
    public Device device;
    //
    public String packageName;
    public int versionCode;
    //
    public long appId;
    public int channelId;
    //
    public AbstractForm() {}
    public AbstractForm(int method) {
        this.method = method;
    }
    @Override
    protected void init(JSONObject obj) {
        method = obj.optInt(A);
        appInstanceId = obj.optLong(B);
        deviceId = obj.optLong(C);
        device = optObj(Device.class, obj.optJSONObject(D));
        packageName = obj.optString(E);
        versionCode = obj.optInt(F);
        appId = obj.optLong(G);
        channelId = obj.optInt(H);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, A, method);
        put(obj, B, appInstanceId);
        put(obj, C, deviceId);
        put(obj, D, toJson(device));
        put(obj, E, packageName);
        put(obj, F, versionCode);
        put(obj, G, appId);
        put(obj, H, channelId);
        return obj;
    }
}
