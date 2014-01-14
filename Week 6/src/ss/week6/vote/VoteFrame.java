package ss.week6.vote;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class VoteFrame extends JFrame implements ActionListener, ItemListener{

	 private JButton vote1, vote2, vote3, accept;
	 private JLabel choice, voted;
	
	public VoteFrame() {
	    super("Vote");
	    init();
	}
	
	private void init() {
	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());
	    choice = new JLabel("Choose a party\n");
	    voted = new JLabel("");
	    vote1 = new JButton("ONE");
	    vote2 = new JButton("TWO");
	    vote3 = new JButton("THREE");
	    accept = new JButton("OK");
	    vote1.addActionListener(this); // object listens to 
	    vote2.addActionListener(this); // object listens to 
	    vote3.addActionListener(this); // object listens to 
	    accept.addActionListener(this); // object listens to 
	    accept.setEnabled(false);
	    c.add(choice);
	    c.add(vote1); c.add(vote2); c.add(vote3);  // add buttons
	    c.add(voted);
	    c.add(accept);
	    setSize(400,100); setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == accept) {
			  voted.setText("");
			  accept.setEnabled(false);
		}else if (ev.getSource() == vote1) {
		    voted.setText("You have chosen 1. Change selection or press OK");
		    accept.setEnabled(true);
		}else if (ev.getSource() == vote2) {
			  voted.setText("You have chosen 2. Change selection or press OK");
			  accept.setEnabled(true);
		}else if (ev.getSource() == vote3) {
			  voted.setText("You have chosen 3. Change selection or press OK");
			  accept.setEnabled(true);
		}
	}

    public static void main (String [] args) {
    	new VoteFrame();
    }

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
