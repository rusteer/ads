package com.ads.android.utils;
import java.util.Collection;

public class CollectionUtils {
    public static <T> boolean isEmpty(Collection<T> s) {
        return s == null || s.size() == 0;
    }
    public static <T> boolean isNotEmpty(Collection<T> s) {
        return !isEmpty(s);
    }
    public static <T> String toString(Collection<T> col) {
        if (CollectionUtils.isNotEmpty(col)) {
            StringBuilder sb = new StringBuilder();
            for (T element : col) {
                if (element != null) sb.append(element.toString()).append(",");
            }
            return sb.toString();
        }
        return null;
    }
}
