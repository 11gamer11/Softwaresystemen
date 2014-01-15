package ss.week6;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class JFrameDemo extends JFrame {
	private JButton bb; 
	private JLabel  lb; 
	
	public JFrameDemo() {
		super("JFrameDemo");
		init();
	}
	
	private void init() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		lb = new JLabel("cool GUI");
		bb = new JButton("Press me");
		c.add(bb); c.add(lb);
		setSize(300,200); setVisible(true);
	}
	
	static public void main(String[] args) {
		new JFrameDemo(); 
	}
}
