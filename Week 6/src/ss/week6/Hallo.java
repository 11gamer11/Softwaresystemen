package ss.week6;

import java.util.Scanner;

public class Hallo {
	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner (System.in);
		while (in.hasNextLine()){
			String line = in.nextLine();
			if(!line.isEmpty()){
			System.out.println("Hallo "+line);
			}else{System.out.println("No Input");break;}
		}
	}
}
