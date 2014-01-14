package ss.week2;

public class Rectangle {
	//@ invariant length() > 0;
	//@ invariant width() > 0;

	private int length;
	private int width;	
	
	//@ requires length > 0;
	//@ requires width > 0;
	
	//@ ensures length() == length;
	//@ ensures width() == width;
	public Rectangle (int length, int width){
		this.length = length;
		this.width = width;
	}
	
	//@pure
	public int length(){
		return this.length;
	}
	
	//@pure
	public int width(){
		return this.width;	
	}
	
	//@ ensures \result == length() * width();
	public int area(){
		int area = this.length*this.width;
		return area;
	}
	
	//@ ensures \result == 2 * (length() + width());
	public int perimeter(){
		int perimeter = 2*(this.length+this.width);
		return perimeter;
	}

}
