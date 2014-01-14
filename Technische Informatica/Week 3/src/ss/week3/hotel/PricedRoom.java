package ss.week3.hotel;

import ss.week2.hotel.Password;
import ss.week2.hotel.Room;

public class PricedRoom extends Room implements Bill.Item{

	private double priceRoom;
	private int numberRoom;
	
	public PricedRoom(int number, double price_room, double price_safe) {
		super(number, new PricedSafe(new Password(), price_safe));
		this.priceRoom = price_room;
		this.numberRoom = number;
	}

	public double getAmount() {
		return this.priceRoom;
	}
	
	public String toString(){
		return super.toString()+"\n"+"Price of room"+this.numberRoom+": "+this.priceRoom;
	}


}
