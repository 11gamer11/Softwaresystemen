package ss.week3.test;

import ss.week3.hotel.Bill;
import ss.week3.hotel.Bill.Item;
import ss.week3.hotel.BillItem;

public class BillTest {
	
    private Bill bill;
    private Item item;
    private Item item2;

    /** Number of errors. */
    private int errors;
    /** Notice belonging to test method. */
    private boolean isPrinted;
    /** Indication that an errors was found in test method. */
    private String description;

    /** Calls all tests in this class one-by-one. */
    public int runTest() {
        System.out.println("Testclass: " + this.getClass());
        setUp();
        testGetSum();
        if (errors == 0) {
            System.out.println("    OK");
        }
        return errors;
    }

    /**
     * Creates a new bill and 2 new items.
     * All test methods should be preceded by a call to this method.
     */
    public void setUp() {
        // initialisation of password-variable
        bill = new Bill(System.out);
        item = new BillItem();
        item2 = new BillItem();
    }

    /**
     * Test the method <tt>getSum()</tt>
     * This method should be preceded by a call to <tt>{@link #setUp}</tt>.
     */
    public void testGetSum() {
        beginTest("Method getSum");
        assertEquals("bill.getSum()", 0.0, bill.getSum());
        
        bill.newItem(item);
        bill.newItem(item2);
        
        assertEquals("bill.getSum()", 6.28, bill.getSum());
        
        bill.close();
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
    
    public static void main(String[] args){
    	new BillTest().runTest();
    }
}
