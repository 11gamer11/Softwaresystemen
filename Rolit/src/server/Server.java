package server;

import game.Game;
import info.ClientInformation;
import info.GameInformation;
import info.Logging;
import info.ServerInformation;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Server implements Runnable {

	// -- Instance variables -----------------------------------------

	public ServerInformation serverInfo = new ServerInformation();

	private ServerSocket serverSocket = null;
	private Thread thread = null;
	private boolean running = false;

	
	private static final int DEFAULT_PORT = 3535;
	private static final int SECOND_DEFAULT_PORT = 1337;
	private static final String MESSAGE_MESSAGE = "msg";
	private static final String LIST_USERS_MESSAGE = "users";
	private static final String START_GAME_MESSAGE = "start";
	private static final String KICK_MESSAGE = "kick";
	private static final String HELP_MESSAGE = "help";
	private static final String INFO_MESSAGE = "info";
	private static final String QUOTE_MESSAGE = "quote";
	private static final String QUIT_MESSAGE = "quit";
	private final String[] msgStrings = {Server.MESSAGE_MESSAGE, "message", "tell"};
	private final String[] quitStrings = {Server.QUIT_MESSAGE, "exit"};
	private final String[] helpStrings = {Server.HELP_MESSAGE, "h", "?"};
	private final String[] infoStrings = {Server.INFO_MESSAGE, "i"};
	
	// -- Constructors -----------------------------------------------
	/*
	 * public Server() { this.Server(STANDARD_PORT); }
	 */

	public Server(int port) {
		this.running = true;
		this.serverInfo.localIp = getLocalIpAddress();
		this.serverInfo.port = port;

		Logging.log(Logging.DEBUG, "Server making own thread");
		this.thread = new Thread(this);
		this.thread.start();
	}

	// -- Commands ---------------------------------------------------

	private InetAddress getLocalIpAddress() {
		InetAddress ip;
		if (this.serverInfo.localIp != null) {
			ip = this.serverInfo.localIp;
		} else {
			InetAddress address = null;
			try {
				address = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				Logging.log(Logging.WARNING, "Unknown server, [" + e.getMessage() + "]");
			}
			ip = address;
		}
		return ip;
	}

	public void messageReceived(ClientHandler clientHandler, String msg) {
		Logging.log(Logging.MSG, "\"" + msg + "\" send by "
				+ clientHandler.clientInfo.nickName);
        if (clientHandler.protocol.isValidCommand(msg)) {
            try {
            	Logging.log(Logging.DEBUG, "Trying to execute command");
                clientHandler.protocol.executeCommand(msg);
            } catch (IllegalArgumentException e) {
            	Logging.log(Logging.ERROR, "Illegal arguments in command " + msg
                                    + " [" + e.getMessage() + "]");
            } catch (IOException e) {
            	Logging.log(Logging.ERROR, "Error while processing command "
                                    + msg + " [" + e.getMessage() + "]");
            }
        }
	}
	
	
	//TODO
	public void broadcast(String msg) {
		ClientHandler receivingClient = null;
		String message = null;

		String[] stringArray = msg.split(" ");
		String first = stringArray[0].toLowerCase();
		if (!first.isEmpty()) {
			for (ClientHandler clientHandler : this.serverInfo.connectedClients) {
				if (clientHandler.clientInfo.nickName.equals(first)) {
					receivingClient = clientHandler;
				}
			}
			Logging.log(Logging.MSG, "Sending message to player or lobby");
			if (receivingClient != null) {
				message = msg.replace(stringArray[0] + " ", "");
				Logging.log(Logging.MSG, "Sending message to client "
						+ receivingClient.clientInfo.nickName);
				receivingClient.send(message);
			} else {
				message = msg;
				// TODO sending message to lobby
			}
		} else {
			Logging.log(Logging.WARNING, "No message given");
		}
	}

	public void handleInput(String input) {
		String cmd = input.substring(1);
		if (input.startsWith("/")) {
			this.execute(cmd);
		} else {
			Logging.log(Logging.INFO, "Commands start with /");
		}
	}
	
	public String[] isConsoleCommand(String msg) {
		Logging.log(0, "Checking if command is console command");
		String message = null;
		String msgCommand = null;
		String[] commandAndMessage = new String[2];
		
		String[] stringArray = msg.split(" ");
		String first = stringArray[0].toLowerCase();
		
		if (Arrays.asList(msgStrings).contains(first)) {
			msgCommand = Server.MESSAGE_MESSAGE;
		}
		if (Arrays.asList(helpStrings).contains(first)) {
			msgCommand = Server.HELP_MESSAGE;
		}
		if (Arrays.asList(infoStrings).contains(first)) {
			msgCommand = Server.INFO_MESSAGE;
		}
		if (Arrays.asList(quitStrings).contains(first)) {
			msgCommand = Server.QUIT_MESSAGE;
		}
		if (first.equals(Server.LIST_USERS_MESSAGE)
				|| first.equals(Server.START_GAME_MESSAGE)
				|| first.equals(Server.KICK_MESSAGE)
				|| first.equals(Server.QUOTE_MESSAGE)) {
			msgCommand = first;
		}
		if (msgCommand != null) {
			message = msg.replace(stringArray[0], "").trim();
			commandAndMessage[0] = msgCommand;
			commandAndMessage[1] = message;
		}
		return commandAndMessage;
	}
	
	public void execute(String cmd) {
		Logging.log(Logging.COMMAND, "Sending command " + cmd);
		String[] message = this.isConsoleCommand(cmd);
		if (message[0] != null) {
			switch (message[0]) {
				case MESSAGE_MESSAGE:
					broadcast(message[1]);
					break;
				case LIST_USERS_MESSAGE:
					listUsers();
					break;
				case START_GAME_MESSAGE:
					startGame(message[1]);
					break;
				case KICK_MESSAGE:
					kick(message[1]);
					break;
				case HELP_MESSAGE:
					help();
					break;
				case INFO_MESSAGE:
					info();
					break;
				case QUOTE_MESSAGE:
					quote();
					break;
				case QUIT_MESSAGE:
					closeServer();
					break;
			}
		} else {
			Logging.log(Logging.COMMAND,
					"Incorrect command, type /" + HELP_MESSAGE + " to get all the commands");
		}

	}

	private void quote() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(
					"./src/info/quotes.txt"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					input));
			List<String> stringList = new ArrayList<String>();
			String strLine;
			Random random = new Random();

			while ((strLine = reader.readLine()) != null) {
				stringList.add(strLine);
			}
			int index = random.nextInt(stringList.size());
			String quote = stringList.get(index);
			Logging.log(Logging.QUOTE, quote);
			input.close();
		} catch (IOException e) {
			Logging.log(Logging.ERROR,
					"Failed reading quote file [" + e.getMessage() + "]");
		}
	}

	private void startGame(String string) {
		// TODO Auto-generated method stub
		
	}

	private void help() {
		String format = "%-30s%s";
		Logging.log(Logging.INFO, "Currently supported commands:");
		Logging.log(Logging.INFO, String.format(format, "/" + MESSAGE_MESSAGE
				+ " [player] <message>",
				"| To send a message to a player or the lobby"));
		Logging.log(Logging.INFO, String.format(format, "/" + LIST_USERS_MESSAGE,
				"| To list all the players connected"));
		Logging.log(Logging.INFO, String.format(format, "/" + START_GAME_MESSAGE
				+ " <game>", "| To start a game."));
		Logging.log(Logging.INFO, String.format(format, "/" + KICK_MESSAGE
				+ " <player> [message]",
				"| To kick a player out of the server."));
		Logging.log(Logging.INFO, String.format(format, "/" + HELP_MESSAGE,
				"| To display the list of all commands."));
		Logging.log(Logging.INFO, String.format(format, "/" + INFO_MESSAGE,
				"| To get information about the server."));
		Logging.log(Logging.INFO, String.format(format, "/" + QUOTE_MESSAGE,
				"| To get a random quote."));
		Logging.log(Logging.INFO,
				String.format(format, "/" + QUIT_MESSAGE, "| To close the server."));

	}

	private void info() {
		String format = "%-30s%s";
		String support = "";
		int numberOfGames = 0;
		int numberOfClients = 0;
		int numberOfLobbyClients = 0;
		int numberOfQueueClients = 0;
		int numberOfinGameClients = 0;
		StringBuilder lobbyClients = new StringBuilder();
		StringBuilder queueClients = new StringBuilder();
		StringBuilder inGameClients = new StringBuilder();
		String lobbyClientsPrefix = "";
		String queueClientsPrefix = "";
		String inGameClientsPrefix = "";
		for (ClientHandler onlineClient : this.serverInfo.connectedClients) {
			ClientInformation clientInfo = onlineClient.clientInfo;
			numberOfClients++;

			if (clientInfo.hasGame) {
				if (clientInfo.game.gameInfo.status == GameInformation.QUEUE) {
					numberOfQueueClients++;
					queueClients.append(queueClientsPrefix);
					queueClientsPrefix = ", ";
					queueClients.append(clientInfo.nickName);
				}
				if (clientInfo.game.gameInfo.status == GameInformation.STARTED) {
					numberOfinGameClients++;
					inGameClients.append(inGameClientsPrefix);
					inGameClientsPrefix = ", ";
					inGameClients.append(clientInfo.nickName);
				}
				if (clientInfo.ownsGame) {
					numberOfGames++;
					inGameClients.append("(host)");
				}
			} else {
				numberOfLobbyClients++;
				lobbyClients.append(lobbyClientsPrefix);
				lobbyClientsPrefix = ", ";
				lobbyClients.append(clientInfo.nickName);
			}
		}
		if (this.serverInfo.support == 0) {
			support = "default";
		}
		Logging.log(Logging.INFO, String.format(format,
				"This server is made by:", this.serverInfo.creators));
		Logging.log(Logging.INFO, String.format(format, "Current version:",
				this.serverInfo.version));
		Logging.log(Logging.INFO,
				String.format(format, "Currently supports:", support));
		Logging.log(
				Logging.INFO,
				String.format(format, "Running on IP:",
						this.serverInfo.localIp.getHostAddress()));
		Logging.log(Logging.INFO,
				String.format(format, "Running on port:", this.serverInfo.port));
		Logging.log(Logging.INFO, String.format(format,
				"Number of games running:", numberOfGames));
		Logging.log(Logging.INFO, String.format(format,
				"Number of client online:", numberOfClients));
		Logging.log(
				Logging.INFO,
				String.format(format, "Clients in lobby:", "|"
						+ numberOfLobbyClients + "| " + lobbyClients.toString()));
		Logging.log(
				Logging.INFO,
				String.format(format, "Clients in queue:", "|"
						+ numberOfQueueClients + "| " + queueClients.toString()));
		Logging.log(
				Logging.INFO,
				String.format(
						format,
						"Clients playing a game:",
						"|" + numberOfinGameClients + "| "
								+ inGameClients.toString()));

	}

	public boolean hasClient(String nickname) {
		boolean hasClient = false;
		if (this.serverInfo.connectedClients.size() > 0) {
			for (ClientHandler clientHandler : this.serverInfo.connectedClients) {
				if (clientHandler.clientInfo.nickName.toLowerCase().equals(
						nickname.toLowerCase())) {
					hasClient = true;
				}
			}
		}
		return hasClient;
	}

	public void removeClient(ClientHandler clientHandler) {
		this.serverInfo.connectedClients.remove(clientHandler);
	}

	public ClientHandler getClient(String name) {
		ClientHandler foundClient = null;
		for (ClientHandler clientHandler : this.serverInfo.connectedClients) {
			if (clientHandler.clientInfo.nickName.toLowerCase().equals(
					name.toLowerCase())) {
				foundClient = clientHandler;
			}
		}
		return foundClient;
	}

	public ArrayList<Game> getGames() {
		ArrayList<Game> games = new ArrayList<Game>();
		for (ClientHandler clientHandler : this.serverInfo.connectedClients) {
			Game game = clientHandler.clientInfo.game;
			if (clientHandler.clientInfo.ownsGame && !game.equals(null)) {
				games.add(game);
			}
		}
		return games;
	}

	public void kick(String clientName) {
		Logging.log(Logging.INFO, "Kicking client " + clientName);
		ClientHandler kickClient = this.getClient(clientName);
		if (kickClient != null) {
			try {
				kickClient.close();
				this.removeClient(kickClient);
			} catch (IOException e) {
				Logging.log(Logging.ERROR,
						"Couldn't kick client. [" + e.getMessage() + "]");
			}
		} else {
			Logging.log(Logging.ERROR, "Client doesn't exist");
		}
	}

	public void listUsers() {
		Logging.log(Logging.INFO, "Connected clients: ");
		if (this.serverInfo.connectedClients.size() == 0) {
			Logging.log(Logging.INFO, "Curently no players online!");
		} else {
			for (ClientHandler clientHandler : this.serverInfo.connectedClients) {
				Logging.log(Logging.INFO, clientHandler.clientInfo.nickName + " - "
						+ clientHandler.clientInfo.kindOfPlayer);
			}
		}
	}
	
	private void closeServer() {
		Logging.log(Logging.INFO, "Closing Server");
		this.running = false;
		for (ClientHandler closingClient : this.serverInfo.connectedClients) {
			try {
				closingClient.close();
			} catch (IOException e) {
				Logging.log(
						Logging.ERROR,
						"Couldn't close client: "
								+ closingClient.clientInfo.nickName + " ["
								+ e.getMessage() + "]");
			}
		}
		System.exit(0);
	}
	
	public void run() {
		Logging.log(Logging.INFO, "Starting the server on port: "
				+ this.serverInfo.port);
		Logging.log(Logging.DEBUG,
				"Local IP: " + this.serverInfo.localIp.getHostAddress());
		Logging.log(Logging.DEBUG, "Port: " + this.serverInfo.port);

		try {
			serverSocket = new ServerSocket(this.serverInfo.port);
			Logging.log(Logging.DEBUG, "ServerSocket created on port "
					+ this.serverInfo.port);

			new InputServerHandler(this);

			while (running) {
				Socket clientSocket = serverSocket.accept();
				Logging.log(Logging.INFO, "A new client connected");
				ClientHandler clientHandler = new ClientHandler(this,
						clientSocket);
				Logging.log(Logging.INFO, "The client connected from "
						+ clientHandler.clientInfo.ipAdres.toString()
								.substring(1) + ":"
						+ clientHandler.clientInfo.port);
			}
		} catch (IOException e) {
			Logging.log(Logging.ERROR,
					"Could not create server" + ", [" + e.getMessage() + "]");
		}
	}

	public static void main(String[] args) {
		int sPort;
		boolean runServer = true;
		ServerSocket tempSocket = null;
		if (args.length != 1) {
			sPort = DEFAULT_PORT;
			Logging.log(Logging.INFO,
					"No port has been specified deafult port (" + DEFAULT_PORT
							+ ") will be used.");
			try {
				tempSocket = new ServerSocket(sPort);
			} catch (IOException e) {
				Logging.log(Logging.INFO, "Port " + sPort
						+ " is already in use");
				sPort = SECOND_DEFAULT_PORT;
				Logging.log(Logging.INFO,
						"First default port is already used the second deafult port ("
								+ SECOND_DEFAULT_PORT + ") will be used.");
				try {
					tempSocket = new ServerSocket(sPort);
				} catch (IOException e1) {
					Logging.log(Logging.INFO, "Port " + sPort
							+ " is already in use");
					Logging.log(Logging.INFO,
							"Your system is ****ed up, because you don't have any "
									+ "awesome ports open. Specify one!");
					runServer = false;
				}
			}
		} else {
			sPort = Integer.parseInt(args[0]);
			try {
				tempSocket = new ServerSocket(sPort);
			} catch (IOException e) {
				Logging.log(Logging.INFO, "Specified Port " + sPort
						+ " is already in use. Please specify a new one!");
				runServer = false;
			}
		}
		if (runServer) {
			try {
				tempSocket.close();
			} catch (IOException e2) {
				Logging.log(Logging.DEBUG,
						"Couldn't close temporarly ServerSocket");
			}
			new Server(sPort);
		}
	}
}