package hotel;

import java.util.ArrayList;

import entity.Request;
import entity.Room;
import manage.PricingManager;
import manage.ReservationManager;
import manage.RoomManager;
import manage.UserManager;

public class Hotel {
	public PricingManager pm;
	public ReservationManager rem;
	public RoomManager rom;
	public UserManager um;
	
	public void validateRequest(Request r) {
		ArrayList<Room> available = new ArrayList<Room>();
		for(Room i:rom.rooms) {
			
		}
	}
}
