package ss.week6.vote;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.*;

@SuppressWarnings({"serial", "unchecked", "rawtypes"})
public class VoteFrame3 extends JFrame{

	private JLabel choice, voted;
	private JButton accept;
	private JComboBox voteOption;
	private String[] voteOptions= {"Choose a party", "One","Two","Three","Four"};
	
	
	public VoteFrame3() {
	    super("Vote");
	    init();
	}
	
	private void init() {
	    Container c = getContentPane();
	    c.setLayout(new FlowLayout());
	    ItemListener pCL = new PartyChoiceListener(voted, accept, voteOption, voteOptions);
	    ActionListener oBL = new OkButtonListener(voted, accept, voteOption);
	    
	    choice = new JLabel("Make your choice");
	    voted = new JLabel("");
	    voteOption = new JComboBox(voteOptions);
	    accept = new JButton("OK");
	    
	    accept.addActionListener(oBL); // object listens to 
	    voteOption.addItemListener(pCL); // object listens to 
	    accept.setEnabled(false);
	    
	    c.add(choice);
	    c.add(voteOption);  // add buttons
	    c.add(voted);
	    c.add(accept);
	    
	    setSize(400,100);
	    setVisible(true);
	}

	private static class PartyChoiceListener implements ItemListener{
	
		private JLabel voted;
		private JButton accept;
		private JComboBox voteOption;
		private String[] voteOptions;
		
		PartyChoiceListener(JLabel voted, JButton accept, JComboBox voteOption, String[] voteOptions){
			this.voted = voted;
			this.accept = accept;
			this.voteOption = voteOption;
			this.voteOptions = voteOptions;
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
	
	private static class OkButtonListener implements ActionListener{
	
		private JLabel voted;
		private JButton accept;
		private JComboBox voteOption;
		
		OkButtonListener(JLabel voted, JButton accept, JComboBox voteOption){
			this.voted = voted;
			this.accept = accept;
			this.voteOption = voteOption;
		}
		
		public void actionPerformed(ActionEvent ev) {
			if (ev.getSource() == accept) {
				  voted.setText("");
				  accept.setEnabled(false);
				  voteOption.setSelectedIndex(0);
			}
		}
	}
	
	public static void main (String [] args) {
		new VoteFrame();
	}
}
