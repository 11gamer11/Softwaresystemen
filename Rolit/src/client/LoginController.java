package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

	private LoginGUI loginGui;
	private static final int PLAYER_COMPUTER = 0;
	private static final int PLAYER_HUMAN = 1;
	private static final String STRATEGY_NAIVE = "naive";
	private static final String STRATEGY_SMART = "smart";


	public LoginController(LoginGUI login) {
		this.loginGui = login;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(this.loginGui.getExitButton())) {
			System.exit(0);
		}

		if (this.loginGui.getConnectButton() == event.getSource()) {
			String serverIP = this.loginGui.getServerField().getText();
			String portText = this.loginGui.getPortField().getText();
			String nickname = this.loginGui.getUsernameField().getText();
			int kindOfPlayer = PLAYER_HUMAN;
			String strategy = null;
			if(this.loginGui.getNaiveComputerPlayerRdButton().isSelected()){
				kindOfPlayer = PLAYER_COMPUTER;
				strategy = STRATEGY_NAIVE;
			System.out.println("test " + kindOfPlayer + " " + strategy);
			}
			if(this.loginGui.getSmartComputerPlayerRdButton().isSelected()){
				kindOfPlayer = PLAYER_COMPUTER;
				strategy = STRATEGY_SMART;
			}
			
			System.out.println("test 2 " + kindOfPlayer + " " + strategy);
			if (!serverIP.equals("") && !portText.equals("")
					&& !nickname.equals("")) {
				int port = Integer.parseInt(portText);
				String password = this.loginGui.getPasswordField().getText();
				Client client = new Client(serverIP, port, nickname, kindOfPlayer, strategy);
				if (!password.equals("")) {
					client.clientInfo.password = password;
				}
				synchronized (client) {
					try {
						while (!client.clientInfo.isConnected && client.clientInfo.running) {
							client.wait();
						}
					} catch (InterruptedException ie) {
						
					}
				}
				if (client.clientInfo.running) {
					this.loginGui.getServerField().setEnabled(false);
					this.loginGui.getPortField().setEnabled(false);
					this.loginGui.getUsernameField().setEnabled(false);
					this.loginGui.getPasswordField().setEnabled(false);
					this.loginGui.getHumanPlayerRdButton().setEnabled(false);
					this.loginGui.getNaiveComputerPlayerRdButton().setEnabled(false);
					this.loginGui.getSmartComputerPlayerRdButton().setEnabled(false);
					this.loginGui.getConnectButton().setEnabled(false);
					this.loginGui.getExitButton().setEnabled(false);
					this.loginGui.getConnectingLabel().setVisible(true);
					client.hello();
				}
			}
		}
	}
}
