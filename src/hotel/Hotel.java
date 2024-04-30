package hotel;

import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Administrator;
import entity.Cleaner;
import entity.Guest;
import entity.Pricing;
import entity.Receptioner;
import entity.Request;
import entity.Reservation;
import entity.Room;
import entity.User;
import enums.Degree;
import enums.Gender;
import enums.ReservationStatus;
import enums.Role;
import enums.RoomStatus;
import enums.RoomType;
import manage.PricingManager;
import manage.ReservationManager;
import manage.RoomManager;
import manage.UserManager;

public class Hotel {
	public PricingManager pm;
	public ReservationManager rem;
	public RoomManager rom;
	public UserManager um;
	
	private static Hotel instance = null;
	
	private Hotel() {
		this.pm = new PricingManager();
		this.rem = new ReservationManager();
		this.rom = new RoomManager();
		this.um = new UserManager();
		loadData();
	}
	
	public static Hotel getInstance() {
		if(instance == null) {
			instance = new Hotel();
		}
		return instance;
	}
	
	public void loadData() {
		String sep = System.getProperty("file.separator");
		try {
			List<String> userData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "users.csv"));
			for(String i:userData) {
				String data[] = i.split(",");
				User e = null;
				if (data.length == 7) {
					e = new Guest(data[0], data[1], data[2], data[3], Gender.valueOf(data[4]), LocalDate.parse(data[5]), data[6]);
				}
				else {
					Role role = Role.valueOf(data[7]);
					switch (role) {
					case ADMINISTRATOR:
						e = new Administrator(data[0], data[1], data[2], data[3], Gender.valueOf(data[4]), 
								LocalDate.parse(data[5]), data[6], role, Degree.valueOf(data[8]), LocalDate.parse(data[9]), 
								Double.parseDouble(data[10]));
						break;
					case RECEPCIONAR:
						e = new Receptioner(data[0], data[1], data[2], data[3], Gender.valueOf(data[4]), 
								LocalDate.parse(data[5]), data[6], role, Degree.valueOf(data[8]), LocalDate.parse(data[9]), 
								Double.parseDouble(data[10]));
						break;
					case HIGIJENICAR:
						e = new Cleaner(data[0], data[1], data[2], data[3], Gender.valueOf(data[4]), 
								LocalDate.parse(data[5]), data[6], role, Degree.valueOf(data[8]), LocalDate.parse(data[9]), 
								Double.parseDouble(data[10]));
						break;
					}
				}
				um.users.add(e);
			}
			List<String> roomData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "rooms.csv"));
			for(String i:roomData) {
				String data[] = i.split(",");
				Room r = new Room(data[0], RoomType.valueOf(data[1]), RoomStatus.valueOf(data[2]));
				rom.rooms.add(r);
			}
			List<String> requestData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "requests.csv"));
			for(String i:requestData) {
				String data[] = i.split(",");
				Request r = new Request(data[0], (Guest) um.readUser(data[1]), ReservationStatus.valueOf(data[2]), RoomType.valueOf(data[3]), 
						LocalDate.parse(data[4]), LocalDate.parse(data[5]), new ArrayList<String>());
				for(int j = 0; j + 6 < data.length; j++) {
					r.services.add(data[j + 6]);
				}
				rem.requests.add(r);
			}
			List<String> reservationData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "reservations.csv"));
			for(String i:reservationData) {
				String data[] = i.split(",");
				Reservation r = new Reservation(data[0], (Guest) um.readUser(data[1]), rom.readRoom(data[2]), LocalDate.parse(data[3]),
						LocalDate.parse(data[4]), Double.parseDouble(data[5]), new ArrayList<String>());
				for(int j = 0; j + 6 < data.length; j++) {
					r.services.add(data[j + 6]);
				}
				rem.reservations.add(r);
			}
			List<String> pricingData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "pricings.csv"));
			for(String i:pricingData) {
				String data[] = i.split(",");
				Pricing p = new Pricing(data[0], LocalDate.parse(data[1]), LocalDate.parse(data[2]), new HashMap<String, Double>());
				for(int j = 0; j + 3 < data.length; j++) {
					String item[] = data[j + 3].split("-");
					p.servicePrices.put(item[0], Double.parseDouble(item[1]));
				}
				pm.pricings.add(p);
			}
			new FileOutputStream(new File("." + sep + "data" + sep + "users.csv")).close();
			new FileOutputStream(new File("." + sep + "data" + sep + "rooms.csv")).close();
			new FileOutputStream(new File("." + sep + "data" + sep + "requests.csv")).close();
			new FileOutputStream(new File("." + sep + "data" + sep + "reservations.csv")).close();
			new FileOutputStream(new File("." + sep + "data" + sep + "pricings.csv")).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveData() {
		um.saveData();
		rom.saveData();
		rem.saveData();
		pm.saveData();
	}
	public ArrayList<Room> validateRequest(Request r) {
		ArrayList<Room> available = new ArrayList<Room>();
		Room check = null;
		LocalDate begin = null;
		LocalDate end = null;
		for(Room i:rom.rooms) {
			if (i.type == r.type) {
				available.add(i);
			}
		}
		for(Reservation i:rem.reservations) {
			check = i.room;
			if(available.contains(check)) {
				begin = i.begin;
				end = i.end;
				if(begin.compareTo(r.end) < 0 || end.compareTo(r.begin) > 0) {
					available.remove(check);
				}
			}
		}
		if (available.isEmpty()) {
			r.status = ReservationStatus.ODBIJENA;
		}else{
			r.status = ReservationStatus.POTVDJENA;
		}
		return available;
	}
	
	public double applyPricing(Request r) {
		double price = 0;
		Pricing current = null;
		LocalDate date = r.begin;
		while(r.end.compareTo(date) >= 0) {
			for(Pricing p:pm.pricings) {
				if(date.compareTo(p.startDate) > 0 && date.compareTo(p.endDate) <= 0) {
					current = p;
					break;
				}
			}
			price += current.servicePrices.get(r.type.toString());
			for(String i:r.services) {
				price += current.servicePrices.get(i);
			}
		}
		return price;
	}
	
	public void checkIn(Request r, Room room)  {
		double price = applyPricing(r);
		ArrayList<Room> available = validateRequest(r); 
		if(r.status == ReservationStatus.POTVDJENA && available.contains(room) && room.status == RoomStatus.SLOBODNA) {
			rem.createReservation(r, room, price);
		}
	}
	
	public ArrayList<RoomType> showAvailable(LocalDate begin, LocalDate end) {
		ArrayList<RoomType> available = new ArrayList<RoomType>();
		LocalDate loop = begin;
		boolean check = true;
		while(end.compareTo(loop) >= 0) {
			for(Room r:rom.rooms) {
				if(available.contains(r.type)) {
					continue;
				}
				check = true;
				for(Reservation i:r.reservations) {
					if(i.end.compareTo(loop) > 0 && i.begin.compareTo(loop) < 0) {
						check = false;
						break;
					}
				}
				if(check == true) {
					available.add(r.type);
				}
			}
			loop = loop.plusDays(1);
		}
		return available;
	}
	
	public ArrayList<Object> showGuestInputs(String username){
		ArrayList<Object> ret = new ArrayList<Object>();
		Guest guest = (Guest) um.readUser(username);
		for(Request i:rem.requests) {
			if (i.guest.equals(guest)) {
				ret.add(i);
			}
		}
		for(Reservation i:rem.reservations) {
			if (i.guest.equals(guest)) {
				ret.add(i);
			}
		}
		return ret;
	}
}
