package board;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Game board for the Rollit game. Module 2 lab assignment.
 * 
 * @author Jeroen Waals
 * @version Version: 0.1
 */
public class Board {

    // -- Constants --------------------------------------------------

    public static final int DIM = 4;
    private static final String DELIM = "   |   ";
    private boolean DEBUG = false;

    // -- Instance variables -----------------------------------------

    /**
     * The DIM by DIM fields of the Tic Tac Toe board. See NUMBERING for the
     * coding of the fields.
     */
    private Mark[] fields;

    // -- Constructors -----------------------------------------------

    /**
     * Creates an empty board.
     */
    public Board() {
        fields = new Mark[DIM * DIM];
        reset();
    }

    // -- Queries ----------------------------------------------------

    /**
     * Creates a deep copy of this field.
     */
    public Board deepCopy() {
        Board copy = new Board();
        for (int i = 0; i < fields.length; i++) {
            copy.fields[i] = this.fields[i];
        }
        return copy;
    }

   /**
     * Returns true if <code>ix</code> is a valid index of a field on tbe board.
     * @return <code>true</code> if <code>0 <= ix < DIM*DIM</code>
     */
   public boolean isField(int ix) {
        return (0 <= ix) && (ix < DIM * DIM);
    }

    /**
     * Returns the content of the field <code>i</code>.
     * 
     * @param i
     *            the number of the field (see NUMBERING)
     * @return the mark on the field
     */
    public Mark getField(int i) {
    	return fields[i];
    }
    
    public int[] rowcol (int index){
    	int[] rowcol = new int[2];
    	int row, col;
    	
    	row = (int) Math.floor(index/DIM);
    	col = (int) index%DIM;
    	
    	rowcol[0] = row;
    	rowcol[1] = col;
    	return rowcol;
    }
    
    /**
     * Returns true if the field <code>i</code> is empty.
     * 
     * @param i
     *            the index of the field (see NUMBERING)
     * @return true if the field is empty
     */
    public boolean isEmptyField(int i) {
        return getField(i).equals(Mark.EMPTY_);
    }
    /**
     * Returns true if the field has any non-empty fields next to it.
     * 
     * @param field
     *            the index of the field (see NUMBERING)
     * @return true if the field has any non-empty fields next to it.
     */
    public boolean isCorrectField(int field) {
    	if(isEmptyField(field)){
    		for(int row = 0; row < DIM*DIM; row += DIM){
    			// First field of row
        		if((field == row) && isCorrectFirstFieldRow(field)){
        			//if(DEBUG){print("Field "+field+" = First field of row, correct");}
        			return true;
    			}
        		// All field of row between first and last field
        		if((field > row && field < row+(DIM-1)) && isCorrectMiddleFieldsRow(field)){
        			//if(DEBUG){print("Field "+field+" = Field between first and last field, correct");}
        			return true;
	    		}
	    		// Last field of row
        		if((field == row+(DIM-1)) && isCorrectLastFieldRow(field)){
        			//if(DEBUG){print("Field "+field+" = Last field of row, correct");}
        			return true;
    			}
    		}
    	}
    	//if(DEBUG){print("Field "+field+" = incorrect");}
		return false;
    }
    
	    private boolean isCorrectFirstFieldRow(int field) {
	    	int checkField;
			for(int i = 0; i < 2; i++){
				checkField = field-DIM+i;
				if(isField(checkField) && !isEmptyField(checkField)){
					//if(DEBUG){print("Field "+field+" has non-empty fields above (\\|/) itself");}
        			return true;
				}
			}
			if(!isEmptyField(field+1)){
				//if(DEBUG){print("Field "+field+" has non-empty fields to the right (X-) of itself");}
				return true;
			}
			for(int i = 0; i < 2; i++){
				checkField = field+DIM+i;
				if(isField(checkField) && !isEmptyField(checkField)){
					//if(DEBUG){print("Field "+field+" has non-empty fields beneith (/|\\) itself");}
        			return true;
				}
			}
			return false;
	    }
	    
