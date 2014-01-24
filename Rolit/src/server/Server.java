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
	private static Server server;
	private InputHandler inputHandler;
	private Thread thread;
	private ClientHandler client;
	
	public ServerInformation serverInfo = new ServerInformation();
	
	private ArrayList<ClientHandler> connectedClients = new ArrayList<ClientHandler>();

	private boolean done = false;
	
	// -- Constructors -----------------------------------------------

    public Server(int port) {
        this.serverInfo.localIp = getLocalIp();
        this.serverInfo.port = port;
        
        try {
        	serverSocket = new ServerSocket(this.serverInfo.port);
            Logging.log(0, "ServerSocket created on port " + this.serverInfo.port);
        } catch (IOException e) {
        	Logging.log(3, "Could not create server");
        }
        if (thread == null) {
	        Logging.log(0, "Server making own thread");
	        this.thread = new Thread(this);
	        this.thread.start();
        }
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
        	Logging.log(3, "Illegal arguments in command " + msg + ", [" + e.getMessage() + "]");
        } catch (IOException e) {
        	Logging.log(3, "Error while processing command " + msg + ", [" + e.getMessage() + "]");
        }
    }
    
    public void handleInput(String msg) {
        Logging.log(0, "Input received: " + msg);
        for (ClientHandler clientHandler : this.connectedClients) {
			Logging.log(0, "Sending message to clientHandler " + clientHandler.clientInfo.name);
			clientHandler.send(msg);
        }
	}
	
    public void addThread(Socket socket) {
        Logging.log(1, "Client accepted: " + socket.getInetAddress() + ":" + socket.getPort());
        client = new ClientHandler(this, socket);
        try {
            client.open();
            client.start();
        } catch (IOException e) {
            Logging.log(3, "Error connecting with client: " + e.getMessage());
        }
    }
    
	public void removeClient(ClientHandler clientHandler) {
		this.connectedClients.remove(clientHandler);
	}
	
    public void stop() {
        if (thread != null) {
            try {
                thread.join();
                thread = null;
            } catch (InterruptedException e) {
                Logging.log(0, "Thread interrupted.");
            }
        }
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
                Socket clientSocket = serverSocket.accept();
                Logging.log(1, "A new client connected");
                ClientHandler clientHandler = new ClientHandler(this, clientSocket);
                Logging.log(1, "The client connected from " 
                					+ clientHandler.clientInfo.ipAdres.toString().substring(1));
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
            server = new Server(sPort);
        }
	}
}