package ss.week4;

public class Polynomial implements Function{

	private Function[] functions;
	
	public Polynomial(Function[] functions){
		this.functions = functions;
	}
	
	public int apply(int arg) {
		int result = 0;
		for(int n=0; n<functions.length; n++){
			result = result + functions[n].apply(arg);
			arg--;
		}
		return result;
	}
	
	public Function derivative() {
		Function sum = functions[0].derivative();
		for(int n=1; n<functions.length; n++){
			sum = new Sum(sum, functions[n].derivative());
		}
		return sum;
	}

	public String toString(){
		return null;
	}
}
