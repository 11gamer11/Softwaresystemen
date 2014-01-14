package ss.week5.leaderboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Leaderboard<Score extends Comparable<Score>, Player> {
	
	public List <Scores<Score, Player>> scoreDB = new ArrayList<Scores<Score, Player>>();
	
	public void addScore(Score score, Player name){
		Calendar date = Calendar.getInstance();
		this.scoreDB.add(new Scores<Score, Player>(score, name, date));
	}
	
	public List <Scores<Score, Player>> getScoresOfPlayer(Player player, int amount){
		Iterator<Scores<Score, Player>> iter = scoreDB.iterator();
		List<Scores<Score, Player>> playerScores = new ArrayList<Scores<Score, Player>>();
		
		while(iter.hasNext()){
			Scores<Score, Player> score = iter.next();
			if(score.getPlayer() == player){
				playerScores.add(score);
			}
		}
		playerScores = sortScoresByScore(playerScores);
		Collections.reverse(playerScores);
		if(playerScores.size() < amount){
			amount = playerScores.size();
		}
		return playerScores.subList(0, amount);
	}
	
	public List <Scores<Score, Player>> sortScoresByScore(List <Scores<Score, Player>> list){
		List <Scores<Score, Player>> tempDB = new ArrayList<Scores<Score, Player>>(list);
		Collections.sort(tempDB);
		return tempDB;
	}
	
	public List <Scores<Score, Player>> getTopScores(int amount){
		List<Scores<Score, Player>> Scores = sortScoresByScore(this.scoreDB);
		Collections.reverse(Scores);
		if(Scores.size() < amount){
			amount = Scores.size();
		}
		return Scores.subList(0, amount);
	}
	
	
	public List<Scores<Score, Player>> getScoresOfDay(Calendar date, boolean Sorted){
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String compareDate = dateFormat.format(date.getTime());
		List<Scores<Score, Player>> dayScores = new ArrayList<Scores<Score, Player>>();
		Iterator<Scores<Score, Player>> iter = scoreDB.iterator();
		
		while(iter.hasNext()){
			Scores<Score, Player> score = iter.next();
			String scoreDate = dateFormat.format(score.getDate().getTime());

			if(scoreDate.equals(compareDate)){
				dayScores.add(score);
			}
		}
		if(Sorted){
			dayScores = sortScoresByScore(dayScores);
			Collections.reverse(dayScores);
		}
		return dayScores;
	}

	
	public static void printList(List<?> list){
		for(int i=0; i < list.size(); i++) {
		    System.out.print(list.get(i));
		}
	}

	public static void main(String[] args){
		Leaderboard<Integer, String> b = new Leaderboard<Integer, String>();
		b.addScore(1337, "Kevin Alberts");
		b.addScore(42, "Sophie Lathouwers");
		b.addScore(12, "The Doctor");
		b.addScore(200, "The Doctor");
		b.addScore(20, "Maryse te Brinke");
		b.addScore(-5, "Derpy Hooves");
		b.addScore(1701, "Jeroen Waals");
		b.addScore(240, "The Doctor");
		b.addScore(11, "The Doctor");
		System.out.println("Top Scores:");
		printList(b.getTopScores(3));
		System.out.println("\nTop Scores The Doctor:");
		printList(b.getScoresOfPlayer("The Doctor", 4));
		System.out.println("\nTodays Scores (Sorted by Time):");
		
		Calendar someCalendar1 = Calendar.getInstance(); // current date/time
		someCalendar1.add(Calendar.HOUR, +1);
		printList(b.getScoresOfDay(someCalendar1, false));
		System.out.println("\nTodays Scores (Sorted by Score):");
		printList(b.getScoresOfDay(someCalendar1, true));
	}
}
