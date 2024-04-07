package manage;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	public void deleteJanitor(Janitor a)
	{
		janitors.remove(a);
	}
}