	    private boolean isCorrectMiddleFieldsRow(int field) {
	    	int checkField;
			for(int i = 0; i < 3; i++){
				checkField = field-(DIM+1)+i;
				if(isField(checkField) && !isEmptyField(checkField)){
					//if(DEBUG){print("Field "+field+" has non-empty fields above (\\|/) itself");}
        			return true;
				}
			}
			if(!isEmptyField(field-1)){
				//if(DEBUG){print("Field "+field+" has non-empty fields to the left (-X)of itself");}
    			return true;
			}
			if(!isEmptyField(field+1)){
				//if(DEBUG){print("Field "+field+" has non-empty fields to the right (X-) ofitself");}
    			return true;
			}
			for(int i = 0; i < 3; i++){
				checkField = field+(DIM-1)+i;
				if(isField(checkField) && !isEmptyField(checkField)){
					//if(DEBUG){print("Field "+field+" has non-empty fields beneith (/|\\) itself");}
        			return true;
				}
			}
			return false;
	    }
	    
	    private boolean isCorrectLastFieldRow(int field) {
	    	int checkField;
			for(int i = 0; i < 2; i++){
				checkField = field-(DIM+1)+i;
				if(isField(checkField) && !isEmptyField(checkField)){
					//if(DEBUG){print("Field "+field+" has non-empty fields above (\\|/) itself");}
        			return true;
				}
			}
			if(!isEmptyField(field-1)){
				//if(DEBUG){print("Field "+field+" has non-empty fields to the left (-X)of itself");}
    			return true;
    		}
			for(int i = 0; i < 2; i++){
				checkField = field+(DIM-1)+i;
				if(isField(checkField) && !isEmptyField(checkField)){
					//if(DEBUG){print("Field "+field+" has non-empty fields beneith (/|\\) itself");}
        			return true;
				}
			}
			return false;
	    }
    
    public List<Integer> getAllCorrectFields(){
    	List<Integer> allCorrectFields = new ArrayList<Integer>();
    	List<Integer> markFields = getAllFields(Mark.EMPTY_);
    	Iterator<Integer> iterator = markFields.iterator();
		while (iterator.hasNext()){
			int field = iterator.next();
			if(isCorrectField(field)){
				allCorrectFields.add(field);
			}
		}
		//if(DEBUG){print("All the fields that have a non-empty field someweher around itself: "+allCorrectFields);}
		return allCorrectFields;
    }
    
    public List<Integer> checkBeat(int field, Mark mark){
    	List<Integer> beatenFields = new ArrayList<Integer>();
    	for(int row = 0; row < DIM; row++){
	    	if(field == row*DIM){
	    		//if(DEBUG){print("Field "+field+" = first of row");}
    			beatenFields.addAll(checkBeatFirstColumn(field, mark));
	    	}
	    	if(field > (row*DIM) && field < (row*DIM+(DIM-1))){
	    		//if(DEBUG){print("Field "+field+" = between first and last");}
    			beatenFields.addAll(checkBeatMiddleColumn(field, mark));
	    	}
	    	if(field == (row*DIM+(DIM-1))){
	    		//if(DEBUG){print("Field "+field+" = last of row");}
    			beatenFields.addAll(checkBeatLastColumn(field, mark));
	    	}
    	}
    	return beatenFields;
    }
    
	    private List<Integer> checkBeatFirstColumn(int field, Mark mark){
	    	List<Integer> beatenFields = new ArrayList<Integer>();
	    	List<Integer> tmpFields = new ArrayList<Integer>();
	    	for(int j = 0; j < 2 ; j++){
		    	for(int i = 1; i < DIM && checkField(-(DIM-j), field, i, false, true); i++){
		    		int beatField = field-((DIM-j)*i);
		    		tmpFields.add(beatField);
		    	}
		    	//if(DEBUG){print("All the fields that are a field above (|/) "+field+": "+tmpFields);}
				beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
			   	tmpFields = new ArrayList<Integer>();
	    	}
		    for(int j = 0; j < 2; j++){
		    	for(int i = 1; i < DIM && checkField((DIM+j), field, i, false, true); i++){
		    		int beatField = field+((DIM+j)*i);
		    			tmpFields.add(beatField);
		    	}
		    	//if(DEBUG){print("All the fields that are beneith (|\\) "+field+": "+tmpFields);}
				beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
		    	tmpFields = new ArrayList<Integer>();
		    }
	    	for(int i = 1; i < DIM && checkField(1, field, i, false, true); i++){
	    		int beatField = field+i;
		    	tmpFields.add(beatField);
	    	}
	    	//if(DEBUG){print("All the fields that are to the right (X-) of "+field+": "+tmpFields);}
			beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
	    	tmpFields = new ArrayList<Integer>();
	    	return beatenFields;
	    }
    
