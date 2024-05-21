package test;

import java.util.*;

public class DictionaryManager {
    private static DictionaryManager dictionaryManager = null;
    HashMap<String, Dictionary> map;

    public DictionaryManager() {
        map = new HashMap<>();
    }

    public static DictionaryManager get() {
        if (dictionaryManager == null) {
            dictionaryManager = new DictionaryManager();
        }
        return dictionaryManager;
    }

    public boolean challenge(String... args) {
        String word = args[args.length - 1];
        boolean ifExist = false;
        for (int i = 0; i < args.length - 1; i++) {
            if (!map.containsKey(args[i])) {
                map.put(args[i], new Dictionary(args[i]));
            }
            if (map.get(args[i]).challenge(word)) {
                ifExist = true;
            }
        }
        return ifExist;
    }

    public boolean query(String... args) {
        String word = args[args.length - 1];
        boolean ifExist = false;
        for (int i = 0; i < args.length - 1; i++) {
            if (!map.containsKey(args[i])) {
                map.put(args[i], new Dictionary(args[i]));
            }
            if (map.get(args[i]).query(word)) {
                ifExist = true;
            }
        }
        return ifExist;
    }

    public int getSize() {
        return map.size();
    }
}
