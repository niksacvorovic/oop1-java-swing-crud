package manage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import entity.Room;
import enums.RoomStatus;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;

public class RoomManager {
	public ArrayList<Room> rooms;
	public ArrayList<String> roomTypes;
	
	public RoomManager() {
		this.rooms = new ArrayList<Room>();
		this.roomTypes = new ArrayList<String>();
	}
	
	public void saveData() {
		String sep = System.getProperty("file.separator");
		ArrayList<String> buffer = new ArrayList<String>();
		for(Room r:rooms) {
			buffer.add(r.toFileString());
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "rooms.csv"), buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createRoom(String roomNumber, String type)  {
		for(Room i:rooms) {
			if(i.getRoomNumber().equals(roomNumber)) {
				throw new DuplicateIDException();
			}
		}
		if(roomTypes.contains(type)) {
			Room r = new Room(roomNumber, type, RoomStatus.SLOBODNA, null);
			rooms.add(r);
		}else {
			throw new DuplicateIDException();
		}
	}
		
	
	public Room readRoom(String roomNumber) {
		Room r = null;
		for(Room i:rooms) {
			if(i.getRoomNumber().equals(roomNumber)) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new NonexistentEntityException();
		}
		return r;
	}
	
	public void updateRoom(String roomNumber, String type, RoomStatus status)  {
		Room r = readRoom(roomNumber);
		r.type = type;
		r.status = status;
	}
	
	public void updateRoom(String roomNumber, String type)  {
		Room r = readRoom(roomNumber);
		r.type = type;
	}
	
	public void deleteRoom(String roomNumber)  {
		Room r = readRoom(roomNumber);
		rooms.remove(r);
	}
}
