package ss.week4;

public class Exponent implements Function{
	public double n;
	
	public double apply(double x) {
		return Math.pow(x,this.n);
	}

	public Function derivative() {
		Function exponent = new Exponent(n-1);
		return exponent;
	}
	
	public Exponent(double n){
		this.n = n;
	}
	
	public String toString(){
		return ("f(x)=x^"+n);	
	}

}
