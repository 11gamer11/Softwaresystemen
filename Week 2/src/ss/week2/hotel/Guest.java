package ss.week2.hotel;

import ss.week2.hotel.Room;

public class Guest {
	 // ------------------ Instance variables ----------------
	
	private String name;
	private Room room;

    // ------------------ Constructor ------------------------
	
    /**
     * Creates a <code>Guest</code> with the given name, without a room.
     * @param name of the new <code>Guest</code>
     */	
	
	public Guest(String n){
		name = n;
	}
	
    // ------------------ Queries --------------------------

    /**
     * Returns the name of this <code>Guest</code>.
     * @return Guest Name
     * 
     */
	public String getName(){
		return name;
	}
	
	/**
     * Returns the number of this <code>Room</code>.
     * @return Room Number
     * 
     */
	public Room getRoom(){
		return room;
	}
	
	// ------------------ Commands --------------------------

	/**
     * Checks the Guest in in the <code>Room</code>.
     * @return	true if the room exists and is not occupied
     * 			false otherwise
     */
	public boolean checkin(Room r){
		if(r.getGuest() == null && this.room == null){
			r.setGuest(this);
			this.room = r;
			return true;
		}
		return false;
	}
	
    /**
     * Checks the Guest out.
     * @return	true if the room exists and if the guest occupies the room
     * 			false otherwise
     */
	public boolean checkout(){
		if(this.room != null && room.getGuest() != null){
			room.setGuest(null);
			this.room = null;
			return true;
		}
		return false;
	}
	
	public String toString() {
        return "Guest: " + getName();
    }

}

