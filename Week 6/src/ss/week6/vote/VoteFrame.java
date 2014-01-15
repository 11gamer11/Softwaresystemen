package ss.week6.vote;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

@SuppressWarnings({"serial", "unchecked", "rawtypes"})
public class VoteFrame extends JFrame implements ActionListener, ItemListener{

	private JLabel choice, voted;
	private JButton accept;
	private JComboBox voteOption;
	private String[] voteOptions= {"Choose a party", "One","Two","Three","Four"};
	
	
	public VoteFrame() {
	    super("Vote");
	    init();
	}
	
	private void init() {
	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());
	    
	    choice = new JLabel("Make your choice");
	    voteOption = new JComboBox(voteOptions);
	    voted = new JLabel("");
	    accept = new JButton("OK");
	    
	    accept.addActionListener(this); // object listens to 
	    voteOption.addItemListener(this); // object listens to 
	    accept.setEnabled(false);
	    
	    c.add(choice);
	    c.add(voteOption);  // add buttons
	    c.add(voted);
	    c.add(accept);
	    
	    setSize(400,100);
	    setVisible(true);
	}

	public void actionPerformed(ActionEvent ev) {
		
		if (ev.getSource() == accept) {
			  voted.setText("");
			  accept.setEnabled(false);
			  voteOption.setSelectedIndex(0);
		}
	}

    public static void main (String [] args) {
    	new VoteFrame();
    }

	public void itemStateChanged(ItemEvent e) {
		int option = voteOption.getSelectedIndex();
		if(option == 0){
			  voted.setText("");
			  accept.setEnabled(false);
		}else{
		    voted.setText("You have chosen "+voteOptions[option]+". Change selection or press OK");
		    accept.setEnabled(true);
		}
	}
	
}
