package ss.week5;

import java.util.ArrayList;
import java.util.Random;

import ss.week5.Board;
import ss.week5.Mark;
import ss.week5.Strategy;

public class NaiveStrategy implements Strategy{

	public String strategyName = "Naive";
		
	public String getName() {
		return this.strategyName;
	}

	public int determineMove(Board b, Mark m) {
		
		ArrayList<Integer> emptyFields = new ArrayList<Integer>();
		for(int i = 0; i < 9; i++){
			if(b.isEmptyField(i)){
				emptyFields.add(i);
			}
		}
	    int randomIndex = new Random().nextInt(emptyFields.size());
		int randomField = emptyFields.get(randomIndex);
		return randomField;
	}
}
