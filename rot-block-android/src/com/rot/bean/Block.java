package com.rot.bean;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public final class Block extends Expire {
    public String port;
    public String content;
    public Block() {
        super();
    }
    public Block(String port, String content) {
        this();
        this.port = port;
        this.content = content;
    }
    @Override
    public void init(JSONObject obj) {
        super.init(obj);
        port = obj.optString(a);
        content = obj.optString(b);
    }
    @Override
    public void readStream(DataInputStream stream) throws IOException {
        super.readStream(stream);
        port = stream.readUTF();
        content = stream.readUTF();
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, a, port);
        put(obj, b, content);
        return obj;
    }
    @Override
    public void writeStream(DataOutputStream stream) throws IOException {
        super.writeStream(stream);
        stream.writeUTF(port);
        stream.writeUTF(content);
    }
}
