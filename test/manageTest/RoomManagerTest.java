package manageTest;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Room;
import enums.RoomStatus;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;
import manage.RoomManager;

public class RoomManagerTest{
	public static RoomManager test = new RoomManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		test.roomTypes.add("Jednokrevetna");
		test.roomTypes.add("Dvokrevetna");
		test.createRoom("101", "Jednokrevetna");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testReadRoom() {
		Room r = test.readRoom("101");
		assertNotNull(r);
	}	

	@Test(expected = NonexistentEntityException.class)
	public void testReadRoomException() {
		Room r = test.readRoom("102");
	}
	
	@Test
	public void testCreateRoom() {
		test.createRoom("102", "Jednokrevetna");
		Room r = test.readRoom("102");
		assertNotNull(r);
		assertEquals("102", r.getRoomNumber());
		assertEquals(RoomStatus.SLOBODNA, r.status);
		assertEquals("Jednokrevetna", r.type);
	}
	
	@Test(expected = DuplicateIDException.class)
	public void testCreateRoomException() {
		test.createRoom("101", "Dvokrevetna");
	}
	
	@Test
	public void testUpdateRoom() {
		test.updateRoom("101", "Dvokrevetna");
		Room r = test.readRoom("101");
		assertEquals("101", r.getRoomNumber());
		assertEquals("Dvokrevetna", r.type);
	}
	
	@Test
	public void testDeleteRoom() {
		Room r = test.readRoom("101");
		test.deleteRoom("101");
		assertTrue(test.rooms.contains(r) == false);
	}
}
