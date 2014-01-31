package ss.week7.threads;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Class for default input/output
 * @author  Arend Rensink en Theo Ruys.
 */
public class SynConsole {
	/** sign for wrong input */
	static public final char FOUT = '\u0004';

	static private BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));
	static private PrintStream out = System.out;

	private SynConsole() {

	}

	/**
	 * write a text on the default output
	 * @param text text to be written
	 * @return 
	 */
	static public synchronized void print(String text) {
		out.print(text);
	}

	/**
	 * write a text on the default output, ending with a new line
	 * @param text text to be written
	 */
	static public synchronized void println(String text) {
		out.println(text);
	}

	/**
	 * Reads a line from the default input. 
	 * @param  text question
	 * @return readed text (never null)
	 */
	static public synchronized String readString(String text) {
		print(text);
		String antw = null;
		try {
			antw = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (antw == null) {
			return "" + FOUT;
		} else {
			return antw;
		}
	}

	/**
	 * Reads an integer from the default input
	 * @param  text vraagtekst
	 * @return ingevoerd getal
	 */
	static public synchronized int readInt(String text) {
		return readInt(text, "Enter a integer");
	}

	/**
	 * Reads an integer from the default input
	 * With an invalid input the question will be asked again
	 * @param text question
	 * @param errorMessage error messsage for wrong input
	 * @return number
	 */
	static public synchronized int readInt(String text, String errorMessage) {
		do {
			String antw = readString(text);
			try {
				return Integer.parseInt(antw);
			} catch (NumberFormatException e) {
				println(errorMessage);
			}
		} while (true);
	}
}
