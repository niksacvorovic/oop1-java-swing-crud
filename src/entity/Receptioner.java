package entity;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;
import enums.Role;

public class Receptioner extends Employee {

	public Receptioner(String username, String password, String name, String lastName, Gender gender,
			LocalDate birthDate, String phoneNumber, Role role, Degree degree, LocalDate employmentDate,
			double baseSalary) {
		super(username, password, name, lastName, gender, birthDate, phoneNumber, role, degree, employmentDate, baseSalary);
	}
	
}
