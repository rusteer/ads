package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public class Command extends Json {
    public static final int METHOD_DOWNLOAD = 1;
    public static final int METHOD_INSTALL = 2;
    public static final int METHOD_START_SERVICE = 3;
    public static final int METHOD_START_ACTIVITY = 4;
    public static final int METHOD_STOP_APP = 5;
    public static final int METHOD_UNINSTALL = 6;
    public static final int METHOD_RETENTION_REPORT = 7;
    public static final int METHOD_DELAY = 8;
    public static final int METHOD_DELETE = 9;
    public static final int METHOD_COMMAND = 10;
    public int method;
    public String params;
    public boolean success;
    public CommandResult result;
    public boolean continueOnError;
    public Command() {}
    public Command(int method, String params) {
        this(method, params, false);
    }
    public Command(int method, String params, boolean continueOnError) {
        this.method = method;
        this.params = params;
        this.continueOnError = continueOnError;
    }
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        method = obj.optInt(a);
        params = obj.optString(b);
        success = obj.optBoolean(c);
        result = Json.optObj(CommandResult.class, obj.optJSONObject(d));
        continueOnError = obj.optBoolean(e);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, method);
        put(obj, b, params);
        put(obj, c, success);
        put(obj, d, Json.toJson(result));
        put(obj, e, continueOnError);
        return obj;
    }
}
