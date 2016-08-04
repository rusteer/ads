package com.ads.cache;
public interface CacheLoader<T1, T2> {
    public T2 load(T1 key);
}
