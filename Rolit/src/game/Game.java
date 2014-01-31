package game;

import info.GameInformation;
import info.Logging;
import info.ServerInformation;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;
import java.util.ArrayList;

import strategy.NaiveStrategy;
import strategy.SmartStrategy;
import strategy.Strategy;
import board.Board;
import board.Mark;
import game.Player;

@SuppressWarnings("resource")
public class Game extends Observable implements Runnable {

	// -- Instance variables -----------------------------------------

	public GameInformation gameInfo = new GameInformation();

	public static final int STATUS_PREMATURE_END = 0;
	public static final int STATUS_NOT_STARTED = 0;
	public static final int STATUS_STARTED = 0;
	public static final int PLAYER_COMPUTER = 0;
	public static final int PLAYER_HUMAN = 1;
	public static final String STRATEGY_NAIVE = "naive";
	public static final String STRATEGY_SMART = "smart";

	// -- Constructors -----------------------------------------------

	public Game(String owner, int kindOfPlayer, String strategy) {
		this.gameInfo.owner = owner;
		this.gameInfo.players = new ArrayList<Player>();
		this.addPlayer(owner, kindOfPlayer, strategy);
	}

	// -- Commands ---------------------------------------------------

	public void addPlayer(String player, int kindOfPlayer, String strategy) {
		int index = getNumPlayers();
		Mark mark = getMark(index);
		if (kindOfPlayer == PLAYER_HUMAN) {
			this.gameInfo.players.add(new HumanPlayer(player, mark));
		} else if (kindOfPlayer == PLAYER_COMPUTER) {
			if (strategy != null && strategy.toLowerCase().equals(STRATEGY_NAIVE)) {
				this.gameInfo.players.add(new ComputerPlayer(mark,
						new NaiveStrategy()));
			} else if (strategy != null && strategy.toLowerCase().equals(STRATEGY_SMART)) {
				this.gameInfo.players.add(new ComputerPlayer(mark,
						new SmartStrategy()));
			} else {
				this.gameInfo.players.add(new ComputerPlayer(mark));
			}
		} else {
			Logging.log(Logging.ERROR,
					"Sorry players of a unknown kind aren't allowed to play");
		}
	}

	public Mark getMark(int playerIndex) {
		return Mark.values()[playerIndex + 1];
	}
	
	/**
	 * Starts the Tic Tac Toe game. <br>
	 * Asks after each ended game if the user want to continue. Continues until
	 * the user does not want to play anymore.
	 */
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Prints a question which can be answered by yes (true) or no (false).
	 * After prompting the question on standard out, this method reads a String
	 * from standard in and compares it to the parameters for yes and no. If the
	 * user inputs a different value, the prompt is repeated and te method reads
	 * input again.
	 * 
	 * @parom prompt the question to print
	 * @param yes
	 *            the String corresponding to a yes answer
	 * @param no
	 *            the String corresponding to a no answer
	 * @return true is the yes answer is typed, false if the no answer is typed
	 */
	private boolean readBoolean(String prompt, String yes, String no) {
		String answer;
		do {
			System.out.print(prompt);
			Scanner in = new Scanner(System.in);
			answer = in.hasNextLine() ? in.nextLine() : null;
		} while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
		return answer.equals(yes);
	}

	/**
	 * Resets the game. <br>
	 * The board is emptied and player[0] becomes the current player.
	 */
	public void reset() {
		this.gameInfo.current = 0;
		this.getBoard().reset();
	}

	// B{Spel.play}
	/**
	 * Plays the Tic Tac Toe game. <br>
	 * First the (still empty) board is shown. Then the game is played until it
	 * is over. Players can make a move one after the other. After each move,
	 * the changed game situation is printed.
	 */
	private void play() {
		update();
		while (!this.getBoard().gameOver()) {
			this.getCurrentPlayer().makeMove(this.getBoard());
			update();
		}
		printResult();
	}

	// E{Spel.play}

	/**
	 * Prints the game situation.
	 */
	private void update() {
		System.out.println("\ncurrent game situation: \n\n"
				+ this.getBoard().toString() + "\n");
	}

	/*
	 * @ requires this.board.gameOver();
	 */

	/**
	 * Prints the result of the last game. <br>
	 */
	private void printResult() {
		if (this.getBoard().winner() != null) {
			Player winner = null;
			for (int i = 0; i < this.gameInfo.players.length; i++) {
				if (this.gameInfo.players[i].getMark().equals(
						this.getBoard().winner())) {
					winner = this.gameInfo.players[i];
				}
			}
			System.out.println("Speler " + winner.getName() + " ("
					+ winner.getMark().toString() + ") has won!");
		} else {
			System.out.println("Draw. There is no winner!");
		}
	}

	public Board getBoard() {
		return this.gameInfo.board;
	}

	public void takeTurn(int i) {
		this.getBoard().setField(i, this.getCurrentMark());
		this.getBoard().setBeatenFields(i, this.getCurrentMark());
		this.gameInfo.current = (this.gameInfo.current + 1)
				% this.gameInfo.players.length;
		setChanged();
		notifyObservers();
	}

	public Mark getCurrentMark() {
		return this.getCurrentPlayer().getMark();
	}

	public Player getCurrentPlayer() {
		return this.gameInfo.players.get(this.gameInfo.current);
	}

	public Player getPlayer(Mark mark) {
		for (int i = 0; i < this.gameInfo.players.length; i++) {
			if (this.gameInfo.players[i].getMark().equals(mark)) {
				return this.gameInfo.players[i];
			}
		}
		return null;
	}

	public int getNumPlayers() {
		return this.gameInfo.players.size();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Game game = new Game("test", 0);
		for(int i = 0; i < game.getNumPlayers(); i++){
			Player player = game.gameInfo.players.get(i);
			System.out.println("player: " + player.getName() + ", color of player: " + player.getMark());
		}
		System.out.println("\n");
		game.addPlayer("test 2", 0, null);
		for(int i = 0; i < game.getNumPlayers(); i++){
			Player player = game.gameInfo.players.get(i);
			System.out.println("player: " + player.getName() + ", color of player: " + player.getMark());
		}	
		System.out.println("\n");
		game.addPlayer("test 2", 1, null);
		for(int i = 0; i < game.getNumPlayers(); i++){
			Player player = game.gameInfo.players.get(i);
			System.out.println("player: " + player.getName() + ", color of player: " + player.getMark());
		}	
	}

	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	public void interrupt() {
		// TODO Auto-generated method stub
		
	}
}
