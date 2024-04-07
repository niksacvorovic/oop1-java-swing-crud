package entity;

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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}