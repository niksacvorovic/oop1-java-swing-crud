package entity;

import java.time.LocalDate;

import enums.Extras;
import enums.ReservationStatus;
import enums.RoomType;

public class Request {
	private String ID;
	public Guest guest;
	public ReservationStatus status;
	public RoomType type;
	public LocalDate begin;
	public LocalDate end;
	public Extras[] extras;
	
	public Request(String ID, Guest guest, ReservationStatus status, RoomType type, LocalDate begin, LocalDate end, Extras... params) {
		this.ID = ID;
		this.guest = guest;
		this.status = status;
		this.type = type;
		this.begin = begin;
		this.end = end;
		this.extras = params;
	}

	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	@Override
	public String toString() {
		String str = "ID: " + this.ID + "\nGost: " + this.guest.getUsername() + "\nStatus: " + this.status.toString() + "\nTip sobe: " + 
				this.type.toString() + "\nPočetak: " + this.begin.toString() + "\nKraj: " + this.end.toString() + "\nDodatne usluge: ";
		for(Extras i:extras) {
			str += i.toString() + " ";
		}
		return str;
	}
}
