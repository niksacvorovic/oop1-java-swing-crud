package manageTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Guest;
import entity.Request;
import entity.Reservation;
import entity.Room;
import enums.Gender;
import enums.RoomStatus;
import enums.Status;
import exceptions.NonexistentEntityException;
import manage.ReservationManager;

import java.time.LocalDate;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class ReservationManagerTest {

    public static ReservationManager test = new ReservationManager();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ReservationManager.setRequestID("1");
        ReservationManager.setReservationID("1");
        Guest guest = new Guest("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456");
        Request deleteTest = test.createRequest(guest, "Jednokrevetna", LocalDate.parse("2024-07-01"), 
        		LocalDate.parse("2024-07-05") ,new ArrayList<>());
        Request readTest = test.createRequest(guest, "Dvokrevetna", LocalDate.parse("2024-07-20"), 
        		LocalDate.parse("2024-07-25") ,new ArrayList<>());
        Request toReservation = test.createRequest(guest, "Dvokrevetna", LocalDate.parse("2024-07-20"), 
        		LocalDate.parse("2024-07-25") ,new ArrayList<>());
        Room room = new Room("200", "Dvokrevetna", RoomStatus.SLOBODNA, null);
        Reservation res = test.createReservation(toReservation, room);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testCreateRequest() {
        Guest guest = new Guest("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456");
        ArrayList<String> services = new ArrayList<>();
        services.add("Breakfast");
        Request request = test.createRequest(guest, "Jednokrevetna", LocalDate.parse("2024-07-10"), LocalDate.parse("2024-07-15"), services);
        assertNotNull(request);
        assertEquals("4", request.getID());  
        assertEquals(guest, request.guest);
        assertEquals(Status.NA_CEKANJU, request.status);
        assertEquals("Jednokrevetna", request.type);
        assertEquals(LocalDate.parse("2024-07-10"), request.begin);
        assertEquals(LocalDate.parse("2024-07-15"), request.end);
        assertEquals(services, request.services);
        assertTrue(request.price == 0);
        assertTrue(guest.userInputs.contains(request));
        assertTrue(test.requests.contains(request));
    }

    @Test(expected = RuntimeException.class)
    public void testCreateRequestException() {
        Guest guest = new Guest("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456");
        test.createRequest(guest, "Dvokrevetna", LocalDate.parse("2024-07-05"), LocalDate.parse("2024-07-01"), new ArrayList<>()); 
    }
    
    @Test
    public void testReadRequest() {
    	Request r = test.readRequest("2");
    	assertTrue(r != null);
    }
    
    @Test
    public void testDeleteRequest() {
    	Request r = test.readRequest("1");
    	test.deleteRequest("1");
    	assertTrue(test.requests.contains(r) == false);    	
    }

    @Test
    public void testCreateReservation() {
        Room room = new Room("101", "Jednokrevetna", RoomStatus.SLOBODNA, null);
        Request request = test.readRequest("2");
        Reservation reservation = test.createReservation(request, room);
        assertEquals("2", reservation.getID()); 
        assertEquals(request.guest, reservation.guest);
        assertEquals(room, reservation.room);
        assertEquals(LocalDate.now(), request.creationDate);
        assertEquals(request.begin, reservation.begin);
        assertEquals(request.end, reservation.end);
        assertEquals(Status.POTVRDJENA, reservation.status);
        assertTrue(request.price == reservation.price);
        assertTrue(request.guest.userInputs.contains(reservation));
        assertTrue(room.reservations.contains(reservation));
        assertTrue(request.guest.userInputs.contains(request) == false);
    }

    @Test
    public void testReadReservation() {
    	Reservation r = test.readReservation("2");
    	assertTrue(r != null);
    }
    
    @Test
    public void testUpdateReservation() {
    	Reservation r = test.readReservation("1");
    	test.updateReservation("1", r.guest, r.room, r.creationDate, r.begin, Status.ZAVRSENA, 200, r.services);
    	assertEquals("1", r.getID());
        assertEquals(Status.ZAVRSENA, r.status);
        assertTrue(r.price == 200);
    }

    @Test(expected = NonexistentEntityException.class)
    public void testReadReservationException() {
        test.readReservation("25");
    }
    
    @Test
    public void testDeleteReservation() {
    	Reservation r = test.readReservation("1");
        test.deleteReservation("1");
        assertTrue(test.reservations.contains(r) == false);
    }
}
