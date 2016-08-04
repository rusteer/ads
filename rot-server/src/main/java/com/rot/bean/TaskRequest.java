package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;

public class TaskRequest extends Request {
    public TaskRequest() {
        super(TASK);
    }
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        super.init(obj);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        return obj;
    }
}
