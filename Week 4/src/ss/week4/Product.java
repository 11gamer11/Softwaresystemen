package ss.week4;

public class Product implements Function{

	private Function g;
	private Function h;
	
	public Product(Function g, Function h){
		this.g = g;
		this.h = h;
	}
	
	public int apply(int x) {
		return g.apply(x)*h.apply(x);
	}

	public Function derivative() {
		return (new Sum(new Product(g.derivative(), h), new Product(g, h.derivative())));
	}
	
	public String toString() {
		return "f(x)=g(x)*h(x)";
	}

}
