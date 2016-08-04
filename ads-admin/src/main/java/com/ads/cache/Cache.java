package com.ads.cache;
import java.util.HashMap;
import java.util.Map;

public class Cache<T1, T2> {
    private int expireSeconds;
    private CacheLoader<T1, T2> loader;
    private Map<T1, T2> cache;
    private Map<T1, Long> timeMap;
    public Cache(int expireSeconds, final CacheLoader<T1, T2> loader) {
        this.expireSeconds = expireSeconds;
        this.loader = loader;
        cache = new HashMap<T1, T2>();
        timeMap = new HashMap<T1, Long>();
    }
    public T2 get(T1 key) {
        long current = System.currentTimeMillis();
        long lastTime = timeMap.containsKey(key) ? timeMap.get(key) : 0;
        if ((current - lastTime) / 1000 > expireSeconds) {
            synchronized (this) {
                T2 value = loader.load(key);
                cache.put(key, value);
                timeMap.put(key, current);
            }
        }
        T2 result = cache.get(key);
        return result;
    }
}
