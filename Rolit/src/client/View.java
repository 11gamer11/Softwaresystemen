package client;

import game.ComputerPlayer;
import game.Game;
import game.HumanPlayer;
import game.Player;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import strategy.NaiveStrategy;
import strategy.SmartStrategy;
import board.Board;
import board.Mark;

@SuppressWarnings("serial")
public class View extends Frame implements Observer {

	private Game game;
	private Controller controller;
	private int dim;
	private JLabel label;
	
	public JButton[] buttons;
	public JButton again;
	public JFrame frame;
	public GridLayout layout;
	
	
	public View(Game newGame) {
		this.game = newGame;
		this.dim = Board.getDim();
		this.frame = new JFrame();
		this.layout = new GridLayout(dim + 1, dim);
		this.buttons = new JButton[dim * dim];
		
		for (int i = 0; i < dim * dim; i++) {
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
		label.setText("Turn: " + game.getCurrentMark().toString());
		
		frame.setVisible(true);
		frame.setLayout(layout);
		frame.setSize(500, 500);
		
		controller = new Controller(this, this.game, buttons, again);
		
		this.game.addObserver(this);
		update(game, null);
	}
	
	public void update(Observable observer, Object object) {
		Board board = game.getBoard();
		checkBoard();
		if (board.gameOver()) {
			again.setEnabled(true);
			Mark winner = board.winner();
			if (winner != null) {
				String nameWinner = game.getPlayer(winner).getName();
				label.setText(nameWinner + " with " + winner + "has won the game");
			} else {
				label.setText("Draw");
			}
		} else {
            if (this.game.getCurrentPlayer() instanceof HumanPlayer) {
            	List<Integer> fields = board.allAllowedFields(this.game.getCurrentMark());
                for (int index = 0; index < fields.size(); index++) {
                	buttons[fields.get(index)].setEnabled(true);
                }
	        }
	        /*if (this.game.getCurrentPlayer() instanceof ComputerPlayer){
				//TODO check computer players move
	        }*/
			label.setText("Turn: " + game.getCurrentMark().toString());
		}
	}
	
	public void checkBoard() {
		for (int index = 0; index < dim * dim; index++) {
			buttons[index].setEnabled(false);
			buttons[index].setText(game.getBoard().getField(index).toString());
		}
	}
	
	
    private static Player createPlayer(String s, Mark m) {
        if (s.equals("-N")) {
            return new ComputerPlayer(m, new NaiveStrategy());
        } else if (s.equals("-S")) {
            return new ComputerPlayer(m, new SmartStrategy());
        } else {
            return new HumanPlayer(s, m);
        }
    }
    
	// The entry main() method
	public static void main(String[] args) {
		System.out.println("Rolit");
		System.out.println("--------------------------");
		int numArgs = args.length;
		Player[] players = new Player[numArgs];
		
		Player s1 = createPlayer(args[0], Mark._RED__);
		Player s2 = createPlayer(args[1], Mark.GREEN_);
		players[0] = s1;
		players[1] = s2;
		System.out.println("Speler 1: " + s1.getName() + ", " + s1.getMark());
		System.out.println("Speler 2: " + s2.getName() + ", " + s2.getMark());
		if (numArgs > 2) {
			Player s3 = createPlayer(args[2], Mark.YELLOW);
		    players[2] = s3;
		   	System.out.println("Speler 3: " + s3.getName() + ", " + s3.getMark());
		}
		if (numArgs > 3) {
			Player s4 = createPlayer(args[3], Mark._BLUE_);
		    players[3] = s4;
		   	System.out.println("Speler 4: " + s4.getName() + ", " + s4.getMark());
		}
		        
		Game newGame = new Game(players);
		View view = new View(newGame);
		newGame.start();
		//Game game = new Game(null, null, null, null); 
	}
}
