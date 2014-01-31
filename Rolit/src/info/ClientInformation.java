package info;

import game.Game;

import java.net.InetAddress;
import java.net.Socket;

import server.Server;

public class ClientInformation {
	
	public static final String VERSION = "G77_0.1";
	public static final int SUPPORT = 1;
	
	//connection info
	public boolean hasHandshaked = false;
    public boolean hasAuthed = false;
    public boolean hasGame = false;
    public boolean ownsGame = false;
    public boolean canBeChallenged = false;
    public boolean isChallenged = false;
    public boolean isChallenger = false;
    public boolean online = false;
	public boolean isConnected = false;
	public boolean running = false;
	public boolean needsAuth = false;
	public boolean hasSentNonce = false;

	//internal connection info
	public InetAddress ipAdres = null;
	public int port = -1;
	public Socket socket = null;
	
	//Server info
	public Server server = null;
	public InetAddress serverIpAdres = null;
	public int serverPort = -1;
	
	//personal info
    public String nickName = null;
	public String password = null;
	public Game game = null;
	public int kindOfPlayer = -1;
	public String strategy = null;
	public int supports = -1;
	public String version = null;
}
