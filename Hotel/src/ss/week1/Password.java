package ss.week1;

public class Password {
	
	 // ------------------ Instance variables ----------------
	
	public static final String INITIAL = "testpass";
	public String password = INITIAL;
	
	// ------------------ Commands --------------------------
		
	/**
     * Checks if the string follows the rules for being a password
     * (not being shorter than 6 characters and not containing spaces).
     * @return	true if string follows the rules
     * 			false otherwise
     */
	public boolean acceptable(String suggestion) {
		return ((!suggestion.contains(" ")) && (suggestion.length() >= 6));
	}
	
	/**
     * Checks if the string is equal to the current password
     * @return	true if string is equal
     * 			false otherwise
     */
	public boolean testWord (String test) {
		return test.equals(password);
	}

	/**
     * Checks if the new password is acceptable 
     * and if the old password is equal to the current password
     * If so it changes the password to the new password
     * @param 	password will be changed to the new password
     * @return	true if password is changed
     * 			false otherwise
     */
	public boolean setWord(String oldpass, String newpass){
		if(testWord(oldpass) && acceptable(newpass)){
			password = newpass;
			return true;
		}
		return false;
	}


}
