package client;

import info.Logging;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import server.ClientHandler;
import network.ClientProtocol;
import network.ServerProtocol;

public class ServerHandler extends Thread {
	public Client parentClient = null;
	public ServerProtocol protocol;

	private Socket socket = null;
	private Thread thread = null;
	private String lastMessage = "";
	private boolean running = true;

	private InputStream inStream = null;
	private BufferedReader inReader = null;
	private OutputStream outStream = null;
	private BufferedWriter outWriter = null;
	private BufferedWriter consoleOutWriter = new BufferedWriter(
			new OutputStreamWriter(System.out));

	public ServerHandler(Client client, Socket server) {
		this.parentClient = client;
		this.socket = server;
		this.protocol = new ServerProtocol(this);
		
		Logging.log(Logging.DEBUG, "ServerHandler making own thread");
		this.thread = new Thread(this);
		this.thread.start();
	}

	public void run() {
		Logging.log(Logging.DEBUG, "Trying to talk to server");
		try {
			this.inStream = this.socket.getInputStream();
			this.inReader = new BufferedReader(new InputStreamReader(
					this.inStream));
			this.outStream = this.socket.getOutputStream();
			this.outWriter = new BufferedWriter(new OutputStreamWriter(
					this.outStream));
			Logging.log(Logging.INFO, "Talking to server");
/*			for (ClientHandler clientHandler : this.
					parentClient.
					clientInfo.
					server.
					serverInfo.
					connectedClients) {
				if (clientHandler.clientInfo.nickName
						.equals(this.parentClient.clientInfo.nickName)) {
					clientHandler.clientInfo = this.parentClient.clientInfo;
				}
			}*/
			// TODO update GUI
		} catch (IOException ioe) {
			Logging.log(Logging.ERROR,
					"Unable to talk to server [" + ioe.getMessage() + "]");
		}
		this.listen();
	}

	public void listen() {
		Logging.log(Logging.DEBUG, "Started listening to server.");
		this.parentClient.clientInfo.isConnected = true;
		synchronized (this.parentClient) {
			this.parentClient.notifyAll();
		}
		while (this.running) {
			try {
				this.lastMessage = this.inReader.readLine();
				this.consoleOutWriter.write(this.lastMessage);
				this.parentClient.messageReceived(this, this.lastMessage);
			} catch (IOException e) {
				if (!this.running) {
					Logging.log(
							Logging.ERROR,
							"Could not recieve messages from server ["
									+ e.getMessage() + "]");
					this.close();
				}
			} catch (NullPointerException npe) {
				Logging.log(Logging.ERROR, "Unexpected server disconnect ["
						+ npe.getMessage() + "]");
				this.running = false;
				this.close();
			}
		}
	}

	public void send(String msg) {
		try {
			this.outWriter.write(msg + "\r\n");
			this.outWriter.flush();
		} catch (IOException e) {
			Logging.log(Logging.ERROR, "Could not send message " + msg + " ["
					+ e.getMessage() + "]");
			if (!this.running) {
				this.close();
			}
		}
	}

	public void sendCommand(String string, String[] args) {
		String body = "";
		for (String arg : args) {
			body = body + " " + arg;
		}

		Logging.log(Logging.INFO, "Client sent to server: " + string + body);
		send(string + body);
	}

	public void close() {
		this.running = false;
		try {
			this.socket.close();
		} catch (IOException e) {
			Logging.log(Logging.ERROR,
					"Couldn't close connection with server [" + e.getMessage()
							+ "]");
		}
	}
}
