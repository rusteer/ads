package workspace.bean;
import org.json.JSONObject;

public class App extends Json {
    public String pathName;
    //
    public String appUrl;
    public String screenUrl;
    public String iconUrl;
    public String id;
    public String description;
    public String name;
    public String packageName;
    public String isApk;
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) { return; }
        id = obj.optString("id");
        name = obj.optString("name");
        iconUrl = obj.optString("icon");
        description = obj.optString("intro");
        packageName = obj.optString("package");
        screenUrl = obj.optString("bigpic");
        appUrl = obj.optString("appurl");
        isApk = obj.optString("isapk");
    }
}
