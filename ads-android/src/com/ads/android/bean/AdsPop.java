package com.ads.android.bean;
import org.json.JSONException;
import org.json.JSONObject;

public class AdsPop extends AbstractAdsProperty {
    public String whitePackages;//适配游戏
    public String blackPackages;//适配游戏
    public int cancelDelaySeconds; //取消按钮延迟出现的时间
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        super.init(obj);
        whitePackages = obj.optString(a);
        blackPackages = obj.optString(b);
        cancelDelaySeconds = obj.optInt(c);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, whitePackages);
        put(obj, b, blackPackages);
        put(obj, c, cancelDelaySeconds);
        return obj;
    }
}
