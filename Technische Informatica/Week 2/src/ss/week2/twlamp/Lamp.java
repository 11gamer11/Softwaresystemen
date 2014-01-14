package ss.week2.twlamp;

/**
 * Bepaalt welke stand
 * @author irish_000 & Jeroen_
 *@ version 1.0
 * beginstand is 0
 */
public class Lamp {
	/**@ invariant getLight() >= 0 && getLight() < 4 ;**/
	
	private Lampstate light = Lampstate.Off;
	public enum Lampstate {Off, Low, Medium, High}
	/**
	 * Maakt de gegeven value de stand 1 hoger tot 4 dan begint hij weer bij 0(uit-low-medium-high-uit)
	 * @param light
	 */
	
	/**@ ensures getLight() == (getLight() + 1) % 4;**/
	public void Switch() {
		int state = (Lampstate.valueOf(light.toString()).ordinal() + 1) % 4;
		light = Lampstate.values().clone()[state];
	}

	/**
	 * Zet de waarde van 'light' om in de bijbehorende stand van de lamp in tekst
	 * @return String
	 */
	
	/*8@ ensures getLight() == light;*/
	//@pure
	public Lampstate getLight(){
		return light;
	}
	
}