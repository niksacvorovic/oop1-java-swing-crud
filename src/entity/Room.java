package entity;

import java.util.ArrayList;

import enums.RoomStatus;
import enums.RoomType;

public class Room {
	private String roomNumber;
	public RoomType type;
	public RoomStatus status;
	public Cleaner cleaner;
	public ArrayList<Reservation> reservations;
	
	public Room(String roomNumber, RoomType type, RoomStatus status, Cleaner cleaner) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.status = status;
		this.cleaner = null;
		this.reservations = new ArrayList<Reservation>();
	}

	public String getRoomNumber() {
		return roomNumber;
	}
	
	@Override
	public String toString() {
		String str = "Broj sobe: " + this.roomNumber + "\nTip sobe: " + this.type.toString() + "\nStatus: " + this.status.toString();
		if (this.cleaner == null) {
			str += "\nOdržavanje: nema\nRezervacije: ";
		}else {
			str += "\nOdržavanje:" + this.cleaner.getUsername() + "\nRezervacije: ";
		}
		if (this.reservations.isEmpty()){
			str += "nema";
		}else{
			for(Reservation i:this.reservations) {
				str += i.getID() + " ";
			}
		}
		return str;
	}
	
	public String toFileString() {
		if (this.cleaner == null) {
			return this.getRoomNumber() + "," + this.type.name() + "," + this.status.name();	
		}else{
			return this.getRoomNumber() + "," + this.type.name() + "," + this.status.name() + "," + this.cleaner.getUsername();
		}
		
	}
}