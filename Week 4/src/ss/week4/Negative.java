package ss.week4;

public class Negative {

	public static void returnNegative(int[] array){
		int length = array.length;
		for(int i=0; i<length; i++){
			if(array[i]<0){
				System.out.println(array[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		int[] testArray = {1,9,-5,3,-7,2,-1,0,8,4,-656516526};
		returnNegative(testArray);
	}
	
}
