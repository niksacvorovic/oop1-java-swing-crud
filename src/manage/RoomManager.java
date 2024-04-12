package manage;

import java.util.ArrayList;

import entity.Room;
import enums.RoomStatus;
import enums.RoomType;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;

public class RoomManager {
	public ArrayList<Room> rooms;
	
	public RoomManager() {
		this.rooms = new ArrayList<Room>();
	}
	
	public void createRoom(String roomNumber, RoomType type) throws Exception {
		for(Room i:rooms) {
			if(i.getRoomNumber() == roomNumber) {
				throw new DuplicateIDException();
			}
		}
		Room r = new Room(roomNumber, type, RoomStatus.SLOBODNA);
		rooms.add(r);
	}
	
	public Room readRoom(String roomNumber) throws Exception{
		Room r = null;
		for(Room i:rooms) {
			if(i.getRoomNumber() == roomNumber) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new NonexistentEntityException();
		}
		return r;
	}
	
	public void updateRoom(String roomNumber, RoomType type, RoomStatus status) throws Exception {
		Room r = readRoom(roomNumber);
		r.type = type;
		r.status = status;
	}
	
	public void deleteRoom(String roomNumber) throws Exception {
		Room r = readRoom(roomNumber);
		rooms.remove(r);
	}
}
