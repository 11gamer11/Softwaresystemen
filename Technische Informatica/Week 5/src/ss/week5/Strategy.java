package ss.week5;

public interface Strategy {
	
	/**
	 * Method to get the name of the strategy.
	 * @return String - Name of the strategy.
	 */
	public String getName();
	
	/**
	 * Method to determine the next best move according to the strategy.
	 * @param b - The current playing board.
	 * @param m - The mark of the player.
	 * @return int - the number of the square which is the best move.
	 */
	public int determineMove(Board b, Mark m);
}