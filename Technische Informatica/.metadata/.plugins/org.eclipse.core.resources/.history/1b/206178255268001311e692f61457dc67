package ss.week4;

public class Constant implements Function{
	public double c;
	
	public double apply(double arg) {
		return c;
	}

	public Function derivative() {
		Function derivative = new Constant(0);
		return derivative;
	}
	
	public Constant(double c){
		this.c = c;
	}
	
	public String toString(){
		return ("f(x)="+c);	
	}
	
}
