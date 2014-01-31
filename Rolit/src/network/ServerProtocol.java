package network;

import info.ClientInformation;
import info.Logging;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;

import client.ServerHandler;
import server.ClientHandler;
import server.Server;

public class ServerProtocol extends MainServerProtocol implements Runnable {
	
	private ServerHandler parentServerHandler;
	private Thread thread;
	private ClientInformation info = null;
	
	public HashMap<String, Integer[]> clientCommands = new HashMap<String, Integer[]>();

	public ServerProtocol(ServerHandler serverHandler) {
		if (serverHandler != null) {
			this.parentServerHandler = serverHandler;
			this.info = this.parentServerHandler.parentClient.clientInfo;
		}

		Logging.log(Logging.DEBUG, "ServerProtocol breaking off into own thread");
		this.thread = new Thread(this);
		this.thread.start();
	}

	public ServerProtocol() {
		// TODO Auto-generated constructor stub
	}

	public void handshake(int supports, String version) throws IOException {
		Logging.log(Logging.OUTGOING_CMD, "hello " + supports + " " + version);
		this.info.supports = supports;
		this.info.version = version;
		this.info.hasHandshaked = true;
		this.info.hasAuthed = true;
		this.info.online = true;
	}

	public void handshake(int supports, String version, String nonce)
		throws IOException {
		Logging.log(Logging.COMMAND, "hello " + supports + " " + version + " "
				+ nonce);
		this.info.supports = supports;
		this.info.version = version;
		PrivateKey pKey = Security.getPrivateKey(this.info.nickName,
				this.info.password);
		byte[] signedNonce = Security.sign(nonce.getBytes(), pKey);
		String b64EncNonce = Security.base64Encode(signedNonce);
		this.info.hasHandshaked = true;
		this.parentServerHandler.sendCommand(ClientProtocol.AUTH,
				new String[] {b64EncNonce});
	}

	public void authOk() throws IOException {
		Logging.log(Logging.COMMAND, "authok");
		if (this.info.hasHandshaked) {
			this.info.hasAuthed = true;
			this.info.online = true;
		} else {
			Logging.log(Logging.ERROR,
					"Server sent AuthOK but client has not handshaked yet.");
		}
	}
	
	public void error(int errorCode) throws IOException {
		Logging.log(Logging.ERROR, "error "+errorCode);
	}

	public void game(String creator, int status, int noPlayers) throws IOException {

	}
	
