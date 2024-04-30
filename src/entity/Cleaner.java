package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Degree;
import enums.Gender;
import enums.Role;
import enums.RoomStatus;

public class Cleaner extends Employee {
	public ArrayList<Room> rooms;

	public Cleaner(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate,
			String phoneNumber, Role role, Degree degree, LocalDate employmentDate, double baseSalary) {
		super(username, password, name, lastName, gender, birthDate, phoneNumber, role, degree, employmentDate, baseSalary);
		this.rooms = new ArrayList<Room>();
	}
	
	public void clean(Room r) {
		if(this.rooms.contains(r)) {
			r.status = RoomStatus.SLOBODNA;
			rooms.remove(r);
		}
	}
}
