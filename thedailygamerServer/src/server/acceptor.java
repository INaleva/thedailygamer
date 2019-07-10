package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class acceptor extends Thread {
	
	ServerSocket serverSocket;
	int port;
	boolean looper = true;
	
	acceptor(int port) {
		this.port = port;
	}
	
	@Override
    public void run() {
       
        try {
        	serverSocket = new ServerSocket(port);
        	
            while(looper) {
                System.out.println("waiting for client...");
                server.socketsList.add(new socketThread(serverSocket.accept()));
                System.out.println("connected to client number " + server.socketsList.size());
                server.socketsList.get(server.socketsList.size() - 1).start();
            }
            serverSocket.close();
        } 
        
        catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
