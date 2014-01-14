package ss.week3.pw;

public interface Checker {
	
	// Checks if the password is acceptable
		public boolean acceptable(String suggestion);
	
	// Generates Password
		public String generatePass();
}