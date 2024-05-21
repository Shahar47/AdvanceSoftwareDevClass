package test;

import java.util.*;;

public class LRU implements CacheReplacementPolicy {
    private Deque<String> lru;

    public LRU() {
        lru = new ArrayDeque<String>();
    }

    @Override
    public void add(String word) {
        if (!lru.contains(word)) {
            lru.addFirst(word);
        } else {
            lru.remove(word);
            lru.addFirst(word);
        }
    }

    @Override
    public String remove() {
        return lru.removeLast();
    }

}
