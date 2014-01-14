package ss.week5;

import java.util.ArrayList;
import java.util.Random;

public class PerfectStrategy implements Strategy{

	public String strategyName = "Perfect";
	private enum MoveType {Losing, Neutral, Winning}
	private MoveType currentMoveType = null;
	private MoveType moveType = null;
	private int bestMove = -1;
	
	public String getName() {
		return this.strategyName;
	}

	public int determineMove(Board b, Mark m) {
		currentMoveType = null;
		bestMove = -1;
		ArrayList<Integer> emptyFields = new ArrayList<Integer>();
		for(int i = 0; i < 9; i++){
			if(b.isEmptyField(i)){
				emptyFields.add(i);
			}
		}	
		//System.out.println(emptyFields);
		if(emptyFields.contains(4)){
			return 4;
		}
		System.out.println(bestMove+" "+currentMoveType+" "+moveType);
		int winningMove = checkForWinningMove(b,m);
		if(winningMove != -1){
			determineMoveType(b, m, winningMove);
			if(checkMoveType()){
				this.bestMove = winningMove;
				System.out.println("winningMove = "+bestMove);
			}
		}
		System.out.println(bestMove+" "+currentMoveType+" "+moveType);
		
		int winningMoveOponent = checkForWinningMove(b,m.other());
		if(winningMoveOponent != -1){
			determineMoveType(b, m, winningMoveOponent);
			if(checkMoveType()){
				this.bestMove = winningMoveOponent;
				System.out.println("winningMoveOponent = "+bestMove);
			}
		}
		System.out.println(bestMove+" "+currentMoveType+" "+moveType);
		
		if(checkForOppositeCorners(b, m)){
			ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
			for(int i = 1; i < 9; i = i + 2){
				if(b.isEmptyField(i)){
					possibleMoves.add(i);
				}
			}
			int randomIndex = new Random().nextInt(possibleMoves.size());
			int randomField = possibleMoves.get(randomIndex);
			determineMoveType(b, m, randomField);
			if(checkMoveType()){
				this.bestMove = randomField;
				System.out.println("random without corners = "+bestMove);
			}
		}
		System.out.println(bestMove+" "+currentMoveType+" "+moveType);
		
		if(!checkForOppositeCorners(b, m) && !emptyCorners(b).isEmpty()){
			int randomIndex = new Random().nextInt(emptyCorners(b).size());
			int randomField = emptyCorners(b).get(randomIndex);
			determineMoveType(b, m, randomField);
			if(checkMoveType()){
				System.out.println(checkMoveType());
				this.bestMove = randomField;
				System.out.println(randomIndex +" "+emptyFields.get(randomIndex)+ " "+ emptyCorners(b)+"random corner= "+bestMove);
			}
			System.out.println(bestMove+" "+currentMoveType+" "+moveType);
		}
		else{
			int randomIndex = new Random().nextInt(emptyFields.size());
			int randomField = emptyFields.get(randomIndex);
			determineMoveType(b, m, randomField);
			if(checkMoveType()){
				this.bestMove = randomField;
				System.out.println("random = "+bestMove);
			}
			System.out.println(bestMove+" "+currentMoveType+" "+moveType);
		}
		return bestMove;
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
			//System.out.println("-1");
		return -1;
	}
	public boolean checkForOppositeCorners(Board b, Mark m){
		Mark o = m.other();
		if (b.getField(4) == m){
			if (b.getField(0) == o && b.getField(8) == o){return true;}
			if (b.getField(2) == o && b.getField(6) == o){return true;}
		}
		return false;
	}
	
	public ArrayList<Integer> emptyCorners(Board b){
		ArrayList<Integer> empty = new ArrayList<Integer>();
		if(b.isEmptyField(0)){empty.add(0);}
		if(b.isEmptyField(2)){empty.add(2);}
		if(b.isEmptyField(6)){empty.add(6);}
		if(b.isEmptyField(8)){empty.add(8);}
		return empty;
	}

	private void determineMoveType(Board b, Mark m, int field) {
		Board tb = b.deepCopy();
		tb.setField(field, m);
		if(tb.hasWinner() && tb.isWinner(m)){
			this.moveType = MoveType.Winning;
		}else{
			tb.setField(field, m.other());
			if(tb.hasWinner() && tb.isWinner(m.other())){
				this.moveType = MoveType.Losing;
			}
			else{
				this.moveType = MoveType.Neutral;
			}
		}
	}
	
	private boolean checkMoveType(){
		//System.out.println(currentMoveType+" "+moveType);
		if(currentMoveType == null){
			currentMoveType = moveType;
			return true;
		}
		else if(currentMoveType == moveType){
			Random random = new Random();
		    return random.nextBoolean(); 
		}
		else if(currentMoveType == MoveType.Neutral && moveType != MoveType.Neutral){
			currentMoveType = moveType;
			return true;
		}
		else if(currentMoveType == MoveType.Losing && moveType == MoveType.Winning){
			currentMoveType = moveType;
			return true;
		}
		return false;
	}
	
}
