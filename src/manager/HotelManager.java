package manager;

import java.util.ArrayList;
import people.Employee;
import people.Administrator;
import people.Janitor;
import people.Receptioner;
import people.Guest;
import other.Room;
import other.Reservation;
import other.Pricing;


public class HotelManager {
	public ArrayList<Administrator> admins;
	public ArrayList<Janitor> janitors;
	public ArrayList<Receptioner> receptioners;
	public ArrayList<Guest> guests;
	public ArrayList<Room> rooms;
	public ArrayList<Reservation> reservations;
	public ArrayList<Pricing> pricings;
	
	
	private HotelManager(ArrayList<Administrator> admins, ArrayList<Janitor> janitors,
			ArrayList<Receptioner> receptioners, ArrayList<Guest> guests, ArrayList<Room> rooms,
			ArrayList<Reservation> reservations, ArrayList<Pricing> pricings) {
		super();
		this.admins = admins;
		this.janitors = janitors;
		this.receptioners = receptioners;
		this.guests = guests;
		this.rooms = rooms;
		this.reservations = reservations;
		this.pricings = pricings;
	}

	public void addReceptioner(Employee newguy)
	{
		Receptioner newReceptioner = (Receptioner) newguy;
		receptioners.add(newReceptioner);
	}
	
	public void addJanitor(Employee newguy)
	{
		Janitor newJanitor = (Janitor) newguy;
		janitors.add(newJanitor);
	}
	
	public void addGuest(Guest newGuest)
	{
		guests.add(newGuest);
	}
}