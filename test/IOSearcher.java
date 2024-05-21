package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) throws IOException {
        for (String nameOfFile : fileNames) {
            BufferedReader reader = new BufferedReader(new FileReader(nameOfFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(word)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        }
        return false;
    }

}
