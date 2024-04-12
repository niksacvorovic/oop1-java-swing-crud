package hotel;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Pricing;
import entity.Request;
import entity.Reservation;
import entity.Room;
import enums.Extras;
import enums.ReservationStatus;
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
	
	public ArrayList<String> validateRequest(Request r) {
		ArrayList<String> available = new ArrayList<String>();
		String check = null;
		String ID = null;
		LocalDate begin = null;
		LocalDate end = null;
		for(Room i:rom.rooms) {
			if (i.type == r.type) {
				available.add(i.getRoomNumber());
			}
		}
		for(Reservation i:rem.reservations) {
			ID = i.getID();
			check = ID.substring(0, 3);
			if(available.contains(check)) {
				begin = LocalDate.parse(ID.substring(4, 14));
				end = LocalDate.parse(ID.substring(15));
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
	
	public double applyPricing(Request r, Pricing p) {
		double price = 0;
		price += p.roomPrices.get(r.type);
		for(Extras i:r.extras) {
			price += p.extrasPrices.get(i);
		}
		return price;
	}
	
	public void checkIn(Request r, Room room) throws Exception {
		LocalDate today = LocalDate.now();
		Pricing p = null;
		for(Pricing i:pm.pricings) {
			if (today.compareTo(i.startDate) >= 0 && today.compareTo(i.endDate) <= 0) {
				p = i;
			}
		}
		double price = applyPricing(r, p);
		ArrayList<String> available = validateRequest(r); 
		if(r.status == ReservationStatus.POTVDJENA && available.contains(room.getRoomNumber())) {
			rem.createReservation(r, room, price);
		}
	}
}
