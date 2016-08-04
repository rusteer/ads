package workspace.bean;
import org.json.JSONException;
import org.json.JSONObject;

public class AdsPush extends AbstractAdsProperty {
    public boolean cancelable;
    public boolean enableSound;
    public boolean enableVibrate;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        super.init(obj);
        cancelable = obj.optBoolean(a);
        enableSound = obj.optBoolean(b);
        enableVibrate = obj.optBoolean(c);
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();//
        put(obj, a, cancelable);
        put(obj, b, enableSound);
        put(obj, c, enableVibrate);
        return obj;
    }
}
