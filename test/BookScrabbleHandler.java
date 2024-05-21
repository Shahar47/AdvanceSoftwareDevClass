package test;

import java.util.*;
import java.io.*;

public class BookScrabbleHandler implements ClientHandler {
    Scanner reader;
    PrintWriter writer;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        reader = new Scanner(new BufferedReader(new InputStreamReader(inFromclient)));
        writer = new PrintWriter(outToClient);
        String input = reader.next();
        String[] strSplit = input.split(",");
        String[] bookWords = new String[strSplit.length - 1];
        System.arraycopy(strSplit, 1, bookWords, 0, strSplit.length - 1);
        boolean result = false;
        if (strSplit[0].equals("C"))
            result = DictionaryManager.get().challenge(bookWords);
        else
            result = DictionaryManager.get().query(bookWords);
        writer.println(result);
        writer.flush();
    }

    @Override
    public void close() {
        reader.close();
        writer.close();
    }
}
