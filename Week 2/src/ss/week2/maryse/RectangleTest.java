package ss.week2.maryse;

public class RectangleTest {
		
	private Rectangle info;
	
	public void testInfo() {
		System.out.println("Setting test:");
		System.out.println("Length: " + info.length());
		System.out.println("Width: " + info.width());
		System.out.println("Area: " + info.area());
		System.out.println("Perimeter: " + info.perimeter());
		
	}
	
	public void TestRect() {
		info = new Rectangle(50,10);
	}
	
	public void doTest() {
		TestRect();
		testInfo();
	}

}
