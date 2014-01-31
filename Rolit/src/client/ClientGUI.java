package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;

public class ClientGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
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
	public ClientGUI() {
		setMinimumSize(new Dimension(500, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblGame = new JLabel("Game");
		contentPane.add(lblGame, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(150dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel, "2, 2");
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1, "4, 2, left, default");
		
		JPanel panel_1 = new JPanel();
		panel_1.setMinimumSize(new Dimension(350, 350));
		panel.add(panel_1, "2, 4, fill, fill");
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("1dlu"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("1dlu"),
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton button = new JButton("1");
		button.setMaximumSize(new Dimension(80, 80));
		button.setMinimumSize(new Dimension(20, 20));
		button.setPreferredSize(new Dimension(40, 40));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_1.add(button, "2, 2");
		
		JButton button_1 = new JButton("1");
		button_1.setPreferredSize(new Dimension(40, 40));
		button_1.setMinimumSize(new Dimension(20, 20));
		button_1.setMaximumSize(new Dimension(80, 80));
		button_1.setAlignmentX(0.5f);
		panel_1.add(button_1, "4, 2");
		
		JButton button_2 = new JButton("1");
		button_2.setPreferredSize(new Dimension(40, 40));
		button_2.setMinimumSize(new Dimension(20, 20));
		button_2.setMaximumSize(new Dimension(80, 80));
		button_2.setAlignmentX(0.5f);
		panel_1.add(button_2, "6, 2");
		
		JButton button_3 = new JButton("1");
		button_3.setPreferredSize(new Dimension(40, 40));
		button_3.setMinimumSize(new Dimension(20, 20));
		button_3.setMaximumSize(new Dimension(80, 80));
		button_3.setAlignmentX(0.5f);
		panel_1.add(button_3, "8, 2");
		
		JButton button_4 = new JButton("1");
		button_4.setPreferredSize(new Dimension(40, 40));
		button_4.setMinimumSize(new Dimension(20, 20));
		button_4.setMaximumSize(new Dimension(80, 80));
		button_4.setAlignmentX(0.5f);
		panel_1.add(button_4, "2, 4");
		
		JButton button_5 = new JButton("1");
		button_5.setPreferredSize(new Dimension(40, 40));
		button_5.setMinimumSize(new Dimension(20, 20));
		button_5.setMaximumSize(new Dimension(80, 80));
		button_5.setAlignmentX(0.5f);
		panel_1.add(button_5, "2, 6");
		
		JButton button_6 = new JButton("1");
		button_6.setPreferredSize(new Dimension(40, 40));
		button_6.setMinimumSize(new Dimension(20, 20));
		button_6.setMaximumSize(new Dimension(80, 80));
		button_6.setAlignmentX(0.5f);
		panel_1.add(button_6, "2, 8");
	}

}
