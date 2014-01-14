package twlamp;

/**
 * Bepaalt welke stand
 * @author irish_000 & Jeroen_
 *@ version 1.0
 * beginstand is 0
 */
public class Lamp {
	
	/**
	 * @param args
	 */
	
	public int light = 0;
	
	/**
	 * Maakt de gegeven value de stand 1 hoger tot 4 dan begint hij weer bij 0(uit-low-medium-high-uit)
	 * @param light
	 */
	
	public void Switch() {
			light = (light + 1) % 4;
	}

	/**
	 * Zet de waarde van 'light' om in de bijbehorende stand van de lamp in tekst
	 * @return String
	 */
	public String getLight(){
		if(light==0){
			return "Off";
		}
		if(light==1){
			return "Low";
		}
		if(light==2){
			return "Medium";
		}
		if(light==3){
			return "High";
		}
		else{
			return null;
		}
	}

}