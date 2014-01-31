package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JList;

public class LobbyGUI extends JFrame {

	private JPanel contentPane;
	private JTextField chattxtField;
	private JList playerList;
	private JList chatList;
	private JList gamesList;
	private JButton btnSend;
	private JButton btnCreate;
	private JButton btnJoin;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LobbyGUI frame = new LobbyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LobbyGUI() {
		setPreferredSize(new Dimension(600, 400));
		setMinimumSize(new Dimension(500, 300));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 556, 394);
		contentPane = new JPanel();
		contentPane.setMinimumSize(new Dimension(250, 200));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblLobby = new JLabel("Lobby");
		lblLobby.setHorizontalAlignment(SwingConstants.CENTER);
		lblLobby.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(lblLobby, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(100dlu;default):grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("15dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("bottom:15dlu"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20dlu"),}));
		
		JLabel lblPlayersOnline = new JLabel("Players Online");
		panel.add(lblPlayersOnline, "2, 2, 3, 1");
		
		JLabel lblChat = new JLabel("Chat");
		panel.add(lblChat, "6, 2");
		
		playerList = new JList();
		panel.add(playerList, "2, 4, 3, 3, fill, fill");
		
		chatList = new JList();
		panel.add(chatList, "6, 4, 1, 7, fill, fill");
		
		JLabel lblGames = new JLabel("Games");
		panel.add(lblGames, "2, 8, 3, 1");
		gamesList = new JList();
		panel.add(gamesList, "2, 10, 3, 3, fill, fill");
		
		chattxtField = new JTextField();
		panel.add(chattxtField, "6, 12, fill, fill");
		chattxtField.setColumns(10);

		btnSend = new JButton("Send");
		panel.add(btnSend, "6, 14");
		
		btnCreate = new JButton("Create Game");
		panel.add(btnCreate, "2, 14");
		
		btnJoin = new JButton("Join game");
		panel.add(btnJoin, "4, 14");
		
	}
    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JTextField getChatInputtxtField() {
        return chattxtField;
    }
    
    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JList getPlayerListField() {
        return playerList;
    }

    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JList getChatListField() {
        return chatList;
    }

    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JList getGamesListField() {
        return gamesList;
    }

    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JButton getSendButton() {
        return btnSend;
    }
    
    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JButton getCreateButton() {
        return btnCreate;
    }

    /**
     * Method to return the connecting to server label.
     * @return JLabel - connecting to server label
     */
    public JButton getJoinButton() {
        return btnJoin;
    }

}
