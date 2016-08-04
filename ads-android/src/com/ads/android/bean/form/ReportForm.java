package com.ads.android.bean.form;
import org.json.JSONException;
import org.json.JSONObject;

public class ReportForm extends AbstractForm {
    public int reportType;//show,install,open,unininstall
    public int module;//pop,banner....
    public long adsId;
    public ReportForm() {
        super(Methods.REPORT);
    }
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        super.init(obj);
        reportType = obj.optInt(a);
        module = obj.optInt(b);
        adsId = obj.optInt(c);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, reportType);
        put(obj, b, module);
        put(obj, c, adsId);
        return obj;
    }
}
