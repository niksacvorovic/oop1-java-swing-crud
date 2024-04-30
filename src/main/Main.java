package main;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Degree;
import enums.Gender;
import enums.Role;
import enums.RoomType;
import hotel.Hotel;
import manage.PricingManager;
import manage.ReservationManager;

public class Main {

	public static void main(String[] args) {
		Hotel hotel = Hotel.getInstance();
		ReservationManager.setRequestID("0");
		ReservationManager.setReservationID("0");
		PricingManager.setPricingID("0");
		hotel.um.createEmployee("peraperic@gmail.com", "jasamadmin", "Pera", "Peric", Gender.MUSKI, LocalDate.parse("1990-06-02"),
				"060123456", Role.ADMINISTRATOR, Degree.OSNOVNE_AKADEMSKE, LocalDate.parse("2012-04-01"), 100000);
		hotel.um.createEmployee("mikamikic@gmail.com", "recepcija1", "Mika", "Mikic", Gender.MUSKI, LocalDate.parse("2000-12-05"),
				"063369258", Role.RECEPCIONAR, Degree.OSNOVNE_STRUKOVNE, LocalDate.parse("2022-02-15"), 60000);
		hotel.um.createEmployee("nikolanikolic@gmail.com", "recepcija2", "Nikola", "Nikolic", Gender.MUSKI, LocalDate.parse("2000-10-10"),
				"062024680", Role.RECEPCIONAR, Degree.OSNOVNE_STRUKOVNE, LocalDate.parse("2012-02-15"), 60000);
		hotel.um.createEmployee("janajanic@gmail.com", "recepcija2", "Jana", "Janic", Gender.MUSKI, LocalDate.parse("1970-07-21"),
				"065987654", Role.HIGIJENICAR, Degree.CETVOROGODISNJA_SREDNJA, LocalDate.parse("2012-02-15"), 45000);
		hotel.um.createGuest("milicamilic@gmail.com", "gost1", "Milica", "Milic", Gender.ZENSKI, LocalDate.parse("2002-07-30"), "065353637");
		hotel.um.createGuest("anaanic@gmail.com", "gost2", "Ana", "Anic", Gender.ZENSKI, LocalDate.parse("2003-06-22"), "0612364954");
		hotel.rom.createRoom("101", RoomType.DVOKREVETNA_BRACNI);
		hotel.rom.createRoom("102", RoomType.DVOKREVETNA);
		hotel.rom.createRoom("103", RoomType.TROKREVETNA_BRACNI);
		hotel.rom.createRoom("104", RoomType.JEDNOKREVETNA);
		System.out.println("Pre izmene sobe:");
		System.out.println(hotel.rom.readRoom("102").toString());
		hotel.rom.updateRoom("102", RoomType.TROKREVETNA_BRACNI, hotel.rom.readRoom("102").status);
		System.out.println("Posle izmene sobe:");
		System.out.println(hotel.rom.readRoom("102").toString());
		System.out.println();
		hotel.pm.services.add("Doručak");
		hotel.pm.services.add("Ručak");
		hotel.pm.services.add("Večera");
		hotel.pm.services.add("Bazen");
		hotel.pm.services.add("Spa centar");
		double[] prices = {25, 35, 40, 45, 50, 6, 10, 8, 12, 15};
		hotel.pm.createPricing(LocalDate.parse("2024-01-01"), LocalDate.parse("2024-12-13"), prices);
		System.out.println("Pre izmene cenovnika:");
		System.out.println(hotel.pm.readPricing("0").toString());
		hotel.pm.updateOnePrice("0", "Doručak", 30.0);
		System.out.println("Posle izmene cenovnika:");
		System.out.println(hotel.pm.readPricing("0").toString());
		System.out.println();
		System.out.println("Prikaz dostupnih soba:");
		ArrayList<RoomType> a = hotel.showAvailable(LocalDate.parse("2024-08-01"), LocalDate.parse("2024-08-31"));
		for(RoomType i:a) {System.out.println(i.toString());}
		ArrayList<String> services = new ArrayList<String>();
		services.add("Doručak");
		services.add("Večera");
		hotel.rem.createRequest(hotel.um.readUser("milicamilic@gmail.com"), RoomType.TROKREVETNA_BRACNI, 
				LocalDate.parse("2024-08-13"), LocalDate.parse("2024-08-23"), services);
		hotel.showAvailable(LocalDate.parse("2024-06-01"), LocalDate.parse("2024-06-30"));
		hotel.rem.createRequest(hotel.um.readUser("anaanic@gmail.com"), RoomType.DVOKREVETNA, LocalDate.parse("2024-06-06"),
				LocalDate.parse("2024-06-12"), services);
		System.out.println();
		System.out.println("Prikaz unosa jednog gosta: ");
		ArrayList <Object> b = hotel.showGuestInputs("milicamilic@gmail.com");
		for(Object i:b) {System.out.println(i.toString());}
		hotel.saveData();
		}
}
