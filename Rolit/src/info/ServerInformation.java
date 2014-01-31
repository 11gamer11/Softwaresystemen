package info;

import game.Game;

import java.net.InetAddress;
import java.util.ArrayList;

import server.ClientHandler;
import network.ClientProtocol;

//import org.apache.commons.codec.binary.Base64;

public class ServerInformation {
	
	public final String creators = "Jeroen Waals en Maryse te Brinke";
	public final int support = 0;
    public final String version = "JM_v0.1";
	
	// Connection info
    public InetAddress localIp = null;
    public InetAddress remoteIp = null;
    public int port = -1;
    
    // Server info
    public ArrayList<Game> games = new ArrayList<Game>();
	public ArrayList<ClientHandler> connectedClients = new ArrayList<ClientHandler>();
	public ArrayList<ClientInformation> connectedClientsInformation = new ArrayList<ClientInformation>();
}
