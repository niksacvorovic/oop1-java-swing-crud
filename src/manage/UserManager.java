package manage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.Administrator;
import entity.Cleaner;
import entity.Employee;
import entity.Guest;
import entity.Receptioner;
import entity.User;
import enums.Degree;
import enums.Gender;
import enums.Role;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;

public class UserManager {
	public ArrayList<User> users;
	
	public UserManager() {
		this.users = new ArrayList<User>();
	}
	
	public void saveData() {
		String sep = System.getProperty("file.separator");
		ArrayList<String> buffer = new ArrayList<String>();
		for(User i:users) {
			if(i instanceof Guest) {
				buffer.add(i.getUsername() + "," + i.getPassword() + "," + i.getName() + "," + i.getLastName() + "," + i.getGender().name()
						+ "," + i.getBirthDate().toString() + "," + i.getPhoneNumber());
			}else{
				Employee j = (Employee) i;
				buffer.add(j.getUsername() + "," + j.getPassword() + "," + j.getName() + "," + j.getLastName() + "," + j.getGender().name()
						+ "," + j.getBirthDate().toString() + "," + j.getPhoneNumber() + "," + j.getRole().name() + "," +
						j.getDegree().name() + "," + j.getEmploymentDate().toString() + "," + Double.toString(j.getBaseSalary()));
			}
		}
		try {
			Files.write(Paths.get("." + sep + "data" + sep + "users.csv"), buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createEmployee(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, 
			String phoneNumber, Role role, Degree degree, LocalDate employmentDate, double salary) 
	{
		for(User i:users) {
			if (i.getUsername().equals(username)) {
				throw new DuplicateIDException();
			}
		}
		Employee a = null;
		switch(role) {
		case ADMINISTRATOR:
			a = new Administrator(username, password, name, lastName, gender, birthDate, phoneNumber, role, degree, employmentDate, salary);
			break;
		case RECEPCIONAR:
			a = new Receptioner(username, password, name, lastName, gender, birthDate, phoneNumber, role, degree, employmentDate, salary);
			break;
		case HIGIJENICAR:
			a = new Cleaner(username, password, name, lastName, gender, birthDate, phoneNumber, role, degree, employmentDate, salary);
			break;
		}
		users.add(a);
	}
	
	public void createGuest(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, 
			String phoneNumber) 
	{
		for(User i:users) {
			if (i.getUsername() == username) {
				throw new DuplicateIDException();
			}
		}
		Guest a = new Guest(username, password, name, lastName, gender, birthDate, phoneNumber);
		users.add(a);
	}
	
	public User readUser(String username) 
	{
		User ret = null;
		for(User i:users) {
			if (username.equals(i.getUsername())){
				ret = i;
				break;
			}
		}
		if (ret == null) {
			throw new NonexistentEntityException();
		}
		return ret;
	}

	public void updateEmployee(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, 
			String phoneNumber, Degree degree, LocalDate employmentDate, double salary) 
	{
		Employee e = null;
		User a = readUser(username);
		if (a instanceof Employee) {
			e = (Employee) a;
		}else{
			throw new RuntimeException();
		}
		e.setName(name);
		e.setLastName(lastName);
		e.setGender(gender);
		e.setBirthDate(birthDate);
		e.setPhoneNumber(phoneNumber);
		e.setPassword(password);
		e.setDegree(degree);
		e.setEmploymentDate(employmentDate);
		e.setBaseSalary(salary);
	}

	public void updateGuest(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, 
			String phoneNumber) 
	{
		Guest e = null;
		User a = readUser(username);
		if (a instanceof Guest) {
			e = (Guest) a;
		}else{
			throw new RuntimeException();
		}
		e.setName(name);
		e.setLastName(lastName);
		e.setGender(gender);
		e.setBirthDate(birthDate);
		e.setPhoneNumber(phoneNumber);
		e.setPassword(password);
	}
	
	public void deleteUser(String username) 
	{
		User e = readUser(username);
		users.remove(e);
	}
}
