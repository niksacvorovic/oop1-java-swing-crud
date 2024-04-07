package manage;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	public void deleteReceptioner(Receptioner a)
	{
		receptioners.remove(a);
	}
}