	    private List<Integer> checkBeatMiddleColumn(int field, Mark mark){
	    	List<Integer> beatenFields = new ArrayList<Integer>();
	    	List<Integer> tmpFields = new ArrayList<Integer>();
	    	for(int j = 0; j < 3 ; j++){
		    	for(int i = 1; i < DIM && checkField(-((DIM-1)+j), field, i, true, true); i++){
		    		int beatField = field-(((DIM-1)+j)*i);
		    		tmpFields.add(beatField);
		    	}
		    		//if(DEBUG){print("All the fields that are above (\\|/) "+field+": "+tmpFields);}
				beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
			   	tmpFields = new ArrayList<Integer>();
	    	}
		    for(int j = 0; j < 3; j++){
		    	for(int i = 1; i < DIM && checkField(((DIM+1)-j), field, i, true, true); i++){
		    		int beatField = field+(((DIM+1)-j)*i);
		    		tmpFields.add(beatField);
		    	}
		    		//if(DEBUG){print("All the fields that are beneith (/|\\) "+field+": "+tmpFields);}
				beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
		    	tmpFields = new ArrayList<Integer>();
		    }
	    	for(int i = 1; i < DIM && checkField(-1, field, i, true, false); i++){
	    		int beatField = field-i;
	    		tmpFields.add(beatField);
	    	}
	    		//if(DEBUG){print("All the fields that are to the left (-X) of "+field+": "+tmpFields);}
			beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
	    	tmpFields = new ArrayList<Integer>();
	    	for(int i = 1; i < DIM && checkField(1, field, i, false, true); i++){
	    		int beatField = field+i;
		    	tmpFields.add(beatField);
	    	}
	    		//if(DEBUG){print("All the fields that are to the right (X-)of "+field+": "+tmpFields);}
			beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
	    	tmpFields = new ArrayList<Integer>();
	    	return beatenFields;
	    }
	    
	    private List<Integer> checkBeatLastColumn(int field, Mark mark){
	    	List<Integer> beatenFields = new ArrayList<Integer>();
	    	List<Integer> tmpFields = new ArrayList<Integer>();
	    	for(int j = 0; j < 2 ; j++){
		    	for(int i = 1; i < DIM && checkField(-(DIM+j), field, i, true, false); i++){
		    		int beatField = field-((DIM+j)*i);
		    		tmpFields.add(beatField);
		    	}
		    		///if(DEBUG){print("All the fields that are above (\\|) "+field+": "+tmpFields);}
				beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
			   	tmpFields = new ArrayList<Integer>();
	    	}
		    for(int j = 0; j < 2; j++){
		    	for(int i = 1; i < 8 && checkField((DIM-j), field, i, true, false); i++){
		    		int beatField = field+((DIM-j)*i);
		    			tmpFields.add(beatField);
		    	}
		    		//if(DEBUG){print("All the fields that are beneith (/|) "+field+": "+tmpFields);}
				beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
		    	tmpFields = new ArrayList<Integer>();
		    }
	    	for(int i = 1; i < DIM && checkField(-1, field, i, true, false); i++){
	    		int beatField = field-i;
	    		tmpFields.add(beatField);
	    	}
	    		//if(DEBUG){print("All the fields that are to the left of (-X) "+field+": "+tmpFields);}
			beatenFields.addAll((checkTempFieldList(tmpFields, mark)));
	    	tmpFields = new ArrayList<Integer>();
	    	return beatenFields;
	    }
	    
