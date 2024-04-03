package people;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;

public class Janitor extends Employee {

	public Janitor(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			String username, String password, Degree degree, LocalDate employmentDate, double salary) {
		super(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
	}

}
