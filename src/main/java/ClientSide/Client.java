package ClientSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    Socket server;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    
    Client(String ip) throws Exception {
        if (ip.isEmpty()) throw new Exception ("IP cannot be empty");
        server = new Socket(ip, 57775);
        inputStream = new DataInputStream(server.getInputStream());
        outputStream = new DataOutputStream(server.getOutputStream());
    }
    
    boolean isConnected() {
        return server.isConnected();
    }
    
    double getTimeDiffInNanos(int iterations) throws Exception {
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; ++i) {
            outputStream.writeUTF("token");
            inputStream.readUTF();
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / (2.0 * iterations);
    }
}