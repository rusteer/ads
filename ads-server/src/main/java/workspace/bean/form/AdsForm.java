package workspace.bean.form;
import org.json.JSONException;
import org.json.JSONObject;

public class AdsForm extends AbstractForm {
    public AdsForm() {
        super(Methods.ADS);
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
