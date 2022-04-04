package com.alioo.monitor.demo.proxy;


import java.net.ServerSocket;
import java.net.Socket;

public class SocketProxy {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                new SocketThread(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}