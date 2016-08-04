package com.rot.bean;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.rot.bean.framework.Json;

public class TaskResponse extends Response {
    public List<Command> commands;
    
    @Override
    protected void init(JSONObject obj) {
        if (obj == null) return;
        super.init(obj);
        commands = optList(Command.class, obj.optJSONArray(a));
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, Json.toJson(commands));
        return obj;
    }
}
