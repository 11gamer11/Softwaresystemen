package client;

import game.Game;
import info.Logging;
import info.ClientInformation;
import info.ServerInformation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

import network.ClientProtocol;
import network.ServerProtocol;

public class Client extends Observable implements Runnable {

	// -- Instance variables -----------------------------------------

	public ClientInformation clientInfo = new ClientInformation();
	public ServerInformation serverInfo = new ServerInformation();
	public ServerProtocol serverProtocol = new ServerProtocol();
	
	private Thread thread = null;
	private Socket server = null;
	private ServerHandler serverHandler = null;
	private InputClientHandler inputHandler = null;
	
	Client(String serverIP, int serverPort, String nickName, int kindOfPlayer, String strategy) {
		this.clientInfo.running = true;
		try {
			this.clientInfo.serverIpAdres = InetAddress.getByName(serverIP);
		} catch (UnknownHostException e) {
			Logging.log(Logging.INFO,
					"Could not find server, [" + e.getMessage() + "]");
			this.closeClient();
		}
		if (this.clientInfo.running) {
			this.clientInfo.serverPort = serverPort;
			this.clientInfo.nickName = nickName;
			this.clientInfo.kindOfPlayer = kindOfPlayer;
			this.clientInfo.strategy = strategy;

			Logging.log(Logging.DEBUG, "Client making own thread");
			this.thread = new Thread(this);
			this.thread.start();
		}
	}

	public void run() {
		Logging.log(Logging.INFO, "Starting client.");

		try {
			this.inputHandler = new InputClientHandler(this);

			Logging.log(Logging.INFO, "Connecting with server on "
					+ this.clientInfo.serverIpAdres.getHostAddress() + ":"
					+ this.clientInfo.serverPort);
			this.server = new Socket(this.clientInfo.serverIpAdres,
					this.clientInfo.serverPort);
			this.serverHandler = new ServerHandler(this, server);
		} catch (IOException ioe) {
			Logging.log(
					Logging.ERROR,
					"Could not open connection with server ["
							+ ioe.getMessage() + "]");
			this.closeClient();
		}

	}

	private void closeClient() {
		this.clientInfo.running = false;
        synchronized (this) {
            this.notifyAll();
        }
	}
	
	private void close() {
		this.serverHandler.close();
		this.closeClient();
	}
	
	public void hello() {
		Logging.log(Logging.DEBUG, "Sent hello to server");
		String[] information = new String[] {
			this.clientInfo.nickName,
			String.valueOf(ClientInformation.SUPPORT),
			ClientInformation.VERSION
		};
		this.serverHandler.sendCommand(ClientProtocol.HANDSHAKE, information);
	}

	public void createGame() {
		Logging.log(Logging.DEBUG, "Sent createGame to server");
		this.serverHandler.sendCommand(ClientProtocol.CREATE_GAME,
				new String[0]);
		this.clientInfo.game = new Game(this.clientInfo.nickName,
				this.clientInfo.kindOfPlayer, this.clientInfo.strategy);
	}

	public void joinGame(String name) {
		Logging.log(Logging.DEBUG, "Sent joinGame to server");
		this.serverHandler.sendCommand(ClientProtocol.JOIN_GAME,
				new String[] {name});
		Game game = this.getClientGame(name);
		this.clientInfo.game = game;
	}

	public Game getClientGame(String name) {
		for (Game game : this.serverInfo.games) {
			if (name.toLowerCase().equals(game.getOwner().toLowerCase())) {
				return game;
			}
		}
		return null;
	}

	public void messageReceived(ServerHandler fromServer, String message) {
		Logging.log(Logging.MSG, message);
		if (fromServer.protocol.isValidCommand(message)) {
			try {
				fromServer.protocol.executeCommand(message);
			} catch (IllegalArgumentException e) {
				Logging.log(Logging.ERROR, "Illegal arguments in command "
						+ message + " [" + e.getMessage() + "]");
			} catch (IOException e) {
				Logging.log(Logging.ERROR, "Error while processing command "
						+ message + " [" + e.getMessage() + "]");
			}
		} else {
			Logging.log(Logging.ERROR, "Server sent unrecognized command. ["
					+ message + "]");
		}

	}

	public ServerHandler getServerHandler() {
		return this.serverHandler;
	}	
	
	public void handleInput(String msg) {
		Logging.log(Logging.DEBUG, "Console input: " + msg);
		if (msg.startsWith("/")) {
			Logging.log(Logging.DEBUG, "Recognised command");
			String msgNoSlash = msg.substring(1);
			String cmd = msgNoSlash.split(" ")[0];
			String argString = msgNoSlash.substring(cmd.length() + 1);
			String[] args = argString.split(" ");
			// Client command
			switch (cmd) {
				case "hello":
					hello();
					break;
				case "creategame":
					createGame();
					break;
				case "joingame":
					joinGame(args[0]);
					break;
				default:
					break;
			}
		} else {
			this.serverHandler.send(msg);
		}
	}
	
	public void reconnect() {
		this.close();
		Client newClient = new Client(
				this.clientInfo.serverIpAdres.getHostAddress(),
				this.clientInfo.serverPort, this.clientInfo.nickName,
				this.clientInfo.kindOfPlayer, this.clientInfo.strategy);
		newClient.clientInfo.password = this.clientInfo.password;
		synchronized (newClient) {
			try {
				while (!newClient.clientInfo.isConnected && newClient.clientInfo.running) {
					newClient.wait();
				}
			} catch (InterruptedException e) {
				Logging.log(Logging.ERROR, "Couldn't reconnect to server [" + e.getMessage() + "]");
			}
		}
		if (newClient.clientInfo.running) {
			newClient.hello();
			LobbyGUI lobby = new LobbyGUI(newClient);
			newClient.updateGUI();
		} else {
			Logging.log(Logging.ERROR, "Couldn't reconnect to server");
		}
	}
}
