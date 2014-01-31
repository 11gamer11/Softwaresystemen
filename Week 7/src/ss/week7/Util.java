<<<<<<< HEAD
package ss.week7;

import java.util.ArrayList;
import java.util.List;

/**.
 *
 * @author Jeroen
 *
 */
public class Util {
/**
 * @param l1 lijst 1
 * @param l2 lijst 2
 * @param <Element> Lijst die uit elementen bestaat
 * @return result
 */
    public static <Element> List<Element> zip(
    final List<Element> l1, final List<Element> l2) {
        ArrayList<Element> result = new ArrayList<Element>();
        for (int i = 0; i < l1.size(); i++) {
            result.add(l1.get(i));
            result.add(l2.get(i));
        }
        return result;
    }

    /**
     * @param i the function argument
     * @return -1, 0 or 1 if the argument is negative, 0 or positive
     */
    public static int signum(final int i) {
        if (i < 0) {
            return -1;
        } else {
        if (i > 0) {
            return 1;
        } else {
            return 0;
        }
        }
    }
}
=======
package ss.week7;

import java.util.ArrayList;
import java.util.List;

public class Util {
    
    public static <ELEMENT> List<ELEMENT> zip(List<ELEMENT> l1, List<ELEMENT> l2) {
        ArrayList<ELEMENT> result = new ArrayList<ELEMENT>();
        for (int i = 0; i < l1.size(); i++) {
        	result.add(l1.get(i));
        	result.add(l2.get(i));
        }
        return result;
    }    

    /**
     * @param i the function argument
     * @return -1, 0 or 1 if the argument is negative, 0 or positive
     */
    public static int signum(int i) {
        if (i < 0) {
            return -1;
        } else if (i > 0) {
            return 1;
    	} else {
        	return 0;
        }
    }
}
>>>>>>> 211da7cf97c44a293dfa4d21396b5997e8229ecc
