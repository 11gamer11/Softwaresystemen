package ss.week4;

public class Constant implements Function{
	
	private int c;

	public Constant(int c){
		this.c = c;
	}
	
	public int apply(int arg) {
		return c;
	}
	
	public Function derivative() {
		Function derivative = new Constant(0);
		return derivative;
	}
	
	public String toString(){
		return ("f(x)="+c);	
	}
	
}
