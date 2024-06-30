package hotelTest;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Cleaner;
import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.Room;
import entity.RoomFeature;
import enums.Degree;
import enums.Gender;
import enums.Role;
import enums.RoomStatus;
import enums.Status;
import hotel.Hotel;

public class HotelTest {
	public static Hotel test = Hotel.getInstance();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		test.pm.setPricingID("0");
		test.rem.setRequestID("1");
		test.rem.setReservationID("1");
		test.um.createEmployee("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456",
				Role.ADMINISTRATOR, Degree.DOKTORSKE, LocalDate.parse("2022-06-27"), 100000);
		test.um.createGuest("lenka", "lenka", "Lenka", "Nikolic", Gender.ZENSKI, LocalDate.parse("2004-05-29"), "060123123");
		test.um.createEmployee("niksa2", "niksa2", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456",
				Role.HIGIJENICAR, Degree.DOKTORSKE, LocalDate.parse("2022-06-27"), 100000);
		test.rfm.createRoomFeature("Klima", 15);
		test.rfm.createRoomFeature("Pušačka soba", 10);
        test.pm.roomTypes.add("Jednokrevetna");
        test.pm.roomTypes.add("Dvokrevetna");
        test.pm.roomTypes.add("Trokrevetna");
        test.pm.services.add("Dorucak");
        test.pm.services.add("Vecera");
        test.rom.roomTypes.add("Jednokrevetna");
        test.rom.roomTypes.add("Dvokrevetna");
        test.rom.roomTypes.add("Trokrevetna");
        test.rom.createRoom("101", "Jednokrevetna");
        test.rom.createRoom("102", "Dvokrevetna");
        test.rom.createRoom("103", "Jednokrevetna");
        test.rom.createRoom("104", "Jednokrevetna");
        test.rom.createRoom("105", "Jednokrevetna");
        test.rom.createRoom("106", "Dvokrevetna");
        test.rom.createRoom("107", "Trokrevetna");
        test.pm.createPricing(LocalDate.parse("2024-06-01"), LocalDate.parse("2024-09-01"), 20, 30, 40, 8, 10);
        test.pm.createPricing(LocalDate.parse("2024-04-01"), LocalDate.parse("2024-05-01"), 20, 30, 40, 8, 10);
        Guest g = (Guest) test.um.readUser("lenka");
        Room r1 = test.rom.readRoom("101");
        Room r2 = test.rom.readRoom("105");
        Room r3 = test.rom.readRoom("103");
        Room r4 = test.rom.readRoom("106");
        RoomFeature rf = test.rfm.readRoomFeature("Klima");
        test.rfm.addFeatureLink(r1, rf);
        test.rfm.addFeatureLink(r2, rf);
        test.rfm.addFeatureLink(r3, rf);
        test.rfm.addFeatureLink(r4, rf);
        ArrayList<String> services = new ArrayList();
        services.add("Dorucak");
        services.add("Klima");
        test.rem.createRequest(g, "Jednokrevetna", LocalDate.parse("2024-07-01"), LocalDate.parse("2024-07-05"), services);
        test.rem.createRequest(g, "Trokrevetna", LocalDate.parse("2024-07-02"), LocalDate.parse("2024-07-04"), services);
        test.rem.createRequest(g, "Dvokrevetna", LocalDate.parse("2024-07-20"), LocalDate.parse("2024-07-25"), services);
        test.rem.createRequest(g, "Jednokrevetna", LocalDate.parse("2024-07-20"), LocalDate.parse("2024-07-25"), services);
        test.rem.createReservation(test.rem.readRequest("4"), r2);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testValidateRequest() {
		Request r1 = test.rem.readRequest("1");
		test.validateRequest(r1);
		Request r2 = test.rem.readRequest("2");
		test.validateRequest(r2);
		assertEquals(r1.status, Status.POTVRDJENA);
		assertEquals(r2.status, Status.ODBIJENA);
	}
	
	@Test
	public void testValidateReservationChanges() {
		Reservation r = test.rem.readReservation("1");
		ArrayList<String> features = new ArrayList<String>();
		features.add("Klima");
		boolean first = test.validateReservationChanges(r, test.rom.readRoom("103"), r.begin, r.end, features);
		boolean second = test.validateReservationChanges(r, test.rom.readRoom("104"), r.begin, r.end, features);
		assertTrue(first);
		assertTrue(second == false);
	}
	
	@Test
	public void testApplyPricing() {
		Reservation r = test.rem.readReservation("1");
		test.applyPricing(r);
		assertTrue(r.price == 215);
	}
	
	@Test
	public void testCheckIn() {
		Request r = test.rem.readRequest("3");
		test.validateRequest(r);
		Room room = test.rom.readRoom("106");
		test.checkIn(r, test.rom.readRoom("106"));
		assertTrue(room.status == RoomStatus.ZAUZETA);
		assertNotNull(test.rem.readReservation("2"));
		assertTrue(test.rem.requests.contains(r) == false);
	}

	@Test
	public void testCheckOut() {
		Reservation r = test.rem.readReservation("1");
		test.checkOut(r);
		Cleaner c = (Cleaner) test.um.readUser("niksa2");
		assertTrue(r.status == Status.ZAVRSENA);
		assertTrue(r.room.status == RoomStatus.SPREMANJE);
		assertTrue(c.rooms.contains(r.room));
	}
}
