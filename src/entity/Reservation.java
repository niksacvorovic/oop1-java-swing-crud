package entity; 

import java.time.LocalDate;

import enums.Extras;

public class Reservation{
	private String ID;
	public String username;
	public Room room;
	public LocalDate begin;
	public LocalDate end;
	public double price;
	public Extras[] extras;
	
	public Reservation(String username, Room room, LocalDate begin, LocalDate end, double price, Extras... params) {
		this.room = room;
		this.begin = begin;
		this.end = end;
		this.price = price;
		this.extras = params;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID() {
		this.ID = room.getRoomNumber() + "-" + begin.toString() + "-" + end.toString();
	}
}
