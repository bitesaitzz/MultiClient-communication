

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServer implements Runnable{

    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    public static ArrayList<ThreadServer> allClients = new ArrayList <> ();
    public ThreadServer(Socket thisSocket ) throws IOException {
        socket = thisSocket;
        allClients.add(this);
        OutputStream outputStream = socket.getOutputStream();
        this.objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        this.objectInputStream = new ObjectInputStream(inputStream);
    }

    public void print(Message a){
        for (ThreadServer client : allClients){
            try {
                if(this != client){
                    String request = a.name + ": "+a.content;
                    System.out.println(request);
                    client.objectOutputStream.writeObject(request);
                }

            } catch (IOException e) {
               closeEverything(socket, objectOutputStream, objectInputStream);
            }
        }

    }
    public void run() {

            System.out.println("Thread started!");
            Message str;
            while (socket.isConnected()){
                try{str = (Message)objectInputStream.readObject();
                    print(str);
                    //System.out.println(str.content + " " + str.name);
                    if(str.content == "exit"){
                        break;
                    }
                } catch (IOException e) {

                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }}
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
            /*
            while (str != "exit"){
                try {
                    str = (String) this.objectInputStream.readObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                allClients.size();
                for (int i = 0; i < allClients.size(); i++){
                    try {
                        allClients.get(i).objectOutputStream.writeObject(str);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                //String name = str.substring(0, firstSpaceIndex);

            }
            */




}
