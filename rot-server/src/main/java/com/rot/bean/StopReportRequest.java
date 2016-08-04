package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public class StopReportRequest extends Request {
    public String packageName;
    public CommandResult result;
    public StopReportRequest() {
        super(STOP_REPORT);
    }
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        super.init(obj);
        packageName=obj.optString(a);
        result=Json.optObj(CommandResult.class, obj.optJSONObject(b));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj,a,packageName);
        put(obj,b,Json.toJson(result));
        return obj;
    }
}
