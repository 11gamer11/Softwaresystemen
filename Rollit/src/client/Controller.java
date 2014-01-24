package client;

import game.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Controller implements ActionListener {
	
	public JButton[] buttons;
	public JButton again;
	public JLabel label;
	private Game game;
	private int dim;
	private View view;
	
	public Controller(View newView, Game newGame, JButton[] buttonList, JButton againButton) {
		this.game = newGame;
		this.view = newView;
		this.buttons = buttonList;
		this.again = againButton;
		this.dim = game.getBoard().DIM;
		
		for (int i = 0; i < dim * dim; i++) {
			buttons[i].addActionListener(this);
		}
		again.addActionListener(this);
	}

	public void actionPerformed(ActionEvent input) {
		for (int i = 0; i < dim * dim; i++) {
			if ((input.getSource() == buttons[i]) && game.getBoard().isEmptyField(i)) {
				game.takeTurn(i);
			}
		}
		if (input.getSource() == again) {
			game.reset();
			/*for(int i = 0; i < dim*dim; i++){
				buttons[i].setText("Empty");
				buttons[i].setEnabled(true);
				//label.setText("Turn: X");
			}*/
			again.setEnabled(false);
		}
		view.update(game, null);
	}
}