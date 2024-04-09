package entity;

import java.time.LocalDate;

import enums.Gender;

public class Guest extends User {

	public Guest(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber) {
		super(username, password, name, lastName, gender, birthDate, phoneNumber);
	}
}
