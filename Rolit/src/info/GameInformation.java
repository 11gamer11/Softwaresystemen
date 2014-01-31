package info;

import java.util.List;

import game.Player;
import board.Board;

public class GameInformation {
	public static final int STARTED = 2;
	public static final int QUEUE = 1;
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	
	public List<Player> players;
    public Board board;
	public int current;
    public String owner;
    public int status;
    public int score;
    public String[] winners;
    
    
	public boolean hasStarted() {
		return this.status == STARTED;
	}
	public boolean isFull() {
		return players.size() == MAX_PLAYERS;
	}

	public boolean isPlayable() {
		return players.size() >= MIN_PLAYERS && !isFull();
	}
	
	
}
