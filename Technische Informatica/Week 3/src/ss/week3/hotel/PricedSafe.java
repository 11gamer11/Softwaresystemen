package ss.week3.hotel;

import ss.week2.hotel.Safe;
import ss.week2.hotel.Password;

public class PricedSafe extends Safe implements Bill.Item{

	private double priceSafe;
	
	public PricedSafe(Password safePassword, double price) {
		super(safePassword);
		this.priceSafe = price;
	}

	public double getAmount() {
		return this.priceSafe;
	}
	
	public String toString(){
		return super.toString()+"\n"+"Safe-price:"+getAmount();
	}

}
