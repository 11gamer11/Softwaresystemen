package info;

public class Logging {
	private static final int VIEWLEVEL = 0;
	
	public static final int DEBUG = 0;
	public static final int COMMAND = 1;
	public static final int INFO = 2;
	public static final int WARNING = 3;
	public static final int ERROR = 4;
	public static final int MSG = 5;
	public static final int INCOMMING_CMD = 6;
	public static final int OUTGOING_CMD = 7;
	public static final int BROADCAST = 8;
	public static final int QUOTE = 9;
	
	
	

    public static void log(int level, String string) {
        String levelString;
        switch(level) {
            case DEBUG:
                levelString = "DEBUG";
                break;
            case COMMAND:
                levelString = "COMMAND";
                break;
            case INFO:
                levelString = "INFO";
                break;
            case WARNING:
            	levelString = "WARNING";
                break;
        	case ERROR:
            	levelString = "ERROR";
                break;
            case MSG:
                levelString = "MSG";
                break;
            case INCOMMING_CMD:
                levelString = "INCOMMING-CMD";
                break;
            case OUTGOING_CMD:
                levelString = "OUTGOING-CMD";
                break;
            case BROADCAST:
                levelString = "BROADCAST";
                break;
            case QUOTE:
                levelString = "QUOTE";
                break;
            default:
                levelString = "INFO";
                break;
        }
        if (level >= Logging.VIEWLEVEL) {
        	System.out.println(levelString + ": " + string);        
        }
    }
}
