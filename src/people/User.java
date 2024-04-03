package people;

import java.time.LocalDate;
import enums.Gender;

public abstract class User {
	protected String name;
	protected String lastName;
	protected Gender gender;
	protected LocalDate birthDate;
	protected String phoneNumber;
	protected String username;
	protected String password;
	
	public User(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username,
			String password) {
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
	}
}