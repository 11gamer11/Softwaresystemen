package ss.week2.twlamp;

public class TestLamp {

	/**
	 * @param args
	 */
	private Lamp stand;
	
	public void testStand() {
		System.out.println("testStand:");
		System.out.println("Eerste Stand: " + stand.getLight());
		stand.Switch();
		System.out.println("Stand na switch: " + stand.getLight());
		stand.Switch();
		System.out.println("Stand na switch: " + stand.getLight());
		stand.Switch();
		System.out.println("Stand na switch: " + stand.getLight());
		stand.Switch();
		System.out.println("Stand na switch: " + stand.getLight());
		stand.Switch();
		System.out.println("Stand na switch: " + stand.getLight());
		
	}
	
	public TestLamp(){
		stand = new Lamp();
	}
	
	public void runTest(){
		testStand();
	}
	
}
