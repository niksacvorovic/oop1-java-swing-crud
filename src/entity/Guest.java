package entity;

import java.time.LocalDate;

import enums.Gender;

public class Guest extends User {

	public Guest(String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber, String username,
			String password) {
		super(name, lastName, gender, birthDate, phoneNumber, username, password);
	}
}
