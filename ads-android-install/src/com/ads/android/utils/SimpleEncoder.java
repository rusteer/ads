package com.ads.android.utils;
import java.io.UnsupportedEncodingException;

public class SimpleEncoder {
    public static int code = 228213;
    public static void setCode(int codeValue) {
        code = codeValue;
    }
    public static byte[] getBytes(String s) {
        if (s != null && s.length() > 0) {
            try {
                byte[] result = s.getBytes("UTF-8");
                for (int i = 0; i < result.length; i++) {
                    result[i] = (byte) (result[i] ^ (i * 3 + code));
                }
                return result;
            } catch (Throwable e) {
                MyLogger.error(e);
            }
        }
        return null;
    }
    public static String getString(byte[] bytes) {
        byte[] newBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            newBytes[i] = (byte) (bytes[i] ^ (i * 3 + code));
        }
        try {
            return new String(newBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            MyLogger.error(e);
        }
        return "";
    }
}