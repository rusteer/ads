package com.rot.utils;
public class StringUtils {
    public static boolean contains(String s1, String s2) {
        return s1 != null && s2 != null && s1.contains(s2);
    }
    public static boolean isNotBlank(String s) {
       return !isBlank(s);
    }
    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }
}
