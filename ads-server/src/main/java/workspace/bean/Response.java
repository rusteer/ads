package workspace.bean;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import workspace.bean.framework.Json;

public class Response extends Json {
    public long deviceId;
    public long appInstanceId;
    public boolean clearData;
    public Config config;
    public List<Ads> adsList;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        deviceId = obj.optLong(a);
        appInstanceId = obj.optLong(b);
        clearData = obj.optBoolean(c);
        config = optObj(Config.class, obj.optJSONObject(d));
        adsList = Json.optList(Ads.class, obj.optJSONArray(e));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, deviceId);
        put(obj, b, appInstanceId);
        put(obj, c, clearData);
        put(obj, d, toJson(config));
        put(obj, e, toJson(adsList));
        return obj;
    }
}
