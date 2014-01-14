package ss.week3;

public class Format {
	
	public static void printLine(String text, double number){
		System.out.println(String.format("%-30s%10.2f", text, number));
	}
	
	public static void main(String[] args){
		printLine("Zero:", 0);
		printLine("Pi:", 3.141592653);
		printLine("The answer to everything:", 42);
		printLine("4w50m3 73x7 w171n6:", 1337);
		printLine("USS Enterprise:", 1701);
	}
	
}
