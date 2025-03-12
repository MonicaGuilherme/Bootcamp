package io.codeforall.kernelfc;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String clientName = "Client1";
        int clientPortNumber = 8085;


        try {
            Socket clientSocket = new Socket(clientName, clientPortNumber);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String message = "I'm a client";
            while (message != null) {
                message = in.readLine();
                out.println(message);
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}







