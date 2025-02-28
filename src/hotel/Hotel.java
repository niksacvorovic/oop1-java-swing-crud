package hotel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import entity.RoomFeature;
import entity.RoomFeatureLink;
import entity.User;
import enums.Degree;
import enums.Gender;
import enums.Status;
import enums.Role;
import enums.RoomStatus;
import manage.PricingManager;
import manage.ReservationManager;
import manage.RoomFeatureManager;
import manage.RoomManager;
import manage.UserManager;

public class Hotel {
	public PricingManager pm;
	public ReservationManager rem;
	public RoomManager rom;
	public UserManager um;
	public RoomFeatureManager rfm;
	
	private static Hotel instance = null;
	
	private Hotel() {
		this.pm = new PricingManager();
		this.rem = new ReservationManager();
		this.rom = new RoomManager();
		this.um = new UserManager();
		this.rfm = new RoomFeatureManager();
	}
	
	public static Hotel getInstance() {
		if(instance == null) {
			instance = new Hotel();
		}
		return instance;
	}
	
	public void loadData() {
		String sep = System.getProperty("file.separator");
		LocalDate today = LocalDate.now();
		try {
			//učitavanje korisnika
			List<String> userData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "users.csv"));
			for(String i:userData) {
				String data[] = i.split(",");
				User e = null;
				if (data.length == 7) {
					e = new Guest(data[0], data[1], data[2], data[3], Gender.valueOf(data[4]), LocalDate.parse(data[5]), data[6]);
					um.guests.add((Guest) e);
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
					r = new Room(data[0], data[1], RoomStatus.valueOf(data[2]), null);
				}else{
					r = new Room(data[0], data[1], RoomStatus.valueOf(data[2]), (Cleaner) um.readUser(data[3]));
					r.cleaner.rooms.add(r);
				}
				rom.rooms.add(r);
			}
			//učitavanje osobina sobe
			List<String> roomFeatureData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "roomfeatures.csv"));
			for(String i:roomFeatureData) {
				String data[] = i.split(",");
				RoomFeature r = new RoomFeature(data[0], Double.parseDouble(data[1]));
				rfm.roomFeatures.add(r);
			}
			//učitavanje veza soba - osobina
			List<String> roomFeatureLinkData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "roomfeaturelinks.csv"));
			for(String i:roomFeatureLinkData) {
				String data[] = i.split(",");
				RoomFeatureLink r = new RoomFeatureLink(rom.readRoom(data[0]), rfm.readRoomFeature(data[1]));
				rfm.roomFeatureLinks.add(r);
			}
			//učitavanje zahteva
			List<String> requestData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "requests.csv"));
			for(String i:requestData) {
				String data[] = i.split(",");
				Request r = new Request(data[0], (Guest) um.readUser(data[1]), Status.valueOf(data[2]), data[3], LocalDate.parse(data[4]),
						LocalDate.parse(data[5]), LocalDate.parse(data[6]), Double.parseDouble(data[7]), new ArrayList<String>());
				for(int j = 0; j + 8 < data.length; j++) {
					r.services.add(data[j + 8]);
				}
				if(r.begin.compareTo(today) < 0 && r.status == Status.POTVRDJENA) {
					r.status = Status.OTKAZANA;
				}
				r.guest.userInputs.add(r);
				rem.requests.add(r);
			}
			//učitavanje rezervacija
			List<String> reservationData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "reservations.csv"));
			for(String i:reservationData) {
				String data[] = i.split(",");
				Reservation r = new Reservation(data[0], (Guest) um.readUser(data[1]), rom.readRoom(data[2]), LocalDate.parse(data[3]),
						LocalDate.parse(data[4]), LocalDate.parse(data[5]), Status.valueOf(data[6]) ,Double.parseDouble(data[7]), 
						new ArrayList<String>());
				for(int j = 0; j + 8 < data.length; j++) {
					r.services.add(data[j + 8]);
				}
				r.guest.userInputs.add(r);
				r.room.reservations.add(r);
				rem.reservations.add(r);
			}
			//učitavanje cenovnika
			List<String> pricingData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "pricings.csv"));
			for(String i:pricingData) {
				String data[] = i.split(",");
				Pricing p = new Pricing(data[0], LocalDate.parse(data[1]), LocalDate.parse(data[2]), new LinkedHashMap<String, Double>());
				for(int j = 0; j + 3 < data.length; j++) {
					String item[] = data[j + 3].split("-");
					p.servicePrices.put(item[0], Double.parseDouble(item[1]));
				}
				pm.pricings.add(p);
			}
			//učitavanje ostalog 
			List<String> miscData = Files.readAllLines(Paths.get("." + sep + "data" + sep + "misc.csv"));
			if(miscData.size() == 3){
				for(String i:miscData.get(0).split(",")) {
					pm.services.add(i);
				}
				for(String i:miscData.get(1).split(",")) {
					rom.roomTypes.add(i);
					pm.roomTypes.add(i);
				}
				String ids[] = miscData.get(2).split(",");
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
		rfm.saveData();
		saveMisc();
	}
	
	public void saveMisc() {
		String sep = System.getProperty("file.separator");
		ArrayList<String> buffer = new ArrayList<String>();
		String services = "";
		for(String i:pm.services) {
			services += i + ",";
		}
		String roomTypes = "";
		for(String i:rom.roomTypes) {
			roomTypes += i + ",";
		}
		if(!(services.isBlank()) && !(roomTypes.isBlank())) {
			services = services.substring(0, services.length() - 1);
			roomTypes = roomTypes.substring(0, roomTypes.length() - 1);
			buffer.add(services);
			buffer.add(roomTypes);
			buffer.add(ReservationManager.getRequestID() + "," + ReservationManager.getReservationID() + "," + PricingManager.getPricingID());
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "misc.csv"), buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Room> validateRequest(Request r) {
		ArrayList<Room> available = new ArrayList<Room>();
		LocalDate begin = null;
		LocalDate end = null;
		boolean hasAllFeatures = false;
		ArrayList<RoomFeature> roomFeatures = new ArrayList<RoomFeature>();
		ArrayList<RoomFeature> chosenFeatures = new ArrayList<RoomFeature>();
		RoomFeature feat = null;
		for(Room i:rom.rooms) {
			if (i.type.equals(r.type)) {
				roomFeatures.clear();
				chosenFeatures.clear();
				for(RoomFeatureLink j:rfm.roomFeatureLinks) {
					if(j.room.equals(i)) {
						roomFeatures.add(j.feature);
					}
				}
				for(String j:r.services) {
					if(!pm.services.contains(j)) {
						feat = rfm.readRoomFeature(j);
						chosenFeatures.add(feat);
					}
				}
				if(roomFeatures.containsAll(chosenFeatures) && roomFeatures.size() == chosenFeatures.size()) {
					available.add(i);
				}
			}
		}
		int counter = 0;
		if(r.status == Status.POTVRDJENA) {
			counter = -1;
		}
		for(Room i:available) {
			for (Reservation e:i.reservations) {
				begin = e.begin;
				end = e.end;
				if(begin.compareTo(r.end) <= 0 && end.compareTo(r.begin) >= 0) {
					counter++;
				}
			}
		}
		for(Request i:rem.requests) {
			if(i.status == Status.POTVRDJENA && i.type.equals(r.type) && i.end.compareTo(r.begin) >= 0 && i.begin.compareTo(r.end) <= 0) {
				counter++;
			}
		}
		
		if (available.isEmpty() || counter == available.size()) {
			r.status = Status.ODBIJENA;
		}else{
			r.status = Status.POTVRDJENA;
		}
		return available;
	}
	
	public boolean validateReservationChanges(Reservation r, Room newroom, LocalDate newbegin, LocalDate newend, ArrayList<String> features) {
		Reservation copy = new Reservation(r);
		LocalDate begin = null;
		LocalDate end = null;
		ArrayList<RoomFeature> newroomFeatures = new ArrayList<RoomFeature>();
		ArrayList<RoomFeature> chosenFeatures = new ArrayList<RoomFeature>();
		copy.room = newroom;
		copy.begin = newbegin;
		copy.end = newend;
		for(RoomFeatureLink i:rfm.roomFeatureLinks) {
			if(i.room.equals(newroom)) {
				newroomFeatures.add(i.feature);
			}
		}
		for(String i:features) {
			chosenFeatures.add(rfm.readRoomFeature(i));
		}
		if(!newroomFeatures.containsAll(chosenFeatures)) {
			return false;
		}
		for(Reservation e:newroom.reservations) {
			if(e != r) {
				begin = e.begin;
				end = e.end;
				if(begin.compareTo(r.end) <= 0 && end.compareTo(r.begin) >= 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public double applyPricing(Request r) {
		double price = 0;
		Pricing current = null;
		LocalDate date = r.begin;
		while(r.end.compareTo(date) > 0) {
			for(Pricing p:pm.pricings) {
				if(date.compareTo(p.startDate) > 0 && date.compareTo(p.endDate) <= 0) {
					current = p;
					break;
				}
			}
			price += current.servicePrices.get(r.type.toString());
			for(String i:r.services) {
				if(pm.services.contains(i)) {
					price += current.servicePrices.get(i);
				}else {
					price += rfm.readRoomFeature(i).price;
				}
			}
			date = date.plusDays(1);
		}
		r.price = price;
		return price;
	}

	public double applyPricing(Reservation r) {
		double price = 0;
		Pricing current = null;
		LocalDate date = r.begin;
		while(r.end.compareTo(date) > 0) {
			for(Pricing p:pm.pricings) {
				if(date.compareTo(p.startDate) > 0 && date.compareTo(p.endDate) <= 0) {
					current = p;
					break;
				}
			}
			price += current.servicePrices.get(r.room.type.toString());
			for(String i:r.services) {
				if(pm.services.contains(i)) {
					price += current.servicePrices.get(i);
				}else {
					price += rfm.readRoomFeature(i).price;
				}
			}
			date = date.plusDays(1);
		}
		r.price = price;
		return price;
	}
	
	public void checkIn(Request r, Room room)  {
		ArrayList<Room> available = availableRooms(r.type, r.begin, r.end, r.services); 
		if(available.contains(room)) {
			room.status = RoomStatus.ZAUZETA;
			rem.createReservation(r, room);
			rem.requests.remove(r);
		}
	}
	
	public void checkOut(Reservation r) {
		r.status = Status.ZAVRSENA;
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
	
	public ArrayList<String> showAvailable(LocalDate begin, LocalDate end, ArrayList<RoomFeature> features) {
		ArrayList<String> available = new ArrayList<String>();
		ArrayList<RoomFeature> roomFeatures = new ArrayList<RoomFeature>();
		LocalDate loop = begin;
		boolean check = true;
		while(end.compareTo(loop) >= 0) {
			for(Room r:rom.rooms) {
				if(available.contains(r.type)) {
					continue;
				}
				roomFeatures.clear();
				for(RoomFeatureLink i:rfm.roomFeatureLinks) {
					if(i.room.equals(r)) {
						roomFeatures.add(i.feature);
					}
				}
				check = true;
				for(Reservation i:r.reservations) {
					if(i.status != Status.POTVRDJENA && i.end.compareTo(loop) > 0 && i.begin.compareTo(loop) < 0) {
						check = false;
						break;
					}
				}
				
				if(check == true && roomFeatures.containsAll(features) && roomFeatures.size() == features.size()) {
					available.add(r.type);
				}
			}
			loop = loop.plusDays(1);
		}
		return available;
	}
	
	public ArrayList<Room> availableRooms(String type, LocalDate begin, LocalDate end, ArrayList<String> services){
		ArrayList<Room> rooms = new ArrayList<Room>();
		ArrayList<RoomFeature> chosenFeatures = new ArrayList<RoomFeature>();
		ArrayList<RoomFeature> roomFeatures = new ArrayList<RoomFeature>();
		for(String i:services) {
			if(!pm.services.contains(i)) {
				chosenFeatures.add(rfm.readRoomFeature(i));
			}
		}
		boolean add = true;
		for(Room i:rom.rooms) {
			if(i.type.equals(type)) {
				add = true;
				roomFeatures.clear();
				for(RoomFeatureLink link:rfm.roomFeatureLinks) {
					if(link.room.equals(i)) {
						roomFeatures.add(link.feature);
					}
				}
				if(!(roomFeatures.containsAll(chosenFeatures) && roomFeatures.size() == chosenFeatures.size())) {
					add = false;
				}
				for(Reservation res:i.reservations) {
					if(res.status != Status.OTKAZANA && begin.compareTo(res.end) <= 0 && end.compareTo(res.begin) >= 0) {
						add = false;
					}
				}
				if(add) {
					rooms.add(i);
				}
			}
		}
		return rooms;
	}
	
	public ArrayList<Object> showGuestInputs(String username){
		Guest guest = (Guest) um.readUser(username);
		return guest.userInputs;
	}
}
