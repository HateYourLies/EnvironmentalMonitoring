package com.briup.server;

import com.briup.entity.Environment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;


public class ServerImpl {
    public List<Environment> receive() {
        Socket socket = null;
        ObjectInputStream ois = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9999);
            socket = serverSocket.accept();
            ois = new ObjectInputStream(socket.getInputStream());
            List<Environment> list = (List<Environment>) ois.readObject();
            System.out.println("list = " + list.size());
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
