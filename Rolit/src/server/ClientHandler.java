package server;

import info.ClientInformation;
import info.Logging;

import java.io.IOException;
import java.net.Socket;

import network.ServerProtocol;

public class ClientHandler implements Runnable {

	private Thread thread;
	
	public ServerProtocol protocol;
	public ClientInformation clientInfo = new ClientInformation();

	public ClientHandler(Server server, Socket socket) {
		this.clientInfo.server = server;
		this.clientInfo.socket = socket;
		this.clientInfo.ipAdres = socket.getRemoteSocketAddress();
		this.clientInfo.port = socket.getPort();

        Logging.log(0, "Client making own thread");
        this.thread = new Thread(this);
        this.thread.start();
	}

	public void send(String msg) {
		// TODO sent the message to client
		
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void open() throws IOException{
		// TODO Auto-generated method stub
		
	}

	public void start() throws IOException{
		// TODO Auto-generated method stub
		
	}
}
