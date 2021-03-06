package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public class CommandResult extends Json {
    public boolean success;
    public int exitValue;
    public String output;
    public String error;
    public CommandResult() {}
    public CommandResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        success = obj.optBoolean(a);
        exitValue = obj.optInt(b);
        output = obj.optString(c);
        error = obj.optString(d);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, success);
        put(obj, b, exitValue);
        put(obj, c, output);
        put(obj, d, error);
        return obj;
    }
}
