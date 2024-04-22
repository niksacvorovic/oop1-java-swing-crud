package entity;

import java.time.LocalDate;

import enums.Gender;

public class Guest extends User {

	public Guest(String username, String password, String name, String lastName, Gender gender, LocalDate birthDate, String phoneNumber) {
		super(username, password, name, lastName, gender, birthDate, phoneNumber);
	}
	
	@Override
	public String toString() {
		String str = "Korisničko ime: " + this.username + "\nLozinka: " + this.password + "\nIme: " + this.name + "\nPrezime: " +
				this.lastName + "\nPol: " + this.gender.toString() + "\nDatum rođenja: " + this.birthDate.toString() + "\nBroj telefona: " + 
				this.phoneNumber;
		return str;
	}

}
