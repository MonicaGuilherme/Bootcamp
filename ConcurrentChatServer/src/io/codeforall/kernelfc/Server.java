package io.codeforall.kernelfc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Server {


    private static final int serverPortNum = 8090;

    private static List<ClientHandler> handlers = new LinkedList<>();


    public static void main(String[] args) {
        ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(serverPortNum);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("server connected!!");
                    handlers.add(new ClientHandler(clientSocket));
                    new Thread((Runnable) handlers).start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        }

        public static synchronized void broadcast (String message, ClientHandler sender){
            for (ClientHandler client : handlers) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }





        }
    }
