package server;

import game.Player;
import info.ClientInformation;
import info.Logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import network.ClientProtocol;
import network.ServerProtocol;

public class ClientHandler implements Runnable {

	
	public ClientInformation clientInfo = new ClientInformation();
	public ClientProtocol protocol = new ClientProtocol(this);
	
	private boolean running = false;
	private Thread thread = null;
	private String message = "";

	private InputStream inStream = null;
	private BufferedReader inReader = null;
	private OutputStream outStream = null;
	private BufferedWriter outWriter = null;
	private BufferedReader consoleInReader = new BufferedReader(
			new InputStreamReader(System.in));
	private BufferedWriter consoleOutWriter = new BufferedWriter(
			new OutputStreamWriter(System.out));

	public ClientHandler(Server server, Socket socket) {
		this.running = true;
		this.clientInfo.server = server;
		this.clientInfo.socket = socket;
		this.clientInfo.ipAdres = socket.getInetAddress();
		this.clientInfo.port = socket.getPort();

		Logging.log(Logging.DEBUG, "Client Handler making own thread");
		this.thread = new Thread(this);
		this.thread.start();
	}

	public void listen() {
		Logging.log(Logging.DEBUG, "Started listening to client "
				+ this.clientInfo.nickName);

		while (running) {
			try {
				this.message = this.inReader.readLine();
				Logging.log(Logging.DEBUG, "Received " + this.message
						+ " from: " + this.clientInfo.nickName);
				this.clientInfo.server.messageReceived(this, this.message);
			} catch (IOException ioe) {
				if (running) {
					Logging.log(
							Logging.ERROR,
							"Could not process message from client ["
									+ ioe.getMessage() + "]");
					try {
						this.close();
					} catch (IOException ioe2) {
						Logging.log(
								Logging.ERROR,
								"Could not close client connection ["
										+ ioe2.getMessage() + "]");
					}
				}
			} catch (NullPointerException npe) {
				Logging.log(Logging.ERROR,
						"Unexpected client disconnect! [" + npe.getMessage() + "]");
				if (running) {
					try {
						this.close();
					} catch (IOException ioe3) {
						Logging.log(
								Logging.ERROR,
								"Could not close client connection ["
										+ ioe3.getMessage() + "]");
					}
				}
			}
		}
	}

	public void send(String msg) {
		try {
			this.outWriter.write(msg + "\r\n");
			this.outWriter.flush();
		} catch (IOException e) {
			Logging.log(Logging.ERROR,
					"Could not send message " + msg + " [" + e.getMessage()
							+ "]");
			try {
				if (running) {
					this.close();
				}
			} catch (IOException e1) {
				Logging.log(
						Logging.ERROR,
						"Could not close connection with client "
								+ this.clientInfo.nickName + " [" + e1.getMessage() + "]");
			}
		}
	}

	public void broadcast(String msg) {
		this.clientInfo.server.broadcast(msg);
	}

	public void sendCommand(String string, String[] args) {
		String body = "";
		for (String arg : args) {
			body = body + " " + arg;
		}

		switch (string) {
			case ServerProtocol.GAME:
			case ServerProtocol.MESSAGE:
			case ServerProtocol.ONLINE:
				Logging.log(Logging.BROADCAST, string + body);
				broadcast(string + body);
				break;
			case ServerProtocol.GAME_OVER:
				Logging.log(Logging.INFO, "Game over for client " + this.clientInfo.nickName);
				
				for (Player player : this.clientInfo.game.gameInfo.players) {
					ClientHandler client  = this.clientInfo.server.getClient(player.getName());
					Logging.log(Logging.INFO, "Sending to " + client.clientInfo.nickName + ": "
							+ string + body);
					client.send(string + body);
				}
				break;
			case ServerProtocol.MOVE_DONE:
				Logging.log(Logging.INFO, "Sending move to client " + this.clientInfo.nickName);
				for (Player player : this.clientInfo.game.gameInfo.players) {
					ClientHandler client  = this.clientInfo.server.getClient(player.getName());
					Logging.log(Logging.INFO, "Sending to " + client.clientInfo.nickName + ": "
							+ string + body);
					client.send(string + body);
				}
				break;
			default:
				Logging.log(Logging.BROADCAST, this.clientInfo.nickName + ": " + string + body);
				send(string + body);
		}
	}

	public void run() {
		Logging.log(Logging.DEBUG, "Trying to open channel to client");
		Logging.log(Logging.INFO, "Client has been assigned the name "
				+ this.clientInfo.nickName);
		try {
			this.inStream = this.clientInfo.socket.getInputStream();
			this.inReader = new BufferedReader(new InputStreamReader(
					this.inStream));
			this.outStream = this.clientInfo.socket.getOutputStream();
			this.outWriter = new BufferedWriter(new OutputStreamWriter(
					this.outStream));
			Logging.log(Logging.INFO, "Opened channel");
		} catch (IOException e) {
			Logging.log(Logging.ERROR,
					"Unable to open channel [" + e.getMessage() + "]");
		}
		this.listen();

	}

	public void close() throws IOException {
		this.running = false;
		if (this.clientInfo.hasHandshaked && this.clientInfo.hasAuthed) {
			this.sendCommand(ServerProtocol.ONLINE,
					new String[] {this.clientInfo.nickName, String.valueOf(false)});
		}
		if (this.clientInfo.game != null) {
			this.clientInfo.game.gameInfo.status = -1;
			this.clientInfo.game.interrupt();
		}
		this.clientInfo.server.removeClient(this);
		this.clientInfo.socket.close();
	}
}
