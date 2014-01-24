package game;

import java.util.Observable;
import java.util.Scanner;

import board.Board;
import board.Mark;

@SuppressWarnings("resource")
public class Game extends Observable {

    // -- Instance variables -----------------------------------------

    /*@
       private invariant board != null;
     */
    /**
     * The board.
     */
    private Board board;

    /*@
       private invariant players.length == NUMBER_PLAYERS;
       private (\forall int i; 0 <= i && i < NUMBER_PLAYERS; players[i] != null); 
     */
    /**
     * The 2 players of the game.
     */
    public Player[] players;

    /*@
       private invariant 0 <= current  && current < NUMBER_PLAYERS;
     */
    /**
     * Index of the current player.
     */
    private int current;

    // -- Constructors -----------------------------------------------

    /*@
      requires s0 != null;
      requires s1 != null;
     */
    /**
     * Creates a new Game object.
     * 
     * @param s0
     *            the first player
     * @param s1
     *            the second player
     * @param s4 
     * @param s3 
     */
    public Game(Player[] playerList) {
        this.board = new Board();
        this.players = playerList;
        this.current = 0;
    }

    // -- Commands ---------------------------------------------------

    /**
     * Starts the Tic Tac Toe game. <br>
     * Asks after each ended game if the user want to continue. Continues until
     * the user does not want to play anymore.
     */
    public void start() {
        boolean doorgaan = true;
        while (doorgaan) {
            reset();
            doorgaan = readBoolean("\n> Start? (y/n)?", "y", "n");
            play();
            doorgaan = readBoolean("\n> Play another time? (y/n)?", "y", "n");
        }
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
        current = 0;
        board.reset();
    }

    // B{Spel.play}
    /**
     * Plays the Tic Tac Toe game. <br>
     * First the (still empty) board is shown. Then the game is played until it
     * is over. Players can make a move one after the other. After each move,
     * the changed game situation is printed.
     */
    private void play() {
        // E{Spel}
        update();
        while (!board.gameOver()) {
            players[current].makeMove(board);
            update();
        }
        printResult();
        // B{Spel}
        // I{Spel} // [BODY-NOG-TOE-TE-VOEGEN]
    }

    // E{Spel.play}

    /**
     * Prints the game situation.
     */
    private void update() {
        System.out.println("\ncurrent game situation: \n\n" + board.toString()
                + "\n");
    }

    /*@
       requires this.board.gameOver();
     */

    /**
     * Prints the result of the last game. <br>
     */
    private void printResult() {
        if (board.winner() != null) {
        	Player winner = null;
        	for (int i = 0; i < players.length; i++) {
        		if (players[i].getMark().equals(board.winner())) {
        			winner = players[i];
        		}
        	}
            System.out.println("Speler " + winner.getName() + " ("
                    + winner.getMark().toString() + ") has won!");
        } else {
            System.out.println("Draw. There is no winner!");
        }
    }

	public Board getBoard() {
		return board;
	}

	public void takeTurn(int i) {
		board.setField(i, players[current].getMark());
		board.setBeatenFields(i, players[current].getMark());
        current = (current + 1) % players.length;
        setChanged();
        notifyObservers();
	}

	public Mark getCurrentMark() {
		return players[current].getMark();
	}
	
	public Player getCurrentPlayer() {
		return players[current];
	}
	
	public Player getPlayer(Mark mark) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getMark().equals(mark)) {
				return players[i];
			}
		}
		return null;
	}
}
