

import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public Socket socket;
    public ObjectOutputStream objectOutputStream;
    public ObjectInputStream objectInputStream;
    public String name;


    public Client(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.name= name;
        OutputStream outputStream = socket.getOutputStream();
       this.objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        this.objectInputStream = new ObjectInputStream(inputStream);
    }

    public static void main(String[] args) throws Exception {
        int port = 9820;
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Client has started");

        Socket socket = new Socket("127.0.0.1", port);
        Client client = new Client(socket, name);
        client.waitt();
        client.print();


    }

    public void waitt() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mes = "";

                while (socket.isConnected()) {
                    try {
                        mes = (String) objectInputStream.readObject();
                        System.out.println(mes);

                    } catch (IOException e) {
                        closeEverything(socket, objectOutputStream,objectInputStream );
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

    }

    public void print() {
        String str = "";

        try {
            Scanner sc = new Scanner(System.in);
            while (socket.isConnected()) {
                str = sc.nextLine();


                //str += "" + name;
                objectOutputStream.writeObject(new Message( str, name ));

            }
        } catch (IOException e) {
            closeEverything(socket, objectOutputStream,objectInputStream );
        }
    }

    public void closeEverything(Socket socket, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        try {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
