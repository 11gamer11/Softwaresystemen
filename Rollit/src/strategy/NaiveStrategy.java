package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import strategy.Strategy;
import board.Board;
import board.Mark;

public class NaiveStrategy implements Strategy{

	public String strategyName = "Naive";
		
	public String getName() {
		return this.strategyName;
	}

	public int determineMove(Board b, Mark m) {
		List<Integer> allowedFields = b.allAllowedFields(m);
	    int randomIndex = new Random().nextInt(allowedFields.size());
		int randomField = allowedFields.get(randomIndex);
		return randomField;
	}
}
