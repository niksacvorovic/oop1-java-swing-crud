package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Degree;
import enums.Gender;
import enums.Role;

public class Cleaner extends Employee {

	public Cleaner(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate,
			String phoneNumber, Role role, Degree degree, LocalDate employmentDate, double baseSalary) {
		super(username, password, name, lastName, gender, birthDate, phoneNumber, role, degree, employmentDate, baseSalary);
		ArrayList<Room> rooms = new ArrayList<Room>();
	}
	
}
