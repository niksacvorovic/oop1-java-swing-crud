package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Request;
import entity.Reservation;
import entity.Room;
import enums.ReservationStatus;
import enums.Extras;
import enums.RoomType;

public class ReservationManager {
	public ArrayList<Request> requests;
	public ArrayList<Reservation> reservations;
	
	public void createRequest(String ID, String username, RoomType type, LocalDate begin, LocalDate end, Extras... params) throws Exception{
		for(Request i:requests) {
			if(i.getID() == ID) {
				throw new Exception();
			}
		}
		Request r = new Request(ID, username, ReservationStatus.NA_CEKANJU, type, begin, end, params);
		requests.add(r);
	}
	
	public Request readRequest(String ID) throws Exception{
		Request r = null;
		for(Request i:requests) {
			if(i.getID() == ID) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new Exception();
		}
		return r;
	}
	
	public void deleteRequest(String ID) throws Exception {
		Request r = readRequest(ID);
		try {
			requests.add(r);
		}catch (Exception ex) {
			System.out.println("Dati objekat ne postoji u sistemu");
		}
	}
	
	public void createReservation(Request r, Room room, double price) throws Exception{
		Reservation res = new Reservation(r.username, room, r.begin, r.end, price, r.extras);
		res.setID();
		reservations.add(res);
	}
	
	public Reservation readReservation(String ID) throws Exception{
		Reservation r = null;
		for(Reservation i:reservations) {
			if(i.getID() == ID) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new Exception();
		}
		return r;

	}
	
	public void updateReservation(String ID, String username, Room room, LocalDate begin, LocalDate end, double price, Extras... params) throws Exception {
		Reservation r = readReservation(ID);
		r.username = username;
		r.room = room;
		r.begin = begin;
		r.end = end;
		r.price = price;
		r.extras = params;
	}
	
	public void deleteReservation(String ID) throws Exception{
		Reservation r = readReservation(ID);
		reservations.remove(r);
	}

}
