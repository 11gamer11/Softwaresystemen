package info;

import java.net.InetAddress;
import java.net.Socket;

import server.Server;

public class ClientInformation {
	//connection info
	public InetAddress ipAdres = null;
	public int port = -1;
	
	//personal info
    public String name;
	public Server server;
	public Socket socket;
}
