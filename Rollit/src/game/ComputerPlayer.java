package game;

import strategy.NaiveStrategy;
import strategy.Strategy;
import board.Board;
import board.Mark;
import game.Player;

public class ComputerPlayer extends Player{
	
	Mark mark;
	Strategy strategy;
	
	public ComputerPlayer(Mark mark, Strategy strategy) {
		super(strategy.getName()+"-"+mark.name(), mark);
		this.mark = mark;
		this.strategy = strategy;
	}
	
	public ComputerPlayer(Mark mark) {
		super("Naive-"+mark.name(), mark);
		this.mark = mark;
		this.strategy = new NaiveStrategy();
	}

	public int determineMove(Board board) {
		return this.strategy.determineMove(board, this.mark);
	}

}
