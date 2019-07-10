package server;
import java.util.ArrayList;

public class server {
	
	 static public ArrayList<socketThread> socketsList = new ArrayList<>();
	 
	 public server(int port) {new Acceptor(port).start();}
}