	    private List<Integer> checkTempFieldList(List<Integer> list, Mark mark){
	    	List<Integer> returnList = new ArrayList<Integer>();
	    	List<Mark> tmpList = new ArrayList<Mark>();
	    	Iterator<Integer> iterator = list.iterator();
			while (iterator.hasNext()){
				tmpList.add(getField(iterator.next()));
			}
	    	int firstMark = tmpList.indexOf(mark);
	    	if(firstMark != -1){
	    		for(int i = 0; i < firstMark; i++){
	    			returnList.add(list.get(i));
	    		}
	    	}
	    		//if(DEBUG){print("All the fields that can be beaten: "+returnList);}
	    	return returnList;
	    }
	    
	    private boolean checkField(int math, int currField, int field, boolean left, boolean right){
	    	return isField(currField+(math*field)) 
	    	    && !getField(currField+(math*field)).equals(Mark.EMPTY_)
	    	    && !checkCol(currField+(math*(field-1)), left, right, currField);
	    }
	    
	    private boolean checkCol(int field, boolean left, boolean right, int fromField){
	    		//if(DEBUG){print(field+" "+left+" "+right+" "+fromField);}
	    	if(left){
		    	for(int i = 0; i < DIM; i++){
		    		if((DIM*i) == field){
		    				//if(DEBUG){print("true first col");}
		    			return true;
		    		}
		    	}
	    	}
	    	if(right){
		    	for(int i = 0; i < DIM; i++){
		    		if((DIM*i+(DIM-1)) == field){
		    				//if(DEBUG){print("true last col");}
		    			return true;
		    		}
		    	}
	    	}
		    	//if(DEBUG){print("false");}
			return false;
	    }

    public List<Integer> checkBeatingFields(Mark mark){
    	List<Integer> beatingFields = new ArrayList<Integer>();
    	List<Integer> markFields = getAllCorrectFields();
    	if(!markFields.isEmpty()){
    		Iterator<Integer> iterator = markFields.iterator();
    		while (iterator.hasNext()) {
    			int beatingField = iterator.next();
    			if(!checkBeat(beatingField, mark).isEmpty()){
    				beatingFields.add(beatingField);
    			}
    		}
    	}
    		//if(DEBUG){print("All the fields that "+mark+"can beat: "+beatingFields);}
		return beatingFields;
    }
	    
	public boolean checkField(int choice, Mark mark) {
    	Iterator<Integer> iterator = checkBeatingFields(mark).iterator();
		if(!iterator.hasNext()){
				//if(DEBUG){print(mark+"has no possible beating fields");}
			iterator = getAllCorrectFields().iterator();
		}
    	while(iterator.hasNext()){
			if(iterator.next().equals(choice)){
					//if(DEBUG){print(mark+"can place at: "+choice);}
				return true;
			}
		}
    	//if(DEBUG){print(mark+"can't place at: "+choice);}
		return false;
	}
	    
    public List<Integer> getAllFields(Mark mark){
    	List<Integer> markFields = new ArrayList<Integer>();
    	for(int i = 0; i < fields.length; i++){
    		if(getField(i).equals(mark)){
    			markFields.add(i);
    		}
    	}
    	return markFields;
    }
    
    
    
    /**
     * Tests if the whole board is full.
     * 
     * @return true if all fields are occupied
     */

