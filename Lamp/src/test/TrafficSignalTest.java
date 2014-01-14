package test;

public class TrafficSignalTest {

	/**
	 * @param args
	 */
	
	private TrafficSignal signal;
		
	private void testChange(){
		System.out.println("testChange:");
		System.out.println("Starting Light: " + signal.light());	
		signal.change(1);
		System.out.println("After 1 change: " + signal.light());	
		signal.change(3);
		System.out.println("After 2 changes: " + signal.light());	
		signal.change(2);
		System.out.println("After 3 changes: " + signal.light());	
		
	}
	
	public TrafficSignalTest(){
		signal = new TrafficSignal();
	}
	
	public void runTest(){
		testChange();
	}

}
