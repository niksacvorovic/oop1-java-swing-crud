package manage;

import java.util.ArrayList;
import enums.RoomType;
import enums.RoomStatus;
import entity.Room;

public class RoomManager {
	public ArrayList<Room> rooms;
	
	public void createRoom(RoomType type, RoomStatus status)
	{
		Room a = new Room(type, status);
		rooms.add(a);
	}
	
	public Room readRoom(Object a)
	{
		
	}
	
	public void deleteRoom(Room a)
	{
		rooms.remove(a);
	}
}