	public void start(String playerOne, String playerTwo) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void start(String playerOne, String playerTwo, String playerThree) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void start(String playerOne, String playerTwo, String playerThree,
			String playerFour) throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void move() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	public void moveDone(String name, int x, int y) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void gameOver(int score, String[] winners) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void message(String name, String body) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void challenge(String challenger, String other1) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void challenge(String challenger, String other1, String other2) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void challenge(String challenger, String other1, String other2,
			String other3) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void challengeResponse(String name, boolean accept) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void canBeChallenged(String name, boolean flag) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void highscore(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void online(String name, boolean isOnline) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public boolean hasGame() {
		// TODO Auto-generated method stub
		return false;
	}

	public void executeCommand(String msg) throws IOException {
		Logging.log(Logging.DEBUG, "Executing command: " + msg);
		String[] splitMsg = msg.split(" ");
		String command = splitMsg[0];
		String pureMessage = msg.substring(command.length() + 1);
		String[] args = pureMessage.split(" ");
		int numArgs = args.length;
		System.out.println(command.equals(HANDSHAKE) + " " + numArgs);
		switch (command) {
			case HANDSHAKE:
				if (numArgs == 2) {
					handshake(Integer.parseInt(args[0]), args[1]);
				} else if (numArgs > 2) {
					handshake(Integer.parseInt(args[0]), args[1], args[2]);
				} else {
					Logging.log(Logging.ERROR,
							"Error in handshake, too few arguments.");
				}
				break;
			case AUTH_OK:
				authOk();
				break;
			case ERROR:
				int errorCode = Integer.parseInt(args[0]);
				error(errorCode);
				break;
			case GAME:
				int status = Integer.parseInt(args[1]);
				int numPlayers = Integer.parseInt(args[2]);
				game(args[0], status, numPlayers);
				break;
			case START:
				start(args);
				break;
			case MOVE:
				move();
				break;
			case MOVE_DONE:
				int x = Integer.parseInt(args[1]);
				int y = Integer.parseInt(args[2]);
				moveDone(args[0], x, y);
				break;
			case GAME_OVER:
				if (numArgs == 0) {
					gameOver();
				} else {
					int maxFields = Integer.parseInt(args[0]);
					String[] gameOverArgs = new String[numArgs - 1];
					for (int i = 1; i < numArgs; i++) {
						gameOverArgs[i - 1] = args[i];
					}
					gameOver(maxFields, gameOverArgs);
				}
				break;
			case MESSAGE:
				String message = "";
				for (String arg : args) {
					if (message.equals("") && !arg.equals(args[0])) {
						message = arg;
					} else if (!arg.equals(args[0])) {
						message = message + " " + arg;
					}
				}
				message(args[0], message);
				break;
			/*case CHALLENGE:
				String[] cmdArgs = new String[args.length - 1];
				for (int i = 1; i < args.length; i++) {
					cmdArgs[i - 1] = args[i];
				}
				challenge(args[0], cmdArgs);
				break;
			case CHALLENGE_RESPONSE:
				challengeResponse(args[0], Boolean.parseBoolean(args[1]));
				break;
			case CAN_BE_CHALLENGED:
				canBeChallenged(args[0], Boolean.parseBoolean(args[1]));
				break;*/
			case HIGHSCORE:
				highscore(args);
				break;
			case ONLINE:
				online(args[0], Boolean.parseBoolean(args[1]));
				break;
			default:
				break;
		}
	}

	private void gameOver() {
		// TODO Auto-generated method stub
		
	}

	private void start(String[] args) {
		// TODO Auto-generated method stub
		
	}

	public boolean isValidCommand(String msg) {
		Logging.log(Logging.DEBUG, "Checking if \"" + msg + "\" is a valid command");
		String[] splitCommand = msg.split(" ");
		String command = splitCommand[0].toLowerCase();
		int numArgs = splitCommand.length - 1;
		boolean isValid = false;
		if (clientCommands.containsKey(command)) {
			for (int possibleArgs : clientCommands.get(command)) {
				if (numArgs == possibleArgs) {
					isValid = true;
				}
				if (possibleArgs == -1 && numArgs > 0
						&& command.equals(MESSAGE)) {
					isValid = true;
				}
			}
		}
		Logging.log(Logging.DEBUG, "\"" + msg + "\" is " + isValid);
		return isValid;
	}
	
	public void run() {
		// Add commands to clientCommands Map
		clientCommands.put(HANDSHAKE.toLowerCase(),
				new Integer[] { 2, 3 });
		clientCommands.put(AUTH_OK.toLowerCase(),
				new Integer[] { 0 });
		clientCommands.put(ERROR.toLowerCase(),
				new Integer[] { 1 });
		clientCommands.put(GAME.toLowerCase(),
				new Integer[] { 3 });
		clientCommands.put(START.toLowerCase(), new Integer[] {
				2, 3, 4 });
		clientCommands.put(MOVE.toLowerCase(),
				new Integer[] { 0 });
		clientCommands.put(MOVE_DONE.toLowerCase(),
				new Integer[] { 3 });
		clientCommands.put(GAME_OVER.toLowerCase(),
				new Integer[] { 0, 2 });
		clientCommands.put(MESSAGE.toLowerCase(),
				new Integer[] { -1 });
		clientCommands.put(CHALLENGE.toLowerCase(),
				new Integer[] { 2 });
		clientCommands.put(CHALLENGE_RESPONSE.toLowerCase(),
				new Integer[] { 2 });
		clientCommands.put(CAN_BE_CHALLENGED.toLowerCase(),
				new Integer[] { 2 });
		clientCommands.put(ONLINE.toLowerCase(),
				new Integer[] { 2 });
		clientCommands.put(HIGHSCORE.toLowerCase(),
				new Integer[] { 2 });
	}
}
