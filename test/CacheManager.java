package test;

import java.util.*;

public class CacheManager {
    private final int maxSize;
    private HashSet<String> cache;
    CacheReplacementPolicy crp;

    public CacheManager(int size, CacheReplacementPolicy crp) {
        maxSize = size;
        cache = new HashSet<>(size);
        this.crp = crp;
    }

    public boolean query(String word) {
        return cache.contains(word);
    }

    public void add(String word) {
        cache.add(word);
        crp.add(word);
        if (maxSize < cache.size())
            cache.remove(crp.remove());

    }

    public int getCacheSize() {
        return cache.size();
    }

}