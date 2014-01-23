package ss.week5;

import ss.week5.Strategy;

import java.util.ArrayList;
import java.util.Random;

public class SmartStrategy implements Strategy{

	public String strategyName = "Smart";
	
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
		
		if(emptyFields.contains(4)){
			return 4;
		}
		
		int winningMove = checkForWinningMove(b,m);
		if(winningMove != -1){
			return winningMove;
		}
		
		int winningMoveOponent = checkForWinningMove(b,m.other());
		if(winningMoveOponent != -1){
			return winningMoveOponent;
		}
		
		int randomIndex = new Random().nextInt(emptyFields.size());
		int randomField = emptyFields.get(randomIndex);
		return randomField;
	}
	
	public int checkForWinningMove(Board b, Mark m){
		
		//Horizontal
		for (int i = 0; i<9; i = i+3){
			if (b.getField(i) == m && b.getField(i+1) == m && b.isEmptyField(i+2)){ return i+2;}
			if (b.getField(i+1) == m && b.getField(i+2) == m && b.isEmptyField(i)){ return i;}
			if (b.getField(i+2) == m && b.getField(i) == m && b.isEmptyField(i+1)){ return i+1;}
		}
		
		//Vertical
		for (int i = 0; i<3; i++){
			if (b.getField(i) == m && b.getField(i+3) == m && b.isEmptyField(i+6)){ return i+6;}
			if (b.getField(i+3) == m && b.getField(i+6) == m && b.isEmptyField(i)){ return i;}
			if (b.getField(i+6) == m && b.getField(i) == m && b.isEmptyField(i+3)){ return i+3;}
		}
		
		//Diagonal
			if (b.getField(0) == m && b.getField(4) == m && b.isEmptyField(8)){ return 8;}
			if (b.getField(4) == m && b.getField(8) == m && b.isEmptyField(0)){ return 0;}
			if (b.getField(8) == m && b.getField(0) == m && b.isEmptyField(4)){ return 4;}

			if (b.getField(2) == m && b.getField(4) == m && b.isEmptyField(6)){ return 6;}
			if (b.getField(4) == m && b.getField(6) == m && b.isEmptyField(2)){ return 2;}
			if (b.getField(6) == m && b.getField(2) == m && b.isEmptyField(4)){ return 4;}

		
		return -1;
	}

}
