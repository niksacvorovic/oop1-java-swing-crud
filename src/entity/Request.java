package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.ReservationStatus;
import enums.RoomType;

public class Request {
	private String ID;
	public Guest guest;
	public ReservationStatus status;
	public RoomType type;
	public LocalDate begin;
	public LocalDate end;
	public ArrayList<String> services;
	
	public Request(String ID, Guest guest, ReservationStatus status, RoomType type, LocalDate begin, LocalDate end, 
			ArrayList<String> services) {
		this.ID = ID;
		this.guest = guest;
		this.status = status;
		this.type = type;
		this.begin = begin;
		this.end = end;
		this.services = services;
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
		for(String i:services) {
			str += i + " ";
		}
		return str;
	}
	
	public String toFileString() {
		String save = this.getID() + "," + this.guest.getUsername() + "," + this.status.name() + "," + this.type.name() + "," + 
				this.begin.toString() + "," + this.end.toString();
		for(String s:this.services) {
			save += "," + s;
		}
		return save;
	}
}
