package ss.week7.threads;

public class TestSynConsole extends Thread {

	TestSynConsole(String name){
		this.setName(name);
	}
	
	private void sum(){
		int int_1, int_2;
		String thread = this.getName();
			int_1 = SynConsole.readInt(thread+": get number 1?");
			int_2 = SynConsole.readInt(thread+": get number 2?");
		System.out.println("\n"+thread+": "+int_1+" + "+int_2+" = "+(int_1+int_2));
	}
	
	public void run(){
		this.sum();
	}
	
	public static void main(String[] args){
		TestSynConsole con1 = new TestSynConsole("Thread A");
		TestSynConsole con2 = new TestSynConsole("Thread B");
		con1.start();
		con2.start();
	}
}
