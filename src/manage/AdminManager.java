package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import entity.Employee;
import enums.Degree;
import enums.Gender;
import interfaces.EmployeeCRUD;

public class AdminManager implements EmployeeCRUD{
	public ArrayList<Administrator> admins;
	
	@Override
	public void createEmployee(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username, 
			String password, Degree degree, LocalDate employmentDate, double salary)
	{
		Administrator a = new Administrator(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
		admins.add(a);
	}
	
	@Override
	public void readEmployee(Employee e)
	{
		Administrator a = null;
		boolean exists = false;
		if(e instanceof Administrator){
			a = (Administrator) e;
		}
		for(Administrator i:admins) {
			if (a.equals(i)){
				a = i;
				exists = true;
				break;
			}
		}
		if (!exists) {
			//?
		}
		
	}
	
	@Override
	public void updateEmployee(Employee e, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
			String username, String password, Degree degree, LocalDate employmentDate, double salary)
	{
		Administrator a = null;
		boolean exists = false;
		if(e instanceof Administrator){
			a = (Administrator) e;
		}
		for(Administrator i:admins) {
			if (a.equals(i)){
				a = i;
				exists = true;
				break;
			}
		}
		if (!exists) {
			//?
		}
		a.setName(name);
		a.setLastName(lastName);
		a.setGender(gender);
		a.setBirthDate(birthDate);
		a.setPhoneNumber(phoneNumber);
		a.setUsername(username);
		a.setPassword(password);
		a.setDegree(degree);
		a.setEmploymentDate(employmentDate);
		a.setBaseSalary(salary);
	}
	
	@Override
	public void deleteEmployee(Employee e) 
	{
		Administrator a = null;
		if(e instanceof Administrator) {
			a = (Administrator) e;
		}
		admins.remove(a);
	}

}
