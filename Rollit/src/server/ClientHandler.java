package server;

import info.ClientInformation;
import info.Logging;

import java.net.Socket;

import network.ServerProtocol;

public class ClientHandler implements Runnable {

	private Thread thread;
	
	public ServerProtocol protocol;
	public ClientInformation clientInfo = new ClientInformation();

	public ClientHandler(Server server, Socket socket) {
		this.clientInfo.server = server;
		this.clientInfo.socket = socket;

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
}
