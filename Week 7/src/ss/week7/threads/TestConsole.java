package ss.week7.threads;

public class TestConsole extends Thread {

	TestConsole(String name){
		this.setName(name);
	}
	
	private void sum(){
		int int_1, int_2;
		String thread = this.getName();
		synchronized(Console.class){
			int_1 = Console.readInt(thread+": get number 1?");
			int_2 = Console.readInt(thread+": get number 2?");
		}
		System.out.println(thread+": "+int_1+" + "+int_2+" = "+(int_1+int_2));
	}
	
	public void run(){
		this.sum();
	}
	
	public static void main(String[] args){
		TestConsole con1 = new TestConsole("Thread A");
		TestConsole con2 = new TestConsole("Thread B");
		con1.start();
		con2.start();
	}
}
