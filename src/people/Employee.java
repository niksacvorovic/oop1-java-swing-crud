package people;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;

public class Employee extends User {
	protected Degree degree;
	protected LocalDate employmentDate;
	protected double baseSalary;
	
	public Employee(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			String username, String password, Degree degree, LocalDate employmentDate, double baseSalary) {
		super(name, lastName, gender, birthDate, phoneNumber, username, password);
		this.degree = degree;
		this.employmentDate = employmentDate;
		this.baseSalary = baseSalary;
	}
}
