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

	private static final int DIM = 8;
	private static final String DELIM = "   |   ";
	private static final int LEFT_DIRECTION = -1;
	private static final int RIGHT_DIRECTION = 1;

	// -- Instance variables -----------------------------------------

	/**
	 * The DIM by DIM fields of the Tic Tac Toe board.
	 */
	private Mark[] fields;

	// -- Constructors -----------------------------------------------

	/**
	 * Creates an empty board.
	 */
	public Board() {
		fields = new Mark[getDim() * getDim()];
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
	 * 
	 * @return <code>true</code> if <code>0 <= ix < DIM*DIM</code>
	 */
	public boolean isField(int ix) {
		return (0 <= ix) && (ix < getDim() * getDim());
	}

	/**
	 * Returns the content of the field <code>i</code>.
	 * 
	 * @param i
	 *            the number of the field.
	 * @return the mark on the field
	 */
	public Mark getField(int index) {
		return fields[index];
	}

	public static int getDim() {
		return DIM;
	}

	/**
	 * Converts field from i to coordinates.
	 * 
	 * @param index
	 *            the number of the field.
	 * @return IntArray with the first value = row & second value = column
	 */
	public int[] rowcol(int index) {
		int[] rowcol = new int[2];
		int row, col;

		row = (int) Math.floor(index / getDim());
		col = (int) index % getDim();

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
		return getField(i).equals(Mark.EMPTY);
	}

	/**
	 * Returns true if the field has any non-empty fields next to it.
	 * 
	 * @param field
	 *            the index of the field
	 * @return true if the field has any non-empty fields next to it.
	 */
	public boolean isCorrectField(int field) {
		if (isEmptyField(field)) {
			for (int row = 0; row < getDim() * getDim(); row += getDim()) {
				int firstField = row;
				int lastField = row + (getDim() - 1);
				if ((field == firstField) && isCorrectFirstFieldRow(field)) {
					return true;
				}
				if ((field > firstField && field < lastField)
						&& isCorrectMiddleFieldsRow(field)) {
					return true;
				}
				if ((field == lastField) && isCorrectLastFieldRow(field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if the first field of a row has any non-empty fields next to
	 * it.
	 */
	private boolean isCorrectFirstFieldRow(int field) {
		int checkField;
		for (int topDirection = 0; topDirection < 2; topDirection++) {
			checkField = field - getDim() + topDirection;
			if (isField(checkField) && !isEmptyField(checkField)) {
				return true;
			}
		}
		if (!isEmptyField(field + RIGHT_DIRECTION)) {
			return true;
		}
		for (int bottomDirection = 0; bottomDirection < 2; bottomDirection++) {
			checkField = field + getDim() + bottomDirection;
			if (isField(checkField) && !isEmptyField(checkField)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if a field between the first and the last field of a row has
	 * any non-empty fields next to it.
	 */
	private boolean isCorrectMiddleFieldsRow(int field) {
		int checkField;
		for (int topDirection = 0; topDirection < 3; topDirection++) {
			checkField = field - (getDim() + 1) + topDirection;
			if (isField(checkField) && !isEmptyField(checkField)) {
				return true;
			}
		}
		if (!isEmptyField(field + LEFT_DIRECTION)) {
			return true;
		}
		if (!isEmptyField(field + RIGHT_DIRECTION)) {
			return true;
		}
		for (int bottomDirection = 0; bottomDirection < 3; bottomDirection++) {
			checkField = field + (getDim() - 1) + bottomDirection;
			if (isField(checkField) && !isEmptyField(checkField)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the last field of a row has any non-empty fields next to
	 * it.
	 */
	private boolean isCorrectLastFieldRow(int field) {
		int checkField;
		for (int topDirection = 0; topDirection < 2; topDirection++) {
			checkField = field - (getDim() + 1) + topDirection;
			if (isField(checkField) && !isEmptyField(checkField)) {
				return true;
			}
		}
		if (!isEmptyField(field + LEFT_DIRECTION)) {
			return true;
		}
		for (int bottomDirection = 0; bottomDirection < 2; bottomDirection++) {
			checkField = field + (getDim() - 1) + bottomDirection;
			if (isField(checkField) && !isEmptyField(checkField)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a list with all the correct fields.
	 * 
	 * @return a list with the index of all the correct fields.
	 */
	public List<Integer> getAllCorrectFields() {
		List<Integer> allCorrectFields = new ArrayList<Integer>();

		List<Integer> markFields = getAllFields(Mark.EMPTY);
		Iterator<Integer> iterator = markFields.iterator();
		while (iterator.hasNext()) {
			int field = iterator.next();
			if (isCorrectField(field)) {
				allCorrectFields.add(field);
			}
		}
		return allCorrectFields;
	}

	/**
	 * Returns a list with all the fields that are hit from a certain field for
	 * a certain Mark.
	 * 
	 * @param field
	 *            the index of the field
	 * @param mark
	 *            the mark that will be in the field
	 * @return a list with fields
	 */
	public List<Integer> checkBeat(int field, Mark mark) {
		List<Integer> beatenFields = new ArrayList<Integer>();

		for (int row = 0; row < getDim(); row++) {
			int firstField = row * getDim();
			int lastField = row * getDim() + (getDim() - 1);
			if (field == firstField) {
				beatenFields.addAll(checkBeatFirstFieldRow(field, mark));
			}
			if (field > firstField && field < lastField) {
				beatenFields.addAll(checkBeatMiddleFieldsRow(field, mark));
			}
			if (field == lastField) {
				beatenFields.addAll(checkBeatLastFieldRow(field, mark));
			}
		}
		return beatenFields;
	}

	/**
	 * Returns a list with all the fields that are hit from a field in the first
	 * column for a certain Mark.
	 */
	private List<Integer> checkBeatFirstFieldRow(int field, Mark mark) {
		List<Integer> beatenFields = new ArrayList<Integer>();
		List<Integer> tmpFields = new ArrayList<Integer>();
		for (int topDirection = 0; topDirection < 2; topDirection++) {
			int direction = getDim() - topDirection;
			for (int level = 1; level < getDim()
					&& checkField(-direction, field, level, false, true); level++) {
				int beatField = field - (direction * level);
				tmpFields.add(beatField);
			}
			beatenFields.addAll(checkTempFieldList(tmpFields, mark));
			tmpFields = new ArrayList<Integer>();
		}
		for (int bottomDirection = 0; bottomDirection < 2; bottomDirection++) {
			int direction = getDim() + bottomDirection;
			for (int level = 1; level < getDim()
					&& checkField(direction, field, level, false, true); level++) {
				int beatField = field + (direction * level);
				tmpFields.add(beatField);
			}
			beatenFields.addAll(checkTempFieldList(tmpFields, mark));
			tmpFields = new ArrayList<Integer>();
		}
		for (int level = 1; level < getDim()
				&& checkField(RIGHT_DIRECTION, field, level, false, true); level++) {
			int beatField = field + level;
			tmpFields.add(beatField);
		}
		beatenFields.addAll(checkTempFieldList(tmpFields, mark));
		tmpFields = new ArrayList<Integer>();
		return beatenFields;
	}

	/**
	 * Returns a list with all the fields that are hit from a field between the
	 * first and the last field of a row in the middle column for a certain
	 * Mark.
	 */
	private List<Integer> checkBeatMiddleFieldsRow(int field, Mark mark) {
		List<Integer> beatenFields = new ArrayList<Integer>();
		List<Integer> tmpFields = new ArrayList<Integer>();
		for (int topDirection = 0; topDirection < 3; topDirection++) {
			int direction = (getDim() - 1) + topDirection;
			for (int level = 1; level < getDim()
					&& checkField(-direction, field, level, true, true); level++) {
				int beatField = field - (direction * level);
				tmpFields.add(beatField);
			}
			beatenFields.addAll(checkTempFieldList(tmpFields, mark));
			tmpFields = new ArrayList<Integer>();
		}
		for (int bottomDirection = 0; bottomDirection < 3; bottomDirection++) {
			int direction = (getDim() + 1) - bottomDirection;
			for (int level = 1; level < getDim()
					&& checkField(direction, field, level, true, true); level++) {
				int beatField = field + (direction * level);
				tmpFields.add(beatField);
			}
			beatenFields.addAll(checkTempFieldList(tmpFields, mark));
			tmpFields = new ArrayList<Integer>();
		}
		for (int level = 1; level < getDim()
				&& checkField(LEFT_DIRECTION, field, level, true, false); level++) {
			int beatField = field - level;
			tmpFields.add(beatField);
		}
		beatenFields.addAll(checkTempFieldList(tmpFields, mark));
		tmpFields = new ArrayList<Integer>();
		for (int level = 1; level < getDim()
				&& checkField(RIGHT_DIRECTION, field, level, false, true); level++) {
			int beatField = field + level;
			tmpFields.add(beatField);
		}
		beatenFields.addAll(checkTempFieldList(tmpFields, mark));
		tmpFields = new ArrayList<Integer>();
		return beatenFields;
	}

	/**
	 * Returns a list with all the fields that are hit from a field in the last
	 * field of a row for a certain Mark.
	 */
	private List<Integer> checkBeatLastFieldRow(int field, Mark mark) {
		List<Integer> beatenFields = new ArrayList<Integer>();
		List<Integer> tmpFields = new ArrayList<Integer>();
		for (int topDirection = 0; topDirection < 2; topDirection++) {
			int direction = getDim() + topDirection;
			for (int level = 1; level < getDim()
					&& checkField(-direction, field, level, true, false); level++) {
				int beatField = field - (direction * level);
				tmpFields.add(beatField);
			}
			beatenFields.addAll(checkTempFieldList(tmpFields, mark));
			tmpFields = new ArrayList<Integer>();
		}
		for (int bottomDirection = 0; bottomDirection < 2; bottomDirection++) {
			int direction = getDim() - bottomDirection;
			for (int level = 1; level < 8
					&& checkField(direction, field, level, true, false); level++) {
				int beatField = field + (direction * level);
				tmpFields.add(beatField);
			}
			beatenFields.addAll(checkTempFieldList(tmpFields, mark));
			tmpFields = new ArrayList<Integer>();
		}
		for (int level = 1; level < getDim()
				&& checkField(LEFT_DIRECTION, field, level, true, false); level++) {
			int beatField = field - level;
			tmpFields.add(beatField);
		}
		beatenFields.addAll(checkTempFieldList(tmpFields, mark));
		tmpFields = new ArrayList<Integer>();
		return beatenFields;
	}

	/**
	 * checkTempFieldList returns a list with all the fields till the first Mark
	 * is found in the input list.
	 * 
	 * @param list
	 *            a list with field indexes
	 * @param mark
	 *            the Mark of the current player
	 * @return a list with fields
	 */
	private List<Integer> checkTempFieldList(List<Integer> list, Mark mark) {
		List<Integer> returnList = new ArrayList<Integer>();
		List<Mark> tmpMarkList = new ArrayList<Mark>();

		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			tmpMarkList.add(getField(iterator.next()));
		}
		int firstMark = tmpMarkList.indexOf(mark);
		if (firstMark != -1) {
			for (int index = 0; index < firstMark; index++) {
				returnList.add(list.get(index));
			}
		}
		return returnList;
	}

	/**
	 * checkField checks if the field exists, not empty is and checks if it is
	 * not in the first or last column if set.
	 * 
	 * @param math
	 *            is the math used for the asked field (direction)
	 * @param currField
	 *            is the field from where it's checked
	 * @param level
	 *            is the level of the field that will be checked
	 * @param left
	 *            boolean if the left (first) column has to be checked
	 * @param right
	 *            boolean if the right (last) column has to be checked
	 * @return true if the field exist, not empty is and not in the first of
	 *         last column is if set
	 */
	private boolean checkField(int math, int currField, int level,
			boolean left, boolean right) {
		return isField(currField + (math * level))
				&& !getField(currField + (math * level)).equals(Mark.EMPTY)
				&& !checkCol(currField + (math * (level - 1)), left, right);
	}

	/**
	 * checkCol checks if a field is in the first or last column if set.
	 * 
	 * @param field
	 *            has to be checked
	 * @param left
	 *            boolean if the left (first) column has to be checked
	 * @param right
	 *            boolean if the right (last) column has to be checked
	 * @return true if the field is in the first or last column if set
	 */
	private boolean checkCol(int field, boolean left, boolean right) {
		if (left) {
			for (int i = 0; i < getDim(); i++) {
				if ((getDim() * i) == field) {
					return true;
				}
			}
		}
		if (right) {
			for (int i = 0; i < getDim(); i++) {
				if ((getDim() * i + (getDim() - 1)) == field) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * checkAllBeatingFields lists all the fields that can beat other fields for
	 * a certain mark.
	 * 
	 * @param mark
	 *            the mark that has to be checked
	 * @return a list of fields
	 */
	public List<Integer> checkAllBeatingFields(Mark mark) {
		List<Integer> beatingFields = new ArrayList<Integer>();
		List<Integer> markFields = getAllCorrectFields();
		if (!markFields.isEmpty()) {
			Iterator<Integer> iterator = markFields.iterator();
			while (iterator.hasNext()) {
				int beatingField = iterator.next();
				if (!checkBeat(beatingField, mark).isEmpty()) {
					beatingFields.add(beatingField);
				}
			}
		}
		return beatingFields;
	}

	/**
	 * allAllowedFields lists all fields that the mark is allowed to play.
	 * 
	 * @param mark
	 *            the mark of the current player
	 * @return lists with fields
	 */
	public List<Integer> allAllowedFields(Mark mark) {
		if (!checkAllBeatingFields(mark).isEmpty()) {
			return checkAllBeatingFields(mark);
		} else {
			return getAllCorrectFields();
		}
	}

	/**
	 * checkField checks if the given field is a correct field.
	 * 
	 * @param choice
	 * @param mark
	 * @return
	 */
	public boolean checkField(int choice, Mark mark) {
		Iterator<Integer> iterator = allAllowedFields(mark).iterator();
		while (iterator.hasNext()) {
			if (iterator.next().equals(choice)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * getAllFields lists all the fields of a certain mark.
	 * 
	 * @param mark
	 *            of a player
	 * @return a list of all the fields of a certain mark
	 */
	public List<Integer> getAllFields(Mark mark) {
		List<Integer> markFields = new ArrayList<Integer>();
		for (int i = 0; i < fields.length; i++) {
			if (getField(i).equals(mark)) {
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
		Mark[] marks = Mark.values();
		List<Integer> scores = new ArrayList<Integer>();
		for (Mark mark : marks) {
			scores.add(getAllFields(mark).size());
		}
		int topScore = Collections.max(scores);
		if (!findDuplicateTopScores(scores, topScore)) {
			int score = scores.indexOf(topScore);
			return marks[score];
		}
		return null;
	}

	/**
	 * findDuplicateTopScores finds duplicates in the top score list to check
	 * for a draw.
	 * 
	 * @param listContainingDuplicates
	 *            list that has to be checked
	 * @param Score
	 *            score that has to be checked
	 * @return
	 */
	public boolean findDuplicateTopScores(
			List<Integer> listContainingDuplicates, int score) {
		int numberOfDuplicates = 0;
		for (int i = 0; i < listContainingDuplicates.size(); i++) {
			if (listContainingDuplicates.get(i) == score) {
				numberOfDuplicates += 1;
			}
		}
		return numberOfDuplicates > 1;
	}

	/**
	 * Returns a String representation of this board. In addition to the current
	 * situation, the String also shows the numbering of the fields.
	 * 
	 * @return the game situation as String
	 */
	public String toString() {
		int integer = String.valueOf((getDim() * getDim()) - 1).length();
		String rowLine = "";
		String decimalFormat = "";
		String board = "";
		for (int i = 0; i < integer; i++) {
			rowLine += "-";
			decimalFormat += "0";
		}

		for (int row = 0; row < getDim(); row++) {
			String rowTextFirst = "";
			for (int col = 0; col < getDim(); col++) {
				rowTextFirst = rowTextFirst + " "
						+ fields[getDim() * row + col].toString() + " ";
				if (col < getDim() - 1) {
					rowTextFirst = rowTextFirst + "|";
				}
			}
			String rowTextSecond = "";
			for (int col = 0; col < getDim(); col++) {
				DecimalFormat form = new DecimalFormat(decimalFormat);
				rowTextSecond = rowTextSecond + " "
						+ form.format(getDim() * row + col) + " ";
				if (col < getDim() - 1) {
					rowTextSecond = rowTextSecond + "|";
				}
			}
			board = board + rowTextFirst + DELIM + rowTextSecond;
			if (row < getDim() - 1) {
				String rowLineFirst = "";
				for (int col = 0; col < getDim(); col++) {
					rowLineFirst = rowLineFirst + "--------";
					if (col < getDim() - 1) {
						rowLineFirst = rowLineFirst + "+";
					}
				}
				String rowLineSecond = "";
				for (int col = 0; col < getDim(); col++) {
					rowLineSecond = rowLineSecond + rowLine + "--";
					if (col < getDim() - 1) {
						rowLineSecond = rowLineSecond + "+";
					}
				}
				board = board + "\n" + rowLineFirst + DELIM + rowLineSecond
						+ "\n";
			}
		}
		return board;
	}

	// -- Commands ---------------------------------------------------

	/**
	 * Empties all fields of this board (i.e., let them refer to the value
	 * Mark.EMPTY).
	 */
	public void reset() {
		for (int i = 0; i < fields.length; i++) {
			setField(i, Mark.EMPTY);
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

	/**
	 * setBeatenFields sets a field to the new mark after it's being beaten.
	 * 
	 * @param fieldIndex
	 *            index of the field
	 * @param mark
	 *            to which mark it has to be set
	 */
	public void setBeatenFields(int fieldIndex, Mark mark) {
		Iterator<Integer> iterator = checkBeat(fieldIndex, mark).iterator();
		while (iterator.hasNext()) {
			fields[iterator.next()] = mark;
		}
	}

	/**
	 * setMiddleFields sets the four fields in the middle to the corresponding
	 * colors.
	 */
	public void setMiddleFields() {
		int midCol1 = getDim() / 2 - 1;
		int midCol2 = getDim() / 2;
		int midRow1 = ((getDim() / 2) * getDim()) - getDim();
		int midRow2 = (getDim() / 2) * getDim();
		setField(midRow1 + midCol1, Mark.RED);
		setField(midRow1 + midCol2, Mark.YELLOW);
		setField(midRow2 + midCol1, Mark.GREEN);
		setField(midRow2 + midCol2, Mark.BLUE);
	}

}
