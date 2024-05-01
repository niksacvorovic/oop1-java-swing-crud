package hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.Administrator;
import entity.Cleaner;
import entity.Employee;
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
			//učitavanje korisnika
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
					um.employees.add((Employee) e);
				}
				um.users.add(e);
			}
			//učitavanje soba
			List<String> roomData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "rooms.csv"));
			for(String i:roomData) {
				String data[] = i.split(",");
				Room r = null;
				if(data.length == 3) {
					r = new Room(data[0], RoomType.valueOf(data[1]), RoomStatus.valueOf(data[2]), null);
				}else{
					r = new Room(data[0], RoomType.valueOf(data[1]), RoomStatus.valueOf(data[2]), (Cleaner) um.readUser(data[3]));
					r.cleaner.rooms.add(r);
				}
				rom.rooms.add(r);
			}
			//učitavanje zahteva
			List<String> requestData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "requests.csv"));
			for(String i:requestData) {
				String data[] = i.split(",");
				Request r = new Request(data[0], (Guest) um.readUser(data[1]), ReservationStatus.valueOf(data[2]), RoomType.valueOf(data[3]), 
						LocalDate.parse(data[4]), LocalDate.parse(data[5]), new ArrayList<String>());
				for(int j = 0; j + 6 < data.length; j++) {
					r.services.add(data[j + 6]);
				}
				r.guest.userInputs.add(r);
				rem.requests.add(r);
			}
			//učitavanje rezervacija
			List<String> reservationData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "reservations.csv"));
			for(String i:reservationData) {
				String data[] = i.split(",");
				Reservation r = new Reservation(data[0], (Guest) um.readUser(data[1]), rom.readRoom(data[2]), LocalDate.parse(data[3]),
						LocalDate.parse(data[4]), Double.parseDouble(data[5]), new ArrayList<String>());
				for(int j = 0; j + 6 < data.length; j++) {
					r.services.add(data[j + 6]);
				}
				r.guest.userInputs.add(r);
				r.room.reservations.add(r);
				rem.reservations.add(r);
			}
			//učitavanje cenovnika
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
			//učitavanje ostalog 
			List<String> miscData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "misc.csv"));
			if(miscData.size() == 2){
				for(String i:miscData.get(0).split(",")) {
					pm.services.add(i);
				}
				String ids[] = miscData.get(1).split(",");
				ReservationManager.setRequestID(ids[0]);
				ReservationManager.setReservationID(ids[1]);
				PricingManager.setPricingID(ids[2]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveData() {
		um.saveData();
		rom.saveData();
		rem.saveData();
		pm.saveData();
		saveMisc();
	}
	
	public void saveMisc() {
		String sep = System.getProperty("file.separator");
		ArrayList<String> buffer = new ArrayList<String>();
		String services = "";
		for(String i:pm.services) {
			services += i + ",";
		}
		services = services.substring(0, services.length() - 1);
		buffer.add(services);
		buffer.add(ReservationManager.getRequestID() + "," + ReservationManager.getReservationID() + "," + PricingManager.getPricingID());
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "misc.csv"), buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			room.status = RoomStatus.ZAUZETA;
			rem.createReservation(r, room, price);
			rem.requests.remove(r);
		}
	}
	
	public void checkOut(Reservation r) {
		r.room.status = RoomStatus.SPREMANJE;
		Cleaner next = null;
		Cleaner newcleaner = null;
		int min = 1000;
		for(Employee i:um.employees) {
			if (i instanceof Cleaner) {
				next = (Cleaner) i;
				if(next.rooms.size() == 0) {
					newcleaner = next;
					break;
				}
				if(next.rooms.size() < min) {
					newcleaner = next;
					min = next.rooms.size();
				}
			}
		}
		r.room.cleaner = newcleaner;
		newcleaner.rooms.add(r.room);
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
		Guest guest = (Guest) um.readUser(username);
		return guest.userInputs;
	}
}
