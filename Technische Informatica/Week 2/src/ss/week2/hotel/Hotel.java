package ss.week2.hotel;

public class Hotel {
	
	// ------------------ Instance variables ----------------

		private String nameHotel;
		
		private Password password;
		
		private Room room1;
		private Room room2;
	
	// ------------------ Constructor ----------------
		
		public Hotel(String name){
			this.nameHotel = name;
			this.password = new Password();
			this.room1 = new Room(101, new Safe(this.password));
			this.room2 = new Room(102, new Safe(this.password));
		}

	// ------------------ Commands ------------------------
	
	//	Checks the guest in if the password is correct, their are free rooms and the guest doesn't have a room yet
		public Room checkIn(String password, String name){
			
			// If hotel full, return null
				if(this.getFreeRoom() == null){
					return null;
				}
			
			// Checks if the password is correct and the guest is not checked in already
				if(this.password.testWord(password) && ( (room1.getGuest() == null) && (room2.getGuest() == null) || (room1.getGuest() == null) && (room2.getGuest().getName() != name) || (room1.getGuest().getName() != name) && (room2.getGuest() == null) || (room1.getGuest().getName() != name) && (room2.getGuest().getName() != name)) ){
					Room room = this.getFreeRoom();
					Guest guest = new Guest(name);
					guest.checkin(room);
					return room;
				}
			return null;
		}
	
	// Checks the guest out if it has a room	
		public void checkOut(String name){
			if(room1.getGuest() != null && room1.getGuest().getName().equals(name)){
				room1.getGuest().checkout();
				room1.getSafe().deactivate();
			}
			if(room2.getGuest() != null && room2.getGuest().getName().equals(name)){
				room2.getGuest().checkout();
				room2.getSafe().deactivate();
			}
		}
		
		// ------------------ Queries --------------------------
	
	// Returns the first room that's free	
		//@pure
		public Room getFreeRoom(){
			if(room1.getGuest() == null){
				return room1;
			}else if(room2.getGuest() == null){
				return room2;
			}
			return null;
		}
	
	// Returns the room of the guest
		//@pure
		public Room getRoom(String name){
			if(room1.getGuest() != null && room1.getGuest().getName().equals(name)){
				return room1;
			}
			if(room2.getGuest() != null && room2.getGuest().getName().equals(name)){
				return room2;
			}
			return null;
		}
	
	// Returns password currently used	
		//@pure
		public Password getPassword(){
			return this.password;
		}
	
	// Returns name of hotel	
		//@pure
		public String getName(){
			return this.nameHotel;
		}		
		
	// toString method	
		//@pure
		public String toString(){
			Guest room1Guest = room1.getGuest();
			Guest room2Guest = room2.getGuest();
			String room1GuestString = "None";
			String room2GuestString = "None";
			if(room1Guest != null){room1GuestString = room1Guest.toString();}
			if(room2Guest != null){room2GuestString = room2Guest.toString();}
			
			String description = "Hotel: "+getName()+"\n"
					+ " " + room1.toString() + "\n"
					+ "  " + room1GuestString + "\n"
					+ "  " + room1.getSafe().toString() + "\n"
					+ " " + room2.toString() + "\n"
					+ "  " + room2GuestString + "\n"
					+ "  " + room2.getSafe().toString() + "\n";
			return description;
		}
}
