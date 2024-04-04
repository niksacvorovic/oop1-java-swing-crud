package people;

import java.time.LocalDate;
import enums.Degree;
import enums.Gender;

public class Administrator extends Employee {

	public Administrator(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			String username, String password, Degree degree, LocalDate employmentDate, double salary) {
		super(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
	}
	
	public Employee createEmployee(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			String username, String password, Degree degree, LocalDate employmentDate, double salary)
	{
		Employee newguy = new Employee(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
		return newguy;
	}
}
