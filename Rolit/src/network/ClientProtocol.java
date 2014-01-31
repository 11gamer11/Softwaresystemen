package network;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import server.ClientHandler;
import client.ServerHandler;
import game.Game;
import info.ClientInformation;
import info.Logging;

public class ClientProtocol extends MainClientProtocol implements Runnable {
	
	private ClientHandler parentClientHandler;
    
    private Thread thread;
    private ClientInformation info;
    private String nonce;
    private PublicKey pubKey;
    private PrivateKey privKey;
	
	public HashMap<String,Integer[]> clientCommands = new HashMap<String,Integer[]>();

	public ClientProtocol(ClientHandler clientHandler) {
		if (clientHandler != null) {
			this.parentClientHandler = clientHandler;
			info = this.parentClientHandler.clientInfo;
		}

		Logging.log(Logging.DEBUG, "ClientProtocol making own thread");
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public boolean isValidCommand(String msg) {

		Logging.log(Logging.DEBUG, "Checking command " + msg + " for validity");

		String[] splitCommand = msg.split(" ");
		String command = splitCommand[0];
		int numArgs = splitCommand.length - 1;

		if (clientCommands.containsKey(command.toLowerCase())) {
			for (int possibleArgs : clientCommands.get(command.toLowerCase())) {
				Logging.log(Logging.DEBUG, "Command has " + numArgs
						+ " arguments and can have " + possibleArgs);
				if (numArgs == possibleArgs) {
					return true;
				}
				if (possibleArgs == -1 && numArgs > 0
						&& command.equals(ServerProtocol.MESSAGE)) {
					return true;
				}
			}
		}
		Logging.log(Logging.DEBUG, "Received invalid command from client: " + msg);
		return false;
	}

	public void executeCommand(String msg) throws IOException {
		String[] splitCommand = msg.split(" ");
		String command = splitCommand[0];
		int numArgs = splitCommand.length - 1;
		String[] args = new String[numArgs];
		for (int i = 1; i <= numArgs; i++) {
			args[i - 1] = splitCommand[i];
		}
		switch (command) {
			case HANDSHAKE:
				int supports = Integer.parseInt(args[1]);
				hello(args[0], supports, args[2]);
				break;
			case AUTH:
				auth(args[0]);
				break;
			case CREATE_GAME:
				createGame();
				break;
			case JOIN_GAME:
				joinGame(args[0]);
				break;
			case START_GAME:
				startGame();
				break;
			case MOVE:
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				move(x, y);
				break;
			case MESSAGE:
				String mesg = "";
				for (String arg : args) {
					if (mesg.equals("")) {
						mesg = arg;
					} else {
						mesg = mesg + " " + arg;
					}
				}
				message(mesg);
				break;
			/*case CHALLENGE:
				challenge(args);
				break;
			case CHALLENGE_RESPONSE:
				challengeResponse(Boolean.parseBoolean(args[0]));
				break;*/
			case HIGHSCORE:
				highscore(args[0], args[1]);
				break;
			default:
				break;
			}
	}

	public void hello(String clientName, int supports, String version) {
		Logging.log(Logging.COMMAND, ServerProtocol.HANDSHAKE + " "
				+ clientName + " " + supports + " " + version);
		System.out.println("test");
		System.out.println(this.info);
		System.out.println(this.info.server);
		System.out.println(this.info.server.serverInfo);
		Logging.log(Logging.DEBUG,
				"Client exists? " + String.valueOf(this.info.server.hasClient(clientName)));
		System.out.println("test");
		if (!this.info.hasAuthed && !this.info.hasHandshaked
				&& !this.info.server.hasClient(clientName)) {
			this.info.nickName = clientName;
			this.info.supports = supports;
			this.info.version = version;
			Logging.log(0, "SERVER NICK: User nickname is "
					+ this.info.nickName);
			if (this.info.nickName.toLowerCase().startsWith("player_")) {
				System.out.println("testing");
				nonce = UUID.randomUUID().toString();
				pubKey = Security.getPublicKey(clientName);
				privKey = Security.getPrivateKey(this.info.nickName,
						this.info.password);
				this.info.needsAuth = true;
				this.parentClientHandler.sendCommand(HANDSHAKE,
						new String[] {String.valueOf(this.info.supports),
							this.info.version, nonce});
				this.info.hasSentNonce = true;
				Logging.log(Logging.DEBUG,
						"The nonce signed with your private key, converted to base64 is:");
				Logging.log(Logging.DEBUG, Security.base64Encode(Security.sign(
						nonce.getBytes(), privKey)));
			} else {
				// Needs no authing
				System.out.println("testing 2");
				this.info.hasAuthed = true;
				this.parentClientHandler.sendCommand(HANDSHAKE,
						new String[] {String.valueOf(this.info.supports),
							this.info.version});
				this.parentClientHandler
						.sendCommand(ServerProtocol.ONLINE, new String[] {
							this.info.nickName, String.valueOf(true) });
				this.sendGames();
				this.sendOnline();
			}
			this.info.hasHandshaked = true;
		} /*else if (this.isAuthDone()) {
			this.sendGenericError();
		} else if (this.isHandshakeDone()) {
			this.sendGenericError();
		} else if (this.parentClientHandler.parentServer.hasClient(clientName)) {
			this.sendGenericError();
		}*/
		this.parentClientHandler.clientInfo.server.serverInfo.connectedClients
				.add(parentClientHandler);
	}

	public void auth(String signature) {
		Logging.log(Logging.COMMAND, AUTH + " " + signature);
		if (this.info.hasHandshaked && this.info.hasSentNonce && this.info.needsAuth
				&& !this.info.hasAuthed) {
			Logging.log(Logging.DEBUG, "Validating signature");
			if (this.verifyAuth(signature)) {
				this.info.hasAuthed = true;
				this.parentClientHandler.sendCommand(ServerProtocol.AUTH_OK,
						new String[0]);
				this.parentClientHandler.sendCommand(ServerProtocol.ONLINE,
						new String[] { this.info.nickName, String.valueOf(true) });
				this.sendGames();
				this.sendOnline();
			} else {
				//this.sendError(Errors.ERROR_INVALID_LOGIN);
				try {
					Logging.log(Logging.WARNING,
							"Invalid signed nonce. Closing connection.");
					this.parentClientHandler.close();
				} catch (IOException e) {
					Logging.log(Logging.ERROR, "Could not close connection. ["
							+ e.getMessage() + "]");
				}
			}
		}/* else if (!this.info.hasHandshaked) {
			this.sendError(Errors.ERROR_HANDSHAKE_MISSING);
		} else if (!this.info.hasSentNonce) {
			this.sendGenericError();
		} else if (this.needsAuth()) {
			this.sendGenericError();
		}*/
	}

	private boolean verifyAuth(String signature) {
		byte[] sign = Security.base64Decode(signature);
		return Security.verify(sign, nonce.getBytes(), pubKey);
	}

	public void createGame() {
		Logging.log(Logging.COMMAND, CREATE_GAME);
		if (this.info.hasHandshaked && !this.info.hasGame) {
			this.info.game = new Game(this.info.nickName, this.info.kindOfPlayer, this.info.strategy);
			this.broadcastGame(this.info.game);
		} else if (!this.isHandshakeDone()) {
			this.sendError(ClientProtocol.ERROR_HANDSHAKE_MISSING);
		} else if (this.hasGame()) {
			this.sendError(ClientProtocol.ERROR_USER_ALREADY_HAS_GAME);
		} else {
			this.sendGenericError();
		}
	}

	public void joinGame(String creator) {
		Logging.log(Logging.COMMAND, JOIN_GAME + " " + creator);
		ClientHandler creatorClient = this.info.server.getClient(creator);
		if (creatorClient != null && creatorClient.clientInfo.game != null) {
			ClientInformation creatorInfo = creatorClient.clientInfo;
			if (this.info.hasHandshaked && !this.info.hasGame
					&& creatorInfo.hasGame
					&& !creatorInfo.game.gameInfo.hasStarted()
					&& !creatorInfo.game.gameInfo.isFull()) {
				creatorInfo.game.addPlayer(this.info.nickName,
						this.info.kindOfPlayer, this.info.strategy);
				this.broadcastGame(this.getNetworkGame());
			} else if (!this.isHandshakeDone()) {
				this.sendError(ClientProtocol.ERROR_HANDSHAKE_MISSING);
			} else if (!this.parentHandler.parentServer.getClient(creator).clientInfo.hasGame) {
				this.sendError(ClientProtocol.ERROR_USER_HAS_NO_GAME);
			} else if (this.hasGame()) {
				this.sendError(ClientProtocol.ERROR_USER_ALREADY_HAS_GAME);
			} else if (this.getGameForClient(creator).isFull()) {
				this.sendError(ClientProtocol.ERROR_GAME_FULL);
			} else {
				this.sendGenericError();
			}
		} else {
			Logging.log(Logging.ERROR, creator + "doesn't exist or doesn't own a game");
		}
	}

	public void startGame() {
		Logging.log(Logging.COMMAND, START_GAME);
		if (this.info.hasHandshaked
				&& this.info.hasGame
				&& this.info.game.gameInfo.owner.toLowerCase()
						.equals(this.info.nickName.toLowerCase())
				&& !this.info.game.gameInfo.hasStarted()
				&& this.info.game.gameInfo.isPlayable()) {
			this.getNetworkGame().setupGame();
			this.getNetworkGame().startGame();
		} else if (!this.isHandshakeDone()) {
			this.sendError(ClientProtocol.ERROR_HANDSHAKE_MISSING);
		} else if (!this.hasGame()) {
			this.sendError(ClientProtocol.ERROR_NO_SUCH_GAME);
		} else if (!this.getNetworkGame().ownerClient.getNick().toLowerCase()
				.equals(this.getNick().toLowerCase())) {
			this.sendGenericError();
		} else if (this.getNetworkGame().hasStarted()) {
			this.sendError(ClientProtocol.ERROR_GAME_FULL);
		} else if (this.getNetworkGame().participants.size() < 2) {
			this.sendError(ClientProtocol.ERROR_TOO_FEW_PLAYERS);
		} else {
			this.sendGenericError();
		}
	}
	//TODO
	public void move(int x, int y) {
		Logging.log(Logging.COMMAND, MOVE + " " + x + " " + y);
		if (this.info.game.getCurrentPlayer().getName()
				.equals(this.info.nickName)) {
			if (this.info.hasHandshaked
					&& this.info.hasGame) {
				this.getNetworkGame().chooseMove(this.getNick(), x, y);
			} else if (!this.isHandshakeDone()) {
				this.sendError(ClientProtocol.ERROR_HANDSHAKE_MISSING);
			} else if (!this.hasGame()) {
				this.sendError(ClientProtocol.ERROR_NO_SUCH_GAME);
			} else if (this.getNetworkGame().currentPlayer.getName() != this
					.getNick()) {
				this.sendError(ClientProtocol.ERROR_INVALID_MOVE);
			} else {
				this.sendGenericError();
			}
		} else {
			Logging.log(Logging.ERROR, this.info.nickName
					+ " isn't the current player and can't move!");
		}
	}

	public void message(String body) {
		// TODO Auto-generated method stub
		
	}

	public void challenge(String other) {
		// TODO Auto-generated method stub
		
	}

	public void challenge(String other1, String other2) {
		// TODO Auto-generated method stub
		
	}

	public void challenge(String other1, String other2, String other3) {
		// TODO Auto-generated method stub
		
	}

	public void challengeResponse(boolean accept) {
		// TODO Auto-generated method stub
		
	}

	public void highscore(String type, String arg) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sends a list of all current games to this client.
	 */
	public void sendGames() {
		Logging.log(Logging.INFO, "Sending initial list of games to client.");
		ArrayList<Game> games = this.parentClientHandler.clientInfo.server
				.getGames();

		for (Game game : games) {
			String nick = game.getOwner();
			String status = String.valueOf(game.gameInfo.status);
			String num = String.valueOf(game.gameInfo.players.size());
			this.parentClientHandler.send(ServerProtocol.GAME + " " + nick + " "
					+ status + " " + num);
		}
	}

	/**
	 * Sends a list of online clients to this client
	 */
	public void sendOnline() {
		Logging.log(Logging.INFO,
				"Sending initial list of online users to client.");
		ArrayList<ClientHandler> clients = this.parentClientHandler.clientInfo.server.serverInfo.connectedClients;
		for (ClientHandler client : clients) {
			String nick = client.clientInfo.nickName;
			this.parentClientHandler.send(ServerProtocol.ONLINE + " " + nick
					+ " true");
		}
	}

	public void run() {
		this.clientCommands.put(HANDSHAKE.toLowerCase(), new Integer[]{3});
		this.clientCommands.put(AUTH.toLowerCase(), new Integer[]{1});
		this.clientCommands.put(CREATE_GAME.toLowerCase(), new Integer[]{0});
		this.clientCommands.put(JOIN_GAME.toLowerCase(), new Integer[]{1});
		this.clientCommands.put(START_GAME.toLowerCase(), new Integer[]{0});
		this.clientCommands.put(MOVE.toLowerCase(), new Integer[]{2});
		this.clientCommands.put(MESSAGE.toLowerCase(), new Integer[]{-1});
		this.clientCommands.put(CHALLENGE.toLowerCase(), new Integer[]{1, 2, 3});
		this.clientCommands.put(CHALLENGE_RESPONSE.toLowerCase(), new Integer[]{1});
		this.clientCommands.put(HIGHSCORE.toLowerCase(), new Integer[]{2});
	}
}
