package ss.week2.hotel;

public class Safe {
	
	// ------------------ Instance variables ----------------
	
		private Password password;
		
		public enum ActiveState {OFF,ON};
		public enum OpenState {OPENED, CLOSED};
		
		private ActiveState stateActive = ActiveState.OFF;
		private OpenState safeOpen = OpenState.CLOSED;
	
	// ------------------ Constructor ------------------------
	
		public Safe(Password safePassword){
			this.password = safePassword;
			assert this.password == safePassword;
		}
	
	// ------------------ Commands ------------------------
	
	//	Activates safe if password is correct.
		public void activate(String passwordTest){
			if (password.testWord(passwordTest)){
				this.stateActive = ActiveState.ON;
				assert this.stateActive == ActiveState.ON;
			}
		}
	
	//	Deactivates safe and closes it.
		public void deactivate(){
			close();
			this.stateActive = ActiveState.OFF;
			assert this.stateActive == ActiveState.OFF;
		}
	
	//	Opens safe if password is correct and safe is activated.
		public void open(String passwordTest){
			if (password.testWord(passwordTest)){
				if (this.stateActive == ActiveState.ON){
					this.safeOpen = OpenState.OPENED;
					assert this.safeOpen == OpenState.OPENED;
				}
			}
		}
	
	//	Closes safe.
		public void close(){
			this.safeOpen = OpenState.CLOSED;
		}
	
	// ------------------ Queries --------------------------
	
	//	Checks if safe is active.
		//@pure;
		public boolean isActive(){
			return this.stateActive == ActiveState.ON;
		}
	
	//	Checks if safe is open.
		//@pure;
		public boolean isOpen(){
			return this.safeOpen == OpenState.OPENED;
		}
	
	//	Returns password object.
		//@pure;
		public Password getPassword(){
			return this.password;
		}
	
	// toString method
		public String toString(){
			return "Safe:"+stateActive;
		}

}