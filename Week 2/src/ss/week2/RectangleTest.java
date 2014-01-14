package ss.week2;

public class RectangleTest {

	/**
	 * @param args
	 */

    public void start() {
        System.out.println("Test class: " + this.getClass());   
        testInitialcondition();
    }
    
    
    public void testInitialcondition() {
        beginTest("Initial condition");
        assertEquals("new Rectangle(3, 2).width()", "2", new Rectangle(3, 2).width());
        assertEquals("new Rectangle(3, 2).length()", "3", new Rectangle(3, 2).length());
        assertEquals("new Rectangle(3, 2).area();", "6", new Rectangle(3, 2).area());
        assertEquals("new Rectangle(3, 2).perimeter();", "10", new Rectangle(3, 2).perimeter());
    }
	
    /**
     * Print the testmethod's description.
     * @param text The description to be printed
     */
    private void beginTest(String text) {
    	System.out.println("    Test: " + text);
    }
	
    /**
     * Tests if the resulting value of a tested expression equals the 
     * expected (correct) value. This implementation prints both values, 
     * with an indication of what was tested, to the standard output. The 
     * implementation does not actually do the comparison.
     */
    private void assertEquals(String text, Object expected, Object result) {
    	System.out.println("        " + text);
    	System.out.println("            Expected:  " + expected);
    	System.out.println("            Result: " + result);
    }

    /** Makes a <tt>GuestTest</tt> object and runs it.*/
    public static void main(String[] args) {
        System.out.println("Log of " + RectangleTest.class + 
                           ", " + new java.util.Date());
        new RectangleTest().start();
    }

}
