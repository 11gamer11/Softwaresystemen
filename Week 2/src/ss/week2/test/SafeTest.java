package ss.week2.test;

import ss.week2.hotel.Password;
import ss.week2.hotel.Safe;

/** 
 * Testprogram Password.
 * Lab exercise Softwaresystems
 * @author Arend Rensink
 * @version $Revision: 1.2 $
 */
public class SafeTest {
    /** Testvariabele for a <tt>Password</tt> object. */
    public Safe pass;

    /** Number of errors. */
    private int errors;
    /** Notice belonging to test method. */
    private boolean isPrinted;
    /** Indication that an errors was found in test method. */
    private String description;
    /** Indication of the expected state of the safe */
    private boolean safeStateActive;
    private boolean safeStateOpen;
    
    /** Calls all tests in this class one-by-one. */
    public int runTest() {
    	String pass = "testpass";
    	Password password = new Password();
        System.out.println("Testclass: " + this.getClass());
        setUp(password);
        this.pass.activate(pass);
        this.safeStateActive = true;
        isActiveTest();
        this.pass.open(pass);
        this.safeStateOpen = true;
        isOpenTest();
        this.pass.deactivate();
        this.safeStateActive = false;
        this.safeStateOpen = false;
        isActiveTest();
        isOpenTest();
        //getPasswordTest();
        
        if (errors == 0) {
            System.out.println("    OK");
        }
        return errors;
    }
    
    public void isActiveTest(){
    	beginTest("Methode isActive");
    	assertEquals("pass.isActive()", this.safeStateActive, pass.isActive());
    }
    
    public void isOpenTest(){
    	beginTest("Methode isOpen");
    	assertEquals("pass.isOpen()", this.safeStateOpen, pass.isOpen());
    }
    
    /*public void getPasswordTest(){
    	beginTest("Methode getPassword");
    	assertEquals("pass.getPassword()", pass.password, pass.getPassword());
    }*/

    /**
     * Sets the instance variable <tt>pass</tt> to a well-defined initial value.
     * All test methods should be preceded by a call to this method.
     */
    public void setUp(Password password) {
        pass = new Safe(password);
    }
    

    /**
     * Fixes the status for the testmethod's description.
     * @param text The description to be printed
     */
    private void beginTest(String text) {
        description = text;
        // the description hasn't been printed yet
        isPrinted = false;
    }

    /**
     * Tests if the resulting value of a tested expression equals the 
     * expected (correct) value. This implementation prints both values, 
     * with an indication of what was tested, to the standard output. The 
     * implementation does not actually do the comparison.
     */
    private void assertEquals(String text, Object expected, Object result) {
        boolean equal;
        // tests equality between expected and result
        // accounting for null
        if (expected == null) {
            equal = result == null;
        } else {
            equal = result != null && expected.equals(result);
        }
        if (!equal) {
            // prints the description if necessary
            if (!isPrinted) {
                System.out.println("    Test: " + description);
                // now the description is printed
                isPrinted = true;
            }
            System.out.println("        " + text);
            System.out.println("            Expected:  " + expected);
            System.out.println("            Result: " + result);
            errors++;
       }
    }
    public static void main(String[] args) {
		new SafeTest().runTest();
	}
}
