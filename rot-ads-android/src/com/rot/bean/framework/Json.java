package com.rot.bean.framework;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Json {
    public static <T extends Json> List<T> optList(Class<T> c, JSONArray array) {
        if (array != null) {
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < array.length(); i++) {
                T t = optObj(c, array.optJSONObject(i));
                if (t != null) {
                    list.add(t);
                }
            }
            return list;
        }
        return null;
    }
    public static <T extends Json> T optObj(Class<T> c, JSONObject obj) {
        if (obj != null) {
            try {
                T t = c.newInstance();
                t.init(obj);
                return t;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static void put(JSONObject obj, String name, boolean value) throws JSONException {
        if (value) {
            obj.put(name, value);
        }
    }
    public static void put(JSONObject obj, String name, int value) throws JSONException {
        if (value != 0) {
            obj.put(name, value);
        }
    }
    public static void put(JSONObject obj, String name, long value) throws JSONException {
        if (value != 0) {
            obj.put(name, value);
        }
    }
    public static void put(JSONObject obj, String name, Object value) {
        if (value != null) {
            try {
                obj.put(name, value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public static <T extends Json> JSONArray toJson(List<T> list) throws JSONException {
        JSONArray array = null;
        if (list != null) {
            array = new JSONArray();
            for (T t : list) {
                array.put(toJson(t));
            }
        }
        return array;
    }
    public static <T extends Json> JSONObject toJson(T t) throws JSONException {
        JSONObject result = null;
        if (t != null) {
            result = t.toJson();
        }
        return result;
    }
    public static final String A = "ﺏ";
    protected static final String B = "ـب‎";
    protected static final String C = "ـبـ‎";
    protected static final String D = "بـ‎";
    protected static final String E = "ﺕ‎";
    protected static final String F = "ـت‎";
    protected static final String G = "ـتـ‎";
    protected static final String H = "تـ‎";
    protected static final String I = "ﺙ";
    protected static final String J = "ـث‎";
    protected static final String K = "ـثـ‎";
    protected static final String L = "ثـ‎";
    protected static final String M = "ﺝ‎";
    protected static final String N = "ـج‎";
    protected static final String O = "ـجـ‎";
    protected static final String P = "جـ‎";
    protected static final String Q = "ﺡ‎";
    protected static final String R = "ح";
    protected static final String S = "حـ‎";
    protected static final String T = "حـ‎";
    //
    protected static final String a = "ﺥ";
    protected static final String b = "خ";
    protected static final String c = "خز";
    protected static final String d = "خـ";
    protected static final String e = "خخخ";
    protected static final String f = "ـد‎";
    protected static final String g = "ـد";
    protected static final String h = "د‎";
    protected static final String i = "ﺫ";
    protected static final String j = "ـذ‎";
    protected static final String k = "ذ";
    protected static final String l = "ذ";
    protected static final String m = "ﺭ‎";
    protected static final String n = "ر‎";
    protected abstract void init(JSONObject obj);
    public void put(JSONObject obj, String name, String value) {
        if (value != null && value.length() > 0) {
            try {
                obj.put(name, value);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
    public <T extends Json> JSONArray toArray(List<T> list) throws JSONException {
        JSONArray result = null;
        if (list != null) {
            result = new JSONArray();
            for (T t : list) {
                if (t != null) {
                    result.put(t.toJson());
                }
            }
        }
        return result;
    }
    public JSONObject toJson() throws JSONException {
        return new JSONObject();
    }
}
