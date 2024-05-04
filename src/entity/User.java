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
	
	public User(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
		this.phoneNumber = phoneNumber;
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

	//setter za username ne postoji - to je read only svojstvo
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toFileString() {
		return this.getUsername() + "," + this.getPassword() + "," + this.getName() + "," + this.getLastName() + "," + this.getGender().name()
				+ "," + this.getBirthDate().toString() + "," + this.getPhoneNumber();
	}
	
	public Object[] toCell() {
		Object data[] = {this.getUsername(), this.getPassword(), this.getName(), this.getLastName(), this.getGender(), this.getBirthDate(),
		        this.getPhoneNumber()};
		return data;
	}
}