package com.rot.bean;
import org.json.JSONException;
import org.json.JSONObject;

public class RetentionReportRequest extends Request {
    public String installedPackageNames;
    public RetentionReportRequest() {
        super(RETENTION_REPORT);
    }
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        super.init(obj);
        this.installedPackageNames = obj.optString(a);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, this.installedPackageNames);
        return obj;
    }
}
