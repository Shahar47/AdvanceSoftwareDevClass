package test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    private BitSet bitSet;
    private MessageDigest[] hashFunctions;
    private int size;

    public BloomFilter(int size, String... algs) {
        this.size = size;
        bitSet = new BitSet(this.size);
        hashFunctions = new MessageDigest[algs.length];
        for (int i = 0; i < algs.length; i++) {
            try {
                hashFunctions[i] = MessageDigest.getInstance(algs[i]);
            } catch (NoSuchAlgorithmException e) {
                e.getStackTrace();
            }
        }
    }

    public void add(String word) {
        for (MessageDigest digest : hashFunctions) {
            byte[] bytes = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            int hash = new BigInteger(1, bytes).intValue();
            bitSet.set(Math.abs(hash % size));
        }
    }

    public boolean contains(String word) {
        if (word == null)
            return false;
        for (MessageDigest digest : hashFunctions) {
            byte[] bytes = digest.digest(word.getBytes(StandardCharsets.UTF_8));
            int hash = new BigInteger(1, bytes).intValue();
            if (!bitSet.get(Math.abs(hash % size)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            sb.append(bitSet.get(i) ? '1' : '0');
        }
        return sb.toString();
    }

}
