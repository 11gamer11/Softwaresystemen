package board;

/**
 * Represents a mark in the Tic Tac Toe game. There three possible values:
 * Mark.XX, Mark.OO and Mark.EMPTY.
 * Module 2 lab assignment
 * 
 * @author Theo Ruys
 * @version $Revision: 1.4 $
 */
public enum Mark {
    
    EMPTY_, _RED__, YELLOW, GREEN_, _BLUE_;

    /*@
       ensures this == Mark.XX ==> \result == Mark.OO;
       ensures this == Mark.OO ==> \result == Mark.XX;
       ensures this == Mark.EMPTY ==> \result == Mark.EMPTY;
     */
    /**
     * Returns the other mark.
     * 
     * @return the other mark is this mark is not EMPTY or EMPTY
     */
    public Mark next(int players, int beurt) {
        int value = (beurt % players)+1;
        return Mark.values()[value];
    	
    	
    	/*if (this == RED) {
            return ;
        } else if (this == YELLOW) {
            return GREEN;
        } else if (this == GREEN) {
            return BLUE;
        } else if (this == BLUE) {
            return RED;
        } else {
            return EMPTY;
        }*/
    }
}
/*
maak variabele met aantal speleers
maak arrayList van kleuren (volgorde)
beurtenteller
beurtenteller mod aantal speler -> index array kleuren*/
