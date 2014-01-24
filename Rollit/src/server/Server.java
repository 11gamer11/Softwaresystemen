package server;

import info.Logging;
import info.ServerInformation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server implements Runnable {
	
	// -- Instance variables -----------------------------------------
	
	private ServerSocket serverSocket;
	private InputHandler inputHandler;
	private Thread thread;
	
	public ServerInformation serverInfo = new ServerInformation();
	
	private ArrayList<ClientHandler> connectedClients = new ArrayList<ClientHandler>();

	private boolean done = false;
	
	// -- Constructors -----------------------------------------------

    public Server(int port) {
        this.serverInfo.localIp = getLocalIp();
        this.serverInfo.port = port;
        
        Logging.log(0, "Server making own thread");
        this.thread = new Thread(this);
        this.thread.start();
    }
	
    // -- Commands ---------------------------------------------------

    private InetAddress getLocalIp() {
        if (this.serverInfo.localIp != null) {
            return this.serverInfo.localIp;
        } else {
            InetAddress addres = null;
            try {
            	addres = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
            	Logging.log(3, e.getMessage());
            }
            return addres;        
        }
	}

    public int getPort() {
        return this.serverInfo.port;
    }
    
    public void messageReceived(ClientHandler clientHandler, String msg) {
    	Logging.log(4, msg);
        //TODO is valid command?
        try {
        	clientHandler.protocol.executeCommand(msg);
        } catch (IllegalArgumentException e) {
        	Logging.log(3, "Illegal arguments in command " + msg + " [" + e.getMessage() + "]");
        } catch (IOException e) {
        	Logging.log(3, "Error while processing command " + msg + " [" + e.getMessage() + "]");
        }
    }
    
    public void handleInput(String msg) {
        Logging.log(0, "Input received: " + msg);
        for (ClientHandler client : this.connectedClients) {
			Logging.log(0, "Sending message to client " + client.clientInfo.name);
			client.send(msg);
        }
	}
	
	public void removeClient(ClientHandler client) {
		this.connectedClients.remove(client);
	}
	
	public void run() {
        Logging.log(1, "Starting the server on port: " + this.serverInfo.port);
        Logging.log(0, "Local IP: " + this.serverInfo.localIp.getHostAddress());
        Logging.log(0, "Port: " + this.serverInfo.port);
        
        try {
            serverSocket = new ServerSocket(this.serverInfo.port);
            Logging.log(0, "ServerSocket created on port " + this.serverInfo.port);

            inputHandler = new InputHandler(this);
            
            while (!done) {
                Socket client = serverSocket.accept();
                Logging.log(1, "A new client connected");
                ClientHandler clientHandler = new ClientHandler(this, client);
                Logging.log(1, "The client connected from " 
                						+ clientHandler.clientInfo.ipAdres.getHostAddress()
                						+ ":" + clientHandler.clientInfo.port);
                connectedClients.add(clientHandler);
            }
                
        } catch (IOException e) {
        	Logging.log(3, "Failed to start server, Error: " + e.getMessage());
        }
	}
	
	public static void main(String[] args) {
        if (args.length != 1) {
            Logging.log(3, "USAGE: java rolit.server.Server <port>");
        } else {
            int sPort = Integer.parseInt(args[0]);
            Server server = new Server(sPort);
        }
	}
}