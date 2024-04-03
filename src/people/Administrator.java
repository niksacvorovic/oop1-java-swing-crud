package people;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;

public class Administrator extends Employee {

	public Administrator(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			String username, String password, Degree degree, LocalDate employmentDate, double salary) {
		super(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
	}
	
}
