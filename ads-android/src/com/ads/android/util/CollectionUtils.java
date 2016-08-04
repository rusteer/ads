package com.ads.android.util;
import java.util.Collection;

public class CollectionUtils {
    public static <T> boolean isEmpty(Collection<T> s) {
        return s == null || s.size() == 0;
    }
    public static <T> boolean isNotEmpty(Collection<T> s) {
        return !isEmpty(s);
    }
}
