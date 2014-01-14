package ss.week6;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ListenerDemo4 extends JFrame 
                       implements ActionListener { 
  private JButton b1, b2;
    private JLabel lb;
  
public ListenerDemo4() {
    super("JFrameDemo");
    init();
}

  private void init() {
    Container c = getContentPane();
    c.setLayout(new FlowLayout());
    b1 = new JButton("ONE");
    b2 = new JButton("TWO");
    lb = new JLabel("");
    b1.addActionListener(this); // object listens to 
    b2.addActionListener(this); // both buttons
    c.add(b1); c.add(b2);       // add buttons
    c.add(lb);
    setSize(300,200); setVisible(true);
}

// method called when button is pressed
public void actionPerformed(ActionEvent ev) {
  if (ev.getSource() == b1) { // b1 is pressed
    lb.setText("ONE is pressed");
  } else 
  if (ev.getSource() == b2) { // b2 werd gedrukt
    lb.setText(
          "TWO is pressed");
    }
}

    public static void main (String [] args) {
	new ListenerDemo4();
    }
}