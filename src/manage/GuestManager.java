package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Guest;
import enums.Gender;

public class GuestManager {
public ArrayList<Guest> guests;
	
	public void createAdmin(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
			String username, String password)
	{
		Guest a = new Guest(name, lastName, gender, birthDate, phoneNumber, username, password);
		guests.add(a);
	}
	
	public void deleteAdmin(Guest a) 
	{
		guests.remove(a);
	}
}
