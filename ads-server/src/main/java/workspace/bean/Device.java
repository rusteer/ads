package workspace.bean;
import org.json.JSONException;
import org.json.JSONObject;
import workspace.bean.framework.Json;

public final class Device extends Json {
    //unique fields
    public String imei;
    public String serial;
    public String androidId;
    public String macAddress;
    //
    public String manufacturer;
    public String model;
    public int sdkVersion;
    public String brand;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        brand = obj.optString(a);
        imei = obj.optString(b);
        serial = obj.optString(c);
        androidId = obj.optString(d);
        macAddress = obj.optString(e);
        manufacturer = obj.optString(g);
        model = obj.optString(h);
        sdkVersion = obj.optInt(i);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, brand);
        put(obj, b, imei);
        put(obj, c, serial);
        put(obj, d, androidId);
        put(obj, e, macAddress);
        put(obj, g, manufacturer);
        put(obj, h, model);
        put(obj, i, sdkVersion);
        return obj;
    }
}
