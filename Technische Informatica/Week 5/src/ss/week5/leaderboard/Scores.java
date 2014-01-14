package ss.week5.leaderboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Scores<Score extends Comparable<Score>,Player> implements Comparable<Scores<Score,Player>>{

	private Calendar date;
	private Score score;
	private Player player;
	
	public Scores(Score score, Player player, Calendar date){
		this.score = score;
		this.player = player;
		this.date = date;
	}
	
	public Calendar getDate(){
		return this.date;
	}
	
	public Score getScore(){
		return this.score;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public int compareTo(Scores<Score, Player> o) {
		return this.score.compareTo(o.score);
	}

	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		String dateString = dateFormat.format(date.getTime());
		return String.format("%-5s%-15d%-5s%-25s%-5s%s%n","Score: ", this.score, "Player: ", this.player, "Date: ", dateString);
	}
	
}
