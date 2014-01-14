package ss.week3.hotel;

import java.io.PrintStream;

public class Bill {
	
	private PrintStream bill;
	private double totalPrice;

	public static interface Item {
		public double getAmount();
	}
	
	public Bill(PrintStream theOutStream){
		this.bill = theOutStream;
		this.totalPrice = 0;
	}
	
	public void newItem(Item item){
		double price = item.getAmount();
		this.totalPrice += price;
		
		this.bill.format("%-50s%10.2f\n", "Item Added:", price);
	}
	
	public double getSum(){
		return this.totalPrice;
	}
	
	public void close(){
		this.bill.format("%-50s%10.2f\n", "Total:", this.getSum());
	}	
}
