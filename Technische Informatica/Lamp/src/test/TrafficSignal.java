package test;

public class TrafficSignal {

	/**
	 * @param args
	 */
			
	private int light = 0;
	
	public int light(){
		return light;
	}
	
	public void change(int value) {
		light = value;
	}

}
