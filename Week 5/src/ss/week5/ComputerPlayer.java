package ss.week5;

import ss.week5.Board;
import ss.week5.Mark;
import ss.week5.Player;

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
