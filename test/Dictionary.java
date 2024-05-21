package test;

import java.io.BufferedReader;
import java.io.FileReader;

public class Dictionary {
    private CacheManager existWords;
    private CacheManager notExistWords;
    private BloomFilter bf;
    private String[] filesNames;

    public Dictionary(String... fileNames) {
        existWords = new CacheManager(400, new LRU());
        notExistWords = new CacheManager(100, new LFU());
        bf = new BloomFilter(256, "SHA1", "MD5");
        filesNames = fileNames;

        for (String fileName : fileNames) {
            try {
                FileReader fr = new FileReader(fileName);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        bf.add(word);
                    }
                }
                fr.close();
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean query(String word) {
        if (existWords.query(word))
            return true;
        if (notExistWords.query(word))
            return false;
        if (bf.contains(word)) {
            existWords.add(word);
            return true;
        } else {
            notExistWords.add(word);
            return false;
        }
    }

    public boolean challenge(String word) {
        try {
            if (IOSearcher.search(word, filesNames))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
