package entity;

import java.time.LocalDate;

import enums.Extras;
import enums.ReservationStatus;
import enums.RoomType;

public class Request {
	private String ID;
	public String username;
	public ReservationStatus status;
	public RoomType type;
	public LocalDate begin;
	public LocalDate end;
	public Extras[] extras;
	
	public Request(String ID, String username, ReservationStatus status, RoomType type, LocalDate begin, LocalDate end, Extras... params) {
		this.ID = ID;
		this.username = username;
		this.status = status;
		this.type = type;
		this.begin = begin;
		this.end = end;
		this.extras = params;
	}

	public String getID() {
		return ID;
	}
}
