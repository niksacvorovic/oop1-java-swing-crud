package entity; 

import java.time.LocalDate;
import java.util.ArrayList;

public class Reservation{
	private String ID;
	public Guest guest;
	public Room room;
	public LocalDate begin;
	public LocalDate end;
	public double price;
	public ArrayList<String> services;
	
	public Reservation(String ID, Guest guest, Room room, LocalDate begin, LocalDate end, double price, ArrayList<String> services) {
		this.ID = ID;
		this.guest = guest;
		this.room = room;
		this.begin = begin;
		this.end = end;
		this.price = price;
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
		String str = "ID: " + this.ID + "\nGost: " + this.guest.getUsername() + "\nSoba: " + this.room.getRoomNumber() +
				"\nPočetak: " + this.begin.toString() + "\nKraj: " + this.end.toString() + "\nCena: " + this.price + "\nDodatne usluge: ";
		for(String i:services) {
			str += i + " ";
		}
		return str;
	}
}
