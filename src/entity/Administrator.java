package entity;

import java.time.LocalDate;
import enums.Degree;
import enums.Gender;

public class Administrator extends Employee {

	public Administrator(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			String username, String password, Degree degree, LocalDate employmentDate, double salary) {
		super(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
	}
	
	public boolean equals(Administrator a) {
		if(this.name.equals(a.name) && this.lastName.equals(a.lastName) && this.gender == a.gender && this.birthDate.equals(a.birthDate) &&
				this.phoneNumber.equals(a.phoneNumber) && this.username.equals(a.username) && this.degree == a.degree 
				&& this.employmentDate.equals(a.employmentDate) && this.baseSalary == a.baseSalary)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
}
