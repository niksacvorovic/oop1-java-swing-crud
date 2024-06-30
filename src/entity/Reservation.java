package entity; 

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Status;

public class Reservation{
	private String ID;
	public Guest guest;
	public Room room;
	public LocalDate creationDate;
	public LocalDate begin;
	public LocalDate end;
	public Status status;
	public double price;
	public ArrayList<String> services;
	
	public Reservation(String ID, Guest guest, Room room, LocalDate creationDate, LocalDate begin, LocalDate end, Status status, double price, ArrayList<String> services) {
		this.ID = ID;
		this.guest = guest;
		this.room = room;
		this.creationDate = creationDate;
		this.begin = begin;
		this.end = end;
		this.status = status;
		this.price = price;
		this.services = services;
	}
	
	public Reservation(Reservation r) {
		this.ID = r.getID();
		this.guest = r.guest;
		this.room = r.room;
		this.creationDate = r.creationDate;
		this.begin = r.begin;
		this.end = r.end;
		this.status = r.status;
		this.price = r.price;
		this.services = r.services;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	@Override
	public String toString() {
		String str = "ID: " + this.ID + "\nGost: " + this.guest.getUsername() + "\nSoba: " + this.room.getRoomNumber() +
				"\nPočetak: " + this.begin.toString() + "\nKraj: " + this.end.toString() + "\nCena: " + this.price + "\nDodatne usluge: ";
		for(String i:services) {
			str += i + " ";
		}
		return str;
	}
	
	public String toFileString() {
		String save = this.getID() + "," + this.guest.getUsername() + "," + this.room.getRoomNumber() + "," + this.creationDate.toString() + ","
	+ this.begin.toString() + "," + this.end.toString() + "," + this.status.name() + "," + Double.toString(this.price);
		for(String s:this.services) {
			save += "," + s;
		}
		return save;
	}
	
	public Object[] toCell() {
		StringBuilder sb = new StringBuilder("");
		for(String i:this.services) {
			sb.append(i + ",");
		}
		String services = sb.substring(0, sb.length() - 1);
		Object data[] = {this.getID(), this.guest.getUsername(), this.status.toString(), this.room.getRoomNumber(), this.begin, this.end, this.price, services};
		return data;
	}
}
