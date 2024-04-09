package interfaces;

import java.time.LocalDate;

import entity.Employee;
import enums.Degree;
import enums.Gender;

public interface EmployeeCRUD {
	void createEmployee(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username, 
			String password, Degree degree, LocalDate employmentDate, double salary);
	Employee readEmployee(Employee e);
	void updateEmployee(Employee e, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
			String username, String password, Degree degree, LocalDate employmentDate, double salary);
	void deleteEmployee(Employee e);
}