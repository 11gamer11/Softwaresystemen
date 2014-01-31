package client;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JTextField;
import javax.swing.JButton;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

public class LoginGUI extends JFrame implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4754680993375612898L;
	private JPanel contentPane;
	private JTextField serverIPtxtField;
	private JTextField serverPorttxtField;
	private JTextField nickNametxtField;
	private JPasswordField passwordField;
	private JButton connectButton;
	private JButton exitButton;
	private JRadioButton humanPlayerRdb;
	private JRadioButton naiveComputerPlayerRdb;
	private JRadioButton smartComputerPlayerRdb;
	private LoginController loginController;
	private JLabel connectingLabel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		new LoginGUI();
	}

	public LoginGUI() {
		initialize();
		this.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public void initialize() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[434px]", "[34.00px][173.00px]"));

		this.loginController = new LoginController(this);
		
		JLabel titel = new JLabel("Rolit Login");
		titel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		titel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titel, "cell 0 0,growx,aligny top");

		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 1,grow");
		panel.setLayout(
			new FormLayout(
				new ColumnSpec[] {
					FormFactory.RELATED_GAP_COLSPEC,
					ColumnSpec.decode("max(90dlu;default)"),
					ColumnSpec.decode("6dlu"),
					ColumnSpec.decode("max(90dlu;default)"),
					FormFactory.RELATED_GAP_COLSPEC,
					FormFactory.DEFAULT_COLSPEC,
				},
				new RowSpec[] {
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.RELATED_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
					FormFactory.PARAGRAPH_GAP_ROWSPEC,
					FormFactory.DEFAULT_ROWSPEC,
				}
			)
		);

		JLabel serverIPLabel = new JLabel("Server IP:");
		serverIPLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(serverIPLabel, "2, 2");

		JLabel serverPortLabel = new JLabel("Port:");
		serverPortLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(serverPortLabel, "4, 2");

		serverIPtxtField = new JTextField();
		panel.add(serverIPtxtField, "2, 4, fill, default");
		serverIPtxtField.setColumns(10);

		serverPorttxtField = new JTextField();
		panel.add(serverPorttxtField, "4, 4, left, default");
		serverPorttxtField.setColumns(10);
		
		JLabel nicknameLabel = new JLabel("Nickname:");
		nicknameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(nicknameLabel, "2, 6");

		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(passwordLabel, "4, 6");
		
		nickNametxtField = new JTextField();
		nickNametxtField.setColumns(10);
		panel.add(nickNametxtField, "2, 8, fill, default");

		passwordField = new JPasswordField();
		panel.add(passwordField, "4, 8, fill, default");
		
		connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(connectButton, "2, 10, fill, default");
		
		exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(exitButton, "4, 10, fill, default");
		
		humanPlayerRdb = new JRadioButton("Human Player");
		panel.add(humanPlayerRdb, "6, 4");
		humanPlayerRdb.setSelected(true);

		naiveComputerPlayerRdb = new JRadioButton("Naive Computer");
		panel.add(naiveComputerPlayerRdb, "6, 6");

		smartComputerPlayerRdb = new JRadioButton("Smart Computer");
		panel.add(smartComputerPlayerRdb, "6, 8");
		
		ButtonGroup group = new ButtonGroup();
		group.add(humanPlayerRdb);
		group.add(naiveComputerPlayerRdb);
		group.add(smartComputerPlayerRdb);
		
		connectingLabel = new JLabel("Connecting to server");
		panel.add(connectingLabel, "6, 10");
		connectingLabel.setVisible(false);
		
        connectButton.addActionListener(this.loginController);
        exitButton.addActionListener(this.loginController);
	}
	
    /**
     * Method to return the JTextField containing the server name/IP.
     * @return JTextField - textfield that should contain the server name/IP
     */
    public JTextField getServerField() {
        return serverIPtxtField;
    }
    
    /**
     * Method to return the JTextField containing the port number.
     * @return JTextField - textfield that should contain the port number
     */
    public JTextField getPortField() {
        return serverPorttxtField;
    }
    
    /**
     * Method to return the JTextField containing the username.
     * @return JTextField - textfield that should contain the username
     */
    public JTextField getUsernameField() {
        return nickNametxtField;
    }
    
    /**
     * Method to return the JTextField containing the password.
     * @return JTextField - textfield that should contain the password
     */
    public JTextField getPasswordField() {
        return passwordField;
    }
    
    /**
     * Method to return the exit button.
     * @return JButton - "Exit" button
     */
    public JButton getExitButton() {
        return exitButton;
    }
    
    /**
     * Method to return the connect button.
     * @return JButton - "Connect" button
     */
    public JButton getConnectButton() {
        return connectButton;
    }

    /**
     * Method to return the HumanPlayer radio button.
     * @return JRadioButton - "Human Player" radio button
     */
    public JRadioButton getHumanPlayerRdButton() {
        return humanPlayerRdb;
    }

    /**
     * Method to return the NaiveComputerPLayer radio button.
     * @return JRadioButton - "Naive Computer" radio button
     */
    public JRadioButton getNaiveComputerPlayerRdButton() {
        return naiveComputerPlayerRdb;
    }

    /**
     * Method to return the SmartComputerPLayer radio button.
     * @return JRadioButton - "Smart Computer" radio button
     */
    public JRadioButton getSmartComputerPlayerRdButton() {
        return smartComputerPlayerRdb;
    }
    
    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JLabel getConnectingLabel() {
        return connectingLabel;
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
