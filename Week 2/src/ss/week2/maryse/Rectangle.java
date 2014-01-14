package ss.week2.maryse;

public class Rectangle {
	
	public int lengthR;
	public int widthR;
	
	public Rectangle (int length, int width){
		lengthR = length;
		widthR = width;
	}

	public int length() {
		return lengthR;
	}
	
	public int width() {
		return widthR;
	}
	
	public int area() {
		return (lengthR * widthR);
	}
	
	public int perimeter(){
		return (2 * lengthR + 2 * widthR);
	}
}
