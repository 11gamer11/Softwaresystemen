package info;

import java.net.Socket;
import java.net.SocketAddress;

import server.Server;

public class ClientInformation {
	//connection info
	public SocketAddress ipAdres = null;
	public int port = -1;
	
	//personal info
    public String name;
	public Server server;
	public Socket socket;
}
