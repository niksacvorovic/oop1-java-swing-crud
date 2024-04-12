package main;

import java.time.LocalDate;

import enums.Degree;
import enums.Gender;
import enums.Role;
import hotel.Hotel;

public class Main {

	public static void main(String[] args) {
		Hotel hotel = Hotel.getInstance();
		try {
		hotel.um.createEmployee("peraperic@gmail.com", "jasamadmin", "Pera", "Peric", Gender.MUSKI, LocalDate.parse("1990-06-02"),
				"060123456", Role.ADMINISTRATOR, Degree.OSNOVNE_AKADEMSKE, LocalDate.parse("2012-04-01"), 100000);
		hotel.um.createEmployee("mikamikic@gmail.com", "recepcija1", "Mika", "Mikic", Gender.MUSKI, LocalDate.parse("2000-12-05"),
				"063369258", Role.RECEPCIONAR, Degree.OSNOVNE_STRUKOVNE, LocalDate.parse("2022-02-15"), 60000);
		hotel.um.createEmployee("nikolanikolic@gmail.com", "recepcija2", "Nikola", "Nikolic", Gender.MUSKI, LocalDate.parse("2000-10-10"),
				"062024680", Role.RECEPCIONAR, Degree.OSNOVNE_STRUKOVNE, LocalDate.parse("2012-02-15"), 60000);
		hotel.um.createEmployee("janajanic@gmail.com", "recepcija2", "Jana", "Janic", Gender.MUSKI, LocalDate.parse("1970-07-21"),
				"065987654", Role.HIGIJENICAR, Degree.CETVOROGODISNJA_SREDNJA, LocalDate.parse("2012-02-15"), 45000);
		}catch(Exception ex) {
			System.out.println("Došlo je do greške");
		}
	}
}
