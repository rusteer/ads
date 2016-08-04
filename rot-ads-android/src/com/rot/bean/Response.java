package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public class Response extends Json {
    public Config config;
    public boolean clearData;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        config = Json.optObj(Config.class, obj.optJSONObject(A));
        clearData = obj.optBoolean(B);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, A, Json.toJson(config));
        put(obj, B, clearData);
        return obj;
    }
}
