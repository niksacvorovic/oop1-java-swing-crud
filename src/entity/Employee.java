package entity;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;

public abstract class Employee extends User {
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

	public Degree getDegree() {
		return degree;
	}

	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	public LocalDate getEmploymentDate() {
		return employmentDate;
	}

	public void setEmploymentDate(LocalDate employmentDate) {
		this.employmentDate = employmentDate;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}
}
