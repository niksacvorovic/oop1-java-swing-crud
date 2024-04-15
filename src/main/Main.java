package main;

import java.time.LocalDate;

import enums.Degree;
import enums.Extras;
import enums.Gender;
import enums.Role;
import enums.RoomType;
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
		hotel.um.createGuest("milicamilic@gmail.com", "gost1", "Milica", "Milic", Gender.ZENSKI, LocalDate.parse("2002-07-30"), "065353637");
		hotel.um.createGuest("ananic@gmail.com", "gost2", "Ana", "Anic", Gender.ZENSKI, LocalDate.parse("2003-06-22"), "0612364954");
		hotel.rom.createRoom("101", RoomType.DVOKREVETNA_BRACNI);
		hotel.rom.createRoom("102", RoomType.DVOKREVETNA);
		hotel.rom.createRoom("103", RoomType.TROKREVETNA_BRACNI);
		hotel.rom.createRoom("104", RoomType.JEDNOKREVETNA);
		hotel.rom.updateRoom("102", RoomType.TROKREVETNA_BRACNI, hotel.rom.readRoom("102").status);
		/* Dodatne usluge su implementirane kao enumeracije, zato što ne sadrže nikakav dodatni podatak osim svog
		   imena. Ovime postižemo jednostavniju implmentaciju (npr. na formi se sve dodatne usluge mogu dodati checkboxom)
		   Mana ovog pristupa je što se dodatne usluge ne mogu dinamički dodati u sistem, već je potrebno urediti fajlove.
		 */
		double[] prices = {25, 35, 40, 45, 50, 6, 10, 8, 12, 15, 10, 8};
		hotel.pm.createPricing(prices, LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-13"));
		hotel.pm.updateOnePrice("2024-01-01-2024-12-13", Extras.DORUCAK, 30.0);
		hotel.showAvailable(LocalDate.parse("2024-08-01"), LocalDate.parse("2024-08-31"));
		hotel.rem.createRequest("0001", "milicamilic@gmail.com", RoomType.TROKREVETNA_BRACNI, LocalDate.parse("2024-08-13"),
				LocalDate.parse("2024-08-23"), Extras.DORUCAK, Extras.VECERA);
		hotel.showAvailable(LocalDate.parse("2024-06-01"), LocalDate.parse("2024-06-30"));
		hotel.rem.createRequest("0002", "anaanic@gmail.com", RoomType.DVOKREVETNA, LocalDate.parse("2024-06-06"),
				LocalDate.parse("2024-06-12"), Extras.DORUCAK, Extras.VECERA);
		hotel.showGuestInputs("milicamilic@gmail.com");
		}catch(Exception ex) {
			System.out.println("Došlo je do greške");
		};
	}
}
