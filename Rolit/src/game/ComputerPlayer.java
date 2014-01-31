package game;

import strategy.NaiveStrategy;
import strategy.Strategy;
import board.Board;
import board.Mark;

public class ComputerPlayer extends Player {
	
	Mark mark;
	Strategy strategy;
	
	public ComputerPlayer(Mark markPlayer, Strategy strategyPlayer) {
		super(strategyPlayer.getName(), markPlayer);
		this.mark = markPlayer;
		this.strategy = strategyPlayer;
	}
	
	public ComputerPlayer(Mark markPlayer) {
		super("Naive-" + markPlayer.name(), markPlayer);
		this.mark = markPlayer;
		this.strategy = new NaiveStrategy();
	}

	public int determineMove(Board board) {
		return this.strategy.determineMove(board, this.mark);
	}

}
