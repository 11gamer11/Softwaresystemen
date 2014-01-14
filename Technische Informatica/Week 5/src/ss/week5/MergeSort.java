package ss.week5;

import java.util.LinkedList;

public class MergeSort {

	public static LinkedList<Integer> mergeSort(LinkedList<Integer> theTardisList){
		
		int top = theTardisList.size();
		int bottom = 0;
		
		int middle = (top-bottom)/2;
		
		if(top <= 1){
			return theTardisList;
		}
		else{
			LinkedList<Integer> firstHalf = new LinkedList<Integer>(theTardisList.subList(bottom,middle));
			LinkedList<Integer> secondHalf = new LinkedList<Integer>(theTardisList.subList(middle,top));
			LinkedList<Integer> sortedFirstHalf = mergeSort(firstHalf);
			LinkedList<Integer> sortedSecondHalf = mergeSort(secondHalf);
			
			LinkedList<Integer> resultingTardisList = new LinkedList<Integer>();
			
			int iFirstStart = 0;
			int iFirstEnd = sortedFirstHalf.size();
			int iSecondStart = 0;
			int iSecondEnd = sortedSecondHalf.size();
			
			while(iFirstStart < iFirstEnd && iSecondStart < iSecondEnd){
				if(sortedFirstHalf.get(iFirstStart) < sortedSecondHalf.get(iSecondStart)){
					resultingTardisList.add(resultingTardisList.size(), sortedFirstHalf.get(iFirstStart));
					iFirstStart++;
				}
				else{
					resultingTardisList.add(resultingTardisList.size(), sortedSecondHalf.get(iSecondStart));
					iSecondStart++;
				}
			}
			if(iFirstStart < iFirstEnd){
				resultingTardisList.addAll(resultingTardisList.size(), sortedFirstHalf.subList(iFirstStart, iFirstEnd));
			}
			else if(iSecondStart < iSecondEnd){
				resultingTardisList.addAll(resultingTardisList.size(), sortedSecondHalf.subList(iSecondStart, iSecondEnd));
			}
			return resultingTardisList;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(2);
		list.add(1337);
		list.add(6);
		list.add(-5);
		list.add(42);
		list.add(1701);
		list.add(-2849);
		list.add(1);
		list.add(0);
		System.out.println(mergeSort(list));
	}

}
