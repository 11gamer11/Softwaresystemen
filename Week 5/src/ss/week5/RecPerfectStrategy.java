package ss.week5;

import ss.week5.Strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RecPerfectStrategy implements Strategy{
	
	public String strategyName = "Recursive Perfect";

	private final int WINNING = 1;
	private final int NEUTRAL = 0;
	private final int LOSING = -1;
	
	public String getName() {
		return this.strategyName;
	}

	public int determineMove(Board b, Mark m) {
		return bestMove(b,m)[0];
	}


	private int[] bestMove(Board b, Mark m) {
		
		int bestQual = -1;
		int bestMove = -1;
		
		List<Integer> emptyFields = new ArrayList<Integer>();
		
		for(int i = 0; i < 9; i++){
			if(b.isEmptyField(i)){
				emptyFields.add(i);
			}
		}
		
		Iterator<Integer> iteratorEmptyFields = emptyFields.iterator();
		
		while(iteratorEmptyFields.hasNext()){
			
			int field = iteratorEmptyFields.next();
			int qual = evaluateMove(b,m,field);
		
			Random random = new Random();
		    
			if(qual > bestQual || (qual == bestQual && random.nextBoolean())){
				bestQual = qual;
				bestMove = field;
			}
		}
		
		int[] result = new int[2];
		result[0] = bestMove;
		result[1] = bestQual;
		
		return result;
		
	}

	private int evaluateMove(Board b, Mark m, int field) {
		int result;
		Board tb = b.deepCopy();
		tb.setField(field, m);
		
		if(!tb.isWinner(m)){
			if(tb.isWinner(m.other())){
				result = LOSING;
			}else if(tb.isFull()){
				result = NEUTRAL;
			}else{
				int[] opponentBestMove = bestMove(tb,m.other());
				int opponentQual = opponentBestMove[1];
				
				if(opponentQual == WINNING){
					result = LOSING;
				}else if(opponentQual == LOSING){
					result = WINNING;
				}else{
					result = NEUTRAL;
				}
			}
		}else{
			result = WINNING;
		}
		return result;
	}
	
	
	
}
