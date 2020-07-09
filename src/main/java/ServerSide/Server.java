package ServerSide;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("Server has been started");
        ServerSocket serverSocket = new ServerSocket(57775);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            new SingleServe(clientSocket).start();
        }
    }
}