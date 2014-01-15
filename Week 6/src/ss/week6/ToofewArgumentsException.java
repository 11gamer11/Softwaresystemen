package ss.week6;

@SuppressWarnings("serial")
public class ToofewArgumentsException extends WrongArgumentException{
	
	public ToofewArgumentsException(){
		super("error: must pass two command line arguments");
	}
	
}
