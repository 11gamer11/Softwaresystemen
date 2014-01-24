package strategy;

import java.util.List;
import java.util.Random;

import board.Board;
import board.Mark;

public class SmartStrategy implements Strategy {

	public String strategyName = "Smart";
	
	public String getName() {
		return this.strategyName;
	}

	public int determineMove(Board b, Mark m) {
		
		List<Integer> allowedFields = b.allAllowedFields(m);
	    int randomIndex = new Random().nextInt(allowedFields.size());
		int randomField = allowedFields.get(randomIndex);
		int dim = b.DIM;
		if (allowedFields.contains(0)) {
			randomField = 0;
		} else if (allowedFields.contains(dim - 1)) {
			randomField = dim - 1;
		} else if (allowedFields.contains((dim * dim) - 1)) {
			randomField = (dim * dim) - 1;
		} else if (allowedFields.contains(dim * (dim - 1))) {
			randomField = dim * (dim - 1);
		}
		return randomField;
	}
}
