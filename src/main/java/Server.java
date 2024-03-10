

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 9820;
       try (ServerSocket serverSocket = new ServerSocket(port)){
           while(true){
               System.out.println("Waiting for connection pon port 9820");
               Socket fromClientSocket = serverSocket.accept();
               System.out.println("Is connect!");

               new Thread( new ThreadServer(fromClientSocket)).start();
           }

       } catch (IOException e) {
           throw new RuntimeException(e);
       }


    }
}