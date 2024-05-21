package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private int port;
    private ClientHandler ch;
    private boolean stop;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        stop = false;
    }

    public void start() {
        new Thread(() -> startServer()).start();
    }

    private void startServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop) {
                try {
                    Socket client = server.accept();
                    ch.handleClient(client.getInputStream(), client.getOutputStream());
                    ch.close();
                    client.close();
                } catch (SocketTimeoutException e) {
                }
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        stop = true;
    }
}