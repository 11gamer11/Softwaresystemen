package ss.week6.vote;

import java.util.Scanner;

/**
 * Driver for the Vote assignment.
 * @author Arend Rensink en Theo Ruys
 * @version 2005.02.15
 */
public class Vote {
    public static void main(String[] args) {
        Result uitslag = new Result();
        VoteFrame stemFrame = new VoteFrame(uitslag);
        ResultJFrame uitslagJFrame = new ResultJFrame(uitslag);
        uitslag.addParty("PvdA");
        uitslag.addParty("VVD");
        uitslagJFrame.setVisible(true);
        stemFrame.setVisible(true);
        String partij;
        do {
            partij = readString("New Party? ");
            if (partij.length() != 0)
                   uitslag.addParty(partij);
        } while (partij.length() != 0);
    }
    
    public static String readString(String prompt) {
        Scanner in = new Scanner(System.in);
        System.out.print(prompt);
        if (in.hasNextLine()) {
            return in.nextLine();
        } else {
            return null;
        }
    }
}
