package manage;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import entity.Employee;
import entity.Receptioner;
import enums.Degree;
import enums.Gender;

public class ReceptionerManager {
	public ArrayList<Receptioner> receptioners;
	
	public void createReceptioner(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username, 
			String password, Degree degree, LocalDate employmentDate, double salary)
	{
		Receptioner a = new Receptioner(name, lastName, gender, birthDate, phoneNumber, username, password, degree, employmentDate, salary);
		receptioners.add(a);
	}
	
	public Receptioner readReceptioner(Receptioner e) throws Exception
	{
		boolean exists = false;
		for(Receptioner i:receptioners) {
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

	public void updateReceptioner(Receptioner e, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, 
			String username, String password, Degree degree, LocalDate employmentDate, double salary) throws Exception
	{
		boolean exists = false;
		for(Receptioner i:receptioners) {
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

	
	public void deleteReceptioner(Receptioner a)
	{
		try {
			receptioners.remove(a);
		}catch(Exception ex){
			System.out.println("Dati objekat ne postoji u sistemu");
		}
	}
}