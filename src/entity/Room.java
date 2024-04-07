package entity;

import enums.RoomStatus;
import enums.RoomType;

public class Room {
	public RoomType type;
	public RoomStatus status;
	
	public Room(RoomType type, RoomStatus status) {
		this.type = type;
		this.status = status;
	}
}
