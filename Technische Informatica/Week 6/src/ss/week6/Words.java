package ss.week6;

import java.util.Scanner;

public class Words {
	private static Scanner input;
	private static Scanner line;
	private static Scanner check;
	
	public static void main(String[] args) {
		//Check for input by user
		input = new Scanner (System.in);
		//Print first text (after this is the textinput)
		System.out.print("Line (or \"end\"): ");
		//While loop while there is input 
		while (input.hasNextLine()){
			//Input line
			String nextline = input.nextLine();
			//Input line as Scanner for processing
			line = new Scanner(nextline);
			//Input line as Scanner for checking for end
			check = new Scanner(nextline);
			//If check has values and if the first value is 'end' to end the programm
			if(check.hasNext() && check.next().equals("end")){
				line.close();
				check.close();
				System.out.print("End of prgrammme.");
				break;
			}else{
				//If line hasn't any values return: 'No Words'
				if(!line.hasNext()){
					System.out.println("No Words!");
				//All the values are printed with the corresponding nr. in the format: 'Word' nr ': ' value
				}else{
					int i = 1;
					while(line.hasNext()){
						String word = line.next();
						System.out.println("Word "+i+": "+word);
						i++;
					}
				}
				//Print text again after printing previous line or returning error (after this is the textinput)
			System.out.print("Line (or \"end\"): ");
			}
		}
	}
}
