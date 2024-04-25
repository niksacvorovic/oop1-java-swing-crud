package hotel;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Guest;
import entity.Pricing;
import entity.Request;
import entity.Reservation;
import entity.Room;
import enums.ReservationStatus;
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
	}
	
	public static Hotel getInstance() {
		if(instance == null) {
			instance = new Hotel();
		}
		return instance;
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
		//ovo može na drugi način kada sobe čuvaju rezervacije
		ArrayList<RoomType> available = new ArrayList<RoomType>();
		LocalDate loop = begin;
		boolean check = true;
		while(end.compareTo(loop) >= 0) {
			for(Room r:rom.rooms) {
				if(available.contains(r.type)) {
					continue;
				}
				check = true;
				for(Reservation i:rem.reservations) {
					if (!(r.equals(i.room)) || i.end.compareTo(begin) <= 0) {
						continue;
					}
					else if(i.end.compareTo(loop) > 0 && i.begin.compareTo(loop) < 0) {
						check = false;
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
