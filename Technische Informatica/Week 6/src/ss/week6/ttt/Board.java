package ss.week6.ttt;

/**
 * Game board for the Tic Tac Toe game. Module 2 lab assignment.
 * 
 * @author Theo Ruys en Arend Rensink
 * @version $Revision: 1.4 $
 */
public class Board {

    // -- Constants --------------------------------------------------

    public static final int DIM = 3;
    private static final String[] NUMBERING = {" 0 | 1 | 2 ", "---+---+---",
        " 3 | 4 | 5 ", "---+---+---", " 6 | 7 | 8 "};
    private static final String LINE = NUMBERING[1];
    private static final String DELIM = "     ";

    // -- Instance variables -----------------------------------------

    /*@
       private invariant fields.length == DIM*DIM;
       invariant (\forall int i; 0 <= i & i < DIM*DIM;
           getField(i) == Mark.EMPTY || getField(i) == Mark.XX || getField(i) == Mark.OO);
     */
    /**
     * The DIM by DIM fields of the Tic Tac Toe board. See NUMBERING for the
     * coding of the fields.
     */
    private Mark[] fields;

    // -- Constructors -----------------------------------------------

    /*@
       ensures (\forall int i; 0 <= i & i < DIM * DIM; this.getField(i) == Mark.EMPTY);
     */
    /**
     * Creates an empty board.
     */
    // B{Bord-impl}
    public Board() {
        // E{Bord}
        fields = new Mark[DIM * DIM];
        reset();
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
    }

    // E{Bord-impl}
    // -- Queries ----------------------------------------------------

    /*@
       ensures \result != this;
       ensures (\forall int i; 0 <= i & i < DIM * DIM; \result.getField(i) == this.getField(i));
     */
    /**
     * Creates a deep copy of this field.
     */
    // B{Bord-impl}
    public Board deepCopy() {
        // E{Bord}
        Board copy = new Board();
        for (int i = 0; i < fields.length; i++) {
            copy.fields[i] = this.fields[i];
        }
        return copy;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return null;
    }

    // E{Bord-impl}

    /*@
       requires 0 <= row & row < DIM;
       requires 0 <= col & col < DIM;
     */
    /**
     * Calculates the index in the linear array of fields from a (row, col)
     * pair.
     * @return the index belonging to the (row,col)-field
     */
    // B{Bord-impl}
    public int index(int row, int col) {
        // E{Bord}
        return DIM * row + col;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return 0;
    }

    // E{Bord-impl}

    /*@
       ensures \result == (0 <= ix && ix < DIM * DIM);
     */
    /**
     * Returns true if <code>ix</code> is a valid index of a field on tbe board.
     * @return <code>true</code> if <code>0 <= ix < DIM*DIM</code>
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean isField(int ix) {
        // E{Bord}
        return (0 <= ix) && (ix < DIM * DIM);
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /*@
       ensures \result == (0 <= row && row < DIM && 0 <= col && col < DIM);
     */
    /**
     * Returns true of the (row,col) pair refers to a valid field on the board.
     * 
     * @return true if <code>0 <= row < DIM && 0 <= col < DIM</code>
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean isField(int row, int col) {
        // E{Bord}
        return (0 <= row) && (row < DIM) && (0 <= col) && (col < DIM);
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}

    /*@
       requires this.isField(i);
       ensures \result == Mark.EMPTY || \result == Mark.XX || \result == Mark.OO;
     */
    /**
     * Returns the content of the field <code>i</code>.
     * 
     * @param i
     *            the number of the field (see NUMBERING)
     * @return the mark on the field
     */
    // B{Bord-impl}
    public Mark getField(int i) {
        // E{Bord}
        return fields[i];
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return null;
    }

    // E{Bord-impl}
    /*@
       requires this.isField(row,col);
       ensures \result == Mark.EMPTY || \result == Mark.XX || \result == Mark.OO;
     */
    /**
     * Returns the content of the field referred to by the (row,col) pair.
     * 
     * @param row
     *            the row of the field
     * @param col
     *            the column of the field
     * @return the mark on the field
     */
    // B{Bord-impl}
    public Mark getField(int row, int col) {
        // E{Bord}
        return fields[index(row, col)];
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return null;
    }

