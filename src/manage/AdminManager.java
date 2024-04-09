package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import enums.Degree;
import enums.Gender;

public class AdminManager{
	public ArrayList<Administrator> admins;

	public void createAdmin(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username, 
			String password, Degree degree, LocalDate employmentDate, double salary)
	{
		Administrator a = new Administrator(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
		admins.add(a);
	}
	
	public Administrator readAdmin(Administrator e) throws Exception
	{
		boolean exists = false;
		for(Administrator i:admins) {
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

	public void updateAdmin(Administrator e, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
			String username, String password, Degree degree, LocalDate employmentDate, double salary) throws Exception
	{
		boolean exists = false;
		for(Administrator i:admins) {
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

	public void deleteEmployee(Administrator e) 
	{
		try {
			admins.remove(e);
		}
		catch(Exception ex){
			System.out.println("Dati objekat ne postoji u sistemu");
		}
	}

}
