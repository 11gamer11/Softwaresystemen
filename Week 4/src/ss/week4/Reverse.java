package ss.week4;

public class Reverse {
	public static int[] reverseArray(int[] array){
		int length = array.length;
		//@ loop_invariant i >= 0 && i <= length/2;
		for(int i=0; i<=(length/2); i++){
			int x = array[i];
			int y = array[(length-1)-i];
			array[i] = y;
			array[(length-1)-i] = x;
		}
		return array;
	}
	
	public static void main(String[] args) {
		int[] INITIAL = {1,3,5,7,9,2,4,6,8};
		int[] testArray = {1,3,5,7,9,2,4,6,8};
		reverseArray(testArray);
		for(int i=0;i<testArray.length; i++){
			System.out.print("Van: "+INITIAL[i]+" Naar:"+testArray[i]+ "\n");
		}
	}

}
