package info;

public class Logging {
    private static final int VIEWLEVEL = 0;

    public static void log(int level, String string) {
        String levelString;
        switch(level) {
            case 0:
                levelString = "DEBUG";
                break;
            case 1:
                levelString = "INFO";
                break;
            case 2:
            	levelString = "WARNING";
                break;
        	case 3:
            	levelString = "ERROR";
                break;
            case 4:
                levelString = "MSG";
                break;
            case 5:
                levelString = "COMMAND";
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
