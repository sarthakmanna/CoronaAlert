package ServerSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class SingleServe extends Thread {
    SingleServe(Socket client) {
        clientSocket = client;
    }
    
    Socket clientSocket;
    
    public void run() {
        System.err.println(clientSocket.getRemoteSocketAddress()+ " Connected...");

        try {
            DataInputStream is = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(clientSocket.getOutputStream());
        
            while (clientSocket.isConnected()) {
                String rec = is.readUTF();
                //System.err.println("Received: \"" + rec + "\"");
                os.writeUTF(rec);
            }

            is.close();
            os.close();
            clientSocket.close();
        } catch(Exception e) {} finally {
            System.err.println(clientSocket.getLocalAddress() + " Disconnected...");
        }
    }
}