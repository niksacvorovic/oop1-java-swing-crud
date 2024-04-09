package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import entity.Employee;
import entity.Janitor;
import enums.Degree;
import enums.Gender;

public class JanitorManager {
	public ArrayList <Janitor> janitors;
	
	public void createJanitor(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username, 
			String password, Degree degree, LocalDate employmentDate, double salary)
	{
		Janitor a = new Janitor(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
		janitors.add(a);
	}
	
	public Janitor readJanitor(Janitor e) throws Exception
	{
		boolean exists = false;
		for(Janitor i:janitors) {
			if (e.equals(i)){
				e = i;
				exists = true;
				break;
			}
		}
		if (!exists) {
			throw new Exception("Dati objekat ne postoji u sistemu");
		}
		return e;
	}

	public void updateJanitor(Janitor e, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
			String username, String password, Degree degree, LocalDate employmentDate, double salary) throws Exception
	{
		boolean exists = false;
		for(Janitor i:janitors) {
			if (e.equals(i)){
				e = i;
				exists = true;
				break;
			}
		}
		if (!exists) {
			throw new Exception("Dati objekat ne postoji u sistemu");
		}
		e.setName(name);
		e.setLastName(lastName);
		e.setGender(gender);
		e.setBirthDate(birthDate);
		e.setPhoneNumber(phoneNumber);
		e.setUsername(username);
		e.setPassword(password);
		e.setDegree(degree);
		e.setEmploymentDate(employmentDate);
		e.setBaseSalary(salary);
	}

	public void deleteJanitor(Janitor a)
	{
		try {
			janitors.remove(a);
		}catch(Exception ex) {
			System.out.println("Dati objekat ne postoji u sistemu");
		}
	}
}
