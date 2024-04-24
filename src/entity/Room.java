package entity;

import java.util.ArrayList;

import enums.RoomStatus;
import enums.RoomType;

public class Room {
	private String roomNumber;
	public RoomType type;
	public RoomStatus status;
	public ArrayList<Reservation> reservations;
	
	public Room(String roomNumber, RoomType type, RoomStatus status) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.status = status;
		this.reservations = new ArrayList<Reservation>();
	}

	public String getRoomNumber() {
		return roomNumber;
	}
	
	@Override
	public String toString() {
		String str = "Broj sobe: " + this.roomNumber + "\nTip sobe: " + this.type.toString() + "\nStatus: " + this.status.toString() +
				"\nRezervacije: ";
		if (this.reservations.isEmpty()){
			str += "nema";
		}else{
			for(Reservation i:this.reservations) {
				str += i.getID() + " ";
			}
		}
		return str;
	}
}