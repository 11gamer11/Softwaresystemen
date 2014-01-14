package ss.week4;

public class IntSet {

	private boolean[] isIn;
	
	//@requires maxElement>=0;
	public IntSet (int maxElement){
		isIn = new boolean[maxElement];
	}
	
	public void addElement(int value){
		isIn[value] = true;
	}
	
	public void deleteElement(int value){
		isIn[value] = false;
	}
	
	public boolean checkElement(int value){
		return isIn[value];
	}

	public IntSet unionSet(IntSet argSet){
		
		int length_this = this.isIn.length;
		int length_arg = argSet.isIn.length;
		int longest;
		IntSet unionSet;
		
		if(length_this > length_arg){
			longest = length_this;
		}else{
			longest = length_arg;
		}
		unionSet = new IntSet(longest);
		
		//@ loop_invariant i >= 0 && i <= longest;
		for(int i=0; i<longest; i++){
			if((i<length_this && this.isIn[i]) || (i<length_arg && argSet.isIn[i])){
				unionSet.addElement(i);
			}
		}
		return unionSet;
	}
}
