package entity;

import enums.RoomStatus;
import enums.RoomType;

public class Room {
	private String roomNumber;
	public RoomType type;
	public RoomStatus status;
	
	public Room(String roomNumber, RoomType type, RoomStatus status) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.status = status;
	}

	public String getRoomNumber() {
		return roomNumber;
	}
}
