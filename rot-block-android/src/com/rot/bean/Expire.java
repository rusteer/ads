package com.rot.bean;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Expire extends Json {
    /**
     * In server side, this field means how long this block will expire
     * In client side, this field means the exact time of expire
     */
    public long expire;
    public Expire() {
        super();
    }
    @Override
    public void init(JSONObject obj) {
        expire = obj.optLong(A);
    }
    public void readStream(DataInputStream stream) throws IOException {
        expire = stream.readLong();
    }
    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject obj = super.toJson();
        put(obj, A, expire);
        return obj;
    }
    public void writeStream(DataOutputStream stream) throws IOException {
        stream.writeLong(expire);
    }
}
