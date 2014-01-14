package ss.week4;

public class Exponent implements Function{
	
	private int n;
	
	public Exponent(int n){
		this.n = n;
	}
	
	public int apply(int x) {
		return (int) Math.pow(x,this.n);
	}

	public Function derivative() {
		Function exponent = new Exponent(n-1);
		return exponent;
	}
	
	public String toString(){
		return ("f(x)=x^"+n);	
	}

}
