package manage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.Room;
import enums.ReservationStatus;
import exceptions.NonexistentEntityException;

public class ReservationManager {
	public ArrayList<Request> requests;
	public ArrayList<Reservation> reservations;
	private static Integer requestID;
	private static Integer reservationID;
	
	public ReservationManager() {
		this.requests = new ArrayList<Request>();
		this.reservations = new ArrayList<Reservation>();
	}
	
	public static String getRequestID() {
		return requestID.toString();
	}
	
	public static String getReservationID() {
		return reservationID.toString();
	}
	
	public static void setRequestID(String s) {
		ReservationManager.requestID = Integer.parseInt(s);
	}
	
	public static void setReservationID(String s) {
		ReservationManager.reservationID = Integer.parseInt(s);
	}
	
	public void saveData() {
		ArrayList<String> reqbuffer = new ArrayList<String>();
		ArrayList<String> resbuffer = new ArrayList<String>();
		String sep = System.getProperty("file.separator");
		for(Request i:requests) {
			reqbuffer.add(i.toFileString());
		}
		for(Reservation i: reservations) {
			
			resbuffer.add(i.toFileString());
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "requests.csv"), reqbuffer);
			Files.write(Paths.get("." + sep + "data" + sep + "reservations.csv"), resbuffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createRequest(Guest guest, String type, LocalDate begin, LocalDate end, ArrayList<String> services) {
		if (end.compareTo(begin) < 0) {
			throw new RuntimeException();
		}
		String ID = requestID.toString();
		requestID ++;
		Request r = new Request(ID, guest, ReservationStatus.NA_CEKANJU, type, begin, end, services);
		guest.userInputs.add(r);
		requests.add(r);
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
		String ID = reservationID.toString();
		reservationID ++;
		Reservation res = new Reservation(ID, r.guest, room, r.begin, r.end, price, r.services);
		r.guest.userInputs.remove(r);
		r.guest.userInputs.add(res);
		reservations.add(res);
		room.reservations.add(res);
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
	
	public void updateReservation(String ID, Guest guest, Room room, LocalDate begin, LocalDate end, double price, 
			ArrayList<String> services)  {
		Reservation r = readReservation(ID);
		r.guest = guest;
		r.room = room;
		r.begin = begin;
		r.end = end;
		r.price = price;
		r.services = services;
	}
	
	public void deleteReservation(String ID) {
		Reservation r = readReservation(ID);
		reservations.remove(r);
	}

}
