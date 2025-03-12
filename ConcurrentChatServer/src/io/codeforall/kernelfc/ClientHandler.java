package io.codeforall.kernelfc;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientResponse;

    public ClientHandler(Socket clientSocket){
        this.socket = clientSocket;

        try {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void run() {
            try {
                out.println("Name please: ");
                clientResponse = in.readLine();
                System.out.println(in.readLine());
                do {
                    Server.broadcast(clientResponse, this);
                } while (clientResponse != null);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.close();
        }


        public void sendMessage(String message){
        out.println(message);
        }

        public void receiveMessage(){

}



    }

