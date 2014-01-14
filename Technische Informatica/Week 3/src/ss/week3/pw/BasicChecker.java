package ss.week3.pw;

import java.math.BigInteger;
import java.security.SecureRandom;

public class BasicChecker implements Checker{
	
	// Checks if the input doesn't contain any spaces and isn't shorter than 6 characters
		public boolean acceptable(String suggestion) {
			return !(suggestion.contains(" ") || suggestion.length() < 6);
		}

	// Generates password
		public String generatePass() {
			String password = "incorrect pass";
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
			new BasicChecker().runTest();
		}
}
