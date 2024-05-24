package entity;

import java.util.ArrayList;

import enums.RoomStatus;

public class Room {
	private String roomNumber;
	public String type;
	public RoomStatus status;
	public Cleaner cleaner;
	public ArrayList<Reservation> reservations;
	
	public Room(String roomNumber, String type, RoomStatus status, Cleaner cleaner) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.status = status;
		this.cleaner = cleaner;
		this.reservations = new ArrayList<Reservation>();
	}

	public String getRoomNumber() {
		return roomNumber;
	}
	
	@Override
	public String toString() {
		String str = "Broj sobe: " + this.roomNumber + "\nTip sobe: " + this.type + "\nStatus: " + this.status.toString();
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
			return this.getRoomNumber() + "," + this.type + "," + this.status.name();	
		}else{
			return this.getRoomNumber() + "," + this.type + "," + this.status.name() + "," + this.cleaner.getUsername();
		}
	}
	
	public Object[] toCell() {
		String cleanerString = null;
		if (this.cleaner == null) {
			cleanerString = "-";
		}else {
			cleanerString = this.cleaner.getUsername();
		}
		Object data[] = {this.getRoomNumber(), this.type, this.status, cleanerString};
		return data;
	}
}