    public boolean isFull() {
        for (int i = 0; i < fields.length; i++) {
            if (isEmptyField(i)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Returns true if the game is over. The game is over when there is a winner
     * or the whole board is full.
     * 
     * @return true if the game is over
     */
    public boolean gameOver() {
        return isFull();
    }

    /**
     * Returns true if the game has a winner. This is the case when one of the
     * marks controls at least one row, column or diagonal.
     * 
     * @return true if the board has a winner.
     */
    public Mark winner() {
    	Mark[] marks = {Mark._RED__, Mark.YELLOW, Mark.GREEN_, Mark._BLUE_};
    	List<Integer> scores = new ArrayList<Integer>();
    	for(Mark mark: marks){
    		scores.add(getAllFields(mark).size());
    	}
    	int topScore = Collections.max(scores);
    	if(!findDuplicateTopScores(scores, topScore)){
    		int score = scores.indexOf(topScore);
    		return marks[score];
    	}
    	return null; 
    }
    
    public boolean findDuplicateTopScores(List<Integer> listContainingDuplicates, int Score){ 
      final List<Integer> duplicatesSet = new ArrayList<Integer>(); 
      final List<Integer> set = new ArrayList<Integer>();

      for (Integer yourInt : listContainingDuplicates){
    	  if (!set.add(yourInt)){
    		  duplicatesSet.add(yourInt);
    	  }
      }
      return duplicatesSet.contains(Score);
    }
    /**
     * Returns a String representation of this board. In addition to the current
     * situation, the String also shows the numbering of the fields.
     * 
     * @return the game situation as String
     */
    public String toString() {
    	int Integer = String.valueOf((DIM*DIM)-1).length();
    	String rowLine = "";
    	String DecimalFormat = "";
    	String board = "";
    	for(int i =0;  i < Integer; i++){
    		rowLine += "-";
    		DecimalFormat += "0";
    	}
    	
    	for (int row = 0; row < DIM; row++) {
            String rowTextFirst = "";
            for (int col = 0; col < DIM; col++) {
            	rowTextFirst = rowTextFirst + " " + fields[DIM * row + col].toString() + " ";
                if (col < DIM - 1) {
                	rowTextFirst = rowTextFirst + "|";
                }
            }
            String rowTextSecond = "";
            for (int col = 0; col < DIM; col++) {
            	DecimalFormat form = new DecimalFormat(DecimalFormat);
            	//String.format("%02d", myNumber)
            	rowTextSecond = rowTextSecond + " " + form.format((DIM * row + col)) + " ";
                if (col < DIM - 1) {
                	rowTextSecond = rowTextSecond + "|";
                }
            }
            board = board + rowTextFirst + DELIM + rowTextSecond;
            if (row < DIM - 1) {
                String rowLineFirst = "";
                for (int col = 0; col < DIM; col++) {
                	rowLineFirst = rowLineFirst + "--------";
                    if (col < DIM - 1) {
                    	rowLineFirst = rowLineFirst + "+";
                    }
                }
                String rowLineSecond = "";
                for (int col = 0; col < DIM; col++) {
                	rowLineSecond = rowLineSecond + rowLine + "--";
                    if (col < DIM - 1) {
                    	rowLineSecond = rowLineSecond + "+";
                    }
                }
            	board = board + "\n" + rowLineFirst + DELIM + rowLineSecond + "\n";
            }
        }
        return board;
    }

    private void print(String string){
    	System.out.println(string);
    }
    
    // -- Commands ---------------------------------------------------

    /**
     * Empties all fields of this board (i.e., let them refer to the value
     * Mark.EMPTY).
     */
    public void reset() {
        for (int i = 0; i < fields.length; i++) {
            setField(i, Mark.EMPTY_);
        }
        setMiddleFields();
    }

    /**
     * Sets the content of field <code>i</code> to the mark <code>m</code>.
     * 
     * @param i
     *            the field number (see NUMBERING)
     * @param m
     *            the mark to be placed
     */
    public void setField(int fieldIndex, Mark mark) {
        fields[fieldIndex] = mark;
    }
    
    public void setbeatenFields(int fieldIndex, Mark mark) {
    	Iterator<Integer> iterator = checkBeat(fieldIndex, mark).iterator();
    	while(iterator.hasNext()){
    		fields[iterator.next()] = mark;
    	}
    }
    
    public void setMiddleFields(){
    	int midCol1 = DIM/2-1;
    	int midCol2 = (DIM/2);
    	int midRow1 = ((DIM/2)*DIM)-DIM;
    	int midRow2 = (DIM/2)*DIM;
        setField((midRow1 + midCol1), Mark._RED__);
        setField((midRow1 + midCol2), Mark.YELLOW);
        setField((midRow2 + midCol1), Mark.GREEN_);
        setField((midRow2 + midCol2), Mark._BLUE_);
    }
}
