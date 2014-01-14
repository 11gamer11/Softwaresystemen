package ss.week3.pw;

import java.math.BigInteger;
import java.security.SecureRandom;

public class StrongChecker extends BasicChecker{
	
	// Checks if the input is acceptable according to BasicChecker and
	// if it starts with a character and ends with a number
		public boolean acceptable(String suggestion) {
			if(super.acceptable(suggestion)){
				
				int length = suggestion.length();
				String start = suggestion.substring(0,1);
				String end = suggestion.substring(length-1);
				
				if(start.matches("[a-zA-Z]") && end.matches("[0-9]") ){
					return true;
				}
			}
			return false;
		}
	
	// Generates password	
		public String generatePass() {
			String password = "incorrect password";
			while(!this.acceptable(password)){
				password = new BigInteger(128, new SecureRandom()).toString(36);
			}
			return password;
		}
	// Run some tests	
		public void runTest() {
			System.out.println("Test class: " + this.getClass().getSimpleName());
			System.out.println(generatePass());
			System.out.println(generatePass());
	    }
	    public static void main(String[] args) {
			new StrongChecker().runTest();
		}
}