    // E{Bord-impl}
    /*@
       requires this.isField(i);
       ensures \result == (this.getField(i) == Mark.EMPTY);
     */
    /**
     * Returns true if the field <code>i</code> is empty.
     * 
     * @param i
     *            the index of the field (see NUMBERING)
     * @return true if the field is empty
     */
    // B{Bord-impl}
    public boolean isEmptyField(int i) {
        // E{Bord}
        return getField(i) == Mark.EMPTY;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /*@
       requires this.isField(row,col);
       ensures \result == (this.getField(row,col) == Mark.EMPTY);

     */
    /**
     * Returns true if the field referred to by the (row,col) pair it empty.
     * 
     * @param row
     *            the row of the field
     * @param col
     *            the column of the field
     * @return true if the field is empty
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean isEmptyField(int row, int col) {
        // E{Bord}
        return isEmptyField(index(row, col));
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /*@
       ensures \result == (\forall int i; i <= 0 & i < DIM * DIM; this.getField(i) != Mark.EMPTY);
     */
    /**
     * Tests if the whole board is full.
     * 
     * @return true if all fields are occupied
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean isFull() {
        // E{Bord}
        boolean result = true;
        for (int i = 0; i < fields.length; i++) {
            if (isEmptyField(i)) {
                result = false;
            }
        }
        return result;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /*@
       ensures \result == this.isFull() || this.hasWinner();

     */
    /**
     * Returns true if the game is over. The game is over when there is a winner
     * or the whole board is full.
     * 
     * @return true if the game is over
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean gameOver() {
        // E{Bord}
        return isFull() || hasWinner();
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /**
     * Checks whether there is a row which is full and only contains the mark
     * <code>m</code>.
     * 
     * @param m
     *            the mark of interest
     * @return true if there is a row controlled by <code>m</code>
     */
    // B{Bord-impl}
    public boolean hasRow(Mark m) {
        // E{Bord}
        int i = 0;
        boolean hrow = false;
        while (i < DIM && !hrow) {
            int j = 0;
            hrow = true;
            while (j < DIM && hrow) {
                hrow = getField(i, j) == m;
                j++;
            }
            i++;
        }
        return hrow;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /**
     * Checks whether there is a column which is full and only contains the mark
     * <code>m</code>.
     * 
     * @param m
     *            the mark of interest
     * @return true if there is a column controlled by <code>m</code>
     */
    // B{Bord-impl}
    public boolean hasColumn(Mark m) {
        // E{Bord}
        int j = 0;
        boolean hcol = false;
        while (j < DIM && !hcol) {
            int i = 0;
            hcol = true;
            while (i < DIM && hcol) {
                hcol = getField(i, j) == m;
                i++;
            }
            j++;
        }
        return hcol;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /**
     * Checks whether there is a diagonal which is full and only contains the
     * mark <code>m</code>.
     * 
     * @param m
     *            the mark of interest
     * @return true if there is a diagonal controlled by <code>m</code>
     */
    // B{Bord-impl}
    public boolean hasDiagonal(Mark m) {
        // E{Bord}
        int i = 0;
        boolean hDia1 = true;
        boolean hDia2 = true;
        while (i < DIM && (hDia1 || hDia2)) {
            hDia1 = hDia1 && (getField(i, i) == m);
            hDia2 = hDia2 && (getField(i, DIM - i - 1) == m);
            i++;
        }
        return hDia1 || hDia2;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /*@
       requires m == Mark.XX | m == Mark.OO;
       ensures \result == this.hasRow(m) ||
                                this.hasColumn(m) |
                                this.hasDiagonal(m);
     */
    /**
     * Checks if the mark <code>m</code> has won. A mark wins if it controls at
     * least one row, column or diagonal.
     * 
     * @param m
     *            the mark of interest
     * @return true if the mark has won
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean isWinner(Mark m) {
        // E{Bord}
        return hasRow(m) || hasColumn(m) || hasDiagonal(m);
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /*@
       ensures \result == isWinner(Mark.XX) | \result == isWinner(Mark.OO);

     */
    /**
     * Returns true if the game has a winner. This is the case when one of the
     * marks controls at least one row, column or diagonal.
     * 
     * @return true if the board has a winner.
     */
    // B{Bord-impl}
    /*@pure*/
    public boolean hasWinner() {
        // E{Bord}
        return isWinner(Mark.XX) || isWinner(Mark.OO);
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
        // I{Bord} return false;
    }

    // E{Bord-impl}
    /**
     * Returns a String representation of this board. In addition to the current
     * situation, the String also shows the numbering of the fields.
     * 
     * @return the game situation as String
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < DIM; i++) {
            String row = "";
            for (int j = 0; j < DIM; j++) {
                row = row + " " + getField(i, j).toString() + " ";
                if (j < DIM - 1) {
                    row = row + "|";
                }
            }
            s = s + row + DELIM + NUMBERING[i * 2];
            if (i < DIM - 1) {
                s = s + "\n" + LINE + DELIM + NUMBERING[i * 2 + 1] + "\n";
            }
        }
        return s;
    }

    // -- Commands ---------------------------------------------------

    /*@
       ensures (\forall int i; 0 <= i & i < DIM * DIM; this.getField(i) == Mark.EMPTY);
     */
    /**
     * Empties all fields of this board (i.e., let them refer to the value
     * Mark.EMPTY).
     */
    // B{Bord-impl}
    public void reset() {
        // E{Bord}
        for (int i = 0; i < fields.length; i++) {
            setField(i, Mark.EMPTY);
        }
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
    }

    // E{Bord-impl}
    /*@
       requires this.isField(i);
       ensures this.getField(i) == m;
     */
    /**
     * Sets the content of field <code>i</code> to the mark <code>m</code>.
     * 
     * @param i
     *            the field number (see NUMBERING)
     * @param m
     *            the mark to be placed
     */
    // B{Bord-impl}
    public void setField(int i, Mark m) {
        // E{Bord}
        fields[i] = m;
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
    }

    // E{Bord-impl}
    /*@
       requires this.isField(row,col);
       ensures this.getField(row,col) == m;

     */
    /**
     * Sets the content of the field represented by the (row,col) pair to the
     * mark <code>m</code>.
     * 
     * @param row
     *            the field's row
     * @param col
     *            the field's column
     * @param m
     *            the mark to be placed
     */
    // B{Bord-impl}
    public void setField(int row, int col, Mark m) {
        // E{Bord}
        setField(index(row, col), m);
        // B{Bord}
        // I{Bord} // [BODY-NOG-TOE-TE-VOEGEN]
    }

    // E{Bord-impl}
}
