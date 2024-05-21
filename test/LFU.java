package test;

import java.util.*;

public class LFU implements CacheReplacementPolicy {
    private HashMap<String, Integer> lfu;
    private PriorityQueue<String> q;

    public LFU() {
        lfu = new HashMap<>();
        q = new PriorityQueue<>((s1, s2) -> lfu.get(s1) - lfu.get(s2));
    }

    @Override
    public void add(String word) {
        if (lfu.containsKey(word)) {
            lfu.put(word, lfu.get(word) + 1);
            q.remove(word);
            q.add(word);
        } else {
            lfu.put(word, 1);
            q.add(word);
        }
    }

    @Override
    public String remove() {
        String s = q.poll();
        lfu.remove(s);
        return s;
    }
}
