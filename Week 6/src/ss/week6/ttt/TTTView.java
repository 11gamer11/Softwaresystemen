package ss.week6.ttt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
 
// An AWT GUI program inherits the top-level container java.awt.Frame
@SuppressWarnings("serial")
public class TTTView extends Frame implements Observer{
	
	public JButton[] buttons;
	public JButton again;
	public JLabel label;
	public JFrame frame;
	public  GridLayout layout;
	
	public void update(Observable observer, Object object) {
		Game game = (Game) observer;
		Board board = game.getBoard();
		
		if(board.gameOver()){
			again.setEnabled(true);
			if(board.hasWinner()){
				if(board.isWinner(Mark.XX)){
					label.setText("XX has won the game");
				}else{
					label.setText("OO has won the game");
				}
				for(int i = 0; i < 9; i++){
						buttons[i].setEnabled(false);
				}
			}else{
				label.setText("Draw");
			}
		}else{
			for(int i = 0; i < 9; i++){
				if(!board.isEmptyField(i)){
					buttons[i].setEnabled(false);
				}else{
					buttons[i].setEnabled(true);
				}	
			}
			if(game.getCurrent().equals(Mark.XX)){
				label.setText("Turn: X");
			}else{
				label.setText("Turn: O");
			}
		}
	}	

	public TTTView(Game game){
		frame = new JFrame();
		layout = new GridLayout(4,3);
		
		buttons = new JButton[9];
		for(int i = 0; i < 9; i++){
			buttons[i] = new JButton();
			buttons[i].setText("Empty");
			frame.add(buttons[i]);
			buttons[i].setVisible(true);
		}
		again = new JButton();
		again.setText("Again?");
		frame.add(again);
		again.setEnabled(false);
		
		label = new JLabel();
		frame.add(label);
		label.setText("Turn: X");
		
		frame.setVisible(true);
		frame.setLayout(layout);
		frame.setSize(250, 250);
		
		new TTTController(buttons, again, game);
		
		game.addObserver(this);
	}
	
	private class TTTController implements ActionListener{
		private Game game;

		public TTTController(JButton[] buttons, JButton again, Game game) {
			this.game = game;
			
			for(int i=0; i < 9; i++){
				buttons[i].addActionListener(this);
			}
			again.addActionListener(this);
		}

		public void actionPerformed(ActionEvent input) {
			for(int i = 0; i < 9; i++){
				if((input.getSource() == buttons[i]) && game.getBoard().isEmptyField(i)){
					game.takeTurn(i);
					buttons[i].setText(game.getCurrent().other().toString());
					buttons[i].setEnabled(false);
				}
			}
			if(input.getSource() == again){
				game.reset();
				for(int i = 0; i < 9; i++){
					buttons[i].setText("Empty");
					buttons[i].setEnabled(true);
					label.setText("Turn: X");
				}
				again.setEnabled(false);
			}
		}
	}
	
	
	
 
   /** The entry main() method */
   public static void main(String[] args) {
	   Game game = new Game();
	   new TTTView(game);
   }


}