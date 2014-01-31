package client;

import info.Logging;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import network.ServerProtocol;

public class LobbyController implements ActionListener {

	/**
	 * . View of the MVC model of the Lobby UI
	 */
	private LobbyGUI lobby;

	private Client client;

	/**
	 * . Constructor of this class
	 * 
	 * @param view
	 *            - View of the MVC model of the Lobby UI
	 */
	public LobbyController(LobbyGUI lobby, Client client) {
		this.lobby = lobby;
		this.client = client;
		//this.lobby.getNickLabel().setText(this.client.clientInfo.nickName);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.lobby.getCreateButton()) {
			Logging.log(Logging.INFO, "Creating game.");
			this.client.getServerHandler().sendCommand(
					ServerProtocol.CREATE_GAME, new String[0]);
		} else if (e.getSource() == this.lobby.getJoinButton()) {
			// This button can be a Join Game button if client has no game, or
			// Start Game button if user has game and is owner.
			if (this.lobby.getJoinButton().getText().toLowerCase()
					.equals(LobbyGUI.BTN_JOIN_GAME.toLowerCase())) {
				// Join game of selected user.
				String creator = this.lobby.selectedPlayer;
				Logging.log(Logging.INFO, "Joining game by player " + creator
						+ ".");
				this.client.getServerHandler().sendCommand(
						ServerProtocol.JOIN_GAME, new String[] { creator });
				this.client.clientInfo.joiningGame = creator;
			} else if (this.lobby.getJoinButton().getText().toLowerCase()
					.equals(LobbyGUI.BTN_START_GAME.toLowerCase())) {
				// Start game if owner.
				if (this.client.clientInfo.ownsGame) {
					Logging.log(Logging.INFO, "Starting game.");
					// TODO show GameUI
					this.client.getServerHandler().sendCommand(
							ServerProtocol.START_GAME, new String[0]);
				}
			}
		} else if (e.getSource() == this.lobby.getSendButton()) {
			if (this.lobby.getChatInputtxtField() != null) {
				String message = this.lobby.getChatInputtxtField().getText();
				this.client.getServerHandler().sendCommand(
						ServerProtocol.MESSAGE, new String[] { message });
			}
		}
	}

}
