package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.Room;
import entity.User;
import enums.ReservationStatus;
import enums.Extras;
import enums.RoomType;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;

public class ReservationManager {
	public ArrayList<Request> requests;
	public ArrayList<Reservation> reservations;
	
	public ReservationManager() {
		this.requests = new ArrayList<Request>();
		this.reservations = new ArrayList<Reservation>();
	}
	
	public void createRequest(String ID, User guest, RoomType type, LocalDate begin, LocalDate end, Extras... params) {
		for(Request i:requests) {
			if(i.getID() == ID) {
				throw new DuplicateIDException();
			}
		}
		if(guest instanceof Guest) {
			Request r = new Request(ID, (Guest) guest, ReservationStatus.NA_CEKANJU, type, begin, end, params);
			requests.add(r);
		}else{
			throw new RuntimeException();
		}
	}
	
	public Request readRequest(String ID) {
		Request r = null;
		for(Request i:requests) {
			if(i.getID() == ID) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new NonexistentEntityException();
		}
		return r;
	}
	
	public void deleteRequest(String ID)  {
		Request r = readRequest(ID);
		requests.remove(r);
	}
	
	public void createReservation(Request r, Room room, double price) {
		Reservation res = new Reservation(r.guest, room, r.begin, r.end, price, r.extras);
		res.setID();
		reservations.add(res);
	}
	
	public Reservation readReservation(String ID) {
		Reservation r = null;
		for(Reservation i:reservations) {
			if(i.getID() == ID) {
				r = i;
				break;
			}
		}
		if(r == null) {
			throw new NonexistentEntityException();
		}
		return r;

	}
	
	public void updateReservation(String ID, Guest guest, Room room, LocalDate begin, LocalDate end, double price, Extras... params)  {
		Reservation r = readReservation(ID);
		r.guest = guest;
		r.room = room;
		r.begin = begin;
		r.end = end;
		r.price = price;
		r.extras = params;
	}
	
	public void deleteReservation(String ID) {
		Reservation r = readReservation(ID);
		reservations.remove(r);
	}

}
