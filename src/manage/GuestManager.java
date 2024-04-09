package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import entity.Employee;
import entity.Guest;
import enums.Degree;
import enums.Gender;

public class GuestManager {
public ArrayList<Guest> guests;
	
public void createGuest(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username, 
		String password)
{
	Guest a = new Guest(name, lastName, gender, birthDate, phoneNumber, username, password);
	guests.add(a);
}

public Guest readGuest(Guest e) throws Exception
{
	boolean exists = false;
	for(Guest i:guests) {
		if (e.equals(i)){
			e = i;
			exists = true;
			break;
		}
	}
	if (!exists) {
		throw new Exception("Dati objekat ne postoji u sistemu");
	}
	return e;
}

public void updateGuest(Guest e, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
		String username, String password) throws Exception
{
	boolean exists = false;
	for(Guest i:guests) {
		if (e.equals(i)){
			e = i;
			exists = true;
			break;
		}
	}
	if (!exists) {
		throw new Exception("Dati objekat ne postoji u sistemu");
	}
	e.setName(name);
	e.setLastName(lastName);
	e.setGender(gender);
	e.setBirthDate(birthDate);
	e.setPhoneNumber(phoneNumber);
	e.setUsername(username);
	e.setPassword(password);
}

public void deleteEmployee(Guest e) 
{
	try {
		guests.remove(e);
	}
	catch(Exception ex){
		System.out.println("Dati objekat ne postoji u sistemu");
	}
}

}
