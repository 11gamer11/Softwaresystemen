package ss.week4;

public class Sum implements Function{

	private Function g;
	private Function h;
	
	public Sum(Function g, Function h){
		this.g = g;
		this.h = h;
	}
	
	public int apply(int x) {
		return g.apply(x) + h.apply(x);
	}

	public Function derivative() {
		return new Sum(g.derivative(), h.derivative());
	}
	
	public String toString(){
		return ("f(x)=g(x)+h(x)");
	}
	
}
