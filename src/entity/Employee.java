package entity;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;
import enums.Role;

public class Employee extends User {
	protected Role role;
	protected Degree degree;
	protected LocalDate employmentDate;
	protected double baseSalary;
	
	public Employee(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber,
			Role role, Degree degree, LocalDate employmentDate, double baseSalary) {
		super(username, password, name, lastName, gender, birthDate, phoneNumber);
		this.role = role;
		this.degree = degree;
		this.employmentDate = employmentDate;
		this.baseSalary = baseSalary;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
